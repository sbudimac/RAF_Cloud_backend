package sbudimac.domaci3.mappers;

import org.springframework.stereotype.Component;
import sbudimac.domaci3.model.User;
import sbudimac.domaci3.model.UserDto;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPermissions());
    }
}
