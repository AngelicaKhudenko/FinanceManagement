package by.it_academy.jd2.classifier_service.core.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCUDTO {

    private String title;

    public boolean fieldsChanged() {

        return Objects.nonNull(title);
    }
}
