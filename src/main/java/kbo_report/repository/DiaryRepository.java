package kbo_report.repository;

import kbo_report.entity.BaseballDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<BaseballDiary, Long> {
    // 특정 사용자가 특정 날짜에 쓴 일기가 있는지 찾는 메서드 (수정/조회용)
    Optional<BaseballDiary> findByUsernameAndDiaryDate(String username, LocalDate diaryDate);
}