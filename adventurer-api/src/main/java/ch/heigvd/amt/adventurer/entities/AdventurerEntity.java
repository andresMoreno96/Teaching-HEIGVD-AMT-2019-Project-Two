package ch.heigvd.amt.adventurer.entities;

import ch.heigvd.amt.adventurer.api.model.Adventurer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "adventurer_api")
public class AdventurerEntity implements Serializable {

    @Id
    @NotBlank
    private String name;

    private String job;

    @Email
    @Column(nullable = false)
    private String userEmail;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<QuestEntity> participation;

    public  AdventurerEntity(){}

    public AdventurerEntity(Adventurer adventurer, String userEmail) {

        this.name=adventurer.getName();
        this.job=adventurer.getJob();
        this.userEmail=userEmail;


    }

    public Adventurer toAdventurer() {
        return new Adventurer().name(name).job(job);
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Set<QuestEntity> getParticipation() {
        return participation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
