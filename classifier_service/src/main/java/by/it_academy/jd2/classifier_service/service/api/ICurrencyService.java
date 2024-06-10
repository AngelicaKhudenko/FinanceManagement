package by.it_academy.jd2.classifier_service.service.api;

import by.it_academy.jd2.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.classifier_service.model.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICurrencyService {

    CurrencyEntity create(CurrencyCUDTO currency);
    Page<CurrencyEntity> get(Pageable pageable);

    CurrencyEntity get(UUID uuid);
}
