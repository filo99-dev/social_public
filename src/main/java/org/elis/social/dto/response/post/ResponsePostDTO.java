package org.elis.social.dto.response.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.dto.response.comment.ResponseCommentDTO;
import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;
import org.elis.social.dto.response.utente.ResponseUserDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponsePostDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ResponseUserDTO owner;
    private Integer likeCount;
    private List<ResponseHashtagDTO> hashtags;
    private Boolean isLiked;


//    private List<ResponseUserDTO> userLikes;
//    private List<ResponseCommentDTO> comments;
}
