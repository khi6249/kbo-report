package kbo_report.service;

import kbo_report.entity.User;
import kbo_report.repository.UserRepository;
import kbo_report.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🌟 추가

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 기능 (기존 유지)
    public void register(User user) {
        String rawPassword = user.getPassword();
        String encryptedPassword = PasswordUtil.encrypt(rawPassword);
        user.setPassword(encryptedPassword);
        
        userRepository.save(user);
    }

    // 로그인 검증 기능 (기존 유지)
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String encryptedInput = PasswordUtil.encrypt(password);
            
            if (user.getPassword().equals(encryptedInput)) {
                return user; 
            }
        }
        return null; 
    }

    // 🌟 [추가] 응원 구단 변경 비즈니스 로직
    @Transactional
    public boolean updateFavoriteTeam(String username, String favoriteTeam) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFavoriteTeam(favoriteTeam); // 변경 감지로 자동 DB Update 실행
            return true;
        }
        return false; 
    }
}