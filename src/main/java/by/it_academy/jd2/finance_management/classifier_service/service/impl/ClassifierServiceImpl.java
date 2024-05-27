package by.it_academy.jd2.finance_management.classifier_service.service.impl;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.finance_management.classifier_service.repository.ICategoryRepository;
import by.it_academy.jd2.finance_management.classifier_service.repository.ICurrencyRepository;
import by.it_academy.jd2.finance_management.classifier_service.service.api.IClassifierService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ClassifierServiceImpl implements IClassifierService {

    private final Converter<CurrencyCUDTO, CurrencyEntity> creationCurrencyConverter;
    private final Converter<CategoryCUDTO, CategoryEntity> creationCategoryConverter;
    private final ICurrencyRepository currencyRepository;
    private final ICategoryRepository categoryRepository;

    public ClassifierServiceImpl(Converter<CurrencyCUDTO, CurrencyEntity> creationCurrencyConverter,
                                 Converter<CategoryCUDTO, CategoryEntity> creationCategoryConverter,
                                 ICurrencyRepository currencyRepository,
                                 ICategoryRepository categoryRepository) {

        this.creationCurrencyConverter = creationCurrencyConverter;
        this.creationCategoryConverter = creationCategoryConverter;
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void create(CurrencyCUDTO currency) {

        if (!currency.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о валюте");
        }

        CurrencyEntity entity = this.creationCurrencyConverter.convert(currency);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);
        entity.setUpdate(creation);

        this.currencyRepository.saveAndFlush(entity);
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
    public Page<CurrencyEntity> getCurrency(Pageable pageable) {

        return this.currencyRepository.findAll(pageable);
    }

    @Override
    public Page<CategoryEntity> getCategory(Pageable pageable) {

        return this.categoryRepository.findAll(pageable);
    }
}
