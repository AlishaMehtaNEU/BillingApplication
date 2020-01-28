package DemoRest.WebApp;

import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest
public class  WebAppApplicationTests {

//	@Autowired
//	MockMvc mockMvc;
//
//	@MockBean
//	UserRepository userRepository;
//
//	@Test
//	void contextLoads() throws Exception {
//		Mockito.when(userRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
//
//		MvcResult mvcResult = mockMvc.perform(
//				MockMvcRequestBuilders.get("/v1/user/self")
//				.accept(MediaType.APPLICATION_JSON)
//		).andReturn();
//
//		Mockito.verify(userRepository.findAll());
//	}
}
