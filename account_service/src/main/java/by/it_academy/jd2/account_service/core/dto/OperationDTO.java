package by.it_academy.jd2.account_service.core.dto;

import by.it_academy.jd2.account_service.controller.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationDTO {
    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creation;

    @JsonProperty("dt_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime update;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    private String description;
    private UUID category;
    private Double value;
    private UUID currency;

}
