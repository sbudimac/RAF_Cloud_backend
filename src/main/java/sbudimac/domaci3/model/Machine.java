package sbudimac.domaci3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
