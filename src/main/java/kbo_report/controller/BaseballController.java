package kbo_report.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/baseball")
public class BaseballController {

    @GetMapping("/today-matches")
    public List<String> getTodayMatches() {
        // 시연용 가상 경기 일정입니다. 나중에 크롤링 기능을 붙이면 실제 데이터로 바뀝니다!
        return Arrays.asList(
            "LG 트윈스 vs 두산 베어스 (18:30 잠실)",
            "KIA 타이거즈 vs SSG 랜더스 (18:30 광주)",
            "삼성 라이온즈 vs 한화 이글스 (18:30 대구)",
            "NC 다이노스 vs KT 위즈 (18:30 창원)",
            "키움 히어로즈 vs 롯데 자이언츠 (18:30 고척)"
        );
    }
}