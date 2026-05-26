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

    // 1. 로그인 처리 (기존 코드 완벽 유지)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        kbo_report.entity.User loginUser = userService.login(username, password);
        
        if (loginUser != null) {
            LoginResponse response = new LoginResponse(true, loginUser.getName(), loginUser.getFavoriteTeam());
            return ResponseEntity.ok(response);
        } else {
            LoginResponse response = new LoginResponse(false, null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 2. 회원가입 처리 (기존 코드 완벽 유지)
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

    // 🌟 [안전성 100% 교체] 회원가입/일기장과 동일하게 무적의 @PostMapping + 순수 String 리턴 조합으로 튜닝!
    // 전체 주소는 POST /api/user/update-team 이 됩니다.
    @PostMapping("/user/update-team")
    public String updateFavoriteTeam(
            @RequestParam("username") String username,
            @RequestParam("favoriteTeam") String favoriteTeam) {
        
        try {
            boolean isUpdated = userService.updateFavoriteTeam(username, favoriteTeam);
            if (isUpdated) {
                return "success"; // 👈 군더더기 없는 순수 텍스트 리턴!
            } else {
                return "fail";
            }
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}