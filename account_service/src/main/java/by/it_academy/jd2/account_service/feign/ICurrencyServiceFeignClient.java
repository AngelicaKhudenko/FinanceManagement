package by.it_academy.jd2.account_service.feign;

import by.it_academy.jd2.account_service.core.dto.CurrencyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "classifierService", url = "${urlCurrencyService}")
public interface ICurrencyServiceFeignClient {

    @GetMapping("/{uuid}")
    CurrencyDTO get(@RequestHeader(value = "Authorization") String authorization,
                    @PathVariable(value = "uuid") UUID uuid);
}
