package sbudimac.domaci3.mappers;

import org.springframework.stereotype.Component;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.model.Permissions;

@Component
public class PermissionMapper {
    public PermissionAuthority permissionAuthorityToPermissions(Permissions permissions) {
        return new PermissionAuthority(permissions);
    }
}
