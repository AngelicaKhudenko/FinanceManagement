package by.it_academy.jd2.account_service.model;

import by.it_academy.jd2.account_service.core.enums.EAccountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="account",schema = "app")
public class AccountEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "id_user")
    private UUID user;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime update;
    private String title;
    private String description;
    private double balance;
    @Enumerated(EnumType.STRING)
    private EAccountType type;
    private UUID currency;

    public AccountEntity() {
    }

    public AccountEntity(UUID uuid,
                         UUID user,
                         LocalDateTime creation,
                         LocalDateTime update,
                         String title,
                         String description,
                         double balance,
                         EAccountType type,
                         UUID currency) {

        this.uuid = uuid;
        this.user = user;
        this.creation = creation;
        this.update = update;
        this.title = title;
        this.description = description;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public EAccountType getType() {
        return type;
    }

    public void setType(EAccountType type) {
        this.type = type;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }
}
