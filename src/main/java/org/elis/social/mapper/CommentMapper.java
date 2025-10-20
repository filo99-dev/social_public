package org.elis.social.mapper;


import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.comment.ResponseCommentDTO;
import org.elis.social.model.Comment;
import org.elis.social.model.Utente;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final UtenteMapper utenteMapper;
    public ResponseCommentDTO toResponseCommentDto(Comment entity, Utente tokenUser){
        ResponseCommentDTO dto = new ResponseCommentDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreationDateTime());
        dto.setOwner(utenteMapper.toResponseUserDto(tokenUser,entity.getOwner()));
        return dto;
    }
}
