package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="phone_number")
    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @Column(name="name")
    @NotNull(message = "Name is required")
    private String name;

    @Column(name="password")
    @NotNull(message = "Password is required")
    private String password;

    @Column(name="user_name")
    @NotNull(message = "Username is required")
    private String username;

    @Column(name="user_roles")
    private List<GrantedAuthority> authorities;

    public User(String phoneNumber, String name, String password, String username, List<GrantedAuthority> authorities) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }
}
