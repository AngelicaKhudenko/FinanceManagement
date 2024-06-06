package by.it_academy.jd2.audit_service.token.enums;

import java.util.Optional;

public enum EUserRole {
    ADMIN("Администратор"),
    USER("Пользователь"),
    MANAGER("Менеджер")
    ;

    private final String description;

    EUserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Получение роли пользователя по её наименованию.
     * @param name - Наименование роли пользователя, которую необходимо получить.
     * @return Optional<EUserRole> - объект Optional, содержащий роль пользователя, если такая роль найдена,
     *         или пустой Optional, если роль с указанным наименованием не найдена.
     */
    public static Optional<EUserRole> getByName(String name){
        for (EUserRole value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
