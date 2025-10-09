package org.elis.social.dto.request.comment;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertCommentDTO {
    @NotNull(message = "id mancante")
   private Long postId;
    @NotBlank(message = "testo mancante o stringa vuota")
   private String text;

}
