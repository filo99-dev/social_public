package org.elis.social.service.definition;

import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;

import java.util.List;

public interface HashtagService {
    List<ResponseHashtagDTO> find10MostPopular();
}
