package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchOddsDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchOddsDTO;
import gr.accepted.stoiximan.model.entity.MatchOdds;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MatchOddsService {
    public List<MatchOdds> findAll();
    MatchOdds findById(UUID id) throws ResourceNotFoundException;
    MatchOdds createMatchOdds(CreateMatchOddsDTO createMatchOddsDTO);
    MatchOdds updateById(UUID id, UpdateMatchOddsDTO updateMatchOddsDTO) throws ResourceNotFoundException;
    void deleteById(UUID id) throws ResourceNotFoundException;
}
