package by.it_academy.jd2.finance_management.classifier_service.service.converter;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CategoryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCUDTOToEntityConverter implements Converter<CategoryCUDTO, CategoryEntity> {
    @Override
    public CategoryEntity convert(CategoryCUDTO item) {

        CategoryEntity entity = new CategoryEntity();

        entity.setTitle(item.getTitle());

        return entity;
    }
}
