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

    // 1. 특정 날짜의 일기 불러오기 API
    @GetMapping("/get")
    public ResponseEntity<String> getDiary(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        Optional<BaseballDiary> diaryOpt = diaryRepository.findByUsernameAndDiaryDate(username, date);
        if (diaryOpt.isPresent()) {
            return ResponseEntity.ok(diaryOpt.get().getContent()); // 저장된 일기 내용 반환
        } else {
            return ResponseEntity.ok(""); // 일기가 없으면 빈 문자열 반환
        }
    }

    // 2. 일기 저장 및 수정 API
    @PostMapping("/save")
    public ResponseEntity<String> saveDiary(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String content) {
        
        // 기존에 해당 날짜에 쓴 일기가 있는지 확인
        Optional<BaseballDiary> diaryOpt = diaryRepository.findByUsernameAndDiaryDate(username, date);
        BaseballDiary diary;
        
        if (diaryOpt.isPresent()) {
            // 이미 있으면 덮어쓰기 (수정)
            diary = diaryOpt.get();
        } else {
            // 없으면 새로 만들기
            diary = new BaseballDiary();
            diary.setUsername(username);
            diary.setDiaryDate(date);
        }
        
        diary.setContent(content);
        diaryRepository.save(diary);
        
        return ResponseEntity.ok("저장 성공!");
    }
}