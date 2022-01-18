package sbudimac.domaci3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "canCreateUsers", column = @Column(name = "create_users_perm")),
            @AttributeOverride(name = "canReadUsers", column = @Column(name = "read_users_perm")),
            @AttributeOverride(name = "canUpdateUsers", column = @Column(name = "update_users_perm")),
            @AttributeOverride(name = "canDeleteUsers", column = @Column(name = "delete_users_perm"))
    })
    private Permissions permissions;
}
