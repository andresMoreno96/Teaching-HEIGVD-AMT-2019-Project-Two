package ch.heigvd.amt.user.entities;

import ch.heigvd.amt.user.api.model.User;
import ch.heigvd.amt.user.api.model.UserNoPassword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "user")
@Table(name = "users", schema = "user-api")
public class UserEntity implements Serializable {

    @Id
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    public UserEntity() {}

    public UserEntity(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
    }

    public UserNoPassword toUser() {
        return new UserNoPassword()
                .email(email)
                .firstName(firstName)
                .lastName(lastName);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
