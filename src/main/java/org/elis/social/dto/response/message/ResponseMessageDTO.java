package org.elis.social.dto.response.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.dto.response.utente.ResponseUserDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessageDTO {
    private Long id;
    private String text;
    private ResponseUserDTO sender;
    private LocalDateTime sentAt;
    private LocalDateTime updatedAt;

}
