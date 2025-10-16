package org.elis.social.repository.jpa;

import org.elis.social.model.Utente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtenteRepositoryJpa extends JpaRepository<Utente,Long> {
    @Query("select u from Utente u where u.username=:username")
    Optional<Utente> findByUsername(String username);

    @Query("select u from Utente u left join u.chats where u.id=:id")
    Optional<Utente> findUserWithChatsByUserId(Long id);
    @Query("select u from Utente u left join u.likedPosts where u.id=:id")
    Optional<Utente> findUserWithLikesByUserId(Long id);
    @Query("select u from Utente u left join u.chatGroups where u.id in(:ids)")
    List<Utente> findAllWithGroupsByIds(List<Long> ids);

    @Query("select case when (count(u)>0) then true else false end from Utente u where u.id=:userId and u in (select c.members from ChatGroup c where c.id=:groupId)")
    Boolean userIsInGroup(Long userId, Long groupId);

    @Query("select u from Utente u join u.likedPosts as post where post.id=:id")
    List<Utente> findLikesByPostId(Long id);
    @Query("select u from Utente u left join u.followers where u.id=:id")
    Optional<Utente> findUserWithFollowersById(Long id);

    Boolean existsByUsername(String username);

}
