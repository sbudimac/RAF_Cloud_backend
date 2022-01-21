package sbudimac.domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.repositories.MachineRespository;

import java.util.List;

@Service
public class MachineService {
    private final MachineRespository machineRespository;

    @Autowired
    public MachineService(MachineRespository machineRespository) {
        this.machineRespository = machineRespository;
    }

    public Machine create(Machine machine, Long userId) {
        return this.machineRespository.createMachineForUser(machine.getName(), machine.getDate(), machine.isActive(), machine.getStatus(), userId);
    }

    public List<Machine> findForUser(Long userId) {
        return this.machineRespository.getMachinesForUser(userId);
    }

    public void deleteById(Long id) {
        machineRespository.deleteById(id);
    }
}
