package by.it_academy.jd2.account_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="operation",schema = "app")
public class OperationEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "account")
    private AccountEntity account;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime update;
    private String description;
    private UUID category;
    @Column(name = "value_operation")
    private Double value;
    private UUID currency;

    public OperationEntity() {
    }

    public OperationEntity(UUID uuid,
                           AccountEntity account,
                           LocalDateTime creation,
                           LocalDateTime update,
                           String description,
                           UUID category,
                           Double value,
                           UUID currency) {

        this.uuid = uuid;
        this.account = account;
        this.creation = creation;
        this.update = update;
        this.description = description;
        this.category = category;
        this.value = value;
        this.currency = currency;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }
}
