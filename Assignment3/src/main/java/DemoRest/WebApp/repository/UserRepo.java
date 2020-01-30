package DemoRest.WebApp.repository;

import DemoRest.WebApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserRepo {

    @Autowired
    UserRepository userRepository;

    public Users findByEmailAddress(String email_address){
        Users user= userRepository.findByEmailAddress(email_address);
        return user;
    }
}
