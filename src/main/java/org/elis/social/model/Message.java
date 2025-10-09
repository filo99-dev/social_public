package org.elis.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String text;
    @CreationTimestamp
    private LocalDateTime sentAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    private Chat chat;
    @ManyToOne
    private ChatGroup chatGroup;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente sender;

}
