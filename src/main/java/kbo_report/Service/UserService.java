package kbo_report.Service;

import kbo_report.Entity.User;
import kbo_report.repository.UserRepository; // Repository 연결 필요
import kbo_report.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired; // 추가
import org.springframework.stereotype.Service;

import java.util.Optional; // 추가

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // DB에 접근하기 위한 도구 추가

    // [기존] 회원가입 기능 (DB 저장 로직 완성)
    public void register(User user) {
        String rawPassword = user.getPassword();
        String encryptedPassword = PasswordUtil.encrypt(rawPassword);
        user.setPassword(encryptedPassword);
        
        // 실제로 DB에 저장하는 코드 추가
        userRepository.save(user); 
        System.out.println("DB 저장 완료: " + encryptedPassword);
    }

    // [신규] 로그인 검증 기능
    public boolean login(String username, String password) {
        // 1. DB에서 아이디로 사용자 찾기
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 2. 사용자가 입력한 비번을 암호화해서 DB의 값과 비교
            String encryptedInput = PasswordUtil.encrypt(password);
            return user.getPassword().equals(encryptedInput);
        }
        return false; // 아이디가 없거나 비번이 틀림
    }
}