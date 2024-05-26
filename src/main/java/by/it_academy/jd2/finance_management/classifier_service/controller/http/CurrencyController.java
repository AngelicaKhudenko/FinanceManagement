package by.it_academy.jd2.finance_management.classifier_service.controller.http;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.service.api.ICurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classifier")
public class CurrencyController {

    private final ICurrencyService currencyService;

    public CurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping(value = "/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CurrencyCUDTO currency) {

        this.currencyService.create(currency);
    }

}
