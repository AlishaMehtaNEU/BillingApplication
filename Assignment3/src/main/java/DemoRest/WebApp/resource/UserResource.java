package DemoRest.WebApp.resource;

import DemoRest.WebApp.exception.AccCreateFieldNotRequiredException;
import DemoRest.WebApp.exception.AccUpdateFieldNotRequiredException;
import DemoRest.WebApp.exception.ForbiddenException;
import DemoRest.WebApp.model.Users;
import DemoRest.WebApp.repository.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.bridge.IMessage;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/v1/user")
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/self")
    public ResponseEntity findByUsername(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> newUser = new HashMap<>();

        Users users = userRepository.findByEmailAddress(authentication.getName());
        newUser.put("id", users.getId());
        newUser.put("email_address", users.getEmailAddress());
        newUser.put("last_name", users.getLast_name());
        newUser.put("first_name", users.getFirst_name());
        newUser.put("account_created", users.getAccount_created());
        newUser.put("account_updated", users.getAccount_updated());

        return new ResponseEntity(newUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addUser(@Valid @RequestBody final Users users, BindingResult bindingResult, ModelMap modelMap, MethodArgumentNotValidException ex) {

        Map<String, Object> newUser = new HashMap<>();

        List<String> details = new ArrayList<>();

        for(ObjectError error : bindingResult.getAllErrors()) {
            if(error.toString().contains("Email"))
                return new ResponseEntity("Enter valid Email", HttpStatus.BAD_REQUEST);
        }

        if(users.getAccount_created() != null){
            throw new AccCreateFieldNotRequiredException();
        }
        if(users.getId() != null){
            return new ResponseEntity("Id value is not required", HttpStatus.BAD_REQUEST);
        }
        if(users.getAccount_updated() != null){
            throw new AccUpdateFieldNotRequiredException();
        }
        if(userRepository.findByEmailAddress(users.getEmailAddress()) != null) {
            throw new ForbiddenException();
        }
        if(users.getFirst_name() == null){
            return new ResponseEntity("Enter first name", HttpStatus.BAD_REQUEST);
        }
        if(users.getLast_name() == null){
            return new ResponseEntity("Enter last name", HttpStatus.BAD_REQUEST);
        }
        if(users.getPassword() == null){
            return new ResponseEntity("Password cannot be null", HttpStatus.BAD_REQUEST);
        }
        else {
            PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                    new LengthRule(8,20),
                    new UppercaseCharacterRule(1),
                    new LowercaseCharacterRule(1),
                    new DigitCharacterRule(1),
                    new SpecialCharacterRule(2),
                    new WhitespaceRule()
            ));
            PasswordData passwordData = new PasswordData(users.getPassword());
            RuleResult validate = passwordValidator.validate(passwordData);
            if(!validate.isValid()){
                return new ResponseEntity("Enter strong password", HttpStatus.BAD_REQUEST);
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(users.getPassword());
            users.setPassword(encodedPassword);
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            users.setAccount_updated(date);
            users.setAccount_created(date);
            userRepository.save(users);
            userRepository.flush();
            LocalDateTime date1 = LocalDateTime.now();
            newUser.put("id", users.getId());
            newUser.put("email_address", users.getEmailAddress());
            newUser.put("last_name", users.getLast_name());
            newUser.put("first_name", users.getFirst_name());
            newUser.put("account_created", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date1));
            newUser.put("account_updated", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date1));
            return new ResponseEntity(newUser, HttpStatus.CREATED);
        }
    }

    @PutMapping("/self")
    public ResponseEntity updateUser(@RequestBody final Users users, HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users userExisting = userRepository.findByEmailAddress(authentication.getName());

        if(!users.getEmailAddress().equals(authentication.getName())){
            return new ResponseEntity("Email id cannot be updated", HttpStatus.BAD_REQUEST);
        }
        if(users.getAccount_created() != null){
            throw new AccCreateFieldNotRequiredException();
        }
        if(users.getAccount_updated() != null){
            throw new AccUpdateFieldNotRequiredException();
        }
        if(users.getFirst_name() == null){
            return new ResponseEntity("Enter first name", HttpStatus.BAD_REQUEST);
        }
        if(users.getLast_name() == null){
            return new ResponseEntity("Enter last name", HttpStatus.BAD_REQUEST);
        }
        if(users.getPassword() == null){
            return new ResponseEntity("Password cannot be null", HttpStatus.BAD_REQUEST);
        }
        if(users.getId() != null){
            return new ResponseEntity("Id cannot be updated", HttpStatus.BAD_REQUEST);
        }
        else{
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                    new LengthRule(8,20),
                    new UppercaseCharacterRule(1),
                    new LowercaseCharacterRule(1),
                    new DigitCharacterRule(1),
                    new SpecialCharacterRule(2),
                    new WhitespaceRule()
            ));
            PasswordData passwordData = new PasswordData(users.getPassword());
            RuleResult validate = passwordValidator.validate(passwordData);
            if (!validate.isValid()){
                return new ResponseEntity("Enter strong password", HttpStatus.BAD_REQUEST);
            }
            if (!userExisting.getFirst_name().equals(users.getFirst_name()))
                userExisting.setFirst_name(users.getFirst_name());
            if (!userExisting.getLast_name().equals(users.getLast_name()))
                userExisting.setLast_name(users.getLast_name());
            userExisting.setAccount_updated(date);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(users.getPassword());
            userExisting.setPassword(encodedPassword);
            userRepository.save(userExisting);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "200");

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}

