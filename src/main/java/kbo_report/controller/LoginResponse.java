package kbo_report.controller;

public class LoginResponse {
    private boolean success;
    private String name;
    private String favoriteTeam;

    // 🌟 1. 스프링 내장 Jackson 라이브러리가 JSON 변환할 때 필수인 기본 생성자!
    public LoginResponse() {
    }

    // 2. 컨트롤러에서 사용할 인자 있는 생성자
    public LoginResponse(boolean success, String name, String favoriteTeam) {
        this.success = success;
        this.name = name;
        this.favoriteTeam = favoriteTeam;
    }

    // 3. 스프링이 값을 읽고 쓰기 위한 Getter / Setter 메서드 전체
    public boolean isSuccess() { 
        return success; 
    }
    
    public void setSuccess(boolean success) { 
        this.success = success; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getFavoriteTeam() { 
        return favoriteTeam; 
    }

    public void setFavoriteTeam(String favoriteTeam) { 
        this.favoriteTeam = favoriteTeam; 
    }
}