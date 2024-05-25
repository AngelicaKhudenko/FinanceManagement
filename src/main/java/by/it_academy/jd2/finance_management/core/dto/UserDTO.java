package by.it_academy.jd2.finance_management.core.dto;

import by.it_academy.jd2.finance_management.controller.utils.LocalDateTimeSerializer;
import by.it_academy.jd2.finance_management.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.core.enums.EUserStatus;
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
public class UserDTO {
    private UUID uuid;
    @JsonProperty("dt_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creation;
    @JsonProperty("dt_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime update;
    private String mail;
    private String fio;
    private EUserRole role;
    private EUserStatus status;
}
