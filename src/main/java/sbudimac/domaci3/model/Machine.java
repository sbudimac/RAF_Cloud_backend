package sbudimac.domaci3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private boolean active;
    private boolean working;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Version
    @JsonIgnore
    private Long version;
}
