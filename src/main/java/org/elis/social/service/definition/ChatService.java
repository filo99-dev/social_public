package org.elis.social.service.definition;

import org.elis.social.dto.request.chat.InsertChatDTO;
import org.elis.social.dto.response.chat.ResponseChatDTO;
import org.elis.social.model.Utente;

import java.util.List;

public interface ChatService {
    ResponseChatDTO insert(InsertChatDTO dto, Utente utente);
    List<ResponseChatDTO> findAllByUserId(Long id,Utente tokenUser);
    void deleteById(Long id, Utente utente);
}
