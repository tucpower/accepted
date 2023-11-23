package gr.accepted.stoiximan.model.dto;

import gr.accepted.stoiximan.model.entity.MatchOdds;
import gr.accepted.stoiximan.model.entity.enumeration.SportEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class CreateMatchDTO {
    private String description;
    private LocalDate matchDate;
    private LocalTime matchTime;
    private String teamA;
    private String teamB;
    private SportEnum sport;
    private List<MatchOdds> matchOdds = new LinkedList<>();
}
