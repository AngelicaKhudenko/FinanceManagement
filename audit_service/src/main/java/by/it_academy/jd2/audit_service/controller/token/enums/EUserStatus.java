package by.it_academy.jd2.audit_service.controller.token.enums;

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

    /**
     * Получение статуса пользователя по его наименованию.
     * @param name - Наименование статуса пользователя, который необходимо получить.
     * @return Optional<EUserStatus> - объект Optional, содержащий статус пользователя, если такой статус найден,
     *         или пустой Optional, если статус с указанным наименованием не найден.
     */
    public static Optional<EUserStatus> getByName(String name){
        for (EUserStatus value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
