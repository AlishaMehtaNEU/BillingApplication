package DemoRest.WebApp;

import DemoRest.WebApp.config.UserDetailsServiceImpl;
import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.BillRepo;
import DemoRest.WebApp.repository.UserRepo;
import DemoRest.WebApp.repository.UserRepository;
import DemoRest.WebApp.resource.BillResource;
import DemoRest.WebApp.resource.UserResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class Test {
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserResource userResource;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource)
                .build();
    }

    @org.junit.Test
    public void testUserPost() throws Exception {

        Users u1 = new Users();
        u1.setPassword("Aa3###12");
        u1.setFirst_name("Alisha");
        u1.setLast_name("Mehta");
        u1.setEmailAddress("mehta.al@husky.neu.eduu");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = this.mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1)))
                .andExpect(status().isCreated()).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        userRepository.findByEmailAddress("mehta.al@husky.neu.eduu");
    }

    @org.junit.Test
    public void testUserGet() throws Exception {

        Users u1 = new Users();
        u1.setPassword("Aa3###12");
        u1.setFirst_name("Alisha");
        u1.setLast_name("Mehta");
        u1.setEmailAddress("mehta.al@husky.neu.eduu");
        when(userRepository.findByEmailAddress("mehta.al@husky.neu.eduu")).thenReturn(u1);

        try {
            Users applicationUser = mock(Users.class);
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
            when(authentication.getName()).thenReturn(u1.getEmailAddress());
            userRepository.save(u1);
            userRepository.flush();
            when(userRepository.findByEmailAddress("mehta.al@husky.neu.eduu")).thenReturn(u1);
            MvcResult result = this.mockMvc.perform(get("/v1/user/self"))
                    .andExpect(status().isOk()).andReturn();
            String contentAsString = result.getResponse().getContentAsString();
            System.out.println("result -- " + contentAsString);

        }
        catch(Exception e){
            System.out.println( " --- e -- " + e);
        }

    }

}
