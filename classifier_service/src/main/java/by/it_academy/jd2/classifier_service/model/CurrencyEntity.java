package by.it_academy.jd2.classifier_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="currency",schema = "app")
public class CurrencyEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime update;
    private String title;
    private String description;

    public CurrencyEntity() {
    }

    public CurrencyEntity(UUID uuid,
                          LocalDateTime creation,
                          LocalDateTime update,
                          String title,
                          String description) {
        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.title = title;
        this.description = description;
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
}
