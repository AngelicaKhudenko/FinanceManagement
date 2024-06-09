package by.it_academy.jd2.classifier_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCUDTO {

    @NotEmpty
    @NotNull
    private String title;
    @NotEmpty
    @NotNull
    private String description;
}
