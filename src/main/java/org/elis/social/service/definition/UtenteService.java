package org.elis.social.service.definition;

import org.elis.social.dto.request.utente.InsertFollowDTO;
import org.elis.social.dto.request.utente.LoginDTO;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.model.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteService {
    List<ResponseUserDTO> findAllFollowersByUserId(Long id);
    ResponseUserDTO findById(Long id);
    List<ResponseUserDTO> findAllUserLikesByPostId(Long id);
    void registerBaseUser(RegisterUserDTO dto);
    ResponseUserDTO login(LoginDTO dto);
    void follow(InsertFollowDTO dto, Utente follower);
}
