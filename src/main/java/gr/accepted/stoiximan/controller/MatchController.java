package gr.accepted.stoiximan.controller;

import gr.accepted.stoiximan.model.dto.CreateMatchDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchDTO;
import gr.accepted.stoiximan.service.MatchService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matches")
@PermitAll
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(matchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(matchService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody CreateMatchDTO createMatchDTO) {
        return ResponseEntity.ok(matchService.createMatch(createMatchDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody UpdateMatchDTO updateMatchDTO) {
        return ResponseEntity.ok(matchService.updateById(id, updateMatchDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        matchService.deleteById(id);
        ResponseEntity.ok("Match was deleted successfully");
    }
}