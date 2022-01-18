package sbudimac.domaci3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public Permissions permissions;

    public UserDto(Long id, String firstName, String lastName, String email, Permissions permissions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.permissions = permissions;
    }
}
