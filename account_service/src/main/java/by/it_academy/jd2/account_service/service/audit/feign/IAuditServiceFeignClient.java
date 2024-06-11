package by.it_academy.jd2.account_service.service.audit.feign;

import by.it_academy.jd2.account_service.service.audit.dto.AuditCUDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auditService", url = "${auditUrlService}")
public interface IAuditServiceFeignClient {

    @PostMapping
    void create(@RequestBody AuditCUDTO audit);
}
