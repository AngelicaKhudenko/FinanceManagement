package by.it_academy.jd2.classifier_service.controller.http;

import by.it_academy.jd2.classifier_service.core.dto.*;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.model.CurrencyEntity;
import by.it_academy.jd2.classifier_service.service.api.IClassifierService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classifier")
public class ClassifierController {

    private final IClassifierService classifierService;
    private final Converter<Page<CurrencyDTO>, PageCurrencyDTO> pageCurrencyDTOConverter;
    private final Converter<Page<CategoryDTO>, PageCategoryDTO> pageCategoryDTOConverter;
    private final Converter<CurrencyEntity, CurrencyDTO> entityCurrencyDTOConverter;
    private final Converter<CategoryEntity, CategoryDTO> entityCategoryDTOConverter;

    public ClassifierController(IClassifierService classifierService,
                                Converter<Page<CurrencyDTO>, PageCurrencyDTO> pageCurrencyDTOConverter, Converter<Page<CategoryDTO>, PageCategoryDTO> pageCategoryDTOConverter,
                                Converter<CurrencyEntity, CurrencyDTO> entityCurrencyDTOConverter, Converter<CategoryEntity, CategoryDTO> entityCategoryDTOConverter) {

        this.classifierService = classifierService;
        this.pageCurrencyDTOConverter = pageCurrencyDTOConverter;
        this.pageCategoryDTOConverter = pageCategoryDTOConverter;
        this.entityCurrencyDTOConverter = entityCurrencyDTOConverter;
        this.entityCategoryDTOConverter = entityCategoryDTOConverter;
    }

    @PostMapping(value = "/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CurrencyCUDTO currency) {

        this.classifierService.create(currency);
    }

    @GetMapping(value = "/currency")
    @ResponseStatus(HttpStatus.OK)
    public PageCurrencyDTO getCurrency(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CurrencyEntity> entities = this.classifierService.getCurrency(pageable);

        Page<CurrencyDTO> currencyDTOS = entities.map(this.entityCurrencyDTOConverter::convert);

        return this.pageCurrencyDTOConverter.convert(currencyDTOS);
    }

    @PostMapping(value = "/operation/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryCUDTO category) {

        this.classifierService.create(category);
    }

    @GetMapping(value = "/operation/category")
    @ResponseStatus(HttpStatus.OK)
    public PageCategoryDTO getCategory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryEntity> entities = this.classifierService.getCategory(pageable);

        Page<CategoryDTO> categoryDTOS = entities.map(this.entityCategoryDTOConverter::convert);

        return this.pageCategoryDTOConverter.convert(categoryDTOS);
    }
}
