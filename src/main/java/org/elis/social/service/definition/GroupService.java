package org.elis.social.service.definition;

import org.elis.social.dto.request.group.AddOrRemoveMemberDTO;
import org.elis.social.dto.request.group.InsertGroupDTO;
import org.elis.social.dto.response.group.ResponseGroupDTO;
import org.elis.social.model.Utente;
import org.springframework.security.core.Authentication;

public interface GroupService {
    ResponseGroupDTO insert(InsertGroupDTO dto, Utente utente);
    void addMember(AddOrRemoveMemberDTO dto, Utente owner);
    void removeMember(AddOrRemoveMemberDTO dto, Utente owner);
    void delete(Long groupId, Utente owner);
}
