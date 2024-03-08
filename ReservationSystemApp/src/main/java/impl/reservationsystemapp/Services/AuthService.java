package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Authentication.util.JwtUtils;
import impl.reservationsystemapp.Entities.User;
import impl.reservationsystemapp.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String login(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authenticate = authenticationManager.authenticate(authToken);

        return JwtUtils.generateToken(((UserDetails)(authenticate.getPrincipal())).getUsername());
    }

    public String signUp(String phoneNumber, String name, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }

        var encodedPassword = passwordEncoder.encode(password);

        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        var user = new User(phoneNumber, name, encodedPassword, username, authorities);


        userRepository.save(user);
        return JwtUtils.generateToken(username);
    }

}
