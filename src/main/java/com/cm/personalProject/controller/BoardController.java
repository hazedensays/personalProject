package com.cm.personalProject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
	} // getBoardInsert()
	
	@PostMapping("/boardInsert")
	public String postBoardInsert(RedirectAttributes rttr, Board entity) {
		
		String uri = "redirect:boardPage";
		
		entity.setBoard_delyn('N');
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
	} // postBoardInsert()
	
	
	// Detail =====================================================
	@GetMapping("/boardDetail")
	public String getBoardDetail(Model model, Board entity, HttpServletRequest request) {

		model.addAttribute("boardDetail", boardService.selectDetail(entity.getBoard_id()));

		if ("U".equals(request.getParameter("jCode"))) {
			return "board/boardUpdate";
		} else {
			return "board/boardDetail";
		}

	} // getBoardDetail()
	
	// Update =====================================================
	@PostMapping("/boardUpdate")
	public String postBoardUpdate(Model model, Board entity, RedirectAttributes rttr) {
	    
	    String uri = "redirect:boardDetail?board_id=" + entity.getBoard_id();
	    try {
	        entity.setBoard_moddate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	        
	        if (boardService.save(entity) > 0) {
	        	//rttr.addFlashAttribute("boardDetail", entity);
	            rttr.addFlashAttribute("message", "Board Update Success");
	        } else {
	            model.addAttribute("message", "Board Update Fail");
	            uri = "board/boardUpdate";
	        }
	    } catch (Exception e) {
	        System.out.println("Board Update Exception => " + e.toString());
	        uri = "board/boardUpdate";
	    }
	    
	    return uri;
	} // postBoardUpdate()
	
	
	// Delete =====================================================
	@PostMapping("/boardDelete/{board_id}")
	public ResponseEntity<?> boardDelete(@PathVariable("board_id") int id, @RequestBody Board entity) {
	    entity.setBoard_id(id);

	    if (entity.getBoard_delyn() == 'N') {
	        entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	        entity.setBoard_delyn('Y');
	        boardService.save(entity);
	    }

	    return ResponseEntity.ok().build();
	}
	
}
