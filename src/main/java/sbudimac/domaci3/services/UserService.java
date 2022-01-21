package sbudimac.domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sbudimac.domaci3.mappers.PermissionMapper;
import sbudimac.domaci3.mappers.UserMapper;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.model.User;
import sbudimac.domaci3.model.UserDto;
import sbudimac.domaci3.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PermissionMapper permissionMapper;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PermissionMapper permissionMapper, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionMapper = permissionMapper;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto current() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> u = this.userRepository.findByEmail(email);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException("No logged in user found.");
        }
        return userMapper.userToUserDto(u.get());
    }

    public UserDto save(User user) {
        Optional<User> u = this.userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            user.setPassword(u.get().getPassword());
            user.setId(u.get().getId());
        } else {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(userMapper.userToUserDto(u));
        }
        return dtos;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + "not found.");
        }
        List<PermissionAuthority> permissions = new ArrayList<>();
        permissions.add(permissionMapper.permissionAuthorityToPermissions(user.get().getPermissions()));

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), permissions);
    }
}
