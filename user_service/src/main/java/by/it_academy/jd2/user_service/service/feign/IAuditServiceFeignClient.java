package by.it_academy.jd2.user_service.service.feign;

import by.it_academy.jd2.user_service.service.audit.dto.AuditCUDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auditService", url = "${url.audit}")
public interface IAuditServiceFeignClient {

    @PostMapping
    void create(@RequestBody AuditCUDTO audit);
}
