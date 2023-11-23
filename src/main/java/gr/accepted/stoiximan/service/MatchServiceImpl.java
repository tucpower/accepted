package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchDTO;
import gr.accepted.stoiximan.model.entity.Match;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.MatchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Match findById(UUID id) throws ResourceNotFoundException {
        return matchRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Match createMatch(CreateMatchDTO createMatchDTO) {
        Match match = new Match();
        BeanUtils.copyProperties(createMatchDTO, match);

        return matchRepository.save(match);
    }

    public Match updateById(UUID id, UpdateMatchDTO updateMatchDTO) throws ResourceNotFoundException {
        Match existingMatch = findById(id);
        if(updateMatchDTO.getDescription() != null) {
            existingMatch.setDescription(updateMatchDTO.getDescription());
        }
        if(updateMatchDTO.getMatchDate() != null) {
            existingMatch.setMatchDate(updateMatchDTO.getMatchDate());
        }
        if(updateMatchDTO.getMatchTime() != null) {
            existingMatch.setMatchTime(updateMatchDTO.getMatchTime());
        }
        if(updateMatchDTO.getTeamA() != null) {
            existingMatch.setTeamA(updateMatchDTO.getTeamA());
        }
        if(updateMatchDTO.getTeamB() != null) {
            existingMatch.setTeamB(updateMatchDTO.getTeamB());
        }
        if(updateMatchDTO.getSport() != null) {
            existingMatch.setSport(updateMatchDTO.getSport());
        }
        if(updateMatchDTO.getMatchOdds() != null) {
            existingMatch.setMatchOdds(updateMatchDTO.getMatchOdds());
        }
        return matchRepository.save(existingMatch);
    }

    public void deleteById(UUID id) throws ResourceNotFoundException {
        if(matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
