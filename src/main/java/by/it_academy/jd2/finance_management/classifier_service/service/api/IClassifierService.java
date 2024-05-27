package by.it_academy.jd2.finance_management.classifier_service.service.api;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassifierService {

    void create(CurrencyCUDTO currency);
    void create(CategoryCUDTO category);
    Page<CurrencyEntity> getCurrency(Pageable pageable);
    Page<CategoryEntity> getCategory(Pageable pageable);
}