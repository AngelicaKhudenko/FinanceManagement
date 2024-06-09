package by.it_academy.jd2.classifier_service.service.api;

import by.it_academy.jd2.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICategoryService {
    void create(CategoryCUDTO category);
    Page<CategoryEntity> get(Pageable pageable);

    CategoryEntity get(UUID uuid);
}
