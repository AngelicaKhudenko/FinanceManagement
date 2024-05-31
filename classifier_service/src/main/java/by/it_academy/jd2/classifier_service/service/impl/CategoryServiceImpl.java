package by.it_academy.jd2.classifier_service.service.impl;

import by.it_academy.jd2.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.repository.ICategoryRepository;
import by.it_academy.jd2.classifier_service.service.api.ICategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements ICategoryService {
    private final Converter<CategoryCUDTO, CategoryEntity> creationCategoryConverter;
    private final ICategoryRepository categoryRepository;

    public CategoryServiceImpl(Converter<CategoryCUDTO, CategoryEntity> creationCategoryConverter,
                               ICategoryRepository categoryRepository) {

        this.creationCategoryConverter = creationCategoryConverter;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void create(CategoryCUDTO category) {

        if (!category.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о категории операции");
        }

        CategoryEntity entity = this.creationCategoryConverter.convert(category);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);
        entity.setUpdate(creation);

        this.categoryRepository.saveAndFlush(entity);

    }

    @Override
    public Page<CategoryEntity> get(Pageable pageable) {

        return this.categoryRepository.findAll(pageable);
    }
}
