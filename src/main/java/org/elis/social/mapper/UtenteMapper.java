package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.model.Utente;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtenteMapper {
    public Utente fromRegisterUserDto(RegisterUserDTO dto){
        Utente u = new Utente();
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getEmail());
        u.setPhoneNumber(dto.getPhoneNumber());
        return u;
    }
    public ResponseUserDTO toResponseUserDto(Utente entity)
    {
        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        return dto;
    }
}
