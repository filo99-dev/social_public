package org.elis.social.service.definition;

import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.dto.request.post.UpdatePostDTO;
import org.elis.social.dto.response.PagedEntity;
import org.elis.social.dto.response.post.ResponsePostDTO;
import org.elis.social.model.Utente;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    List<ResponsePostDTO> findAllByHashtagName(String hashtag, Utente tokenUser);
    List<ResponsePostDTO> findAll(Utente utente);
    ResponsePostDTO insert(InsertPostDTO dto, Utente utente);
    void deleteById(Long id, Utente utente);
    ResponsePostDTO update(UpdatePostDTO dto, Utente utente);
    List<ResponsePostDTO> findAllByLoggedUser(Utente utente);
    List<ResponsePostDTO> findAllByUserId(Long id,Utente utente);
    ResponsePostDTO likeById(Long id, Utente utente);
}
