package org.elis.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    private Ruolo role;
    @Column(nullable = false)
    private String phoneNumber;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id","followers_id"}))
    private List<Utente> followers;
    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id","followed_id"}))
    private List<Utente> followed;
    @OneToMany(mappedBy = "owner")
    private List<Post> posts;
    @ManyToMany
    private List<Post> likedPosts;
    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"users_id","chats_id"}))
    private List<Chat> chats;
    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"members_id","groups_id"}))
    private List<ChatGroup> chatGroups;
    @OneToMany(mappedBy = "owner",orphanRemoval = true)
    private List<ChatGroup> createdGroups;
    @OneToMany(mappedBy = "sender")
    private List<Message> messages;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.toString()));
    }
}
