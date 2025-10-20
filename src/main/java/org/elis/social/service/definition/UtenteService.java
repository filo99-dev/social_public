package org.elis.social.service.definition;

import org.elis.social.dto.request.utente.InsertFollowDTO;
import org.elis.social.dto.request.utente.LoginDTO;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.dto.response.utente.ResponseUtenteWithFollowFlagDTO;
import org.elis.social.model.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteService {
    ResponseUtenteWithFollowFlagDTO findWithFollowByUsername(String username, Utente tokenUser);
    void checkUsernameAvailability(String username);
    List<ResponseUserDTO> findAllFollowersByUserId(Long id);
    ResponseUtenteWithFollowFlagDTO findById(Long id, Utente tokenUser);
    List<ResponseUserDTO> findAllUserLikesByPostId(Long id);
    void registerBaseUser(RegisterUserDTO dto);
    ResponseUserDTO login(LoginDTO dto);
    void follow(InsertFollowDTO dto, Utente follower);
}
