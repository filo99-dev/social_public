package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;
import org.elis.social.mapper.HashtagMapper;
import org.elis.social.repository.jpa.HashtagRepositoryJpa;
import org.elis.social.service.definition.HashtagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepositoryJpa repo;
    private final HashtagMapper mapper;


    @Override
    public List<ResponseHashtagDTO> find10MostPopular() {
        return repo.find10MostPopular().stream().map(mapper::toResponseHashtagDto).toList();
    }
}
