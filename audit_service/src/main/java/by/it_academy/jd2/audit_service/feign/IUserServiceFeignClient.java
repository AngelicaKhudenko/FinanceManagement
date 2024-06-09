package by.it_academy.jd2.audit_service.feign;

import by.it_academy.jd2.audit_service.token.UserDetailsExpanded;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userService", url = "${urlUserService}")
public interface IUserServiceFeignClient {

    @GetMapping("/details")
    UserDetailsExpanded getUserDetails(@RequestHeader("Authorization") String authorization);
}
