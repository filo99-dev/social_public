package org.elis.social.service.definition;

import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.dto.request.post.UpdatePostDTO;
import org.elis.social.dto.response.post.ResponsePostDTO;
import org.elis.social.model.Utente;

import java.util.List;

public interface PostService {
    ResponsePostDTO insert(InsertPostDTO dto, Utente utente);
    void deleteById(Long id, Utente utente);
    ResponsePostDTO update(UpdatePostDTO dto, Utente utente);
    List<ResponsePostDTO> findAllByLoggedUser(Utente utente);
    List<ResponsePostDTO> findAllByUserId(Long id);
    ResponsePostDTO likeById(Long id, Utente utente);
}
