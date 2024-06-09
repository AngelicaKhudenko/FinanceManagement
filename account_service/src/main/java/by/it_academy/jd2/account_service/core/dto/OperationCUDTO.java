package by.it_academy.jd2.account_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationCUDTO {

    @NotNull
    private LocalDateTime date;
    @NotEmpty
    @NotNull
    private String description;
    @NotNull
    private UUID category;
    @NotNull
    private Double value;
    @NotNull
    private UUID currency;
}
