package by.it_academy.jd2.classifier_service.controller.http;

import by.it_academy.jd2.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.classifier_service.core.dto.CategoryDTO;
import by.it_academy.jd2.classifier_service.core.dto.PageDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.service.api.ICategoryService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ConversionService conversionService;

    public CategoryController(ICategoryService categoryService,
                              ConversionService conversionService) {

        this.categoryService = categoryService;
        this.conversionService = conversionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryCUDTO category) {

        this.categoryService.create(category);
    }

    @GetMapping
    public PageDTO<CategoryDTO> getCategory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryEntity> entities = this.categoryService.get(pageable);

        Page<CategoryDTO> categoryDTOS = entities.map(entity -> conversionService.convert(entity, CategoryDTO.class));

        return new PageDTO<>(categoryDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public CategoryDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        CategoryEntity entity = this.categoryService.get(uuid);

        return this.conversionService.convert(entity, CategoryDTO.class);
    }
}
