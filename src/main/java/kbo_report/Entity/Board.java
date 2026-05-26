package kbo_report.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 고유 번호 (자동 증가)

    @Column(nullable = false)
    private String username; // 작성자 계정 ID

    @Column(nullable = false)
    private String teamName; // 구단 이름 (예: LG 트윈스, 삼성 라이온즈)

    @Column(nullable = false, length = 1000)
    private String content; // 게시글 내용

    private LocalDateTime createdAt; // 작성 시간

    // 엔티티가 생성될 때 현재 시간 자동으로 주입
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getter / Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}