package kbo_report.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "baseball_diary")
public class BaseballDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;      // 누구의 일기인지 (로그인한 사용자 ID)
    private LocalDate diaryDate;  // 일기 날짜 (예: 2026-05-26)
    
    @Column(columnDefinition = "TEXT")
    private String content;       // 일기 내용 (경기 결과, 직관 후기 등)

    private String emoji; // 🌟 [추가] 이모지 저장용 필드 (예: "🔥", "⚾", "😢")

    // 기본 생성자
    public BaseballDiary() {}

    // Getter, Setter
    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDate getDiaryDate() { return diaryDate; }
    public void setDiaryDate(LocalDate diaryDate) { this.diaryDate = diaryDate; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}