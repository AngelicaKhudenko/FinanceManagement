package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.core.dto.MailDTO;
import by.it_academy.jd2.user_service.core.enums.EMailStatus;
import by.it_academy.jd2.user_service.model.MailEntity;
import by.it_academy.jd2.user_service.repository.IMailRepository;
import by.it_academy.jd2.user_service.service.api.IMailService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MailServiceImpl implements IMailService {

    private final IMailRepository mailRepository;
    private final ConversionService conversionService;

    public MailServiceImpl(IMailRepository mailRepository,
                           ConversionService conversionService) {

        this.mailRepository = mailRepository;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public void create(MailDTO mailDTO) {

        MailEntity entity = this.conversionService.convert(mailDTO, MailEntity.class);

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
            throw new IllegalStateException("Письмо с таким id не зарегистрировано");
        }

        MailEntity entityDB = optional.get();

        if (!entityDB.getUpdate().equals(entity.getUpdate())) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.mailRepository.saveAndFlush(entity);
    }

    @Override
    public List<MailEntity> getByStatus(EMailStatus status) {

        return this.mailRepository.findAllByStatus(status);
    }
}
