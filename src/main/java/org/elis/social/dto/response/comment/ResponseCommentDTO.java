package org.elis.social.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.dto.response.utente.ResponseUserDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCommentDTO {
    private Long id;
    private ResponseUserDTO owner;
    private LocalDateTime createdAt;
}
