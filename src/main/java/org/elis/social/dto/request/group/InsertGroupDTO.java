package org.elis.social.dto.request.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InsertGroupDTO {
    @NotBlank(message = "nome mancante o vuoto")
    private String name;
    @NotEmpty(message = "la lista con gli id dei membri e' vuota o mancante")
    private List<Long> membersIds;

}
