package org.elis.social.service.definition;

import org.elis.social.dto.request.comment.InsertCommentDTO;
import org.elis.social.dto.response.comment.ResponseCommentDTO;
import org.elis.social.model.Utente;

import java.util.List;

public interface CommentService {
    void insert(InsertCommentDTO dto, Utente utente);
    List<ResponseCommentDTO> findAllByPostId(Long id);
    void deleteById(Long id, Utente utente);
}
