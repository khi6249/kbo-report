package kbo_report.controller;

import kbo_report.entity.BaseballDiary;
import kbo_report.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryRepository diaryRepository;

    // 1. 특정 날짜의 일기 + 이모지 불러오기 API
    @GetMapping("/get")
    public ResponseEntity<String> getDiary(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        Optional<BaseballDiary> diaryOpt = diaryRepository.findByUsernameAndDiaryDate(username, date);
        if (diaryOpt.isPresent()) {
            BaseballDiary d = diaryOpt.get();
            String content = d.getContent() != null ? d.getContent() : "";
            String emoji = d.getEmoji() != null ? d.getEmoji() : "";
            // 안드로이드에서 쪼개서 쓸 수 있게 구분자(|)를 넣어서 리턴
            return ResponseEntity.ok(content + "|" + emoji); 
        } else {
            return ResponseEntity.ok("|"); // 데이터가 없으면 빈 값 리턴
        }
    }

    // 2. 일기 및 이모지 저장 API
    @PostMapping("/save")
    public ResponseEntity<String> saveDiary(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String content,
            @RequestParam String emoji) { // 🌟 이모지 파라미터 추가
        
        Optional<BaseballDiary> diaryOpt = diaryRepository.findByUsernameAndDiaryDate(username, date);
        BaseballDiary diary;
        
        if (diaryOpt.isPresent()) {
            diary = diaryOpt.get();
        } else {
            diary = new BaseballDiary();
            diary.setUsername(username);
            diary.setDiaryDate(date);
        }
        
        diary.setContent(content);
        diary.setEmoji(emoji); // 🌟 이모지 저장
        diaryRepository.save(diary);
        
        return ResponseEntity.ok("저장 성공!");
    }
}