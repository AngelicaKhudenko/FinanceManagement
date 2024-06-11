package by.it_academy.jd2.audit_service.model;

import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="audit",schema = "app")
public class AuditEntity {
    @Id
    @Column(name = "id_subject")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Column(name = "id_user")
    private UUID user;
    private String text;
    @Enumerated(EnumType.STRING)
    private ETypeEssence type;
    @Column(name = "id_object")
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid,
                       LocalDateTime creation,
                       UUID user,
                       String text,
                       ETypeEssence type,
                       String id) {

        this.uuid = uuid;
        this.creation = creation;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
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

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ETypeEssence getType() {
        return type;
    }

    public void setType(ETypeEssence type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
