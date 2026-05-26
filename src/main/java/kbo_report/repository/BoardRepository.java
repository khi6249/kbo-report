package kbo_report.repository;

import kbo_report.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    // 🌟 특정 구단(teamName)의 글만 id 역순(최신순)으로 정렬해서 리스트로 긁어오기!
    List<Board> findByTeamNameOrderByIdDesc(String teamName);
}