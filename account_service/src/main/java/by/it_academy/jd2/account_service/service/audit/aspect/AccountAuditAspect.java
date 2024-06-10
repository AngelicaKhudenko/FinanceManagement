package by.it_academy.jd2.account_service.service.audit.aspect;

import by.it_academy.jd2.account_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.account_service.controller.token.dto.UserDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.account_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.account_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.account_service.service.audit.feign.IAuditServiceFeignClient;
import by.it_academy.jd2.account_service.service.feign.IUserServiceFeignClient;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AccountAuditAspect {

    private final IAuditServiceFeignClient auditServiceFeignClient;
    private final IUserServiceFeignClient userServiceFeignClient;
    private final String createText = "Создание аккаунта";
    private final String getByIdText = "Получение аккаунта по id";
    private final String getAllText = "Получение списка аккаунтов";
    private final String updateText = "Обновление аккаунта";

    public AccountAuditAspect(IAuditServiceFeignClient auditServiceFeignClient,
                              IUserServiceFeignClient userServiceFeignClient) {

        this.auditServiceFeignClient = auditServiceFeignClient;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @AfterReturning(pointcut ="execution( * by.it_academy.jd2.account_service.service.impl.AccountServiceImpl.create(..))", returning = "account")
    public void afterCreate(AccountEntity account) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,account.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.AccountServiceImpl.get(..))", returning = "account")
    public void afterGetById(AccountEntity account) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getByIdText,userActing,account.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.AccountServiceImpl.get(..))", returning="page")
    public void afterGetAll(Page<AccountEntity> page) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getAllText,userActing, String.valueOf(page.hashCode()));

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.AccountServiceImpl.update(..)) && args(uuid)")
    public void afterUpdate(UUID uuid) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.updateText,userActing,uuid.toString());

        this.auditServiceFeignClient.create(audit);
    }

    private UserActingDTO getUserActing() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        UserDetailsExpanded userDetails = this.userServiceFeignClient.getUserDetails("Bearer " + token);

        if (userDetails == null) {
            return null;
        }

        UserDTO userDTO = userDetails.getUser();

        UserActingDTO userActing = UserActingDTO
                .builder()
                .uuid(userDTO.getUuid())
                .mail(userDTO.getMail())
                .fio(userDTO.getFio())
                .role(userDTO.getRole())
                .build();

        return userActing;
    }

    private AuditCUDTO getAuditCUDTO(String text, UserActingDTO user, String id) {

        AuditCUDTO auditCUDTO = AuditCUDTO
                .builder()
                .user(user)
                .text(text)
                .type(ETypeEssence.ACCOUNT)
                .id(id)
                .build();

       return auditCUDTO;
    }
}
