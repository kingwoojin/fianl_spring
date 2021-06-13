package kr.inhatc.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.service.BoardService;

@Controller //웹사이트로 접근할 때
//@RestController //결과물을 바로 받아 올 때
public class BoardController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BoardService boardService;
	

//	@RequestMapping("/")
//	public String hello() {
//		return "index";
//	}
	
	@RequestMapping("/board/boardList")
	public String boardList(Model model) {
		// 서비스 로직
		List<BoardDto> list = boardService.boardList();
		log.debug("======================>"+list.size());
		//System.out.println(list.get(0));
		//mvc에 model은 데이터입니다~라고 말해야함
		
		model.addAttribute("list", list); //map형태
//		model.addAttribute("name","홍길동");
		
//		for(BoardDto vo : list) {
//			System.out.println(vo.getBoardIdx());
//		}
		// 뷰어 이동 
		return "board/boardList";
	}
	
	@RequestMapping("/board/boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	
	@RequestMapping("/board/boardInsert")
	//파일과 관련된 전ㅇ보는 http가 받음
	public String boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		boardService.boardInsert(board, multipartHttpServletRequest); //스프링에 획기적인 방식이라는듯
		return "redirect:/board/boardList";
	}
	//RestController말고 Controller일때 return문에 있는 문장은 의미가 없어짐
	//웹 페이지를 찾아가는 것이기 때문. ""안에 페이지 이름쓰기.
	//스프링 부트 기본 연결 - 처음에 root라는 애를 찾아오면 이쪽(RequestMapping?)으로 와서
	//""안에 적혀있는 html파일을 templates에서 찾음.
	// .html은 적지않지만 기본적으로 html을 찾아들어옴.
	@RequestMapping("/board/boardDetail")
	public String boardDetail(@RequestParam int boardIdx,Model model) {
		BoardDto board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		return "board/boardDetail"; //html의 주소를 찾아감
	}
	
	@RequestMapping("/board/boardUpdate")
	public String boardUpdate(BoardDto board) {
//		System.out.println();
		boardService.boardUpdate(board); //스프링에 획기적인 방식이라는듯
		return "redirect:/board/boardList";
	}
	
	//@get post 
	@RequestMapping("/board/boardDelete")//@RequestParam("boardIdx") int boardIdx 도 사용가능
	public String boardDelete(@RequestParam("boardIdx") int boardIdx) {
		boardService.boardDelete(boardIdx); //스프링에 획기적인 방식이라는듯
		return "redirect:/board/boardList"; //redirect가 적히면 컨트롤러의 주소를 찾아감
	}
	
	@RequestMapping("/board/downloadBoardFile")//@RequestParam("boardIdx") int boardIdx 도 사용가능
	public void downloadBoardFile(
			@RequestParam("idx") int idx,
			@RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception {
	
		FileDto boardFile = boardService.selectFileInfo(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition","attachment; filename=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding","binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
}
