package DemoRest.WebApp.repository;

import DemoRest.WebApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmailAddress(String email_address);
    Users findById(String id);
}
