package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchOddsDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchOddsDTO;
import gr.accepted.stoiximan.model.entity.MatchOdds;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.MatchOddsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchOddsServiceImpl implements MatchOddsService {

    @Autowired
    private MatchOddsRepository matchOddsRepository;

    @Override
    public List<MatchOdds> findAll() {
        return matchOddsRepository.findAll();
    }

    public MatchOdds findById(UUID id) throws ResourceNotFoundException {
        return matchOddsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public MatchOdds createMatchOdds(CreateMatchOddsDTO createMatchOddsDTO) {
        MatchOdds matchOdds = new MatchOdds();
        BeanUtils.copyProperties(createMatchOddsDTO, matchOdds);

        return matchOddsRepository.save(matchOdds);
    }

    public MatchOdds updateById(UUID id, UpdateMatchOddsDTO updateMatchOddsDTO) throws ResourceNotFoundException {
        MatchOdds existingMatchOdds = findById(id);
        if(updateMatchOddsDTO.getSpecifier() != null) {
            existingMatchOdds.setSpecifier(updateMatchOddsDTO.getSpecifier());
        }
        if(updateMatchOddsDTO.getOdd() != null) {
            existingMatchOdds.setOdd(updateMatchOddsDTO.getOdd());
        }
        if(updateMatchOddsDTO.getMatch() != null) {
            existingMatchOdds.setMatch(updateMatchOddsDTO.getMatch());
        }
        return matchOddsRepository.save(existingMatchOdds);
    }

    public void deleteById(UUID id) throws ResourceNotFoundException {
        if(matchOddsRepository.existsById(id)) {
            matchOddsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
