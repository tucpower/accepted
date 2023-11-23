package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchOddsDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchOddsDTO;
import gr.accepted.stoiximan.model.entity.Match;
import gr.accepted.stoiximan.model.entity.MatchOdds;
import gr.accepted.stoiximan.model.entity.enumeration.SportEnum;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.MatchOddsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchOddsServiceTest {

    @InjectMocks
    MatchOddsServiceImpl matchOddsService;

    @Mock
    MatchOddsRepository matchOddsRepository;

    private static final String SPECIFIER = "X";
    private static final float ODD = 2.5F;
    private static final UUID MATCH_ODDS_ID = UUID.randomUUID();

    private List<MatchOdds> matchOddsList;
    private MatchOdds matchOdds1;
    private Match match;

    @BeforeEach
    void setUp() {
        match = Match.builder()
                .id(UUID.randomUUID())
                .description("OSFP-PAO")
                .matchDate(LocalDate.of(2020, 1, 18))
                .matchTime(LocalTime.of(20, 0, 0))
                .teamA("OSFP")
                .teamB("PAO")
                .sport(SportEnum.FOOTBALL)
                .matchOdds(matchOddsList)
                .build();

        matchOdds1 = MatchOdds.builder()
                .id(MATCH_ODDS_ID)
                .specifier(SPECIFIER)
                .odd(ODD)
                .match(match)
                .build();
        MatchOdds matchOdds2 = MatchOdds.builder()
                .id(UUID.randomUUID())
                .specifier("1")
                .odd(2F)
                .match(match)
                .build();
        matchOddsList = new ArrayList<>();
        matchOddsList.add(matchOdds1);
        matchOddsList.add(matchOdds2);
    }

    @Test
    void findAll_Success() {
        when(matchOddsRepository.findAll()).thenReturn(matchOddsList);

        List<MatchOdds> matchOdds = matchOddsService.findAll();

        assertThat(matchOdds).isNotNull();
        assertEquals(2, matchOdds.size());
    }

    @Test
    void findById_Success() {
        when(matchOddsRepository.findById(MATCH_ODDS_ID)).thenReturn(Optional.ofNullable(matchOdds1));

        MatchOdds matchOdds = matchOddsService.findById(MATCH_ODDS_ID);

        assertThat(matchOdds).isNotNull();
        assertEquals(MATCH_ODDS_ID, matchOdds.getId());
        assertEquals(SPECIFIER, matchOdds.getSpecifier());
        assertEquals(ODD, matchOdds.getOdd());
        assertEquals(match, matchOdds.getMatch());
    }

    @Test
    void findById_NotFound() {
        ResourceNotFoundException thrown =
                Assertions.assertThrows(ResourceNotFoundException.class, () -> matchOddsService.findById(MATCH_ODDS_ID));
        assertNull(thrown.getMessage());
    }

    @Test
    void createMatchOdds_Success() {
        CreateMatchOddsDTO createMatchOddsDTO = new CreateMatchOddsDTO();
        createMatchOddsDTO.setSpecifier(SPECIFIER);
        createMatchOddsDTO.setOdd(ODD);
        createMatchOddsDTO.setMatch(match);

        MatchOdds createMatchOdds = new MatchOdds();
        createMatchOdds.setSpecifier(createMatchOddsDTO.getSpecifier());
        createMatchOdds.setOdd(createMatchOddsDTO.getOdd());
        createMatchOdds.setMatch(createMatchOddsDTO.getMatch());

        when(matchOddsRepository.save(createMatchOdds)).thenReturn(matchOdds1);

        MatchOdds createdMatchOdds = matchOddsService.createMatchOdds(createMatchOddsDTO);

        assertEquals(MATCH_ODDS_ID, createdMatchOdds.getId());
        assertEquals(SPECIFIER, createdMatchOdds.getSpecifier());
        assertEquals(ODD, createdMatchOdds.getOdd());
        assertEquals(match, createdMatchOdds.getMatch());
    }

    @Test
    void updateById_Success() {
        UpdateMatchOddsDTO updateMatchOddsDTO = new UpdateMatchOddsDTO();
        updateMatchOddsDTO.setSpecifier(SPECIFIER);
        updateMatchOddsDTO.setOdd(ODD);
        updateMatchOddsDTO.setMatch(match);

        MatchOdds updateMatchOdds = new MatchOdds();
        updateMatchOdds.setId(MATCH_ODDS_ID);
        updateMatchOdds.setSpecifier(updateMatchOddsDTO.getSpecifier());
        updateMatchOdds.setOdd(updateMatchOddsDTO.getOdd());
        updateMatchOdds.setMatch(updateMatchOddsDTO.getMatch());

        when(matchOddsRepository.findById(MATCH_ODDS_ID)).thenReturn(Optional.ofNullable(matchOdds1));
        when(matchOddsRepository.save(updateMatchOdds)).thenReturn(matchOdds1);

        MatchOdds updatedMatchOdds = matchOddsService.updateById(MATCH_ODDS_ID, updateMatchOddsDTO);

        assertEquals(SPECIFIER, updatedMatchOdds.getSpecifier());
        assertEquals(ODD, updatedMatchOdds.getOdd());
        assertEquals(match, updatedMatchOdds.getMatch());
    }

    @Test
    void deleteById() {
        when(matchOddsRepository.existsById(MATCH_ODDS_ID)).thenReturn(true);
        doNothing().when(matchOddsRepository).deleteById(MATCH_ODDS_ID);

        matchOddsService.deleteById(MATCH_ODDS_ID);

        verify(matchOddsRepository, times(1)).existsById(MATCH_ODDS_ID);
        verify(matchOddsRepository, times(1)).deleteById(MATCH_ODDS_ID);
    }
}