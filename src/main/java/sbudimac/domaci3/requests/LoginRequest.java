package sbudimac.domaci3.requests;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
