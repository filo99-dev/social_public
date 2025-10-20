package org.elis.social.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.group.AddOrRemoveMemberDTO;
import org.elis.social.dto.request.group.InsertGroupDTO;
import org.elis.social.dto.response.group.ResponseGroupDTO;
import org.elis.social.model.Utente;
import org.elis.social.service.definition.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @GetMapping("/base")
    public ResponseEntity<List<ResponseGroupDTO>> base(Authentication auth) {
        Utente u = (Utente) auth.getPrincipal();
        return ResponseEntity.ok(groupService.findAllByTokenUser(u));
    }
    @PostMapping("/base/group")
    public ResponseEntity<ResponseGroupDTO> insert(@Valid @RequestBody InsertGroupDTO dto, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();

        return ResponseEntity.ok(groupService.insert(dto,u));
    }
    @PostMapping("/base/group/addmember")
    public ResponseEntity<Void> addMember(@Valid @RequestBody AddOrRemoveMemberDTO dto, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();
        groupService.addMember(dto,u);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/base/group/removemember")
    public ResponseEntity<Void> removeMember(@Valid @RequestBody AddOrRemoveMemberDTO dto, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();
        groupService.removeMember(dto,u);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/base/group/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth)
    {
        Utente u = (Utente) auth.getPrincipal();
        groupService.delete(id,u);
        return ResponseEntity.ok().build();
    }

}