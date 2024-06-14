package by.it_academy.jd2.audit_service.controller.http;

import by.it_academy.jd2.audit_service.controller.token.dto.UserDTO;
import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.core.dto.AuditDTO;
import by.it_academy.jd2.audit_service.core.dto.PageDTO;
import by.it_academy.jd2.audit_service.core.dto.UserActingDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import by.it_academy.jd2.audit_service.service.api.IAuditService;
import by.it_academy.jd2.audit_service.service.feign.IUserServiceFeignClient;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final IAuditService auditService;
    private final ConversionService conversionService;
    private final IUserServiceFeignClient userServiceFeignClient;

    public AuditController(IAuditService auditService,
                           ConversionService conversionService,
                           IUserServiceFeignClient userServiceFeignClient) {
        this.auditService = auditService;
        this.conversionService = conversionService;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AuditCUDTO audit) {

        this.auditService.create(audit);
    }

    @GetMapping
    public PageDTO<AuditDTO> get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AuditEntity> entities = this.auditService.get(pageable);

        Page<AuditDTO> auditDTOS = entities.map(entity -> conversionService.convert(entity, AuditDTO.class));

        auditDTOS.forEach(auditDTO -> auditDTO.setUser(getActingUser(auditDTO.getUuid())));

        return new PageDTO<>(auditDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public AuditDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        AuditEntity entity = this.auditService.get(uuid);

        AuditDTO auditDTO = this.conversionService.convert(entity, AuditDTO.class);

        auditDTO.setUser(getActingUser(uuid));

        return auditDTO;
    }

    private UserActingDTO getActingUser (UUID uuid) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        UserDTO userDTO = this.userServiceFeignClient.getUser(uuid, token);

        UserActingDTO user = UserActingDTO
                .builder()
                .uuid(userDTO.getUuid())
                .mail(userDTO.getMail())
                .fio(userDTO.getFio())
                .role(userDTO.getRole())
                .build();

        return user;
    }
}
