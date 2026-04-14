package kbo_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import kbo_report.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    // 1. 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isSuccess = userService.login(username, password);
        
        if (isSuccess) {
            return "로그인 성공! 환영합니다, " + username + "님.";
        } else {
            return "로그인 실패: 아이디 또는 비밀번호를 확인하세요.";
        }
    }

    // 회원가입 처리 (수정된 버전)
    @PostMapping("/signup")
    public String signup(@RequestParam String username, 
                         @RequestParam String password, 
                         @RequestParam String name) {
        try {
            // 1. 새로운 User 엔티티 객체 생성
            kbo_report.entity.User user = new kbo_report.entity.User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);

            // 2. 서비스의 register 메서드 호출
            userService.register(user);
            
            return "회원가입 성공! 이제 로그인해 주세요.";
        } catch (Exception e) {
            return "회원가입 실패: " + e.getMessage();
        }
    }
}