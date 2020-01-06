package ch.heigvd.amt.user.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(schema = "user_api")
public class PasswordResetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private UserEntity user;

    public PasswordResetEntity() {}

    public PasswordResetEntity(UserEntity user) {
        this();
        setUser(user);
    }

    public long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
