package by.it_academy.jd2.audit_service.core.converters;

import by.it_academy.jd2.audit_service.core.dto.AuditCUDTO;
import by.it_academy.jd2.audit_service.core.dto.UserActingDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditCUDTOToEntityConverter implements Converter<AuditCUDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditCUDTO item) {

        AuditEntity entity = new AuditEntity();

        UserActingDTO user = item.getUser();
        if (user == null) {
            entity.setUser(null);
        } else {
            entity.setUser(user.getUuid());
        }

        entity.setText(item.getText());
        entity.setType(item.getType());
        entity.setId(item.getId());

        return entity;
    }
}