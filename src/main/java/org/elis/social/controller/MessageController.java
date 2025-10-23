package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.message.InsertMessageDTO;
import org.elis.social.dto.request.message.UpdateMessageDTO;
import org.elis.social.dto.response.message.ResponseMessageDTO;
import org.elis.social.model.Utente;
import org.elis.social.service.definition.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/base/message")
    public ResponseEntity<ResponseMessageDTO> insert(@Valid @RequestBody InsertMessageDTO dto, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(messageService.insert(dto,u));
    }
    @GetMapping("/base/message/group/{id}")
    public ResponseEntity<List<ResponseMessageDTO>> findAllByGroupId(@PathVariable Long id, Authentication auth){
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(messageService.findAllByGroupId(id,u));
    }
    @GetMapping("/base/message/chat/{id}")
    public ResponseEntity<List<ResponseMessageDTO>> findAllByChatId(@PathVariable Long id, Authentication auth){
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(messageService.findAllByChatId(id,u));
    }

}