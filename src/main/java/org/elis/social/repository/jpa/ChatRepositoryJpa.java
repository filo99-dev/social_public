package org.elis.social.repository.jpa;

import jakarta.transaction.Transactional;
import org.elis.social.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatRepositoryJpa extends JpaRepository<Chat,Long> {

    @Query("select c from Chat c join c.users as user where user.id=:id")
    List<Chat> findAllByUserId(Long id);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Chat c JOIN c.users u WHERE u.id IN (:userId1, :userId2) GROUP BY c.id HAVING COUNT(DISTINCT u.id) = 2")
    Boolean checkExistingChat(Long userId1, Long userId2);

}
