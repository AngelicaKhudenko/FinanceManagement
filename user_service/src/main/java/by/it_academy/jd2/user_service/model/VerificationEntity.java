package by.it_academy.jd2.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name="verification",schema = "app")
public class VerificationEntity {
    @Id
    private String mail;
    private String code;

    public VerificationEntity() {
    }

    public VerificationEntity(String mail, String code) {
        this.mail = mail;
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
