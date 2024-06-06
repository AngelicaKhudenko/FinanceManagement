package by.it_academy.jd2.audit_service.core.enums;


import java.util.Optional;

public enum ETypeEssence {
    USER("Пользователь"),
    REPORT("Отчет"),
    OPERATION("Операция"),
    CATEGORY("Категория"),
    CURRENCY("Валюта"),
    ACCOUNT("Счет")
    ;

    private final String description;

    ETypeEssence(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<ETypeEssence> getByName(String name){
        for (ETypeEssence value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

}
