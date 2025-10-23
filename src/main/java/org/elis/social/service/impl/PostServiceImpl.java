package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.dto.request.post.UpdatePostDTO;
import org.elis.social.dto.response.PagedEntity;
import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;
import org.elis.social.dto.response.post.ResponsePostDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.errorhandling.exceptions.OwnershipException;
import org.elis.social.mapper.PostMapper;
import org.elis.social.model.Hashtag;
import org.elis.social.model.Post;
import org.elis.social.model.Ruolo;
import org.elis.social.model.Utente;
import org.elis.social.repository.jpa.*;
import org.elis.social.service.definition.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepositoryJpa postRepositoryJpa;
    private final PostMapper postMapper;
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    private final HashtagRepositoryJpa hashtagRepositoryJpa;


    @Override
    public List<ResponsePostDTO> findAllByHashtagName(String hashtag, Utente tokenUser) {
        return postRepositoryJpa.findAllByHashtagName(hashtag).stream().map(t->postMapper.toResponsePostDTO(t,tokenUser)).toList();
    }

    @Override
    public List<ResponsePostDTO> findAll(Utente utente) {
        return postRepositoryJpa.findAll().stream().map(t->postMapper.toResponsePostDTO(t,utente)).toList();
    }

    @Override
    public ResponsePostDTO insert(InsertPostDTO dto, Utente utente) {
        Post newPost = new Post();
        newPost.setText(dto.getText());
        newPost.setHashtags(new ArrayList<>());
        newPost.setComments(new ArrayList<>());
        newPost.setUserLikes(new ArrayList<>());
        if(dto.getHashtags()!=null&&!dto.getHashtags().isEmpty())
        {
            createOrSearchHashtags(newPost, dto.getHashtags());
        }
        newPost.setOwner(utente);
        return postMapper.toResponsePostDTO(postRepositoryJpa.save(newPost),null);
    }

    @Override
    public void deleteById(Long id, Utente utente) {
        Post toDelete =findPostById(id);
        if(utente.getRole()== Ruolo.BASE)
        {
            checkPostOwnership(utente, id);
        }
        postRepositoryJpa.delete(toDelete);

    }

    @Override
    public ResponsePostDTO update(UpdatePostDTO dto, Utente utente) {
        boolean updated = false;
        Post toUpdate =findPostById(dto.getId());
        if(utente.getRole()==Ruolo.BASE)
        {
            checkPostOwnership(utente, dto.getId());
        }
        if(dto.getNewText()!=null)
        {
            updated=true;
            toUpdate.setText(dto.getNewText());
        }
        if(dto.getNewHashtags()!=null)
        {
            updated=true;
            if(dto.getNewHashtags().isEmpty())
            {
                toUpdate.setHashtags(new ArrayList<>());
            }
            else{
                createOrSearchHashtags(toUpdate, dto.getNewHashtags());
            }
        }
        if(updated)
        {
            toUpdate=postRepositoryJpa.save(toUpdate);
        }
        var response = postMapper.toResponsePostDTO(toUpdate,utente);
        return response;

    }

    @Override
    public List<ResponsePostDTO> findAllByLoggedUser(Utente utente) {
        return postRepositoryJpa.findAllByUserId(utente.getId()).stream().map(t->{
            var response = postMapper.toResponsePostDTO(t,utente);
            return response;
        }).toList();
    }

    @Override
    public List<ResponsePostDTO> findAllByUserId(Long id,Utente utente) {
        return postRepositoryJpa.findAllByUserId(id).stream().map(t->{
            var response = postMapper.toResponsePostDTO(t,utente);
            return response;
        }).toList();
    }

    @Override
    public ResponsePostDTO likeById(Long id, Utente utente) {
        Post toLike = findPostById(id);
        final Long userId = utente.getId();
        utente = utenteRepositoryJpa.findUserWithLikesByUserId(userId).orElseThrow(()->new NotFoundException("utente non trovato per id: "+userId));
        Post temp = null;
        for(Post p : utente.getLikedPosts())
        {
            if(p.getId().equals(id))
            {
                temp=p;
                break;
            }
        }
        if(temp==null)
        {
           utente.getLikedPosts().add(toLike);
        }
        else{
            utente.getLikedPosts().remove(temp);
        }
        utenteRepositoryJpa.save(utente);
        var response =  postMapper.toResponsePostDTO(postRepositoryJpa.save(toLike),utente);
        return response;

    }

    //Metodi di utilita' (speriamo che funzionino)
    private void checkPostOwnership(Utente utente, Long postId)
    {
        List<Post> userPosts = postRepositoryJpa.findAllByUserId(utente.getId());
        if(userPosts.stream().noneMatch(t->t.getId().equals(postId)))
        {
            throw new OwnershipException("Il post che stai cercando di eliminare non ti appartiene");
        }
    }

    private void createOrSearchHashtags(Post post, List<String> hashtags)
    {
        hashtags = hashtags.stream().distinct().toList();
        for(String hashtagName : hashtags)
        {
            if(!hashtagRepositoryJpa.existsByName(hashtagName))
            {
                Hashtag newHashtag = new Hashtag();
                newHashtag.setName(hashtagName);
                newHashtag=hashtagRepositoryJpa.save(newHashtag);
                post.getHashtags().add(newHashtag);
            }
            else{
                Hashtag newHashtag = hashtagRepositoryJpa.findByName(hashtagName).orElseThrow(()->new NotFoundException("hashtag non trovato per nome: "+hashtagName));
                post.getHashtags().add(newHashtag);
            }
        }
    }
    private Post findPostById(Long id){
        return postRepositoryJpa.findById(id).orElseThrow(()->new NotFoundException("post non trovato per id: "+id));
    }


}

