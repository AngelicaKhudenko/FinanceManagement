package by.it_academy.jd2.classifier_service.core.converters;

import by.it_academy.jd2.classifier_service.core.dto.CategoryDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
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