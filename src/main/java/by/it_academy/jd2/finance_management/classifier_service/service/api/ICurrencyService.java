package by.it_academy.jd2.finance_management.classifier_service.service.api;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICurrencyService {

    void create(CurrencyCUDTO currency);

    Page<CurrencyEntity> get(Pageable pageable);
}
