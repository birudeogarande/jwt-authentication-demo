package com.saatvik.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void welcome() throws Exception {
        mvc.perform(get("/auth/welcome"))
                .andExpect(content().string("Welcome this endpoint is not secure"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void userProfile() throws Exception {
        mvc.perform(get("/auth/user/userProfile"))
                .andExpect(content().string("Welcome to User Profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void adminProfile() throws Exception {
        mvc.perform(get("/auth/admin/adminProfile"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Admin Profile"));

    }
}