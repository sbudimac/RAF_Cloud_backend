package sbudimac.domaci3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sbudimac.domaci3.model.Machine;
import sbudimac.domaci3.model.PermissionAuthority;
import sbudimac.domaci3.services.MachineService;
import sbudimac.domaci3.services.UserService;

import java.util.Collection;

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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMachines(@RequestBody Long userId) {
        if (collectPermissions().getPermissions().isCanSearchMachines()) {
            return ResponseEntity.ok(machineService.findForUser(userId));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMachine(@RequestBody Machine machine, @RequestBody Long userId) {
        if (collectPermissions().getPermissions().isCanCreateMachines()) {
            return ResponseEntity.ok(machineService.create(machine, userId));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> destroyMachine(@PathVariable Long id) {
        if (collectPermissions().getPermissions().isCanDestroyMachines()) {
            machineService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    private PermissionAuthority collectPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> permissions = this.userService.loadUserByUsername(email).getAuthorities();
        return (PermissionAuthority) permissions.toArray()[0];
    }
}
