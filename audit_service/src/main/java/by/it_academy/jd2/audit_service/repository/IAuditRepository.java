package by.it_academy.jd2.audit_service.repository;

import by.it_academy.jd2.audit_service.model.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAuditRepository extends JpaRepository<AuditEntity, UUID> {

}
