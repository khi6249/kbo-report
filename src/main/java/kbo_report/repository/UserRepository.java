package kbo_report.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kbo_report.entity.User; // 이 줄이 빠져있어서 User를 못 찾는 거예요!

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}