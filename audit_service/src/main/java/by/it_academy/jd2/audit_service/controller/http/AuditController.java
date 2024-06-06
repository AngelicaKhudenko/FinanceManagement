package by.it_academy.jd2.audit_service.controller.http;

import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.core.dto.AuditDTO;
import by.it_academy.jd2.audit_service.core.dto.PageDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import by.it_academy.jd2.audit_service.service.api.IAuditService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/audit")
public class AuditController {

    private final IAuditService auditService;
    private final Converter<AuditEntity, AuditDTO> entityAuditDTOConverter;


    public AuditController(IAuditService auditService,
                           Converter<AuditEntity, AuditDTO> entityAuditDTOConverter) {

        this.auditService = auditService;
        this.entityAuditDTOConverter = entityAuditDTOConverter;
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

        Page<AuditDTO> accountDTOS = entities.map(this.entityAuditDTOConverter::convert);

        return new PageDTO<>(accountDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public AuditDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        AuditEntity entity = this.auditService.get(uuid);

        return this.entityAuditDTOConverter.convert(entity);
    }
}
