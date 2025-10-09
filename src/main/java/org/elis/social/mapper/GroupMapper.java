package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.group.ResponseGroupDTO;
import org.elis.social.model.ChatGroup;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final UtenteMapper utenteMapper;
    public ResponseGroupDTO toResponseGroupDTO(ChatGroup entity)
    {
        ResponseGroupDTO dto = new ResponseGroupDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner(utenteMapper.toResponseUserDto(entity.getOwner()));
        dto.setMembers(entity.getMembers().stream().map(utenteMapper::toResponseUserDto).toList());
        return dto;
    }
}
