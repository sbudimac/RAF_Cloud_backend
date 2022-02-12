package sbudimac.domaci3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "error_message")
public class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Long machineId;
    @Column(nullable = false)
    private String operation;
    @Column(nullable = false)
    private String message;
    @JsonIgnore
    @Column(nullable = false)
    private Long userId;

    public ErrorMessage(Date date, Long machineId, Long userId, String operation, String message) {
        this.date = date;
        this.machineId = machineId;
        this.userId = userId;
        this.operation = operation;
        this.message = message;
    }

    public ErrorMessage() {

    }
}
