package by.it_academy.jd2.classifier_service.controller.http;

import by.it_academy.jd2.classifier_service.core.dto.*;
import by.it_academy.jd2.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.classifier_service.service.api.ICurrencyService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final ICurrencyService currencyService;
    private final ConversionService conversionService;

    public CurrencyController(ICurrencyService currencyService,
                              ConversionService conversionService) {

        this.currencyService = currencyService;
        this.conversionService = conversionService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CurrencyCUDTO currency) {

        this.currencyService.create(currency);
    }

    @GetMapping
    public PageDTO<CurrencyDTO> getCurrency(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CurrencyEntity> entities = this.currencyService.get(pageable);

        Page<CurrencyDTO> currencyDTOS = entities.map(entity -> conversionService.convert(entity, CurrencyDTO.class));

        return new PageDTO<>(currencyDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public CurrencyDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        CurrencyEntity entity = this.currencyService.get(uuid);

        return this.conversionService.convert(entity, CurrencyDTO.class);
    }
}
