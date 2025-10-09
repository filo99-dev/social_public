package org.elis.social.repository.jpa;

import jakarta.transaction.Transactional;
import org.elis.social.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepositoryJpa extends JpaRepository<Message,Long> {
    @Query("select m from Message m where m in (select c.messages from ChatGroup c where c.id=:id)")
    List<Message> findAllByGroupId(Long id);
    @Query("select m from Message m where m in (select c.messages from Chat c where c.id=:id)")
    List<Message> findAllByChatId(Long id);
    @Query("select case when (count(m)>0) then true else false end from Message m where m.id=:messageId and m in (select u.messages from Utente u where m.id=:messageId and u.id=:userId)")
    Boolean checkMessageOwnership(Long userId, Long messageId);
}
