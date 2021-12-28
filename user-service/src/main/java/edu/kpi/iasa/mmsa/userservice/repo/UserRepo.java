package edu.kpi.iasa.mmsa.userservice.repo;

import edu.kpi.iasa.mmsa.userservice.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
}
