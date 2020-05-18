package org.fouda.administration.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerIT {
    private static final String API_V_1_USERS = "/api/v1/users";
    private static final String API_V_1_USER = "/api/v1/users/{userId}";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThatCreatesUserWithNegativeId() throws Exception {
        String jsonRequest = "{\n" +
                "\"id\": \"-1000\",\n" +
                "\"firstName\": \"Amr\",\n" +
                "\"surName\": \"Fouda\",\n" +
                "\"githubProfileUrl\": \"https://github.com/amr-fouda\",\n" +
                "\"position\": \"Developer\"\n" +
                "}";
        expectBadRequest(jsonRequest);
    }


    @Test
    public void testThatCreatesUserWithValidInput() throws Exception {
        String jsonRequest = "{\n" +
                "\"id\": \"1000\",\n" +
                "\"firstName\": \"Amr\",\n" +
                "\"surName\": \"Fouda\",\n" +
                "\"githubProfileUrl\": \"https://github.com/amr-fouda\",\n" +
                "\"position\": \"Developer\"\n" +
                "}";
        mockMvc.perform(post(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("firstName", equalToIgnoringCase("Amr")))
                .andExpect(jsonPath("surName", equalToIgnoringCase("Fouda")))
                .andExpect(jsonPath("githubProfileUrl", equalToIgnoringCase("https://github.com/amr-fouda")))
                .andExpect(jsonPath("position", equalToIgnoringCase("Developer")));
    }

    @Test
    public void testThatSaveUserUsingPutMethod() throws Exception {
        String jsonRequest = getValidaJsonRequest();
        mockMvc.perform(put(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("firstName", equalToIgnoringCase("Amr")))
                .andExpect(jsonPath("surName", equalToIgnoringCase("Fouda")))
                .andExpect(jsonPath("githubProfileUrl", equalToIgnoringCase("https://github.com/amr-fouda")))
                .andExpect(jsonPath("position", equalToIgnoringCase("Developer")));

        mockMvc.perform(put(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("firstName", equalToIgnoringCase("Amr")))
                .andExpect(jsonPath("surName", equalToIgnoringCase("Fouda")))
                .andExpect(jsonPath("githubProfileUrl", equalToIgnoringCase("https://github.com/amr-fouda")))
                .andExpect(jsonPath("position", equalToIgnoringCase("Developer")));
    }

    @Test
    public void testThatDeleteJustSavedUser() throws Exception {
        mockMvc.perform(put(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).content(getValidaJsonRequest()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("firstName", equalToIgnoringCase("Amr")))
                .andExpect(jsonPath("surName", equalToIgnoringCase("Fouda")))
                .andExpect(jsonPath("githubProfileUrl", equalToIgnoringCase("https://github.com/amr-fouda")))
                .andExpect(jsonPath("position", equalToIgnoringCase("Developer")));

        mockMvc.perform(delete(API_V_1_USER, 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    private void expectBadRequest(String jsonRequest) throws Exception {
        mockMvc.perform(post(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("errors", hasSize(2)));
    }

    private String getValidaJsonRequest() {
        return "{\n" +
                "\"id\": \"1\",\n" +
                "\"firstName\": \"Amr\",\n" +
                "\"surName\": \"Fouda\",\n" +
                "\"githubProfileUrl\": \"https://github.com/amr-fouda\",\n" +
                "\"position\": \"Developer\"\n" +
                "}";
    }

}
