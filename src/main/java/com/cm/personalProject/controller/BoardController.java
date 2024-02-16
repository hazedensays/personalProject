package com.cm.personalProject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Board;
import com.cm.personalProject.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping(value = "/board")
@Controller
public class BoardController {

	private BoardService boardService;

	// List =====================================================
	@GetMapping("/boardPage")
	public void getBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchType", defaultValue = "") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(5).build();
		PageResultDTO<Board> resultDTO = boardService.selectList(requestDTO, searchType, keyword);

		model.addAttribute("selectList", resultDTO.getEntityList());
		model.addAttribute("resultDTO", resultDTO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		
	}// getBoardList()
	
	// Insert =====================================================
	@GetMapping("/boardInsert")
	public void getBoardInsert() {
		
	}
	
	@PostMapping("/boardInsert")
	public String postBoardInsert(RedirectAttributes rttr, Board entity) {
		
		String uri = "redirect:boardPage";
		
		entity.setBoard_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		try {
			if (boardService.save(entity) > 0) {
				rttr.addFlashAttribute("message", "board insert success");
			} else {
				rttr.addFlashAttribute("message", "board insert fail");
				uri = "board/boardInsert";
			}
		} catch (Exception e) {
			uri = "board/boardInsert";
			rttr.addFlashAttribute("message", "board insert fail");
			System.out.println("BoardInsert Exception => " + e.toString());
		}

		return uri;
	}
	
	
	
	
}
