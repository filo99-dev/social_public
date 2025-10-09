package org.elis.social.repository.jpa;

import org.elis.social.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HashtagRepositoryJpa extends JpaRepository<Hashtag,Long> {
    boolean existsByName(String name);
    Optional<Hashtag> findByName(String name);
    @Query("select h from Hashtag h left join h.posts as p group by h order by count(p) desc limit 10")
    List<Hashtag> find10MostPopular();
}
