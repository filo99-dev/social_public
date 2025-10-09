package org.elis.social.repository.jpa;

import org.elis.social.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepositoryJpa extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.post.id=:id")
    List<Comment> findAllByPostId(Long id);
    @Query("select c from Comment c where c.owner.id=:id")
    List<Comment> findAllByUserId(Long id);
}
