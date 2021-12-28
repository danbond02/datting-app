package edu.kpi.iasa.mmsa.messageservice.repo;

import edu.kpi.iasa.mmsa.messageservice.repo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdAndReceiverIdOrderByDate (Long senderId, Long receiverId);
}
