package by.it_academy.jd2.account_service.service.feign;

import by.it_academy.jd2.account_service.core.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "classifierService", url = "${urlCategoryService}")
public interface ICategoryServiceFeignClient {

    @GetMapping("/{uuid}")
    CategoryDTO get(@RequestHeader(value = "Authorization") String authorization,
                    @PathVariable(value = "uuid") UUID uuid);
}
