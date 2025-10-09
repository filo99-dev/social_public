package org.elis.social.controller;

import lombok.RequiredArgsConstructor;
import org.elis.social.dto.response.hashtag.ResponseHashtagDTO;
import org.elis.social.service.definition.HashtagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService service;

    @GetMapping("/all/hashtags/popular")
    public ResponseEntity<List<ResponseHashtagDTO>> find10MostPopular(){
        return ResponseEntity.ok(service.find10MostPopular());
    }
}
