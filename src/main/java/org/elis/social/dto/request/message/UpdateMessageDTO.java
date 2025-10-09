package org.elis.social.dto.request.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMessageDTO {

    @NotNull(message = "id mancante o stringa vuota")
    private Long id;
    @NotBlank(message = "testo mancante o stringa vuota")
    private String newText;
}
