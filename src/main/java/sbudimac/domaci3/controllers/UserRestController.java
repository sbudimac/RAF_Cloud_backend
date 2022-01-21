package sbudimac.domaci3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.model.User;
import sbudimac.domaci3.services.UserService;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/curr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.current());
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        if (collectPermissions().getPermissions().isCanReadUsers()) {
            return ResponseEntity.ok(userService.findAll());
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (collectPermissions().getPermissions().isCanCreateUsers()) {
            return ResponseEntity.ok(userService.save(user));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (collectPermissions().getPermissions().isCanUpdateUsers()) {
            return ResponseEntity.ok(userService.save(user));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (collectPermissions().getPermissions().isCanDeleteUsers()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    private PermissionAuthority collectPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> permissions = this.userService.loadUserByUsername(email).getAuthorities();
        return (PermissionAuthority) permissions.toArray()[0];
    }
}
