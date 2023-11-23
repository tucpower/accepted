package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchDTO;
import gr.accepted.stoiximan.model.entity.Match;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    public List<Match> findAll();
    Match findById(UUID id) throws ResourceNotFoundException;
    Match createMatch(CreateMatchDTO createMatchDTO);
    Match updateById(UUID id, UpdateMatchDTO updateMatchDTO) throws ResourceNotFoundException;
    void deleteById(UUID id) throws ResourceNotFoundException;
}
