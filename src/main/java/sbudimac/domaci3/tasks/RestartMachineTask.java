package sbudimac.domaci3.tasks;

import sbudimac.domaci3.model.ErrorMessage;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.Status;
import sbudimac.domaci3.repositories.ErrorMessageRepository;
import sbudimac.domaci3.repositories.MachineRespository;

import java.util.Date;
import java.util.Optional;

public class RestartMachineTask implements Runnable {

    private Long machineId;
    private Long userId;
    private MachineRespository machineRespository;
    private ErrorMessageRepository errorMessageRepository;

    public RestartMachineTask(Long machineId, Long userId, MachineRespository machineRespository, ErrorMessageRepository errorMessageRepository) {
        this.machineId = machineId;
        this.userId = userId;
        this.machineRespository = machineRespository;
        this.errorMessageRepository = errorMessageRepository;
    }

    @Override
    public void run() {
        Optional<Machine> machine = machineRespository.findByIdAndUserIdAndActive(machineId, userId, true);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() != Status.RUNNING) {
                errorMessageRepository.save(new ErrorMessage(new Date(), m.getId(), this.userId, "RESTART", "Machine is already running."));
                return;
            }
            if (m.isWorking()) {
                errorMessageRepository.save(new ErrorMessage(new Date(), m.getId(), this.userId, "RESTART", "Machine is currently working."));
            } else {
                try {
                    m.setWorking(true);
                    machineRespository.save(m);
                    Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                    m.setStatus(Status.STOPPED);
                    machineRespository.save(m);
                    Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                    m = machineRespository.findById(m.getId()).get();
                    m.setStatus(Status.RUNNING);
                    m.setWorking(false);
                    machineRespository.save(m);
                } catch (Exception e) {
                    errorMessageRepository.save(new ErrorMessage(new Date(), m.getId(), this.userId, "RESTART", "Failed to restart a machine."));
                }
            }
        }
    }
}
