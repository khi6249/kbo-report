package kbo_report.controller;

import kbo_report.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isSuccess = userService.login(username, password);
        
        if (isSuccess) {
            return "로그인 성공! 환영합니다, " + username + "님.";
        } else {
            return "로그인 실패: 아이디 또는 비밀번호를 확인하세요.";
        }
    }
}