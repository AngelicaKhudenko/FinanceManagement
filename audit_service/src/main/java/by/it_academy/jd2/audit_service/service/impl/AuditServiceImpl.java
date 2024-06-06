package by.it_academy.jd2.audit_service.service.impl;

import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import by.it_academy.jd2.audit_service.repository.IAuditRepository;
import by.it_academy.jd2.audit_service.service.api.IAuditService;
import org.springframework.core.convert.converter.Converter;
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
    private final Converter<AuditCUDTO, AuditEntity> creationConverter;

    public AuditServiceImpl(IAuditRepository auditRepository,
                            Converter<AuditCUDTO, AuditEntity> creationConverter) {

        this.auditRepository = auditRepository;
        this.creationConverter = creationConverter;
    }

    @Override
    public Page<AuditEntity> get(Pageable pageable) {

        return this.auditRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void create(AuditCUDTO audit) {

        if (!audit.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о действии");
        }

        if (!audit.correctConstants(audit.getType())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        AuditEntity entity = this.creationConverter.convert(audit);

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
