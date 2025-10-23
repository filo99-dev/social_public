package org.elis.social.repository.jpa;

import jakarta.transaction.Transactional;
import org.elis.social.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepositoryJpa extends JpaRepository<Post,Long> {

    @Query("select p from Post p join p.hashtags as hashtag where hashtag.name=:hashtag")
    List<Post> findAllByHashtagName(String hashtag);
//    Page<Post> findAllByHashtagsName(String hashtag, Pageable pageable);
    @Query("select p from Post p where p.owner.id=:id")
    List<Post> findAllByUserId(Long id);
    @Query("select p from Post p join p.userLikes as users where users.id=:id")
    List<Post> findAllLikedByUserId(Long id);

}
