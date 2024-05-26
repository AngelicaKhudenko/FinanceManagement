package by.it_academy.jd2.finance_management.classifier_service.service.converter;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyCUDTOToEntityConverter implements Converter<CurrencyCUDTO, CurrencyEntity> {
    @Override
    public CurrencyEntity convert(CurrencyCUDTO item) {

        CurrencyEntity entity = new CurrencyEntity();

        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());

        return entity;
    }
}
