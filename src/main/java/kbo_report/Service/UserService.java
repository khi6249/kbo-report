package kbo_report.Service;

import kbo_report.Entity.User;
import kbo_report.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    // 회원가입 기능
    public void register(User user) {
        // 1. 사용자가 입력한 생 비밀번호(예: 1234)를 가져옴
        String rawPassword = user.getPassword();
        
        // 2. 아까 만든 도구로 암호화 실행
        String encryptedPassword = PasswordUtil.encrypt(rawPassword);
        
        // 3. 암호화된 비번으로 교체해서 저장 준비
        user.setPassword(encryptedPassword);
        
        // 4. DB 저장 (이후 Repository 호출 코드가 추가될 예정입니다)
        System.out.println("암호화된 비번으로 가입 완료: " + encryptedPassword);
    }
}