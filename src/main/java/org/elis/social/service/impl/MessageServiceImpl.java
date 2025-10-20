package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.message.InsertMessageDTO;
import org.elis.social.dto.request.message.UpdateMessageDTO;
import org.elis.social.dto.response.message.ResponseMessageDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.mapper.MessageMapper;
import org.elis.social.model.Chat;
import org.elis.social.model.ChatGroup;
import org.elis.social.model.Message;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.ChatRepositoryJpa;
import org.elis.social.repository.jpa.GroupRepositoryJpa;
import org.elis.social.repository.jpa.MessageRepositoryJpa;
import org.elis.social.repository.jpa.UtenteRepositoryJpa;
import org.elis.social.service.definition.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageMapper messageMapper;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final MessageRepositoryJpa messageRepositoryJpa;
    private final GroupRepositoryJpa groupRepositoryJpa;
    private final ChatRepositoryJpa chatRepositoryJpa;
    @Override
    public ResponseMessageDTO insert(InsertMessageDTO dto, Utente sender) {
        checkMessageValidity(dto);
        Message toSend = new Message();
        toSend.setText(dto.getText());
        toSend.setSender(sender);
        if(dto.getGroupId()!=null)
        {
            checkIfUserIsInGroup(sender.getId(),dto.getGroupId());
            ChatGroup toSendTo = groupRepositoryJpa.findById(dto.getGroupId()).orElseThrow(()->new NotFoundException("gruppo non trovato per id: "+dto.getGroupId()));
            toSend.setChatGroup(toSendTo);
        }
        else if(dto.getChatId()!=null)
        {
            Chat toSendTo = chatRepositoryJpa.findById(dto.getChatId()).orElseThrow(()->new NotFoundException("chat non trovata per id: "+dto.getChatId()));
            checkIfUserIsInChat(toSendTo, sender.getId());
            toSend.setChat(toSendTo);
        }
        toSend=messageRepositoryJpa.save(toSend);
        return messageMapper.toResponseMessageDto(toSend,sender);
    }

    @Override
    public ResponseMessageDTO update(UpdateMessageDTO dto, Utente sender) {
        if(!messageRepositoryJpa.checkMessageOwnership(sender.getId(),dto.getId())){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "il messaggio che vuoi modificare non ti appartiene");
        }
        Message toUpdate = messageRepositoryJpa.findById(dto.getId()).orElseThrow(()->new NotFoundException("messaggio non trovato per id: "+dto.getId()));
        toUpdate.setText(dto.getNewText());
        return messageMapper.toResponseMessageDto(messageRepositoryJpa.save(toUpdate),sender);
    }

    @Override
    public List<ResponseMessageDTO> findAllByGroupId(Long id, Utente utente) {
        checkIfUserIsInGroup(utente.getId(),id);
        return messageRepositoryJpa.findAllByGroupId(id).stream().map(t->messageMapper.toResponseMessageDto(t,utente)).toList();
    }

    @Override
    public List<ResponseMessageDTO> findAllByChatId(Long id, Utente utente) {
        return messageRepositoryJpa.findAllByChatId(id).stream().map(t->messageMapper.toResponseMessageDto(t,utente)).toList();
    }

    private void checkMessageValidity(InsertMessageDTO dto){
        if(dto.getChatId()==null&&dto.getGroupId()==null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "il messaggio deve essere collegato ad un gruppo o ad una chat");
        }
        else if(dto.getChatId()!=null&&dto.getGroupId()!=null)
        {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "il messaggio non puo' essere destinato sia ad una chat che ad un gruppo");
        }
    }
    private void checkIfUserIsInGroup(Long userId, Long groupId)
    {
        if(!utenteRepositoryJpa.userIsInGroup(userId, groupId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"l'utente non e' nel gruppo");
        }
    }
    private void checkIfUserIsInChat(Chat chat, Long userId)
    {
        if(chat.getUsers().stream().noneMatch(t->t.getId().equals(userId)))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "non fai parte di questa chat");
        }
    }
}
