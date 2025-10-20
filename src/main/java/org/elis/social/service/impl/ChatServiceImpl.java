package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.chat.InsertChatDTO;
import org.elis.social.dto.response.chat.ResponseChatDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.errorhandling.exceptions.OwnershipException;
import org.elis.social.mapper.ChatMapper;
import org.elis.social.model.Chat;
import org.elis.social.model.Message;
import org.elis.social.model.Ruolo;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.ChatRepositoryJpa;
import org.elis.social.repository.jpa.MessageRepositoryJpa;
import org.elis.social.repository.jpa.UtenteRepositoryJpa;
import org.elis.social.service.definition.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepositoryJpa chatRepositoryJpa;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final MessageRepositoryJpa messageRepositoryJpa;
    private final ChatMapper chatMapper;

    @Override
    public ResponseChatDTO insert(InsertChatDTO dto, Utente sender) {

        Chat newChat = new Chat();
        sender=findUserWithChat(sender.getId());
        Utente receiver = findUserWithChat(dto.getReceiverId());
        //nelle chat di sender non deve esserci una chat che tra i membri abbia il receiver
        if(Boolean.TRUE.equals(chatRepositoryJpa.checkExistingChat(sender.getId(),dto.getReceiverId()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"la chat tra questi due utenti esiste gia'");
        }
        newChat.setName(sender.getUsername()+"_"+receiver.getUsername());
        newChat = chatRepositoryJpa.save(newChat);
        setUserChats(sender, newChat);
        setUserChats(receiver,newChat);

        Message firstMessage = new Message();
        firstMessage.setText(dto.getFirstMessage());
        firstMessage.setSender(sender);
        firstMessage.setChat(newChat);
        messageRepositoryJpa.save(firstMessage);
        newChat.setUsers(List.of(sender,receiver));

        return chatMapper.toResponseChatDto(newChat,sender);
    }

    @Override
    public List<ResponseChatDTO> findAllByUserId(Long id,Utente tokenUser) {
        return chatRepositoryJpa.findAllByUserId(id).stream().map(t->chatMapper.toResponseChatDto(t,tokenUser)).toList();
    }

    @Override
    public void deleteById(Long id, Utente utente) {
        Chat toDelete = chatRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("chat non trovata per id: "+id));
        if(utente.getRole()== Ruolo.BASE)
        {
            if(toDelete.getUsers().stream().noneMatch(t->t.getId().equals(utente.getId())))
            {
                throw new OwnershipException("La chat che stai cercando di eliminare non ti appartiene");
            }
        }
        chatRepositoryJpa.delete(toDelete);
    }


    private void setUserChats(Utente utente, Chat chat){
        utente.getChats().add(chat);
        utenteRepositoryJpa.save(utente);
    }
    private Utente findUserWithChat(Long userId)
    {
        return utenteRepositoryJpa.findUserWithChatsByUserId(userId).orElseThrow(()->new NotFoundException("utente non trovato per id: "+userId));
    }
}
