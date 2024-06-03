package by.it_academy.jd2.user_service.controller.converter;

import by.it_academy.jd2.user_service.core.dto.PageUserDTO;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageUserDTOConverter implements Converter<Page<UserDTO>, PageUserDTO> {

    @Override
    public PageUserDTO convert(Page<UserDTO> page) {

        PageUserDTO pageDTO = new PageUserDTO();

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