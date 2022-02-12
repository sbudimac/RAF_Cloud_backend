package sbudimac.domaci3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.model.Status;
import sbudimac.domaci3.services.MachineService;
import sbudimac.domaci3.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/machines")
public class MachineRestController {
    private final UserService userService;
    private final MachineService machineService;

    @Autowired
    public MachineRestController(UserService userService, MachineService machineService) {
        this.userService = userService;
        this.machineService = machineService;
    }

    @GetMapping(value = "/all/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMachines(@PathVariable("userId") Long userId) {
        if (collectPermissions().getPermissions().isCanSearchMachines()) {
            return ResponseEntity.ok(machineService.findForUser(userId));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(value = "/search/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMachines(@PathVariable("userId") Long userId, @RequestParam(required = false) String name, @RequestParam(required = false) String status, @RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
        if (collectPermissions().getPermissions().isCanSearchMachines()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localFrom = null;
            LocalDate localTo = null;
            if (dateFrom != null) {
                localFrom = LocalDate.parse(dateFrom, formatter);
            }
            if (dateTo != null) {
                localTo = LocalDate.parse(dateTo, formatter);
            }
            if (status != null) {
                String[] statusArray = status.split(",");
                List<Status> statusList = new ArrayList<>();
                for (String s : statusArray) {
                    statusList.add(Status.valueOf(s));
                }
                return ResponseEntity.ok(machineService.searchMachines(name, statusList, localFrom, localTo, userId));
            }
            return ResponseEntity.ok(machineService.searchMachines(name, null, localFrom, localTo, userId));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@PathVariable("id") Long id) {
        if (collectPermissions().getPermissions().isCanStartMachines()) {
            if (machineService.machineIsWorking(id)) {
                return ResponseEntity.status(409).body("Machine is currently working.");
            }
            if (machineService.canStartMachine(id)) {
                machineService.startMachine(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(409).body("Machine is in a wronge state.");
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@PathVariable("id") Long id) {
        if (collectPermissions().getPermissions().isCanStopMachines()) {
            if (machineService.machineIsWorking(id)) {
                return ResponseEntity.status(409).body("Machine is currently working.");
            }
            if (machineService.canStopMachine(id)) {
                machineService.stopMachine(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(409).body("Machine is in a wrong state.");
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restartMachine(@PathVariable("id") Long id) {
        if (collectPermissions().getPermissions().isCanRestartMachines()) {
            if (machineService.machineIsWorking(id)) {
                return ResponseEntity.status(409).body("Machine is currently working.");
            }
            if (machineService.canStopMachine(id)) {
                machineService.restartMachine(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(409).body("Machine is in a wrong state.");
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/schedule/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStart(@PathVariable ("id") Long id, @RequestBody String date) {
        if (collectPermissions().getPermissions().isCanStartMachines()) {
            date = transformDate(date);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
            machineService.scheduleStart(id, dateTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/schedule/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStop(@PathVariable ("id") Long id, @RequestBody String date) {
        if (collectPermissions().getPermissions().isCanStopMachines()) {
            date = transformDate(date);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
            machineService.scheduleStop(id, dateTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PatchMapping(value = "/schedule/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleRestart(@PathVariable ("id") Long id, @RequestBody String date) {
        if (collectPermissions().getPermissions().isCanRestartMachines()) {
            date = transformDate(date);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
            machineService.scheduleRestart(id, dateTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMachine(@RequestBody Machine machine, @PathVariable("id") Long id) {
        if (collectPermissions().getPermissions().isCanCreateMachines()) {
            return ResponseEntity.ok(machineService.create(machine, id));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> destroyMachine(@PathVariable Long id) {
        if (collectPermissions().getPermissions().isCanDestroyMachines()) {
            Optional<Machine> machine = machineService.findById(id);
            if (machine.isPresent()) {
                Machine m = machine.get();
                if (m.getStatus() == Status.STOPPED) {
                    if (m.isWorking()) {
                        return ResponseEntity.status(409).body("Machine is currently working.");
                    }
                    machineService.destroyMachine(id);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(400).body("Machine is not stopped.");
                }
            }
            return ResponseEntity.status(404).body("Machine not found.");
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    private PermissionAuthority collectPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> permissions = this.userService.loadUserByUsername(email).getAuthorities();
        return (PermissionAuthority) permissions.toArray()[0];
    }

    private String transformDate(String date) {
        date = date.replace("\"", "");
        date = date.replace("date", "");
        date = date.replace("{", "");
        date = date.replace("}", "");
        date = date.substring(1);
        return date;
    }
}
