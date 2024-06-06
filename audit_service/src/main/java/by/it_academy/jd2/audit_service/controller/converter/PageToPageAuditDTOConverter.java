package by.it_academy.jd2.audit_service.controller.converter;

import by.it_academy.jd2.audit_service.core.dto.AuditDTO;
import by.it_academy.jd2.audit_service.core.dto.PageAuditDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageAuditDTOConverter implements Converter<Page<AuditDTO>, PageAuditDTO> {

    @Override
    public PageAuditDTO convert(Page<AuditDTO> page) {

        PageAuditDTO pageDTO = new PageAuditDTO();

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
