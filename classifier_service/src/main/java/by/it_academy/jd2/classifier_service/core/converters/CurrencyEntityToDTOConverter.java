package by.it_academy.jd2.classifier_service.core.converters;

import by.it_academy.jd2.classifier_service.core.dto.CurrencyDTO;
import by.it_academy.jd2.classifier_service.model.CurrencyEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyEntityToDTOConverter implements Converter<CurrencyEntity, CurrencyDTO> {
    @Override
    public CurrencyDTO convert(CurrencyEntity item) {

        return CurrencyDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .title(item.getTitle())
                .description(item.getDescription())
                .build();
    }
}
