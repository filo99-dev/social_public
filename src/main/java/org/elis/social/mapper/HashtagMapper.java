package org.elis.social.mapper;

import org.apache.coyote.Response;
import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;
import org.elis.social.model.Hashtag;
import org.springframework.stereotype.Component;

@Component
public class HashtagMapper {
    public ResponseHashtagDTO toResponseHashtagDto(Hashtag entity)
    {
        ResponseHashtagDTO dto = new ResponseHashtagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
