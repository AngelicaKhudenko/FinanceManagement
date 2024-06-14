package by.it_academy.jd2.user_service.service.audit.aspect;

import by.it_academy.jd2.user_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.user_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.user_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.user_service.service.feign.IAuditServiceFeignClient;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.controller.token.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class UserAuditAspect {

    private final IAuditServiceFeignClient auditServiceFeignClient;
    private final UserHolder userHolder;
    private final String createText = "Создание пользователем нового пользователя";
    private final String getAllText = "Получение пользователем списка пользователей";
    private final String getByIdText = "Получение пользователем другого пользователя";
    private final String updateText = "Пользователь обновил другого пользователя";

    public UserAuditAspect(IAuditServiceFeignClient auditServiceFeignClient,
                           UserHolder userHolder) {

        this.auditServiceFeignClient = auditServiceFeignClient;
        this.userHolder = userHolder;
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.UserServiceImpl.create(..))", returning = "entity")
    public void afterCreate(JoinPoint joinPoint, UserEntity entity) {

        String className = joinPoint.getSignature().getDeclaringTypeName();

        if (!className.equals("by.it_academy.jd2.user_service.controller.http.UserController")) {
            return;
        }

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,entity.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.UserServiceImpl.get(..))", returning = "page")
    public void afterGetAll(Page<UserEntity> page) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getAllText,userActing, String.valueOf(page.hashCode()));

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.UserServiceImpl.get(..))", returning = "user")
    public void afterGetById(JoinPoint joinPoint, UserEntity user) {

        String className = joinPoint.getSignature().getDeclaringTypeName();

        if (!className.equals("by.it_academy.jd2.user_service.controller.http.UserController")) {
            return;
        }

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getByIdText,userActing,user.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.user_service.service.impl.UserServiceImpl.update(..)) && args(uuid)")
    public void afterUpdate(JoinPoint joinPoint, UUID uuid) {

        String className = joinPoint.getSignature().getDeclaringTypeName();

        if (!className.equals("by.it_academy.jd2.user_service.controller.http.UserController")) {
            return;
        }

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.updateText,userActing,uuid.toString());

        this.auditServiceFeignClient.create(audit);
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
