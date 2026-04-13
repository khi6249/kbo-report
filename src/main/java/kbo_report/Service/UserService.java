package kbo_report.service; // 현재 폴더 구조가 repository 안에 service가 있으므로 이렇게 써야 합니다.

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

    // 회원가입 기능
    public void register(User user) {
        String rawPassword = user.getPassword();
        String encryptedPassword = PasswordUtil.encrypt(rawPassword);
        user.setPassword(encryptedPassword);
        
        userRepository.save(user);
        System.out.println("DB 저장 완료: " + encryptedPassword);
    }

    // 로그인 검증 기능
    public boolean login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String encryptedInput = PasswordUtil.encrypt(password);
            return user.getPassword().equals(encryptedInput);
        }
        return false;
    }
}