package wolox.training.repositories;

import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
