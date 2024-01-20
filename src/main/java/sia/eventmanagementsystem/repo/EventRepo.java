package sia.eventmanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.eventmanagementsystem.entity.Event;

public interface EventRepo extends JpaRepository<Event,Long> {
}
