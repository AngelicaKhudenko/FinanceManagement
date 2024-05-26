package by.it_academy.jd2.finance_management.classifier_service.controller.converter;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CategoryDTO;
import by.it_academy.jd2.finance_management.classifier_service.core.dto.PageCategoryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageCategoryDTOConverter implements Converter<Page<CategoryDTO>, PageCategoryDTO> {

    @Override
    public PageCategoryDTO convert(Page<CategoryDTO> page) {

        PageCategoryDTO pageDTO = new PageCategoryDTO();

        pageDTO.setNumber(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements((long) page.getTotalPages());
        pageDTO.setFirst(page.isFirst());
        pageDTO.setNumberOfElements(page.getNumberOfElements());
        pageDTO.setLast(page.isLast());
        pageDTO.setContent(page.getContent());

        return pageDTO;
    }
}