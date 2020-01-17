package ch.heigvd.amt.adventurer.entities;

import ch.heigvd.amt.adventurer.api.model.Quest;
import ch.heigvd.amt.adventurer.api.model.QuestCreate;
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

    public QuestEntity(QuestCreate questCreate, AdventurerEntity adventurerEntity){

        this.title=questCreate.getTitle();
        this.description=questCreate.getDescription();
        this.owner=adventurerEntity;


    }

    public QuestEntity(Quest quest, AdventurerEntity adventurerEntity) {
        this.id = quest.getId();
        this.description = quest.getDescription();
        this.ended = quest.getEnded();
        this.title = quest.getTitle();
        this.owner = adventurerEntity;


    }

    public Quest toQuest() {
        return new Quest().id(id).description(description)
                .ended(ended).title(title).adventurerName(owner.getName());
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnded() {
        return ended;
    }

    public AdventurerEntity getOwner() {
        return owner;
    }

    public Set<AdventurerEntity> getParticipants() {
        return participants;
    }
}
