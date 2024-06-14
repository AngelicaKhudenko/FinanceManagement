package by.it_academy.jd2.user_service.service.scheduling;


import by.it_academy.jd2.user_service.service.job.impl.MailSendJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MailSendScheduling {

    private final MailSendJob mailSendJob;

    public MailSendScheduling(MailSendJob mailSendJob) {
        this.mailSendJob = mailSendJob;
    }


    @Scheduled(timeUnit = TimeUnit.SECONDS,fixedDelay = 50)
    public void start() {
        this.mailSendJob.start();
    }
}
