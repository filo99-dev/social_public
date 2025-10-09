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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {
    private final UtenteMapper utenteMapper;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ResponseUserDTO> findAllFollowersByUserId(Long id) {

        return utenteRepositoryJpa.findUserWithFollowersById(id)
                .orElseThrow(()->new NotFoundException("utente non trovato per id: "+id))
                .getFollowers().stream().map(utenteMapper::toResponseUserDto).toList();
    }

    @Override
    public ResponseUserDTO findById(Long id) {
        return utenteMapper.toResponseUserDto(utenteRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("utente non trovato per id: "+id)));
    }

    @Override
    public List<ResponseUserDTO> findAllUserLikesByPostId(Long id) {
        return utenteRepositoryJpa.findLikesByPostId(id).stream().map(utenteMapper::toResponseUserDto).toList();
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
        return utenteMapper.toResponseUserDto(utenteLogin);
    }

    @Override
    public void follow(InsertFollowDTO dto, Utente follower) {
        Utente utenteWithFollowers = utenteRepositoryJpa.findUserWithFollowersById(dto.getToFollowUserId()).orElseThrow(()->new NotFoundException("utente non trovato per id: "+dto.getToFollowUserId()));
        Utente temp = null;
        for(Utente u : utenteWithFollowers.getFollowers())
        {
            if(u.getId().equals(follower.getId()))
            {
                temp=u;
            }
        }
        if(temp!=null)
        {
            utenteWithFollowers.getFollowers().remove(temp);
        }
        else{
            utenteWithFollowers.getFollowers().add(follower);
        }
        utenteRepositoryJpa.save(utenteWithFollowers);
    }
}
