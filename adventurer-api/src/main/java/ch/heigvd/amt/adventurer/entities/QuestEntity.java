package ch.heigvd.amt.adventurer.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "adventurer_api")
public class QuestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ColumnDefault("false")
    private boolean ended;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private AdventurerEntity owner;

    @ManyToMany(mappedBy = "participation", fetch = FetchType.LAZY)
    private Set<AdventurerEntity> participants;
}
