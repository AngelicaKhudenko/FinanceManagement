package by.it_academy.jd2.user_service.service.audit.aspect;

import by.it_academy.jd2.user_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.user_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.user_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.user_service.service.feign.IAuditServiceFeignClient;
import by.it_academy.jd2.user_service.core.dto.LoginDTO;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.controller.token.UserHolder;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Aspect
@Component
public class CabinetAuditAspect {

    private final IAuditServiceFeignClient auditFeign;
    private final UserHolder userHolder;
    private final IUserService userService;
    private final String createText = "Регистрация пользователя";
    private final String verifyText = "Верификация пользователя";
    private final String loginText = "Вход пользователя в приложение";
    private final String getOnMeText = "Получение пользователем информации о себе";

    public CabinetAuditAspect(IAuditServiceFeignClient auditFeign,
                              UserHolder userHolder, IUserService userService) {

        this.auditFeign = auditFeign;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.CabinetServiceImpl.create(..))", returning = "entity")
    public void afterCreate(UserEntity entity) {

        UserActingDTO userActing = getUserActing(entity);

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,entity.getUuid().toString());

        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.CabinetServiceImpl.verify(..))", returning = "entity")
    public void afterVerify(UserEntity entity) {

        UserActingDTO userActing = getUserActing(entity);

        AuditCUDTO audit = getAuditCUDTO(this.verifyText,userActing,entity.getUuid().toString());

        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.CabinetServiceImpl.login(..)) && args(loginDTO)")
    public void afterLogin(LoginDTO loginDTO) {

        Optional<UserEntity> optional = this.userService.getByMail(loginDTO.getMail());
        if (optional.isEmpty()) {
            throw new IllegalStateException("Ошибка токена");
        }

        UserEntity user = optional.get();

        UserActingDTO userActing = getUserActing(user);

        AuditCUDTO audit = getAuditCUDTO(this.loginText,userActing,user.getUuid().toString());

        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.CabinetServiceImpl.getInfoAboutMe(..))", returning = "user")
    public void afterGetInfo(UserEntity user) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getOnMeText,userActing,user.getUuid().toString());

        this.auditFeign.create(audit);
    }

    private UserActingDTO getUserActing() {

        UserDetailsExpanded userDetailsExpanded = this.userHolder.getUser();

        UserDTO user = userDetailsExpanded.getUser();

        UserActingDTO userActing = UserActingDTO
                .builder()
                .uuid(user.getUuid())
                .mail(user.getMail())
                .fio(user.getFio())
                .role(user.getRole())
                .build();

        return userActing;
    }

    private UserActingDTO getUserActing(UserEntity entity) {

        UserActingDTO userActing = UserActingDTO
                .builder()
                .uuid(entity.getUuid())
                .mail(entity.getMail())
                .fio(entity.getFio())
                .role(entity.getRole())
                .build();

        return userActing;
    }

    private AuditCUDTO getAuditCUDTO(String text, UserActingDTO user, String id) {

        AuditCUDTO auditCUDTO = AuditCUDTO
                .builder()
                .user(user)
                .text(text)
                .type(ETypeEssence.USER)
                .id(id)
                .build();

       return auditCUDTO;
    }
}
