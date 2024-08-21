package com.saatvik.app.controller;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("""
            Welcome api test case
            """)
    void welcome() throws Exception {
        mvc.perform(get("/auth/welcome"))
                .andExpect(content().string("Welcome this endpoint is not secure"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            userProfile pass test case
            """)
    @WithMockUser
    void userProfile() throws Exception {
        mvc.perform(get("/auth/user/userProfile"))
                .andExpect(content().string("Welcome to User Profile"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            userProfile fail test case
            """)
    void userProfileFail() throws Exception {
        mvc.perform(get("/auth/user/userProfile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("""
            adminProfile pass testcase
            """)
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void adminProfilePass() throws Exception {
        mvc.perform(get("/auth/admin/adminProfile"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("""
            adminProfile fail testcase
            """)
    @WithMockUser
    void adminProfileFail() throws Exception {
        mvc.perform(get("/auth/admin/adminProfile"))
                .andExpect(status().isForbidden());
    }
}