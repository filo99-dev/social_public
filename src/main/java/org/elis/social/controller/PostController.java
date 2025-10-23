package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.dto.request.post.UpdatePostDTO;
import org.elis.social.dto.response.PagedEntity;
import org.elis.social.dto.response.post.ResponsePostDTO;
import org.elis.social.model.Utente;
import org.elis.social.service.definition.PostService;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @GetMapping("/base/post/{hashtag}")
    public ResponseEntity<List<ResponsePostDTO>> getPostsByHashtag(@PathVariable String hashtag, Authentication auth) {
        Utente u =(Utente) auth.getPrincipal();
        return ResponseEntity.ok(postService.findAllByHashtagName(hashtag,u));
    }
    @GetMapping("/base/post/findall")
    public ResponseEntity<List<ResponsePostDTO>> postsByPage(Authentication auth) {
        Utente u =(Utente) auth.getPrincipal();
        return ResponseEntity.ok(postService.findAll(u));
    }
    //Prende tutti i post in base all'id passato tramite path
    @GetMapping("/base/post/user/{id}")
    public ResponseEntity<List<ResponsePostDTO>> findAllByUserId(@PathVariable Long id, Authentication auth){
        Utente u =(Utente) auth.getPrincipal();
        return ResponseEntity.ok(postService.findAllByUserId(id,u));
    }
    //Prende tutti i post dell'utente proprietario del token
    @GetMapping("/base/post")
    public ResponseEntity<List<ResponsePostDTO>> findAllByLoggedUser(Authentication auth)
    {
        Utente u =(Utente) auth.getPrincipal();
        return ResponseEntity.ok(postService.findAllByLoggedUser(u));
    }
    //Permette di inserire un post
    //Testo obbligatorio not blank
    //hashtag opzionali ma controllo not blank se presenti
    @PostMapping("/base/post")
    public ResponseEntity<ResponsePostDTO> insert(@Valid @RequestBody InsertPostDTO dto, Authentication auth){
        Utente u =(Utente) auth.getPrincipal();
        ResponsePostDTO response = postService.insert(dto,u);
        return ResponseEntity.ok(response);
    }
    //Permette di aggiornare un post
    //I dati da passare sono opzionali (null), se non vengono inviati il campo non viene aggiornato
    //nuovo testo not blank
    //nuovi hashtag not blank -> se inviata una lista vuota vengono rimossi gli hashtag
    @PatchMapping("/base/post")
    public ResponseEntity<ResponsePostDTO> update(@Valid @RequestBody UpdatePostDTO dto, Authentication auth)
    {
        Utente u =(Utente) auth.getPrincipal();
        ResponsePostDTO response = postService.update(dto,u);
        return ResponseEntity.ok(response);
    }
    //Permette di mettere mi piace ad un post
    //effetto toggle
    @PatchMapping("/base/post/like/{id}")
    public ResponseEntity<ResponsePostDTO> likeByPostId(@PathVariable Long id,Authentication auth){
        Utente u =(Utente) auth.getPrincipal();
        ResponsePostDTO response = postService.likeById(id,u);
        return ResponseEntity.ok(response);
    }
    //Permette di eliminare un post
    //like e commenti associati verranno RIMOSSI DAL DB
    @DeleteMapping("/base/post/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication auth)
    {
        Utente u =(Utente) auth.getPrincipal();
        postService.deleteById(id,u);
        return ResponseEntity.ok().build();
    }
}
