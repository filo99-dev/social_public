package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.comment.InsertCommentDTO;
import org.elis.social.dto.response.comment.ResponseCommentDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.errorhandling.exceptions.OwnershipException;
import org.elis.social.mapper.CommentMapper;
import org.elis.social.model.Comment;
import org.elis.social.model.Post;
import org.elis.social.model.Ruolo;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.CommentRepositoryJpa;
import org.elis.social.repository.jpa.PostRepositoryJpa;
import org.elis.social.service.definition.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepositoryJpa commentRepositoryJpa;
    private final PostRepositoryJpa postRepositoryJpa;
    private final CommentMapper commentMapper;


    @Override
    public ResponseCommentDTO insert(InsertCommentDTO dto, Utente utente) {
        Post toComment = postRepositoryJpa.findById(dto.getPostId()).orElseThrow(()->new NotFoundException("Post non trovato per id: "+dto.getPostId()));
        Comment newComment = new Comment();
        newComment.setText(dto.getText());
        newComment.setPost(toComment);
        newComment.setOwner(utente);
        return commentMapper.toResponseCommentDto(commentRepositoryJpa.save(newComment),utente);
    }

    @Override
    public List<ResponseCommentDTO> findAllByPostId(Long id,Utente tokenUser) {
        return commentRepositoryJpa.findAllByPostId(id).stream().map(t->commentMapper.toResponseCommentDto(t,tokenUser)).toList();
    }

    @Override
    public void deleteById(Long id, Utente utente) {
        Comment toDelete = findById(id);
        if(utente.getRole()== Ruolo.BASE)
        {
            checkCommentOwnership(utente,id);
        }
        commentRepositoryJpa.delete(toDelete);

    }
    private void checkCommentOwnership(Utente utente, Long commentId)
    {
        List<Comment> userComments = commentRepositoryJpa.findAllByUserId(utente.getId());
        if(userComments.stream().noneMatch(t->t.getId().equals(commentId)))
        {
            throw new OwnershipException("Il commento che stai cercando di eliminare non ti appartiene");
        }
    }
    private Comment findById(Long id)
    {

        return commentRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("commento non trovato per id: "+id));
    }
}
