package org.elis.social.repository.jpa;

import org.elis.social.model.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepositoryJpa extends JpaRepository<ChatGroup,Long> {
}
