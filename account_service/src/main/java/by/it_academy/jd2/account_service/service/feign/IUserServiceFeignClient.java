package by.it_academy.jd2.account_service.service.feign;

import by.it_academy.jd2.account_service.controller.token.UserDetailsExpanded;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userService", url = "${url.users}")
public interface IUserServiceFeignClient {

    @GetMapping("/details")
    UserDetailsExpanded getUserDetails(@RequestHeader("Authorization") String authorization);
}
