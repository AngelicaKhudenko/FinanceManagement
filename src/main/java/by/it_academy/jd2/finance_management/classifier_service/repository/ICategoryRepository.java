package by.it_academy.jd2.finance_management.classifier_service.repository;

import by.it_academy.jd2.finance_management.classifier_service.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, UUID> {

}
