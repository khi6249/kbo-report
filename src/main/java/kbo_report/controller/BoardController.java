package kbo_report.controller;

import kbo_report.entity.Board;
import kbo_report.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    // 📝 1. 구단 게시판 글쓰기 API (POST /api/board/write)
    @PostMapping("/write")
    public String writePost(
            @RequestParam("username") String username,
            @RequestParam("teamName") String teamName,
            @RequestParam("content") String content) {
        try {
            Board board = new Board();
            board.setUsername(username);
            board.setTeamName(teamName);
            board.setContent(content);

            boardRepository.save(board); // DB 저장
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    // 📋 2. 해당 구단 전용 게시글 전체 조회 API (GET /api/board/list)
    @GetMapping("/list")
    public List<Board> getTeamPosts(@RequestParam("teamName") String teamName) {
        // 리포지토리를 통해 특정 구단 글만 최신순으로 긁어서 JSON 리스트로 반환!
        return boardRepository.findByTeamNameOrderByIdDesc(teamName);
    }
}