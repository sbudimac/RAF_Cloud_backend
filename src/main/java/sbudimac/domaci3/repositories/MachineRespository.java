package sbudimac.domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.Status;

import java.sql.Date;
import java.util.List;

public interface MachineRespository extends JpaRepository<Machine, Long> {
    @Modifying
    @Query(value =
    "INSERT INTO machine (name, date, active, status, user_id) VALUES (:name, :date, :active, :status, :userId)",
    nativeQuery = true)
    Machine createMachineForUser(@Param("name") String name, @Param("date")Date date, @Param("active") Boolean active, @Param("status")Status status, @Param("userId") Long userId);

    @Query(value =
    "SELECT * FROM machine m JOIN user u ON m.user_id = u.id WHERE u.id = :userId",
    nativeQuery = true)
    List<Machine> getMachinesForUser(@Param("userId") Long userId);
}
