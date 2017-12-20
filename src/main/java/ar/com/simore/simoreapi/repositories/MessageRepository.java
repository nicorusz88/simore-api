package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByFrom_IdAndTo_IdOrFrom_IdAndTo_IdOrderBySendDateAsc(Long user1Id, Long user2Id, Long user2Id1, Long user1Id1);

    List<Message> findByFrom_IdOrderBySendDateAsc(Long userId);

    List<Message> findByTo_IdOrderBySendDateAsc(Long userId);
}
