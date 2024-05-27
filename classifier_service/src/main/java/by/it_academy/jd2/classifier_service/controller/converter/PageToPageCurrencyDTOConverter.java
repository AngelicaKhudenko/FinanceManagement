package by.it_academy.jd2.classifier_service.controller.converter;

import by.it_academy.jd2.classifier_service.core.dto.PageCurrencyDTO;
import by.it_academy.jd2.classifier_service.core.dto.CurrencyDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageCurrencyDTOConverter implements Converter<Page<CurrencyDTO>, PageCurrencyDTO> {

    @Override
    public PageCurrencyDTO convert(Page<CurrencyDTO> page) {

        PageCurrencyDTO pageDTO = new PageCurrencyDTO();

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
