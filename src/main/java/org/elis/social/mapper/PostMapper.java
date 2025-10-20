package org.elis.social.mapper;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.dto.response.post.ResponsePostDTO;
import org.elis.social.model.Comment;
import org.elis.social.model.Hashtag;
import org.elis.social.model.Post;
import org.elis.social.model.Utente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final UtenteMapper utenteMapper;
    private final HashtagMapper hashtagMapper;
    private final CommentMapper commentMapper;
    public ResponsePostDTO toResponsePostDTO(Post entity, Utente tokenUser){
        ResponsePostDTO dto = new ResponsePostDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreationDateTime());
        dto.setUpdatedAt(entity.getLastUpdateDateTime());

        dto.setOwner(utenteMapper.toResponseUserDto(tokenUser,entity.getOwner()));
        List<Utente> utentiLikes = entity.getUserLikes();
        if(utentiLikes!=null)
        {
            dto.setLikeCount(utentiLikes.size());
//            dto.setUserLikes(utentiLikes.stream()
//                    .map(utenteMapper::toResponseUserDto)
//                    .toList());
        }
        List<Hashtag> hashtags = entity.getHashtags();
        if(hashtags!=null)
        {
            dto.setHashtags(hashtags.stream()
                    .map(hashtagMapper::toResponseHashtagDto)
                    .toList());
        }
//        List<Comment> comments = entity.getComments();
//        if(comments!=null)
//        {
//            dto.setComments(comments.stream()
//                    .map(commentMapper::toResponseCommentDto)
//                    .toList());
//        }
        return dto;
    }
    public Post fromInsertPostDto(InsertPostDTO dto)
    {
        Post entity = new Post();
        entity.setHashtags(dto.getHashtags().stream().map(t->{
            Hashtag h = new Hashtag();
            h.setName(t);
            return h;
        }).toList());
        entity.setText(dto.getText());
        return entity;
    }
}
