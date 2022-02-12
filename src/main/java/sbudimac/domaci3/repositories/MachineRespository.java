package sbudimac.domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MachineRespository extends JpaRepository<Machine, Long> {
    List<Machine> findByUserIdAndActive(Long userId, boolean active);
    List<Machine> findByUserIdAndNameContainingAndActive(Long userId, String name, boolean active);
    List<Machine> findByUserIdAndStatusInAndActive(Long userId, List<Status> status, boolean active);
    List<Machine> findByUserIdAndDateAfterAndActive(Long userId, LocalDate dateFrom, boolean active);
    List<Machine> findByUserIdAndDateBeforeAndActive(Long userId, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndDateBetweenAndActive(Long userId, LocalDate dateFrom, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndNameContainingAndStatusInAndActive(Long userId, String name, List<Status> status, boolean active);
    List<Machine> findByUserIdAndNameContainingAndDateAfterAndActive(Long userId, String name, LocalDate dateFrom, boolean active);
    List<Machine> findByUserIdAndNameContainingAndDateBeforeAndActive(Long userId, String name, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndNameContainingAndDateBetweenAndActive(Long userId, String name, LocalDate dateFrom, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndStatusInAndDateAfterAndActive(Long userId, List<Status> status, LocalDate dateFrom, boolean active);
    List<Machine> findByUserIdAndStatusInAndDateBeforeAndActive(Long userId, List<Status> status, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndStatusInAndDateBetweenAndActive(Long userId, List<Status> status, LocalDate dateBefore, LocalDate dateTo, boolean active);
    List<Machine> findByUserIdAndNameContainingAndStatusInAndDateBetweenAndActive(Long userId, String name, List<Status> status, LocalDate dateFrom, LocalDate dateTo, boolean active);
    Optional<Machine> findByIdAndUserIdAndActive(Long machineId, Long userId, boolean active);

















    /*
    @Modifying
    @Query(value =
    "INSERT INTO machine (name, date, active, status, user_id) VALUES (:name, :date, :active, :status, :userId)",
    nativeQuery = true)
    void createMachineForUser(@Param("name") String name, @Param("date") LocalDate date, @Param("active") Boolean active, @Param("status")Status status, @Param("userId") Long userId);
    */

    /*
    @Query(value =
    "SELECT m.* FROM machine m JOIN user u ON m.user_id = u.id WHERE u.id = :userId",
    nativeQuery = true)
    List<Machine> getMachinesForUser(@Param("userId") Long userId);

    @Query(value =
    "SELECT * FROM machine m JOIN user u ON m.user_id = u.id WHERE u.id = :userId "
    + "AND ((:name IS NULL) OR (:name IS NOT NULL AND LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))))"
    + "AND ((:status IS NULL) OR (m.status IN :status))"
    + "AND ((:dateFrom IS NULL) OR (m.date > :dateFrom))"
    + "AND ((:dateTo IS NULL) OR (m.date < :dateTo))",
    nativeQuery = true)
    List<Machine> searchMachines(@Param("name") String name, @Param("status") List<Status> status, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("userId") Long userId);
    */
}
