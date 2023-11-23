package gr.accepted.stoiximan.controller;

import gr.accepted.stoiximan.model.dto.CreateMatchOddsDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchOddsDTO;
import gr.accepted.stoiximan.service.MatchOddsService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matchOdds")
@PermitAll
public class MatchOddsController {

    @Autowired
    private MatchOddsService matchOddsService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(matchOddsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(matchOddsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createMatchOdds(@RequestBody CreateMatchOddsDTO createMatchOddsDTO) {
        return ResponseEntity.ok(matchOddsService.createMatchOdds(createMatchOddsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody UpdateMatchOddsDTO updateMatchOddsDTO) {
        return ResponseEntity.ok(matchOddsService.updateById(id, updateMatchOddsDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        matchOddsService.deleteById(id);
        ResponseEntity.ok("Match Odds were deleted successfully");
    }
}