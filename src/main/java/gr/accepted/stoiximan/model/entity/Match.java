package gr.accepted.stoiximan.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import gr.accepted.stoiximan.model.entity.enumeration.SportEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate matchDate;

    @NotNull
    private LocalTime matchTime;

    @NotBlank
    @Column(name = "team_a")
    private String teamA;

    @NotBlank
    @Column(name = "team_b")
    private String teamB;

    @NotNull
    private SportEnum sport;

    @OneToMany(mappedBy = "match")
    @JsonIgnore
    @Nullable
    private List<MatchOdds> matchOdds = new LinkedList<>();
}
