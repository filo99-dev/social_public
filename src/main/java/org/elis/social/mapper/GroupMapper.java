package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.group.ResponseGroupDTO;
import org.elis.social.model.ChatGroup;
import org.elis.social.model.Utente;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final UtenteMapper utenteMapper;
    public ResponseGroupDTO toResponseGroupDTO(ChatGroup entity, Utente tokenUser)
    {
        ResponseGroupDTO dto = new ResponseGroupDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner(utenteMapper.toResponseUserDto(tokenUser,entity.getOwner()));
        dto.setMembers(entity.getMembers().stream().map(t->utenteMapper.toResponseUserDto(tokenUser,entity.getOwner())).toList());
        return dto;
    }
}
