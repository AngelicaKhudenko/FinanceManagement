package by.it_academy.jd2.classifier_service.service.impl;

import by.it_academy.jd2.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.repository.ICategoryRepository;
import by.it_academy.jd2.classifier_service.service.api.ICategoryService;
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
public class CategoryServiceImpl implements ICategoryService {

    private final ConversionService conversionService;
    private final ICategoryRepository categoryRepository;

    public CategoryServiceImpl(ConversionService conversionService,
                               ICategoryRepository categoryRepository) {

        this.conversionService = conversionService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void create(CategoryCUDTO category) {

        CategoryEntity entity = this.conversionService.convert(category, CategoryEntity.class);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        this.categoryRepository.saveAndFlush(entity);

    }

    @Override
    public Page<CategoryEntity> get(Pageable pageable) {

        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public CategoryEntity get(UUID uuid) {

        Optional<CategoryEntity> optional = this.categoryRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new IllegalArgumentException("Категория с таким id отсутствует");
        }

        return optional.get();
    }
}
