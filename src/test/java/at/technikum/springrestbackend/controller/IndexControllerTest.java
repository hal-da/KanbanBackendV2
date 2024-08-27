package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class IndexControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private IndexController indexController;

    @Mock
    private UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void testWelcome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Kanbantastisch API"));
    }

   /*
   @Test
    void testTokenValidator() throws Exception {
        // Mock UserPrincipal
        when(userPrincipal.getEmail()).thenReturn("test@example.com");
        when(userPrincipal.getAuthorities()).thenReturn("ROLE_USER");
        when(userPrincipal.getUserId()).thenReturn("user-id");

        // Mock SecurityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        securityContext.setAuthentication(authentication);

        mockMvc.perform(get("/tokenValidator")
                        .principal(userPrincipal))  // Set the principal for the request
                .andExpect(status().isOk())
                .andExpect(content().string("Token is valid: test@example.com with authorities: ROLE_USER userId: user-id email: test@example.com"));
    }

    */
}
