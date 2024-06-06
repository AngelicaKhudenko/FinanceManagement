package by.it_academy.jd2.audit_service.service.api;

import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {

    AuditEntity get(UUID uuid);

    Page<AuditEntity> get(Pageable pageable);

    void create(AuditCUDTO audit);
}
