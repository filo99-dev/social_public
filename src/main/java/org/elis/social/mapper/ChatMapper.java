package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.chat.ResponseChatDTO;
import org.elis.social.model.Chat;
import org.elis.social.model.Utente;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final UtenteMapper utenteMapper;
    public ResponseChatDTO toResponseChatDto(Chat entity)
    {
        ResponseChatDTO dto = new ResponseChatDTO();
        dto.setId(entity.getId());
        dto.setMembers(entity.getUsers().stream().map(utenteMapper::toResponseUserDto).toList());
        return dto;
    }
}
