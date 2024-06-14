package by.it_academy.jd2.classifier_service.service.audit.aspect;


import by.it_academy.jd2.classifier_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.classifier_service.controller.token.dto.UserDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.service.audit.dto.AuditCUDTO;
import by.it_academy.jd2.classifier_service.service.audit.dto.UserActingDTO;
import by.it_academy.jd2.classifier_service.service.audit.enums.ETypeEssence;
import by.it_academy.jd2.classifier_service.service.feign.IAuditServiceFeignClient;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CategoryAuditAspect {

    private final IAuditServiceFeignClient auditFeign;
    private final String createText = "Создание категории";
    private final String getAllText = "Получение списка категорий";

    public CategoryAuditAspect(IAuditServiceFeignClient auditFeign) {

        this.auditFeign = auditFeign;
    }

    @AfterReturning(pointcut = "execution( * by.it_academy.jd2.classifier_service.service.impl.CategoryServiceImpl.create(..))",returning = "category")
    public void afterCreate(CategoryEntity category) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.createText,userActing,category.getUuid().toString());

        this.auditFeign.create(audit);
    }

    @AfterReturning(value = "execution( * by.it_academy.jd2.classifier_service.service.impl.CategoryServiceImpl.get(..))", returning = "page")
    public void afterGetAll(Page<CategoryEntity> page) {

        UserActingDTO userActing = getUserActing();

        AuditCUDTO audit = getAuditCUDTO(this.getAllText,userActing, String.valueOf(page.hashCode()));

        this.auditFeign.create(audit);
    }

    private UserActingDTO getUserActing() {

        UserDetailsExpanded userDetailsExpanded = (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetailsExpanded == null) {
            return null;
        }

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
                .type(ETypeEssence.CATEGORY)
                .id(id)
                .build();

       return auditCUDTO;
    }
}
