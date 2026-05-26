package kbo_report.controller;

import kbo_report.entity.Board;
import kbo_report.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // 📝 3. [추가] 게시글 수정 API (PUT /api/board/update)
    @PutMapping("/update")
    public String updatePost(
            @RequestParam("id") Long id,
            @RequestParam("username") String username, // 보안 확인용 (글쓴이 본인 확인)
            @RequestParam("content") String content) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                // 글을 쓴 사람 이름과 요청한 사람 이름이 일치하는지 체크!
                if (board.getUsername().equals(username)) {
                    board.setContent(content);
                    boardRepository.save(board);
                    return "success";
                } else {
                    return "denied"; // 권한 없음
                }
            }
            return "not_found";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    // 🗑️ 4. [추가] 게시글 삭제 API (DELETE /api/board/delete)
    @DeleteMapping("/delete")
    public String deletePost(
            @RequestParam("id") Long id,
            @RequestParam("username") String username) { // 보안 확인용
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                if (board.getUsername().equals(username)) {
                    boardRepository.delete(board);
                    return "success";
                } else {
                    return "denied";
                }
            }
            return "not_found";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}