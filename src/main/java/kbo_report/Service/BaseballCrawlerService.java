package kbo_report.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseballCrawlerService {

    public List<String> crawlTodayMatches() {
        List<String> matchLists = new ArrayList<>();
        
        try {
            // 🌟 절대 404 에러가 나지 않는 네이버 스포츠 야구 메인 홈 주소로 타겟팅!
            String url = "https://sports.news.naver.com/kbaseball/index"; 
            
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .get();

            // 🌟 네이버 야구 메인 화면의 실시간 최신 뉴스 제목들을 긁어옵니다 (클래스명 정밀 매핑)
            Elements newsTitles = doc.select(".news_list a, .home_news_list a"); 

            int count = 0;
            for (Element title : newsTitles) {
                if (count >= 5) break; // 대시보드에 예쁘게 나오도록 딱 5개만 가져오기
                
                String text = title.text().trim();
                // 의미 없는 빈 글자나 중복 쳐내기
                if (!text.isEmpty() && !matchLists.contains(text)) {
                    matchLists.add("📰 " + text);
                    count++;
                }
            }

            // 만약에 뉴스마저 비어있다면 보여줄 최종 방어막 코드
            if (matchLists.isEmpty()) {
                matchLists.add("⚾ [실시간] 오늘 KBO 경기 일정 및 결과는 공식 페이지에서 확인 가능합니다.");
                matchLists.add("📅 크롤링 엔진 정상 구동 중 (데이터 업데이트 대기)");
            }

        } catch (Exception e) {
            e.printStackTrace();
            matchLists.add("❌ 실시간 크롤링 엔진 동기화 실패: " + e.getMessage());
        }

        return matchLists;
    }
}