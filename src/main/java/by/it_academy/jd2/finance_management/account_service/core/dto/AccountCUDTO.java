package by.it_academy.jd2.finance_management.account_service.core.dto;

import by.it_academy.jd2.finance_management.account_service.core.enums.EAccountType;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCUDTO {
    private String title;
    private String description;
    private EAccountType type;
    private UUID currency;

    public boolean fieldsChanged() {
        return Objects.nonNull(title) &&
                Objects.nonNull(description) &&
                Objects.nonNull(type) &&
                Objects.nonNull(currency);
    }

    public boolean correctConstants(EAccountType accountType) {

        return EAccountType.getByName(accountType.name()).isPresent();
    }
}
