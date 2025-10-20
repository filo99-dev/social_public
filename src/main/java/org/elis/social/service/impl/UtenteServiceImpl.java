package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.utente.InsertFollowDTO;
import org.elis.social.dto.request.utente.LoginDTO;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.mapper.UtenteMapper;
import org.elis.social.model.Ruolo;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.UtenteRepositoryJpa;
import org.elis.social.service.definition.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {
    private final UtenteMapper utenteMapper;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDTO findWithFollowByUsername(String username,Utente tokenUser) {
        Utente toFind = utenteRepositoryJpa.findByUsername(username).orElseThrow(() -> new NotFoundException("utente non trovato per username "+username));

        return utenteMapper.toResponseUserDto(tokenUser,toFind);
    }

    @Override
    public void checkUsernameAvailability(String username) {
        if(utenteRepositoryJpa.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username già utilizzato");
        }
    }

    @Override
    public List<ResponseUserDTO> findAllFollowersByUserId(Long id, Utente tokenUser) {

        return utenteRepositoryJpa.findUserWithFollowersById(id)
                .orElseThrow(()->new NotFoundException("utente non trovato per id: "+id))
                .getFollowers().stream().map(t->utenteMapper.toResponseUserDto(tokenUser,t)).toList();
    }

    @Override
    public ResponseUserDTO findById(Long id, Utente tokenUser) {
        var toFind = utenteRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("utente non trovato per id: "+id));
        return utenteMapper.toResponseUserDto(tokenUser,toFind);
    }

    @Override
    public List<ResponseUserDTO> findAllUserLikesByPostId(Long id,Utente tokenUser) {
        return utenteRepositoryJpa.findLikesByPostId(id).stream().map(t->utenteMapper.toResponseUserDto(t,tokenUser)).toList();
    }

    @Override
    public void registerBaseUser(RegisterUserDTO dto) {
        Utente entity = utenteMapper.fromRegisterUserDto(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(Ruolo.BASE);
        utenteRepositoryJpa.save(entity);
    }

    @Override
    public ResponseUserDTO login(LoginDTO dto) {
        Utente utenteLogin = utenteRepositoryJpa.findByUsername(dto.getUsername()).orElseThrow(()->new NotFoundException("utente non trovato per username: "+dto.getUsername()));
        if(!passwordEncoder.matches(dto.getPassword(),utenteLogin.getPassword()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"credenziali errate");
        }
        return utenteMapper.toResponseUserDto(null,utenteLogin);
    }

    @Override
    public void follow(InsertFollowDTO dto, Utente tokenUser) {
        if(dto.getToFollowUserId().equals(tokenUser.getId()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"non puoi seguirti da solo");
        }
        Utente utenteWithFollowers = utenteRepositoryJpa.findUserWithFollowersById(dto.getToFollowUserId()).orElseThrow(()->new NotFoundException("utente non trovato per id: "+dto.getToFollowUserId()));
        Utente temp = null;
        for(Utente u : utenteWithFollowers.getFollowers())
        {
            if(u.getId().equals(tokenUser.getId()))
            {
                temp=u;
            }
        }
        if(temp!=null)
        {
            utenteWithFollowers.getFollowers().remove(temp);
        }
        else{
            utenteWithFollowers.getFollowers().add(tokenUser);
        }
        utenteRepositoryJpa.save(utenteWithFollowers);
    }
}
