package by.it_academy.jd2.account_service.core.dto;

import by.it_academy.jd2.account_service.core.enums.EAccountType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCUDTO {
    @NotEmpty
    @NotNull
    private String title;
    @NotEmpty
    @NotNull
    private String description;
    @NotNull
    private EAccountType type;
    @NotNull
    private UUID currency;

    public boolean correctConstants(EAccountType accountType) {

        return EAccountType.getByName(accountType.name()).isPresent();
    }
}
