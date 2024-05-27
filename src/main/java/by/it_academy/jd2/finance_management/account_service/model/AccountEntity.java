package by.it_academy.jd2.finance_management.account_service.model;

import by.it_academy.jd2.finance_management.account_service.core.enums.EAccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="account",schema = "app")
public class AccountEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Column(name = "dt_update")
    private LocalDateTime update;
    private String title;
    private String description;
    private double balance;
    private EAccountType type;
    private UUID currency;

    public AccountEntity() {
    }

    public AccountEntity(UUID uuid,
                         LocalDateTime creation,
                         LocalDateTime update,
                         String title,
                         String description,
                         double balance,
                         EAccountType type,
                         UUID currency) {

        this.uuid = uuid;
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
