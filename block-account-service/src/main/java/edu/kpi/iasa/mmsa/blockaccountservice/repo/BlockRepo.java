package edu.kpi.iasa.mmsa.blockaccountservice.repo;

import edu.kpi.iasa.mmsa.blockaccountservice.repo.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepo extends JpaRepository<Block, Long> {

    List<Block> findByUserId(Long userId);
    Block findByUserIdAndBlockedUserId(Long userId, Long blockedUserId);
}
