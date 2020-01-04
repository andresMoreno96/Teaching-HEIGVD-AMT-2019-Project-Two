package ch.heigvd.amt.user.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(schema = "user-api")
public class PasswordResetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date expireOn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    public PasswordResetEntity() {}

    public PasswordResetEntity(UserEntity user) {
        this();

        setUser(user);

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 2);
        setExpireOn(calendar.getTime());
    }

    public long getId() {
        return id;
    }

    public Date getExpireOn() {
        return expireOn;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExpireOn(Date expireOn) {
        this.expireOn = expireOn;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
