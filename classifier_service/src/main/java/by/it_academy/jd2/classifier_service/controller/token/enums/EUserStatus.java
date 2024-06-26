package by.it_academy.jd2.classifier_service.controller.token.enums;

import java.util.Optional;

public enum EUserStatus {
    WAITING_ACTIVATION("Ожидает активации"),
    ACTIVATED("Активирован"),
    DEACTIVATED ("Деактивирован")
    ;

    private final String description;

    EUserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public static Optional<EUserStatus> getByName(String name){
        for (EUserStatus value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
