package kbo_report.controller; // 💡 실제 controller 파일 맨 위에 있는 패키지명과 똑같이 맞춰주세요!

public class LoginResponse {
    private boolean success;
    private String name;
    private String favoriteTeam;

    // 생성자
    public LoginResponse(boolean success, String name, String favoriteTeam) {
        this.success = success;
        this.name = name;
        this.favoriteTeam = favoriteTeam;
    }

    // Getter, Setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFavoriteTeam() { return favoriteTeam; }
    public void setFavoriteTeam(String favoriteTeam) { this.favoriteTeam = favoriteTeam; }
}