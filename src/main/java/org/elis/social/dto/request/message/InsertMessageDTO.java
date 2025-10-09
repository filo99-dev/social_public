package org.elis.social.dto.request.message;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertMessageDTO {
    @NotBlank(message = "testo mancante o stringa vuota")
    private String text;
    private Long chatId;
    private Long groupId;

}
