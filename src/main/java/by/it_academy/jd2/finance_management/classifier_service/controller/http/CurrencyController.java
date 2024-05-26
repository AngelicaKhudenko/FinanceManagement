package by.it_academy.jd2.finance_management.classifier_service.controller.http;

import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyCUDTO;
import by.it_academy.jd2.finance_management.classifier_service.core.dto.CurrencyDTO;
import by.it_academy.jd2.finance_management.classifier_service.core.dto.PageCurrencyDTO;
import by.it_academy.jd2.finance_management.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.finance_management.classifier_service.service.api.ICurrencyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classifier")
public class CurrencyController {

    private final ICurrencyService currencyService;

    private final Converter<Page<CurrencyDTO>, PageCurrencyDTO> pageCurrencyDTOConverter;
    private final Converter<CurrencyEntity, CurrencyDTO> entityCurrencyDTOConverter;

    public CurrencyController(ICurrencyService currencyService,
                              Converter<Page<CurrencyDTO>, PageCurrencyDTO> pageCurrencyDTOConverter,
                              Converter<CurrencyEntity, CurrencyDTO> entityCurrencyDTOConverter) {

        this.currencyService = currencyService;
        this.pageCurrencyDTOConverter = pageCurrencyDTOConverter;
        this.entityCurrencyDTOConverter = entityCurrencyDTOConverter;
    }

    @PostMapping(value = "/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CurrencyCUDTO currency) {

        this.currencyService.create(currency);
    }

    @GetMapping(value = "/currency")
    @ResponseStatus(HttpStatus.OK)
    public PageCurrencyDTO get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CurrencyEntity> entities = this.currencyService.get(pageable);

        Page<CurrencyDTO> currencyDTOS = entities.map(this.entityCurrencyDTOConverter::convert);

        return this.pageCurrencyDTOConverter.convert(currencyDTOS);
    }
}
