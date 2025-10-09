package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.message.ResponseMessageDTO;
import org.elis.social.model.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final UtenteMapper utenteMapper;
    public ResponseMessageDTO toResponseMessageDto(Message entity)
    {
        ResponseMessageDTO dto = new ResponseMessageDTO();
        dto.setId(entity.getId());
        dto.setSender(utenteMapper.toResponseUserDto(entity.getSender()));
        dto.setSentAt(entity.getSentAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setText(entity.getText());
        return dto;
    }
}
