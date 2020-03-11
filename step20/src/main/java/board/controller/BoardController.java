package board.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.bean.BoardDTO;
import board.dao.BoardDAO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/board/boardList.do")
	public ModelAndView boardList(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if(request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		//BoardDAO boardDAO = new BoardDAO();
		List<BoardDTO> list = boardService.boardList(startNum, endNum);
		// 페이징 숫자 3개씩
		int totalA = boardService.getTotalA();  // 총글수
		int totalP = (totalA + 4) / 5;		// 총 페이지수
		int startPage = (pg-1)/3*3 + 1;
		int endPage = startPage + 2;
		if(endPage > totalP) endPage = totalP;
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		// 데이터 공유
		modelAndView.addObject("list", list);
		modelAndView.addObject("pg", pg);
		modelAndView.addObject("totalP", totalP);
		modelAndView.addObject("startPage", startPage);
		modelAndView.addObject("endPage", endPage);
		// view 처리 jsp 파일 이름
		modelAndView.setViewName("boardList.jsp");
		return modelAndView;
	}
	//Json
	@RequestMapping(value = "/board/boardListJson.do")
	public ModelAndView boardListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if(request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		//BoardDAO boardDAO = new BoardDAO();
		List<BoardDTO> list = boardService.boardList(startNum, endNum);
		
		//JSON으로 결과값 반환
		String rt="";
		int total = list.size();
		
		if(total > 0 ) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		//JSON 객체 생성
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("total", total);
		
		if(total > 0) {
			JSONArray item = new JSONArray();
			for(int i=0; i<list.size(); i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("seq", boardDTO.getSeq());
				temp.put("id", boardDTO.getId());
				temp.put("name", boardDTO.getName());
				temp.put("subject", boardDTO.getSubject());
				temp.put("content", boardDTO.getContent());
				temp.put("hit", boardDTO.getHit());
				temp.put("logtime", boardDTO.getLogtime());
				//JSONArray에 저장
				item.put(temp);
			}
			//JSON 객체에 저장
			json.put("item", item);
		}
		//검색 결과를 서블릿으로 리턴
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json",json);
		modelAndView.setViewName("boardListJson.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/board/boardView.do")
	public ModelAndView boardView(HttpServletRequest request) {
		// 1. 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// 2. DB
		//BoardDAO boardDAO = new BoardDAO();
		// 조회수 증가
		boardService.updateHit(seq);
		// 상세 데이터 얻기
		BoardDTO boardDTO = boardService.boardView(seq);
		
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		// 데이터 공유
		modelAndView.addObject("boardDTO", boardDTO);
		modelAndView.addObject("pg", pg);
		// view 처리 파일
		modelAndView.setViewName("boardView.jsp");
		return modelAndView;		
	}
	
	@RequestMapping(value = "/board/boardWriteForm.do")
	public ModelAndView boardWriteForm() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("boardWriteForm.jsp");
		return modelAndView;		
	}
	
	@RequestMapping(value = "/board/boardWrite.do")
	public ModelAndView boardWrite(HttpServletRequest request) {
		// 1. 데이터
		HttpSession session = request.getSession();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String id = (String)session.getAttribute("memId");
		String name = (String)session.getAttribute("memName");
		// 2. DB
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setName(name);
		boardDTO.setId(id);
		boardDTO.setSubject(subject);
		boardDTO.setContent(content);
		
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardService.boardWrite(boardDTO);
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		// 데이터 공유
		modelAndView.addObject("su", su);
		// view 처리 파일 
		modelAndView.setViewName("boardWrite.jsp");
		return modelAndView;		
	}
	
	@RequestMapping(value = "/board/boardDelete.do")
	public ModelAndView boardDelete(HttpServletRequest request) {
		// 1. 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		// 2. DB
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardService.boardDelete(seq);
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		// 데이터 공유데이터 저장
		modelAndView.addObject("su", su);
		modelAndView.addObject("pg", pg);
		//request.setAttribute("pg", pg);
		//request.setAttribute("su", su);
		
		// view 처리 파일 이름 저장
		modelAndView.setViewName("boardDelete.jsp");
		return modelAndView;
	}
}

















