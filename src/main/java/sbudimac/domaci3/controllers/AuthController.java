package sbudimac.domaci3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.requests.LoginRequest;
import sbudimac.domaci3.responses.LoginResponse;
import sbudimac.domaci3.responses.PermissionResponse;
import sbudimac.domaci3.services.UserService;
import sbudimac.domaci3.utils.JwtDecoder;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtDecoder jwtDecoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new LoginResponse(jwtDecoder.generateToken(request.getEmail())));
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> gatherPermissions() {
        return ResponseEntity.ok(new PermissionResponse(collectPermissions().getPermissions()));
    }

    private PermissionAuthority collectPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> permissions = this.userService.loadUserByUsername(email).getAuthorities();
        return (PermissionAuthority) permissions.toArray()[0];
    }
}
