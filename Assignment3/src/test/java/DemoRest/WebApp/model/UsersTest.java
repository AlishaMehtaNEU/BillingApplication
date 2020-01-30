package DemoRest.WebApp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void userObject() {
        Users user = new Users();
        user.setId("test");
        assertTrue(user.getId() == "test");
        user.setFirst_name("Alisha");
        assertTrue(user.getFirst_name() == "Alisha");
        user.setLast_name("Mehta");
        assertTrue(user.getLast_name() == "Mehta");
        user.setPassword("Aa123###");
        assertTrue(user.getPassword() == "Aa123###");
        user.setEmailAddress("alisha@gmail.com");
        assertTrue(user.getEmailAddress() == "alisha@gmail.com");
    }

}