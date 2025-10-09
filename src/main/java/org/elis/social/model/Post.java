package org.elis.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente owner;
    @ManyToMany(mappedBy = "likedPosts")
    private List<Utente> userLikes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"hashtag_id","post_id"}))
    private List<Hashtag> hashtags;
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;


    @PreRemove
    private void detatchLikes(){
        for(Utente utenteLike : userLikes)
        {
            utenteLike.getLikedPosts().removeIf(t->t.getId().equals(this.id));
        }
    }
}
