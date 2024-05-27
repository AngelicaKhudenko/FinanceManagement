package by.it_academy.jd2.account_service.core.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationCUDTO {

    private LocalDateTime date;
    private String description;
    private UUID category;
    private Double value;
    private UUID currency;

    public boolean fieldsChanged() {
        return Objects.nonNull(date) &&
                Objects.nonNull(description) &&
                Objects.nonNull(category) &&
                Objects.nonNull(value) &&
                Objects.nonNull(currency);
    }
}
