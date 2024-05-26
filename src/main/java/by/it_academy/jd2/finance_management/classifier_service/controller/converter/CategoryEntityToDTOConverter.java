package by.it_academy.jd2.finance_management.classifier_service.controller.converter;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CategoryDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CategoryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToDTOConverter implements Converter<CategoryEntity, CategoryDTO> {
    @Override
    public CategoryDTO convert(CategoryEntity item) {

        return CategoryDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .title(item.getTitle())
                .build();
    }
}