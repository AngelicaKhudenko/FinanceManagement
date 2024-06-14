package by.it_academy.jd2.account_service.core.dto;

import by.it_academy.jd2.account_service.controller.utils.UnixTimestampDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @JsonDeserialize(using = UnixTimestampDeserializer.class)
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
