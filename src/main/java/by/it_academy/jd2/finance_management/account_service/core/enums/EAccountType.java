package by.it_academy.jd2.finance_management.account_service.core.enums;

import by.it_academy.jd2.finance_management.user_service.core.enums.EUserRole;

import java.util.Optional;

public enum EAccountType {

    CASH("Наличные деньги"),
    BANK_ACCOUNT("Счет в банке"),
    BANK_DEPOSIT("Депозит в банке")
    ;

    private final String description;

    EAccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<EAccountType> getByName(String name){
        for (EAccountType value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
