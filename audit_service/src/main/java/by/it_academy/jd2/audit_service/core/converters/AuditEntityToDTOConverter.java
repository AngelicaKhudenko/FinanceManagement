package by.it_academy.jd2.audit_service.core.converters;

import by.it_academy.jd2.audit_service.core.dto.AuditDTO;

import by.it_academy.jd2.audit_service.model.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditEntityToDTOConverter implements Converter<AuditEntity, AuditDTO> {

    @Override
    public AuditDTO convert(AuditEntity item) {

        return AuditDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .text(item.getText())
                .type(item.getType())
                .id(item.getId())
                .build();
    }
}
