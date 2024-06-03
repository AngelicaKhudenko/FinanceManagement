package by.it_academy.jd2.user_service.service.scheduling;


import by.it_academy.jd2.user_service.service.job.impl.MailSendJob;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MailSendScheduling {
    private final int coreSize = 10;
    private final long delay = 0;
    private final long period = 50;
    private final TimeUnit unit = TimeUnit.SECONDS;

    public MailSendScheduling(MailSendJob mailSendJob) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(coreSize);

        executorService.scheduleAtFixedRate(mailSendJob::start, delay, period, unit);
    }
}
