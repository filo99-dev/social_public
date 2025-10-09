package org.elis.social.dto.response.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elis.social.dto.response.utente.ResponseUserDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseGroupDTO {
    private Long id;
    private String name;
    private ResponseUserDTO owner;
    private List<ResponseUserDTO> members;
}
