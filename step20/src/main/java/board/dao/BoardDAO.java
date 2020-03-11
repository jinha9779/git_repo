package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import board.bean.BoardDTO;

@Repository
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;	
	
	// 저장
	public int boardWrite(BoardDTO boardDTO) {		
		return sqlSession.insert("mybatis.boardMapper.boardWrite", boardDTO);
	}
	// 목록보기
	public List<BoardDTO> boardList(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum); 
		return sqlSession.selectList("mybatis.boardMapper.boardList", map);
	}
	// 상세보기
	public BoardDTO boardView(int seq) {		
		return sqlSession.selectOne("mybatis.boardMapper.boardView", seq);
	}	
	// 조회수 증가
	public int updateHit(int seq) {		
		return sqlSession.update("mybatis.boardMapper.updateHit", seq);
	}
	// 총목록 수 구하기
	public int getTotalA() {		
		return sqlSession.selectOne("mybatis.boardMapper.getTotalA");
	}
	// 삭제
	public int boardDelete(int seq) {
		return sqlSession.delete("mybatis.boardMapper.boardDelete", seq);
	}
	
}






