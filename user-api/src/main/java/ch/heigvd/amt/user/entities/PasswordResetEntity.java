package ch.heigvd.amt.user.entities;

import org.hibernate.annotations.GenerationTime;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(schema = "user_api")
public class PasswordResetEntity implements Serializable {

    @Id
    private String email;

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity user;

    @Column(nullable = false)
    private String uuid;

    public PasswordResetEntity() {
        uuid = UUID.randomUUID().toString();
    }

    public PasswordResetEntity(UserEntity user) {
        this();
        setEmail(user.getEmail());
        setUser(user);
    }

    public String getEmail() {
        return email;
    }

    public String getUuid() {
        return uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
