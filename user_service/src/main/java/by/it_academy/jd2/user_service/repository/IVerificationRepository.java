package by.it_academy.jd2.user_service.repository;

import by.it_academy.jd2.user_service.model.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationRepository extends JpaRepository<VerificationEntity, String> {
}
