package kbo_report.controller;

import kbo_report.service.BaseballCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/baseball")
public class BaseballController {

    @Autowired
    private BaseballCrawlerService baseballCrawlerService; // 🌟 크롤링 엔진 서비스 주입

    @GetMapping("/today-matches")
    public List<String> getTodayMatches() {
        // 🌟 기존 주소와 리턴 타입을 그대로 유지하여 안드로이드 앱과의 충돌을 원천 차단합니다!
        // 가짜 데이터 대신 진짜 네이버 스포츠에서 긁어온 실시간 데이터를 반환합니다.
        return baseballCrawlerService.crawlTodayMatches();
    }
}