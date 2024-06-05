package by.it_academy.jd2.account_service.controller.converter;

import by.it_academy.jd2.account_service.core.dto.AccountDTO;
import by.it_academy.jd2.account_service.core.dto.PageAccountDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageAccountDTOConverter implements Converter<Page<AccountDTO>, PageAccountDTO> {

    @Override
    public PageAccountDTO convert(Page<AccountDTO> page) {

        PageAccountDTO pageDTO = new PageAccountDTO();

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
