package org.elis.social.dto.response.utente;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUtenteWithFollowFlagDTO extends ResponseUserDTO{
    private Boolean isFollowed;
}
