package org.elis.social.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InsertPostDTO {
    @NotBlank(message = "testo mancante o vuoto")
    private String text;
    private List<@NotBlank(message = "testo dell'hashtag mancante o vuoto") String> hashtags;
}
