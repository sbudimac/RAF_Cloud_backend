package sbudimac.domaci3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sbudimac.domaci3.repositories.ErrorMessageRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/error")
public class ErrorMessagesController {
    private final ErrorMessageRepository errorMessageRepository;

    @Autowired
    public ErrorMessagesController(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getErrors(@PathVariable("id") Long id) {
        return ResponseEntity.ok(errorMessageRepository.findByUserId(id));
    }
}
