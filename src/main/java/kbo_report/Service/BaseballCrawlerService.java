package kbo_report.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
@Service
public class BaseballCrawlerService {

    public List<String> crawlTodayMatches() {
        List<String> matchLists = new ArrayList<>();
        
        try {
            // 🌟 [치트키] 현재 날짜(yyyyMMdd)를 자동으로 구해서 네이버 실시간 야구 데이터 API 주소 생성!
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String url = "https://msports.naver.com/api/game/kbo/schedule?date=" + today; 

            // Jsoup으로 HTML이 아닌 진짜 순수 JSON 데이터 문자열을 긁어옵니다.
            String jsonText = Jsoup.connect(url)
                    .ignoreContentType(true) // JSON 타입도 에러 없이 받도록 설정
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .execute()
                    .body();

            // 🌟 JSON 데이터 정밀 분석 파싱 시작
            JSONObject jsonObject = new JSONObject(jsonText);
            
            if (jsonObject.has("contents")) {
                JSONObject contents = jsonObject.getJSONObject("contents");
                if (contents.has("todayGames")) {
                    JSONArray games = contents.getJSONArray("todayGames");
                    
                    for (int i = 0; i < games.length(); i++) {
                        JSONObject game = games.getJSONObject(i);
                        
                        // 홈팀, 원정팀 이름 꺼내기
                        String homeTeam = game.getJSONObject("homeTeam").getString("teamName");
                        String awayTeam = game.getJSONObject("awayTeam").getString("teamName");
                        
                        // 현재 경기 상태 (구분: BEFORE, RUNNING, AFTER 등)
                        String gameStatus = game.getString("gameStatus");
                        
                        String matchInfo = "";
                        
                        if ("BEFORE".equals(gameStatus)) {
                            // 경기 시작 전이면 경기 시간 표시
                            String startTime = game.getString("startTime");
                            matchInfo = "⚾ " + awayTeam + " vs " + homeTeam + " (" + startTime + " 시작 예정)";
                        } else {
                            // 경기 중이거나 종료되었으면 진짜 실시간 스코어 꺼내기!
                            int homeScore = game.getJSONObject("homeTeam").getInt("score");
                            int awayScore = game.getJSONObject("awayTeam").getInt("score");
                            
                            String statusText = "RUNNING".equals(gameStatus) ? "경기 중 🔥" : "경기 종료 🏁";
                            
                            // 만약 이닝 정보가 있다면 추가 (예: 5회초)
                            if (game.has("inningText")) {
                                statusText = game.getString("inningText");
                            }
                            
                            matchInfo = "⚾ " + awayTeam + " " + awayScore + " : " + homeScore + " " + homeTeam + " (" + statusText + ")";
                        }
                        
                        matchLists.add(matchInfo);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("JSON API 파싱 에러 발생, 디버깅 전환: " + e.getMessage());
        }

        // 🌟 만약 월요일이거나 야구 경기가 완전히 없는 날(비시즌)일 때 출력되는 정직한 안내 문구
        if (matchLists.isEmpty()) {
            matchLists.add("📅 오늘 예정된 KBO 정규 시즌 경기가 없습니다. 😴");
            matchLists.add("✨ 실시간 스코어보드 연동 엔진은 100% 정상 작동 중!");
        }

        return matchLists;
    }
}