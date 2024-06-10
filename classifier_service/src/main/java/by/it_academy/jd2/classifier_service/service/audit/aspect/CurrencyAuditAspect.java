package by.it_academy.jd2.classifier_service.service.audit.aspect;

import by.it_academy.jd2.classifier_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.classifier_service.controller.token.dto.UserDTO;
import by.it_academy.jd2.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.classifier_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.classifier_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.classifier_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.classifier_service.service.audit.feign.IAuditServiceFeignClient;
import by.it_academy.jd2.classifier_service.service.feign.IUserServiceFeignClient;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CurrencyAuditAspect {

    private final IAuditServiceFeignClient auditServiceFeignClient;
    private final IUserServiceFeignClient userServiceFeignClient;
    private final String createText = "Создание валюты";
    private final String getAllText = "Получение списка валют";

    public CurrencyAuditAspect(IAuditServiceFeignClient auditServiceFeignClient,
                               IUserServiceFeignClient userServiceFeignClient) {

        this.auditServiceFeignClient = auditServiceFeignClient;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @AfterReturning(pointcut = "execution ( * by.it_academy.jd2.classifier_service.service.impl.CurrencyServiceImpl.create(..))", returning = "currency")
    public void afterCreate(CurrencyEntity currency) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,currency.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution ( * by.it_academy.jd2.classifier_service.service.impl.CurrencyServiceImpl.get(..))", returning = "page")
    public void afterGetAll(Page<CurrencyEntity> page) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getAllText,userActing, String.valueOf(page.hashCode()));

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
                .type(ETypeEssence.CURRENCY)
                .id(id)
                .build();

       return auditCUDTO;
    }
}
