package org.elis.social.service.definition;

import org.elis.social.dto.request.message.InsertMessageDTO;
import org.elis.social.dto.request.message.UpdateMessageDTO;
import org.elis.social.dto.response.message.ResponseMessageDTO;
import org.elis.social.model.Utente;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MessageService {
    ResponseMessageDTO insert(InsertMessageDTO dto, Utente sender);
    ResponseMessageDTO update(UpdateMessageDTO dto, Utente sender);
    List<ResponseMessageDTO> findAllByGroupId(Long id, Utente utente);
    List<ResponseMessageDTO> findAllByChatId(Long id, Utente utente);

}
