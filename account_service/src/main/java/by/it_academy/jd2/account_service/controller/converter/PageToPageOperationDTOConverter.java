package by.it_academy.jd2.account_service.controller.converter;

import by.it_academy.jd2.account_service.core.dto.OperationDTO;
import by.it_academy.jd2.account_service.core.dto.PageOperationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class PageToPageOperationDTOConverter implements Converter<Page<OperationDTO>, PageOperationDTO> {

    @Override
    public PageOperationDTO convert(Page<OperationDTO> page) {

        PageOperationDTO pageDTO = new PageOperationDTO();

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
