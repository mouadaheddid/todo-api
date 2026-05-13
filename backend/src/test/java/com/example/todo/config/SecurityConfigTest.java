package com.example.todo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Test d'intégration : on a besoin du vrai SecurityFilterChain pour valider CORS
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void preflight_fromAngularDevOrigin_shouldReturn200WithCorsHeaders() throws Exception {
        mockMvc.perform(options("/api/todos")
                        .header("Origin", "http://localhost:4200")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }

    @Test
    void preflight_fromUnauthorizedOrigin_shouldNotEchoCorsHeader() throws Exception {
        // Une origine non listee ne doit pas recevoir l'en-tete Allow-Origin
        mockMvc.perform(options("/api/todos")
                        .header("Origin", "http://evil.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
}
