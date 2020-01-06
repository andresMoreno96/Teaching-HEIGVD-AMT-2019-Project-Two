package ch.heigvd.amt.adventurer.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Adventurer")
@Table(name = "adventurers", schema = "adventurer_api")
public class AdventurerEntity implements Serializable {

    @Id
    private String name;

    private String job;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @ColumnDefault("0")
    private AdventurerRank rank;

    @Column(nullable = false)
    private String user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participation",
            joinColumns = @JoinColumn(name = "adventurer"),
            inverseJoinColumns = @JoinColumn(name = "quest")
    )
    private Set<QuestEntity> participation;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public AdventurerRank getRank() {
        return rank;
    }

    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setRank(AdventurerRank rank) {
        this.rank = rank;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
