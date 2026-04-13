package kbo_report.repository;

import kbo_report.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 아이디로 사용자 찾기
    Optional<User> findByUsername(String username);
}