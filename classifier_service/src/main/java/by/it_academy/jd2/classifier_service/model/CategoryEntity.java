package by.it_academy.jd2.classifier_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="category",schema = "app")
public class CategoryEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime update;
    private String title;

    public CategoryEntity() {
    }

    public CategoryEntity(UUID uuid,
                          LocalDateTime creation,
                          LocalDateTime update,
                          String title) {

        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.title = title;
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
}
