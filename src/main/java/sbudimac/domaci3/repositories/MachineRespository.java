package sbudimac.domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sbudimac.domaci3.model.Machine;

public interface MachineRespository extends JpaRepository<Machine, Long> {
}
