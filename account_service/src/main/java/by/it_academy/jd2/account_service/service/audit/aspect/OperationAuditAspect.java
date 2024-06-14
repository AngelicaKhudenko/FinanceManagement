package by.it_academy.jd2.account_service.service.audit.aspect;

import by.it_academy.jd2.account_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.account_service.controller.token.dto.UserDTO;
import by.it_academy.jd2.account_service.model.OperationEntity;
import by.it_academy.jd2.account_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.account_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.account_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.account_service.service.feign.IAuditServiceFeignClient;
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
public class OperationAuditAspect {

    private final IAuditServiceFeignClient auditServiceFeignClient;
    private final IUserServiceFeignClient userServiceFeignClient;
    private final String createText = "Создание операции";
    private final String getAllText = "Получение списка операций";
    private final String updateText = "Обновление операции";
    private final String deleteText = "Удаление операции";

    public OperationAuditAspect(IAuditServiceFeignClient auditServiceFeignClient,
                                IUserServiceFeignClient userServiceFeignClient) {

        this.auditServiceFeignClient = auditServiceFeignClient;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.OperationServiceImpl.create(..))", returning = "operation")
    public void afterCreate(OperationEntity operation) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,operation.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.OperationServiceImpl.get(..))", returning = "page")
    public void afterGetAll(Page<OperationEntity> page) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getAllText,userActing, String.valueOf(page.hashCode()));

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.OperationServiceImpl.create(..)) && args(operationUUID)")
    public void afterUpdate(UUID operationUUID) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.updateText,userActing,operationUUID.toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.account_service.service.impl.OperationServiceImpl.delete(..)) && args(operationUUID)")
    public void afterDelete(UUID operationUUID) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.deleteText,userActing,operationUUID.toString());

        this.auditServiceFeignClient.create(audit);
    }

    private UserActingDTO getUserActing() {

        UserDetailsExpanded userDetailsExpanded = (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = userDetailsExpanded.getUser();

        UserActingDTO user = UserActingDTO
                .builder()
                .uuid(userDTO.getUuid())
                .mail(userDTO.getMail())
                .fio(userDTO.getFio())
                .role(userDTO.getRole())
                .build();

        return user;
    }

    private AuditCUDTO getAuditCUDTO(String text, UserActingDTO user, String id) {

        AuditCUDTO auditCUDTO = AuditCUDTO
                .builder()
                .user(user)
                .text(text)
                .type(ETypeEssence.OPERATION)
                .id(id)
                .build();

       return auditCUDTO;
    }
}
