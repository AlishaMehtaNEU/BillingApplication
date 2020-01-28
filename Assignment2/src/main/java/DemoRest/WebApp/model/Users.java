package DemoRest.WebApp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "users")
public class Users {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotNull(message = "First Name is compulsory")
    @Column(name = "first_name")
    private String first_name;

    @NotNull(message = "Last Name is compulsory")
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "password")
    private String password;


    @NotNull(message = "Email Address is compulsory")
    @Email(message = "Email is invalid")
    @Column(name = "email_address", unique = true)
    private String emailAddress;

    public void setAccount_created(Date account_created) {
        this.account_created = account_created;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "account_created")
    private Date account_created;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "account_updated")
    private Date account_updated;

    public Users(){
    }

    public Users(Users users) {
        this.emailAddress = users.getEmailAddress();
        this.password = users.getPassword();
        this.first_name = users.getFirst_name();
        this.last_name = users.getLast_name();
        this.id = users.getId();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email_address) {
        this.emailAddress = email_address;
    }

    public Date getAccount_created() {
        return account_created;
    }

    public Date getAccount_updated() {
        return account_updated;
    }

    public void setAccount_updated(Date account_updated) {
        this.account_updated = account_updated;
    }
}
