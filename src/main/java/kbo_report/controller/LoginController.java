package kbo_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import kbo_report.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    // 1. 로그인 처리 (이름을 반환하도록 수정된 버전)
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // userService.login이 이제 User 객체(또는 null)를 반환합니다.
        kbo_report.entity.User loginUser = userService.login(username, password);
        
        if (loginUser != null) {
            // 로그인 성공 시 앱으로 "성공!김영웅" 형태로 이름을 붙여서 보냅니다.
            return "성공!" + loginUser.getName();
        } else {
            return "로그인 실패: 아이디 또는 비밀번호를 확인하세요.";
        }
    }

    // 2. 회원가입 처리 (5개 항목 모두 받는 버전 유지)
    @PostMapping("/signup")
    public String signup(@RequestParam String username, 
                         @RequestParam String password, 
                         @RequestParam String name,
                         @RequestParam String email,          
                         @RequestParam String favoriteTeam) { 
        try {
            kbo_report.entity.User user = new kbo_report.entity.User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setEmail(email);          
            user.setFavoriteTeam(favoriteTeam); 

            userService.register(user);
            
            return "회원가입 성공! 이제 로그인해 주세요.";
        } catch (Exception e) {
            return "회원가입 실패: " + e.getMessage();
        }
    }
}