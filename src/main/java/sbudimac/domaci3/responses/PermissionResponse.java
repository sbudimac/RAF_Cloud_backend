package sbudimac.domaci3.responses;

import lombok.Data;
import sbudimac.domaci3.model.Permissions;

@Data
public class PermissionResponse {
    private Permissions permissions;

    public PermissionResponse(Permissions permissions) {
        this.permissions = permissions;
    }
}
