package by.it_academy.jd2.audit_service.service.feign;

import by.it_academy.jd2.audit_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.audit_service.controller.token.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "userService", url = "${url.users}")
public interface IUserServiceFeignClient {

    @GetMapping("/details")
    UserDetailsExpanded getUserDetails(@RequestHeader("Authorization") String authorization);
}
