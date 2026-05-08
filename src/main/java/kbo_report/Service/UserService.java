package kbo_report.service;

import kbo_report.entity.User;
import kbo_report.repository.UserRepository;
import kbo_report.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 기능 (그대로 유지)
    public void register(User user) {
        String rawPassword = user.getPassword();
        String encryptedPassword = PasswordUtil.encrypt(rawPassword);
        user.setPassword(encryptedPassword);
        
        userRepository.save(user);
        System.out.println("DB 저장 완료: " + encryptedPassword);
    }

    // 로그인 검증 기능 (boolean -> User 반환으로 변경)
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String encryptedInput = PasswordUtil.encrypt(password);
            
            if (user.getPassword().equals(encryptedInput)) {
                return user; // 성공 시 User 정보 전체를 돌려줍니다!
            }
        }
        return null; // 실패 시 빈 값(null)을 돌려줍니다.
    }
}