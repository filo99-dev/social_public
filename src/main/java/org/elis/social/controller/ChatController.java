package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.chat.InsertChatDTO;
import org.elis.social.dto.response.chat.ResponseChatDTO;
import org.elis.social.model.Utente;
import org.elis.social.service.definition.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/base/chat")
    public ResponseEntity<ResponseChatDTO> insert(@Valid @RequestBody InsertChatDTO dto, Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        return ResponseEntity.ok(chatService.insert(dto,u));
    }
    @GetMapping("/base/chat")
    public ResponseEntity<List<ResponseChatDTO>> findAllByOwner(Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        return ResponseEntity.ok(chatService.findAllByUserId(u.getId(),u));
    }
    @DeleteMapping("/base/chat/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication auth)
    {
        Utente u = (Utente)auth.getPrincipal();
        chatService.deleteById(id,u);
        return ResponseEntity.ok().build();

    }
}