package by.it_academy.jd2.user_service.core.converters;

import by.it_academy.jd2.user_service.core.dto.MailDTO;
import by.it_academy.jd2.user_service.model.MailEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MailDTOtoEntityConverter implements Converter<MailDTO, MailEntity> {
    @Override
    public MailEntity convert(MailDTO item) {

        MailEntity entity = new MailEntity();

        entity.setFrom(item.getFrom());
        entity.setTopic(item.getTopic());
        entity.setTo(item.getTo());
        entity.setText(item.getText());

        return entity;
    }
}
