package org.elis.social.dto.response.utente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.dto.response.post.ResponsePostDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseProfileDTO {
    private ResponseUserDTO user;
    private List<ResponsePostDTO> posts;
    private Integer followerCount;
    private Integer followedCount;
}
