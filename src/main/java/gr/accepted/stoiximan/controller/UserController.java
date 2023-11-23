package gr.accepted.stoiximan.controller;

import gr.accepted.stoiximan.model.dto.CreateUserDTO;
import gr.accepted.stoiximan.model.dto.UpdateUserDTO;
import gr.accepted.stoiximan.service.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@PermitAll
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(userService.createUser(createUserDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateById(id, updateUserDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        ResponseEntity.ok("User was deleted successfully");
    }
}