package by.it_academy.jd2.finance_management.user_service.model;

import by.it_academy.jd2.finance_management.user_service.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.user_service.core.enums.EUserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users",schema = "app")
public class UserEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime update;
    private String mail;
    private String fio;
    @Enumerated(EnumType.STRING)
    private EUserRole role;
    @Enumerated(EnumType.STRING)
    private EUserStatus status;
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid,
                      LocalDateTime creation,
                      LocalDateTime update,
                      String mail,
                      String fio,
                      EUserRole role,
                      EUserStatus status,
                      String password) {
        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
