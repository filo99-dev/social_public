package org.elis.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elis.social.dto.request.utente.LoginDTO;
import org.elis.social.dto.request.utente.RegisterUserDTO;
import org.elis.social.model.Post;
import org.elis.social.repository.jpa.PostRepositoryJpa;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//        org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = SocialApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UtenteTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepositoryJpa postRepositoryJpa;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Test
    void registerBadRequest() throws Exception{
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setEmail("");
        dto.setPassword("");
        dto.setUsername("");
        dto.setPhoneNumber("");
        String json = mapper.writeValueAsString(dto);
        mockMvc.perform(post("/all/register").contentType(MEDIA_TYPE).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.fieldErrors").exists())
                .andExpect(jsonPath("$.fieldErrors").isMap())
                .andExpect(jsonPath("$.fieldErrors").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.length()").value(4));
    }
    @Test
    @Order(1)
    void registerSuccess() throws Exception{
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setEmail("m.draghi@gmail.com");
        dto.setPassword("VivaLEuropa2018!");
        dto.setUsername("xXxDraghixXx");
        dto.setPhoneNumber("+393334585307");
        String json = mapper.writeValueAsString(dto);
        mockMvc.perform(post("/all/register").contentType(MEDIA_TYPE).content(json))
                .andExpect(status().isOk());
    }
    @Test
    @Order(2)
    void loginSuccess() throws Exception{
        LoginDTO dto = new LoginDTO();
        dto.setPassword("VivaLEuropa2018!");
        dto.setUsername("xXxDraghixXx");
        String json = mapper.writeValueAsString(dto);
        RequestBuilder request = post("/all/login")
                .contentType(MEDIA_TYPE)
                .content(json);
        ResultMatcher risultato = status().isOk();
        ResultMatcher risultatoToken = header().exists(HttpHeaders.AUTHORIZATION);
        ResultMatcher risultatoBody = jsonPath("$").exists();
        ResultMatcher risultatoBodyCampo = jsonPath("$.email").exists();
        ResultMatcher risultatoBodyCampoValore = jsonPath("$.email").value("m.draghi@gmail.com");

        mockMvc.perform(request)
                .andExpect(risultato)
                .andExpect(risultatoToken)
                .andExpect(risultatoBody)
                .andExpect(risultatoBodyCampo)
                .andExpect(risultatoBodyCampoValore)
                .andDo(print());
    }




}
