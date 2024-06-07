package by.it_academy.jd2.account_service.controller.converter;

import by.it_academy.jd2.account_service.core.dto.OperationDTO;
import by.it_academy.jd2.account_service.model.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationEntityToDTOConverter implements Converter<OperationEntity, OperationDTO> {
    @Override
    public OperationDTO convert(OperationEntity item) {

        return OperationDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .description(item.getDescription())
                .category(item.getCategory())
                .value(item.getValue())
                .currency(item.getCurrency())
                .build();
    }
}
