package org.elis.social.dto.request.utente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDTO {
    @NotBlank(message = "l'username mancante o stringa vuota")
    private String username;
    @Email(message = "l'email non rispetta il pattern corretto")
    @NotBlank(message = "email mancante o stringa vuota")
    private String email;
    @Pattern(regexp = "^\\+?(\\d{1,3})?[-. ]?(\\(?\\d{3}\\)?)[-. ]?(\\d{3})[-. ]?(\\d{4})$",message = "il numero non rispetta il pattern di un numero di telefono")
    @NotNull(message = "numero di telefono mancante")
    private String phoneNumber;
    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "la password deve contenere minimo 8 caratteri, un carattere minuscolo, un carattere maiuscolo, un numero e un carattere speciale")
    @NotNull(message = "password mancante")
    private String password;
}
