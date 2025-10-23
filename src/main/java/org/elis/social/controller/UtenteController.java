package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.utente.InsertFollowDTO;
import org.elis.social.dto.request.utente.LoginDTO;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.model.Utente;
import org.elis.social.security.jwt.JwtUtilities;
import org.elis.social.service.definition.UtenteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UtenteController {
    private final UtenteService utenteService;
    private final JwtUtilities jwtUtilities;

    @GetMapping("/base/utente")
    public ResponseEntity<List<ResponseUserDTO>> findALl(Authentication auth){
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findAll(u)) ;
    }
    @GetMapping("/base/findbyid/{id}")
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable Long id, Authentication auth) {
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findById(id,u));
    }
    @GetMapping("/base/findbyusername/{username}")
    public ResponseEntity<ResponseUserDTO>  findUtenteWithFollowByUsername(@PathVariable String username, Authentication auth) {
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findWithFollowByUsername(username,u));
    }
    @GetMapping("/all/username/check/{username}")
    public ResponseEntity<Void> checkUsername(@PathVariable String username) {
        utenteService.checkUsernameAvailability(username);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/base/likes/post/{id}")
    public ResponseEntity<List<ResponseUserDTO>> findAllLikesByPostId(@PathVariable Long id, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findAllUserLikesByPostId(id,u));
    }

    @PostMapping("/all/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDTO dto)
    {
        ResponseUserDTO response = utenteService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION,jwtUtilities.generaToken(response)).build();
    }
    @PostMapping("/all/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDTO dto)
    {
        utenteService.registerBaseUser(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/base/follow")
    public ResponseEntity<Void> follow(@Valid @RequestBody InsertFollowDTO dto, Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        utenteService.follow(dto,u);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/base/followers")
    public ResponseEntity<List<ResponseUserDTO>> findFollowersByUserToken(Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findAllFollowersByUserId(u.getId(),u));
    }
    @GetMapping("/base/followers/{id}")
    public ResponseEntity<List<ResponseUserDTO>> findFollowersByUserId(@PathVariable Long id,Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        return ResponseEntity.ok(utenteService.findAllFollowersByUserId(id,u));
    }

}
