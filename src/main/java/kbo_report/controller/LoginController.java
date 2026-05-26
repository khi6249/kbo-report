package kbo_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kbo_report.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    // 1. 로그인 처리 (JSON 객체인 LoginResponse를 반환하도록 고도화)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        // userService.login이 User 객체(또는 null)를 반환합니다.
        kbo_report.entity.User loginUser = userService.login(username, password);
        
        if (loginUser != null) {
            // 로그인 성공 시: success=true, 이름, DB에 저장된 선호 구단 정보를 담아서 반환
            LoginResponse response = new LoginResponse(true, loginUser.getName(), loginUser.getFavoriteTeam());
            return ResponseEntity.ok(response);
        } else {
            // 로그인 실패 시: success=false 반환 (401 Unauthorized 상태코드)
            LoginResponse response = new LoginResponse(false, null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 2. 회원가입 처리 (5개 항목 모두 받는 기존 버전 완벽 유지)
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