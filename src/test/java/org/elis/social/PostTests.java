package org.elis.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elis.social.dto.request.post.InsertPostDTO;
import org.elis.social.model.Post;
import org.elis.social.repository.jpa.PostRepositoryJpa;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = SocialApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class PostTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepositoryJpa postRepositoryJpa;
    private final String MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    @WithUserDetails("xXxDraghixXx")
    void insertPostSuccess() throws Exception{
        InsertPostDTO dto = new InsertPostDTO();
        dto.setText("Viva l'europa");
        dto.setHashtags(new ArrayList<>(List.of("#europa","#democrazia","#welfarestate")));
        mockMvc.perform(post("/base/post").contentType(MEDIA_TYPE).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

//    @Test
//    void findAllPostsRepositoryTest(){
//        List<Post> allPosts = postRepositoryJpa.findAll();
//        Assertions.assertNotNull(allPosts);
//        assertThat(allPosts.size()).isNotNegative();
//        assertThat(allPosts).allMatch(t->!t.getHashtags().isEmpty());
//    }
}
