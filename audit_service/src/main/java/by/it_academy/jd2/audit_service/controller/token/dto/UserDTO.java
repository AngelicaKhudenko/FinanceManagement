package by.it_academy.jd2.audit_service.controller.token.dto;

import by.it_academy.jd2.audit_service.controller.utils.LocalDateTimeDeserializer;
import by.it_academy.jd2.audit_service.controller.utils.LocalDateTimeSerializer;
import by.it_academy.jd2.audit_service.controller.token.enums.EUserRole;
import by.it_academy.jd2.audit_service.controller.token.enums.EUserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creation;

    @JsonProperty("dt_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime update;

    private String mail;
    private String fio;
    private EUserRole role;
    private EUserStatus status;
}
