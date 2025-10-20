package org.elis.social.repository.jpa;

import org.elis.social.model.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepositoryJpa extends JpaRepository<ChatGroup,Long> {
    @Query("select c from ChatGroup c join c.members as member where member.id=:userId")
    List<ChatGroup> findAllByTokenUserId(Long userId);
}
