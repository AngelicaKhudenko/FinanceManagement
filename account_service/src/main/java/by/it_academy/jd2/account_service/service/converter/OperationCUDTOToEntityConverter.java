package by.it_academy.jd2.account_service.service.converter;

import by.it_academy.jd2.account_service.core.dto.OperationCUDTO;
import by.it_academy.jd2.account_service.model.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationCUDTOToEntityConverter implements Converter<OperationCUDTO, OperationEntity> {
    @Override
    public OperationEntity convert(OperationCUDTO item) {

        OperationEntity entity = new OperationEntity();

        entity.setDate(item.getDate());
        entity.setDescription(item.getDescription());
        entity.setCategory(item.getCategory());
        entity.setValue(item.getValue());
        entity.setCurrency(item.getCurrency());

        return entity;
    }
}