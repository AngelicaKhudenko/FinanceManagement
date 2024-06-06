package by.it_academy.jd2.audit_service.core.dto;

import by.it_academy.jd2.audit_service.controller.utils.LocalDateTimeSerializer;
import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
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
public class AuditDTO {
    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creation;

    private UserActingDTO user;
    private String text;
    private ETypeEssence type;
    private String id;
}
