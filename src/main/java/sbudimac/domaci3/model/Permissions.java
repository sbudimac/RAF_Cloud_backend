package sbudimac.domaci3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@Embeddable
public class Permissions {
    private boolean canCreateUsers;
    private boolean canReadUsers;
    private boolean canUpdateUsers;
    private boolean canDeleteUsers;
}
