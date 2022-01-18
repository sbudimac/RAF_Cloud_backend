package sbudimac.domaci3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sbudimac.domaci3.mappers.PermissionMapper;
import sbudimac.domaci3.mappers.UserMapper;
import sbudimac.domaci3.repositories.UserRepository;
import sbudimac.domaci3.services.UserService;

@Configuration
public class ServiceConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService service(UserRepository userRepository, PermissionMapper permissionMapper, UserMapper userMapper) {
        return new UserService(userRepository, permissionMapper, userMapper, passwordEncoder());
    }
}
