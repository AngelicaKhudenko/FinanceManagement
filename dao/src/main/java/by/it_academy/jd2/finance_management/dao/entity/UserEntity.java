package by.it_academy.jd2.finance_management.dao.entity;

import by.it_academy.jd2.finance_management.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.core.enums.EUserStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private LocalDateTime update;
    private Long version;
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
                      Long version,
                      String mail,
                      String fio,
                      EUserRole role,
                      EUserStatus status,
                      String password) {
        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.version = version;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(LocalDateTime version) {

        Instant instant = version.atZone(ZoneId.systemDefault()).toInstant();
        this.version = instant.toEpochMilli();
    }

    public void setVersion(Long version) {
        this.version = version;
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
