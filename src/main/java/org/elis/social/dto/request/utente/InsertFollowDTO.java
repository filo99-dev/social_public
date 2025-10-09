package org.elis.social.dto.request.utente;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertFollowDTO {
    @NotNull(message = "id mancante")
    Long toFollowUserId;
}
