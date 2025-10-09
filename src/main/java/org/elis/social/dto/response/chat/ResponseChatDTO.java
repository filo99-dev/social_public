package org.elis.social.dto.response.chat;

import lombok.*;
import org.elis.social.dto.response.utente.ResponseUserDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseChatDTO {
    private Long id;
    private List<ResponseUserDTO> members;
}
