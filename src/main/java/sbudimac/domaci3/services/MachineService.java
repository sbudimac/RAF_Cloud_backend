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

    public Machine create(Machine machine) {
        return this.machineRespository.save(machine);
    }

    public List<Machine> findAll() {
        return this.machineRespository.findAll();
    }

    public void deleteById(Long id) {
        machineRespository.deleteById(id);
    }
}
