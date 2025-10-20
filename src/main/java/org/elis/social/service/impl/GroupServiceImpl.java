package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.group.AddOrRemoveMemberDTO;
import org.elis.social.dto.request.group.InsertGroupDTO;
import org.elis.social.dto.response.group.ResponseGroupDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.mapper.GroupMapper;
import org.elis.social.model.ChatGroup;
import org.elis.social.model.Ruolo;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.GroupRepositoryJpa;
import org.elis.social.repository.jpa.UtenteRepositoryJpa;
import org.elis.social.service.definition.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepositoryJpa groupRepositoryJpa;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final GroupMapper groupMapper;
    @Override
    public ResponseGroupDTO insert(InsertGroupDTO dto,Utente utente) {
        dto.getMembersIds().add(utente.getId());
        List<Utente> newMembers = utenteRepositoryJpa.findAllWithGroupsByIds(dto.getMembersIds());
        if(dto.getMembersIds().size()!=newMembers.size())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alcuni utenti non sono validi, ricontrolla i dati inviati");
        }
        ChatGroup newGroup = new ChatGroup();
        newGroup.setMembers(new ArrayList<>());
        newGroup.setName(dto.getName());
        newGroup.setOwner(utente);
        newGroup = groupRepositoryJpa.save(newGroup);
        for(Utente u : newMembers)
        {
            u.getChatGroups().add(newGroup);
            newGroup.getMembers().add(u);
            utenteRepositoryJpa.save(u);
        }
        return groupMapper.toResponseGroupDTO(newGroup,utente);
    }

    @Override
    public void addMember(AddOrRemoveMemberDTO dto, Utente owner) {
       ChatGroup group = findGroupById(dto.getGroupId());
       checkGroupOwner(owner,group);
        Utente toAdd = findUserById(dto.getMemberId());
       if(utenteRepositoryJpa.userIsInGroup(dto.getMemberId(),dto.getGroupId()))
       {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"l'utente e' gia' nel gruppo");
       }
       toAdd.getChatGroups().add(group);
       utenteRepositoryJpa.save(toAdd);
    }

    @Override
    public void removeMember(AddOrRemoveMemberDTO dto, Utente owner) {
        ChatGroup group = findGroupById(dto.getGroupId());
        checkGroupOwner(owner,group);
        Utente toRemove = findUserById(dto.getMemberId());
        if(!utenteRepositoryJpa.userIsInGroup(dto.getMemberId(), dto.getGroupId()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"l'utente non e' nel gruppo");
        }
        ChatGroup temp = null;
        for(ChatGroup c : toRemove.getChatGroups())
        {
            if(c.getId().equals(group.getId()))
            {
                temp=c;
            }
        }
        toRemove.getChatGroups().remove(temp);
        utenteRepositoryJpa.save(toRemove);

    }

    @Override
    public void delete(Long groupId, Utente owner) {
        ChatGroup toRemove = findGroupById(groupId);
        if(owner.getRole()== Ruolo.BASE)
        {
            checkGroupOwner(owner,toRemove);
        }
        groupRepositoryJpa.delete(toRemove);
    }

    private void checkGroupOwner(Utente owner, ChatGroup group)
    {
        if(!group.getOwner().getId().equals(owner.getId()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "non sei il proprietario di questo gruppo");
        }
    }

    private Utente findUserById(Long id){
       return utenteRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("utente non trovato per id: "+id));
    }
    private ChatGroup findGroupById(Long id){
        return groupRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("gruppo non trovato per id: "+id));
    }

}
