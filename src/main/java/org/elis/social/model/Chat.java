package org.elis.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "chats",fetch = FetchType.EAGER)
    private List<Utente> users;
    @OneToMany(mappedBy = "chat",orphanRemoval = true)
    private List<Message> messages;



    @PreRemove
    void detachUsers()
    {
        for(Utente u : users)
        {
            u.getChats().removeIf(t->t.getId().equals(this.id));
        }
    }

}
