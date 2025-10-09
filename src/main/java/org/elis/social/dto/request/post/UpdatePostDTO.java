package org.elis.social.dto.request.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UpdatePostDTO {
    @NotNull(message = "id mancante")
    private Long id;
    @NotEmpty(message = "stringa vuota")
    private String newText;
    private List<@NotEmpty(message = "stringa vuota") String> newHashtags;
}
