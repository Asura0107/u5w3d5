package u5w3d5.u5w3d5.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import u5w3d5.u5w3d5.entities.Events;

import java.util.UUID;

@Repository
public interface EventsDAO extends JpaRepository<Events, UUID> {
}
