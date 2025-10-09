package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.comment.InsertCommentDTO;
import org.elis.social.dto.response.comment.ResponseCommentDTO;
import org.elis.social.model.Utente;
import org.elis.social.service.definition.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all/comment/post/{id}")
    public ResponseEntity<List<ResponseCommentDTO>> findAllByPostId(@PathVariable Long id){
        return ResponseEntity.ok(commentService.findAllByPostId(id));
    }
    @PostMapping("/base/comment")
    public ResponseEntity<Void> insert(@Valid @RequestBody InsertCommentDTO dto, Authentication auth){
        Utente u = (Utente)auth.getPrincipal();
        commentService.insert(dto,u);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/base/comment/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        commentService.deleteById(id,u);
        return ResponseEntity.ok().build();

    }
}