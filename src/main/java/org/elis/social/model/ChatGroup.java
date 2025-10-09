package org.elis.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Utente owner;
    @ManyToMany(mappedBy = "chatGroups")
    private List<Utente> members;
    @OneToMany(mappedBy = "chatGroup",orphanRemoval = true)
    private List<Message> messages;

    @PreRemove
    private void detatchMembers(){
        for(Utente u : members)
        {
            u.getChatGroups().removeIf(t->t.getId().equals(this.id));
        }
    }
}
