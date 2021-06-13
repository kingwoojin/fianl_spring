package kr.inhatc.spring.boardjpa.controller;

import java.io.Console; 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.boardjpa.entity.Boards;
import kr.inhatc.spring.boardjpa.service.BoardjpaService;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.service.UserService;

@Controller
public class BoardjpaController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
//	private UserService userService; Autowired안쓸려면 class위에 @RequiredArgsConstructor를 쓰고 final 넣어주기
	private BoardjpaService boardjpaService;
	
	
	//요구가 들어올때 맵핑, localhost의 포트번호로 들어오면
	//기본적으로 html파일을 찾아감
	//에러나면 UserController 여기부분 경로 겹치는거 때문일듯 
//	@RequestMapping("/")
//	//Hello 출력하는 매서드
//	public String hello() {
//		System.out.println("========================> 여기");
//		return "redirect:/boardjpa/boardjpaList";
//	}
	
	
	@RequestMapping(value = "/boardjpa/boardjpaList", method=RequestMethod.GET)
	public String boardjpaList(Model model) {
		List<Boards> list = boardjpaService.boardjpaList();
		model.addAttribute("list", list);
		
		// 뷰어 이동
		return "boardjpa/boardjpaList";
	}
	
	@RequestMapping(value = "/boardjpa/boardjpaInsert", method=RequestMethod.GET)
	public String boardjpaWrite() {
		
		// 뷰어 이동
		return "boardjpa/boardjpaWrite";
	}
	
	@RequestMapping(value = "/boardjpa/boardjpaInsert", method=RequestMethod.POST)
	public String boardjpaInsert(Boards board) {
		boardjpaService.saveBoardjpa(board);
		// 뷰어 이동
		return "redirect:/boardjpa/boardjpaList";
	}
	
	@RequestMapping(value = "/boardjpa/boardjpaDetail/{idx}", method=RequestMethod.GET)
	public String boardjpaDetail(@PathVariable("idx")Integer idx, Model model) {
		Boards board = boardjpaService.boardjpaDetail(idx);
		
		
		
		model.addAttribute("boardjpa", board);
		System.out.println("===============> dd1? :" + board);
		// 뷰어 이동
		return "boardjpa/boardjpaDetail";
	}
	
	@RequestMapping(value = "/boardjpa/boardjpaUpdate/{idx}/{hitCnt}", method=RequestMethod.POST)
	public String boardjpaUpdate(@PathVariable("idx")Integer idx,@PathVariable("hitCnt")Integer hitCnt ,Boards board) {
		System.out.println("========================>>" + board);
		
		board.setIdx(idx);
		
		boardjpaService.saveBoardjpa2(board);
		
		board.setHitCnt(hitCnt+1);
		boardjpaService.savecntup(board);
		// 뷰어 이동
		return "redirect:/boardjpa/boardjpaList";
	}
	
	@RequestMapping(value = "/boardjpa/boardjpaDelete/{idx}", method=RequestMethod.GET)
	public String boardjpaDelete(@PathVariable("idx")Integer idx) {
		
		boardjpaService.boardjpaDelete(idx);
		
		return "redirect:/boardjpa/boardjpaList";  
	}
	
}
