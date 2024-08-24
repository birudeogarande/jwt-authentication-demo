package com.saatvik.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saatvik.app.custom.WithCustomUser;
import com.saatvik.app.dto.UserInfo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.MethodName.class)
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
    @DisplayName("""
            Add new user testcase""")
    void addUserPass() throws Exception {



        mvc.perform(post("/auth/addNewUser")
                        .content(asJsonString(
                                new UserInfo("test",
                                        "testuser@gmail.com",
                                        "password",
                                        "ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON))
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
//    @WithUserDetails("saatvik.garande@gmail.com")
        //(userDetailsServiceBeanName = "userInfoService", value = "testuser@gmail.com")
//    @Disabled
    @WithCustomUser
    void adminProfilePass() throws Exception {
        mvc.perform(get("/auth/admin/adminProfile"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Admin Profile"));
    }

    @Test
    @DisplayName("""
            adminProfile fail testcase
            """)
    @WithMockUser
    @Transactional
    void adminProfileFail() throws Exception {
        mvc.perform(get("/auth/admin/adminProfile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("""
            Delete user testcase
            """)
    @WithMockUser
    void deleteUser() throws Exception {
        var email = "testuser@gmail.com";
        mvc.perform(delete("/auth/deleteUser/"+email)).andExpect(status().is2xxSuccessful());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}