package by.it_academy.jd2.finance_management.classifier_service.service.impl;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.finance_management.classifier_service.repository.ICurrencyRepository;
import by.it_academy.jd2.finance_management.classifier_service.service.api.ICurrencyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CurrencyServiceImpl implements ICurrencyService {

    private final Converter<CurrencyCUDTO, CurrencyEntity> creationConverter;
    private final ICurrencyRepository currencyRepository;

    public CurrencyServiceImpl(Converter<CurrencyCUDTO, CurrencyEntity> creationConverter,
                               ICurrencyRepository currencyRepository) {
        this.creationConverter = creationConverter;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public void create(CurrencyCUDTO currency) {

        if (!currency.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о валюте");
        }

        CurrencyEntity entity = this.creationConverter.convert(currency);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        this.currencyRepository.saveAndFlush(entity);
    }

    @Override
    public Page<CurrencyEntity> get(Pageable pageable) {

        return this.currencyRepository.findAll(pageable);
    }


}
