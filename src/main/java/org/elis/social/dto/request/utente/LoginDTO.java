package org.elis.social.dto.request.utente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
   @NotBlank(message = "username mancante o stringa vuota")
   private String username;
   @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "la password deve contenere minimo 8 caratteri, un carattere minuscolo, un carattere maiuscolo, un numero e un carattere speciale")
   @NotNull(message = "password mancante")
   private String password;
}
