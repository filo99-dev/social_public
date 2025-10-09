package org.elis.social.dto.request.group;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddOrRemoveMemberDTO {
    @NotNull(message = "id del gruppo mancante")
    private Long groupId;
    @NotNull(message = "id utente mancante")
    private Long memberId;
}
