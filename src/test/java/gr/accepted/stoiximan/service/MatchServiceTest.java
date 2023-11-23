package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateMatchDTO;
import gr.accepted.stoiximan.model.dto.UpdateMatchDTO;
import gr.accepted.stoiximan.model.entity.Match;
import gr.accepted.stoiximan.model.entity.MatchOdds;
import gr.accepted.stoiximan.model.entity.enumeration.SportEnum;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.MatchRepository;
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
class MatchServiceTest {

    @InjectMocks
    MatchServiceImpl matchService;

    @Mock
    MatchRepository matchRepository;

    private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 1, 18);
    private static final LocalTime LOCAL_TIME = LocalTime.of(20, 0, 0);
    private static final String MATCH_DESCRIPTION = "OSFP-PAO";
    private static final String TEAM_A = "OSFP";
    private static final String TEAM_B = "PAO";
    private static final UUID MATCH_ID = UUID.randomUUID();

    private List<Match> matches;
    private Match match1;
    private List<MatchOdds> matchOddsList1;

    @BeforeEach
    void setUp() {
        MatchOdds matchOdds1 = MatchOdds.builder().build();
        matchOddsList1 = new ArrayList<>();
        matchOddsList1.add(matchOdds1);
        MatchOdds matchOdds2 = MatchOdds.builder().build();
        List<MatchOdds> matchOddsList2 = new ArrayList<>();
        matchOddsList1.add(matchOdds2);

        match1 = Match.builder()
                .id(MATCH_ID)
                .description(MATCH_DESCRIPTION)
                .matchDate(LOCAL_DATE)
                .matchTime(LOCAL_TIME)
                .teamA(TEAM_A)
                .teamB(TEAM_B)
                .sport(SportEnum.FOOTBALL)
                .matchOdds(matchOddsList1)
                .build();
        Match match2 = Match.builder()
                .id(UUID.randomUUID())
                .description("AEK-PAOK")
                .matchDate(LOCAL_DATE)
                .matchTime(LOCAL_TIME)
                .teamA("AEK")
                .teamB("PAOK")
                .sport(SportEnum.FOOTBALL)
                .matchOdds(matchOddsList2)
                .build();
        matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);
    }

    @Test
    void findAll_Success() {
        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> matches = matchService.findAll();

        assertThat(matches).isNotNull();
        assertEquals(2, matches.size());
    }

    @Test
    void findById_Success() {
        when(matchRepository.findById(MATCH_ID)).thenReturn(Optional.ofNullable(match1));

        Match match = matchService.findById(MATCH_ID);

        assertThat(match).isNotNull();
        assertEquals(MATCH_ID, match.getId());
        assertEquals(MATCH_DESCRIPTION, match.getDescription());
        assertEquals(LOCAL_DATE, match.getMatchDate());
        assertEquals(LOCAL_TIME, match.getMatchTime());
        assertEquals(TEAM_A, match.getTeamA());
        assertEquals(TEAM_B, match.getTeamB());
        assertEquals(SportEnum.FOOTBALL, match.getSport());
        assertEquals(matchOddsList1, match.getMatchOdds());
    }

    @Test
    void findById_NotFound() {
        ResourceNotFoundException thrown =
                Assertions.assertThrows(ResourceNotFoundException.class, () -> matchService.findById(MATCH_ID));
        assertNull(thrown.getMessage());
    }

    @Test
    void createMatch_Success() {
        CreateMatchDTO createMatchDTO = new CreateMatchDTO();
        createMatchDTO.setDescription(MATCH_DESCRIPTION);
        createMatchDTO.setMatchDate(LOCAL_DATE);
        createMatchDTO.setMatchTime(LOCAL_TIME);
        createMatchDTO.setTeamA(TEAM_A);
        createMatchDTO.setTeamB(TEAM_B);
        createMatchDTO.setSport(SportEnum.FOOTBALL);
        createMatchDTO.setMatchOdds(matchOddsList1);

        Match createMatch = new Match();
        createMatch.setDescription(createMatchDTO.getDescription());
        createMatch.setMatchDate(createMatchDTO.getMatchDate());
        createMatch.setMatchTime(createMatchDTO.getMatchTime());
        createMatch.setTeamA(createMatchDTO.getTeamA());
        createMatch.setTeamB(createMatchDTO.getTeamB());
        createMatch.setSport(createMatchDTO.getSport());
        createMatch.setMatchOdds(createMatchDTO.getMatchOdds());

        when(matchRepository.save(createMatch)).thenReturn(match1);

        Match createdMatch = matchService.createMatch(createMatchDTO);

        assertEquals(MATCH_ID, createdMatch.getId());
        assertEquals(MATCH_DESCRIPTION, createdMatch.getDescription());
        assertEquals(LOCAL_DATE, createdMatch.getMatchDate());
        assertEquals(LOCAL_TIME, createdMatch.getMatchTime());
        assertEquals(TEAM_A, createdMatch.getTeamA());
        assertEquals(TEAM_B, createdMatch.getTeamB());
        assertEquals(SportEnum.FOOTBALL, createdMatch.getSport());
        assertEquals(matchOddsList1, createdMatch.getMatchOdds());
    }

    @Test
    void updateById() {
        UpdateMatchDTO updateMatchDTO = new UpdateMatchDTO();
        updateMatchDTO.setDescription(MATCH_DESCRIPTION);
        updateMatchDTO.setMatchDate(LOCAL_DATE);
        updateMatchDTO.setMatchTime(LOCAL_TIME);
        updateMatchDTO.setTeamA(TEAM_A);
        updateMatchDTO.setTeamB(TEAM_B);
        updateMatchDTO.setSport(SportEnum.FOOTBALL);
        updateMatchDTO.setMatchOdds(matchOddsList1);

        Match updateMatch = new Match();
        updateMatch.setId(MATCH_ID);
        updateMatch.setDescription(updateMatchDTO.getDescription());
        updateMatch.setMatchDate(updateMatchDTO.getMatchDate());
        updateMatch.setMatchTime(updateMatchDTO.getMatchTime());
        updateMatch.setTeamA(updateMatchDTO.getTeamA());
        updateMatch.setTeamB(updateMatchDTO.getTeamB());
        updateMatch.setSport(updateMatchDTO.getSport());
        updateMatch.setMatchOdds(updateMatchDTO.getMatchOdds());

        when(matchRepository.findById(MATCH_ID)).thenReturn(Optional.ofNullable(match1));
        when(matchRepository.save(updateMatch)).thenReturn(match1);

        Match updatedMatch = matchService.updateById(MATCH_ID, updateMatchDTO);

        assertEquals(MATCH_DESCRIPTION, updatedMatch.getDescription());
        assertEquals(LOCAL_DATE, updatedMatch.getMatchDate());
        assertEquals(LOCAL_TIME, updatedMatch.getMatchTime());
        assertEquals(TEAM_A, updatedMatch.getTeamA());
        assertEquals(TEAM_B, updatedMatch.getTeamB());
        assertEquals(SportEnum.FOOTBALL, updatedMatch.getSport());
        assertEquals(matchOddsList1, updatedMatch.getMatchOdds());
    }

    @Test
    void deleteById() {
        when(matchRepository.existsById(MATCH_ID)).thenReturn(true);
        doNothing().when(matchRepository).deleteById(MATCH_ID);

        matchService.deleteById(MATCH_ID);

        verify(matchRepository, times(1)).existsById(MATCH_ID);
        verify(matchRepository, times(1)).deleteById(MATCH_ID);
    }
}