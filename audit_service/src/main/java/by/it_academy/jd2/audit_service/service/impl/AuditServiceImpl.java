package by.it_academy.jd2.audit_service.service.impl;

import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import by.it_academy.jd2.audit_service.repository.IAuditRepository;
import by.it_academy.jd2.audit_service.service.api.IAuditService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements IAuditService {
    private final IAuditRepository auditRepository;
    private final ConversionService conversionService;

    public AuditServiceImpl(IAuditRepository auditRepository,
                            ConversionService conversionService) {

        this.auditRepository = auditRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Page<AuditEntity> get(Pageable pageable) {

        return this.auditRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void create(AuditCUDTO audit) {

        if (ETypeEssence.getByName(audit.getType().name()).isEmpty()) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        AuditEntity entity = this.conversionService.convert(audit, AuditEntity.class);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        this.auditRepository.saveAndFlush(entity);
    }

    @Override
    public AuditEntity get(UUID uuid) {

        Optional<AuditEntity> optional = this.auditRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new IllegalArgumentException("Действие с таким id отсутствует");
        }

        return optional.get();
    }
}
