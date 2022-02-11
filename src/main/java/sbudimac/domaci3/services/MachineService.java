package sbudimac.domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.Status;
import sbudimac.domaci3.repositories.MachineRespository;
import sbudimac.domaci3.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    private final UserRepository userRepository;
    private final MachineRespository machineRespository;

    @Autowired
    public MachineService(UserRepository userRepository, MachineRespository machineRespository) {
        this.userRepository = userRepository;
        this.machineRespository = machineRespository;
    }

    public Machine create(Machine machine, Long id) {
        machine.setUser(userRepository.getById(id));
        return this.machineRespository.save(machine);
    }

    public List<Machine> findForUser(Long userId) {
        return this.machineRespository.findByUserIdAndActive(userId, true);
    }

    public List<Machine> searchMachines(String name, List<Status> status, LocalDate dateFrom, LocalDate dateTo, Long userId) {
        if (name != null && status == null && dateFrom == null && dateTo == null) {
            return this.machineRespository.findByUserIdAndNameContainingAndActive(userId, name, true);
        }
        if (name == null && status != null && dateFrom == null && dateTo == null) {
            return this.machineRespository.findByUserIdAndStatusInAndActive(userId, status, true);
        }
        if (name == null && status == null && dateFrom != null && dateTo == null) {
            return this.machineRespository.findByUserIdAndDateAfterAndActive(userId, dateFrom, true);
        }
        if (name == null && status == null && dateFrom == null && dateTo != null) {
            return this.machineRespository.findByUserIdAndDateBeforeAndActive(userId, dateTo, true);
        }
        if (name == null && status == null && dateFrom != null && dateTo != null) {
            return this.machineRespository.findByUserIdAndDateBetweenAndActive(userId, dateFrom, dateTo, true);
        }
        if (name != null && status != null && dateFrom == null  && dateTo == null) {
            return this.machineRespository.findByUserIdAndNameContainingAndStatusInAndActive(userId, name, status, true);
        }
        if (name != null && status == null && dateFrom != null  && dateTo == null) {
            return this.machineRespository.findByUserIdAndNameContainingAndDateAfterAndActive(userId, name, dateFrom, true);
        }
        if (name != null && status == null && dateFrom == null && dateTo != null) {
            return this.machineRespository.findByUserIdAndNameContainingAndDateBeforeAndActive(userId, name, dateTo, true);
        }
        if (name != null && status == null && dateFrom != null && dateTo != null) {
            return this.machineRespository.findByUserIdAndNameContainingAndDateBetweenAndActive(userId, name, dateFrom, dateTo, true);
        }
        if (name == null && status != null && dateFrom != null && dateTo ==  null) {
            return this.machineRespository.findByUserIdAndStatusInAndDateAfterAndActive(userId, status, dateFrom, true);
        }
        if (name == null && status != null && dateFrom == null && dateTo !=  null) {
            return this.machineRespository.findByUserIdAndStatusInAndDateBeforeAndActive(userId, status, dateTo, true);
        }
        if (name == null && status != null && dateFrom != null && dateTo !=  null) {
            return this.machineRespository.findByUserIdAndStatusInAndDateBetweenAndActive(userId, status, dateFrom, dateTo, true);
        }
        if (name != null && status != null && dateFrom != null && dateTo != null) {
            return this.machineRespository.findByUserIdAndNameContainingAndStatusInAndDateBetweenAndActive(userId, name, status, dateFrom, dateTo, true);
        }

        return new ArrayList<>();
    }

    @Async
    public void startMachine(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() != Status.STOPPED) {
                return;
            }
            m.setWorking(true);
            machineRespository.save(m);
            try {
                Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                m = machineRespository.findById(id).get();
                m.setStatus(Status.RUNNING);
                m.setWorking(false);
                machineRespository.save(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void stopMachine(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() != Status.RUNNING) {
                return;
            }
            m.setWorking(true);
            machineRespository.save(m);
            try {
                Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                m = machineRespository.findById(id).get();
                m.setStatus(Status.STOPPED);
                m.setWorking(false);
                machineRespository.save(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void restartMachine(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() != Status.RUNNING) {
                return;
            }
            m.setWorking(true);
            try {
                Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                m = machineRespository.findById(id).get();
                m.setStatus(Status.STOPPED);
                machineRespository.save(m);
                Thread.sleep((long)(Math.random() * (15 -10) + 10) * 1000);
                m = machineRespository.findById(id).get();
                m.setStatus(Status.RUNNING);
                m.setWorking(false);
                machineRespository.save(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(Long id) {
        machineRespository.deleteById(id);
    }

    public boolean machineIsWorking(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.isWorking()) {
                return true;
            }
        }
        return false;
    }

    public boolean canStartMachine(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() == Status.STOPPED) {
                return true;
            }
        }
        return false;
    }

    public boolean canStopMachine(Long id) {
        Optional<Machine> machine = machineRespository.findById(id);
        if (machine.isPresent()) {
            Machine m = machine.get();
            if (m.getStatus() == Status.RUNNING) {
                return true;
            }
        }
        return false;
    }
}
