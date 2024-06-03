package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.core.dto.MailDTO;
import by.it_academy.jd2.user_service.core.enums.EMailStatus;
import by.it_academy.jd2.user_service.model.MailEntity;
import by.it_academy.jd2.user_service.repository.IMailRepository;
import by.it_academy.jd2.user_service.service.api.IMailService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;
    private final IMailRepository mailRepository;
    private final Converter<MailDTO, MailEntity> converter;

    public MailServiceImpl(JavaMailSender javaMailSender,
                           IMailRepository mailRepository,
                           Converter<MailDTO, MailEntity> converter) {

        this.javaMailSender = javaMailSender;
        this.mailRepository = mailRepository;
        this.converter = converter;
    }

    @Override
    public void send(MailEntity entity) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(entity.getFrom());
        message.setTo(entity.getTo());
        message.setSubject(entity.getTopic());
        message.setText(entity.getText());

        javaMailSender.send(message);
    }

    @Transactional
    @Override
    public void create(MailDTO mailDTO) {

        if (!mailDTO.fieldsChanged()) {
            throw new IllegalArgumentException("Не переданы параметры для создания сообщения");
        }

        MailEntity entity = this.converter.convert(mailDTO);

        entity.setUuid(UUID.randomUUID());
        entity.setStatus(EMailStatus.LOADED);
        entity.setCreation(LocalDateTime.now());

        this.mailRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public void update(MailEntity entity) {

        Optional<MailEntity> optional = this.mailRepository.findById(entity.getUuid());

        if (optional.isEmpty()) {
            throw new IllegalStateException("Письмо с таким id не зарегистрирвоан");
        }

        this.mailRepository.saveAndFlush(entity);
    }

    @Override
    public List<MailEntity> getByStatus(EMailStatus status) {

        return this.mailRepository.findAllByStatus(status);
    }
}
