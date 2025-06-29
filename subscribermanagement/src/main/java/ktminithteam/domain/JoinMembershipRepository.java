package ktminithteam.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinMembershipRepository extends JpaRepository<JoinMembership, Long> {
    Optional<JoinMembership> findByUsername(String username);
    Optional<JoinMembership> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}