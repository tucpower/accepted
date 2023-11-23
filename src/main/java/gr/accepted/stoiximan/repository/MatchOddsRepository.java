package gr.accepted.stoiximan.repository;

import gr.accepted.stoiximan.model.entity.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, UUID> {

}
