package co.edu.uniquindio.seci_proyect.controllers.Integration.utils;

import co.edu.uniquindio.seci_proyect.dtos.user.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginUtil {
    public static String login(String username, String password, MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        var result = mockMvc.perform(
                        post("/login")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(new LoginRequest(username, password)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        var json = result.getResponse().getContentAsString();
        return JsonPath.parse(json).read("$.token").toString();
    }
}