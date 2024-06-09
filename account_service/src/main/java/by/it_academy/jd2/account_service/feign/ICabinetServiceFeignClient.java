package by.it_academy.jd2.account_service.feign;

import by.it_academy.jd2.account_service.token.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userService", url = "${urlCabinetService}")
public interface ICabinetServiceFeignClient {

    @GetMapping("/me")
    UserDTO getUser(@RequestHeader("Authorization") String authorization);
}
