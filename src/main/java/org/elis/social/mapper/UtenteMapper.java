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
    public ResponseUserDTO toResponseUserDto(Utente tokenUser,Utente other)
    {

        ResponseUserDTO response = new ResponseUserDTO();
        response.setUsername(other.getUsername());
        response.setId(other.getId());
        response.setEmail(other.getEmail());
        response.setRole(other.getRole());
        response.setPhoneNumber(other.getPhoneNumber());
        if(tokenUser != null){
            response.setIsFollowed(other.getFollowers().stream().anyMatch(t->t.getId().equals(tokenUser.getId())));
            response.setIsFollowing(other.getFollowers().stream().anyMatch(t->t.getId().equals(tokenUser.getId())));
        }
        return response;
    }
}
