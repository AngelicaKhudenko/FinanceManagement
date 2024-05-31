package by.it_academy.jd2.classifier_service.controller.http;

import by.it_academy.jd2.classifier_service.core.dto.CategoryCUDTO;
import by.it_academy.jd2.classifier_service.core.dto.CategoryDTO;
import by.it_academy.jd2.classifier_service.core.dto.PageCategoryDTO;
import by.it_academy.jd2.classifier_service.model.CategoryEntity;
import by.it_academy.jd2.classifier_service.service.api.ICategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classifier/operation/category")
public class CategoryController {
    private final ICategoryService categoryService;
    private final Converter<Page<CategoryDTO>, PageCategoryDTO> pageCategoryDTOConverter;
    private final Converter<CategoryEntity, CategoryDTO> entityCategoryDTOConverter;

    public CategoryController(ICategoryService categoryService,
                              Converter<Page<CategoryDTO>, PageCategoryDTO> pageCategoryDTOConverter,
                              Converter<CategoryEntity, CategoryDTO> entityCategoryDTOConverter) {

        this.categoryService = categoryService;
        this.pageCategoryDTOConverter = pageCategoryDTOConverter;
        this.entityCategoryDTOConverter = entityCategoryDTOConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryCUDTO category) {

        this.categoryService.create(category);
    }

    @GetMapping
    public PageCategoryDTO getCategory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryEntity> entities = this.categoryService.get(pageable);

        Page<CategoryDTO> categoryDTOS = entities.map(this.entityCategoryDTOConverter::convert);

        return this.pageCategoryDTOConverter.convert(categoryDTOS);
    }
}
