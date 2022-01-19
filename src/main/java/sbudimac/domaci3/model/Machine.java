package sbudimac.domaci3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private boolean active;
    @ManyToOne
    //@JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
}
