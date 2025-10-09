package org.elis.social.dto.request.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertChatDTO {
    @NotNull(message = "id del destinatario null")
    private Long receiverId;
    @NotBlank(message = "primo messaggio mancante o stringa vuota")
    private String firstMessage;
}
