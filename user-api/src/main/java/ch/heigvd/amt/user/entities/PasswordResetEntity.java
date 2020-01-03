package ch.heigvd.amt.user.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
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
}
