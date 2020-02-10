package DemoRest.WebApp;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.UserRepository;
import DemoRest.WebApp.resource.UserResource;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
public class AbstractTest {

    @Autowired
    private UserResource userResource;
    //
    @Autowired
    private UserRepository userRepository;

    @Mock
    private Users users;

    @Autowired
    private MockMvc mvc;

//
//    @LocalServerPort
//    int randomServerPort;

    @Test
    public void testFindAll() throws Exception {
        // given
        Users u1 = new Users();
        u1.setPassword("Aa3###12");
        u1.setFirst_name("Alisha");
        u1.setLast_name("Mehta");
        u1.setEmailAddress("mehta.al@husly.neu.edu");

        List<Users> users = new ArrayList<Users>();
        users.add(u1);
        UserRepository userRepository = new UserRepository() {
            @Override
            public Users findByEmailAddress(String email_address) {
                return null;
            }

            @Override
            public Users findById(String id) {
                return null;
            }

            @Override
            public List<Users> findAll() {
                return null;
            }

            @Override
            public List<Users> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Users> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Users> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Users> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Users> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Users getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Users> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Users> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Users> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Users> S save(S s) {
                return null;
            }

            @Override
            public Optional<Users> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Users users) {

            }

            @Override
            public void deleteAll(Iterable<? extends Users> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Users> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Users> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Users> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Users> boolean exists(Example<S> example) {
                return false;
            }
        };
        userRepository.save(u1);
        Users ufound = userRepository.findByEmailAddress(u1.getEmailAddress());

//        assertThat(u1.getFirst_name())
//                .isEqualTo(resp.get);

    }
}