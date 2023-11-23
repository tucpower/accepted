package gr.accepted.stoiximan.repository;

import gr.accepted.stoiximan.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u " +
            "where u.email = :username " +
            "and u.password = :password")
    User authenticateUser(@Param("username") String username, @Param("password") String password);
}
