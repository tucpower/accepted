package gr.accepted.stoiximan.model.dto;

import gr.accepted.stoiximan.model.entity.Match;
import lombok.Data;

@Data
public class CreateMatchOddsDTO {
    private String specifier;
    private Float odd;
    private Match match;
}
