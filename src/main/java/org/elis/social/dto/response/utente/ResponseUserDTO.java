package org.elis.social.dto.response.utente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.model.Ruolo;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDTO {
    private Long id;
    private String email;
    private String username;
    private String phoneNumber;
    private Ruolo role;
    private Boolean isFollowed;
    private Boolean isFollowing;
}
