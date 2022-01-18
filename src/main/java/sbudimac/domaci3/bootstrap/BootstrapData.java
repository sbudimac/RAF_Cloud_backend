package sbudimac.domaci3.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sbudimac.domaci3.model.Permissions;
import sbudimac.domaci3.model.User;
import sbudimac.domaci3.repositories.UserRepository;

@Component
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("WOOOOOOOOOOOOO");
        User user = new User();
        user.setFirstName("Stefan");
        user.setLastName("Budimac");
        user.setEmail("sbudimac@gmail.com");
        user.setPassword(this.passwordEncoder.encode("1234"));
        Permissions permissions = new Permissions();
        user.setPermissions(permissions);
        user.getPermissions().setCanReadUsers(true);
        user.getPermissions().setCanCreateUsers(true);
        user.getPermissions().setCanUpdateUsers(true);
        user.getPermissions().setCanDeleteUsers(true);
        userRepository.save(user);
        System.out.println("WOOOOO");
    }
}
