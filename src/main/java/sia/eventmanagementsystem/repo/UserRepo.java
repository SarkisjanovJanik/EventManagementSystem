package sia.eventmanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.eventmanagementsystem.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {
}
