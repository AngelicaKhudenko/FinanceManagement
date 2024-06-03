package by.it_academy.jd2.user_service.service.job.impl;


import by.it_academy.jd2.user_service.core.enums.EMailStatus;
import by.it_academy.jd2.user_service.model.MailEntity;
import by.it_academy.jd2.user_service.service.api.IMailService;
import by.it_academy.jd2.user_service.service.job.api.IJob;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailSendJob implements IJob {

    private final IMailService mailService;

    public MailSendJob(IMailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void start() {

        List<MailEntity> loaded = this.mailService.getByStatus(EMailStatus.LOADED);

        for (MailEntity mail : loaded) {

            try {
                this.mailService.send(mail);
                mail.setStatus(EMailStatus.OK);
            } catch (MailException e) {
                mail.setStatus(EMailStatus.ERROR);
            } finally {
                this.mailService.update(mail);
            }
        }
    }
}
