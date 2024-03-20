package com.cm.personalProject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cm.personalProject.domain.LikesId;
import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Board;
import com.cm.personalProject.entity.Comments;
import com.cm.personalProject.entity.Likes;
import com.cm.personalProject.service.BoardService;
import com.cm.personalProject.service.CommentsService;
import com.cm.personalProject.service.LikesService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping(value = "/board")
@Controller
public class BoardController {

	private BoardService boardService;
	private LikesService likesService;
	private CommentsService commentsService;

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
	public String getBoardDetail(Model model, Board entity, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		entity = boardService.selectDetail(entity.getBoard_id());

		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(5).build();
		PageResultDTO<Comments> resultDTO = commentsService.selectList(requestDTO, entity.getBoard_id());

		model.addAttribute("resultDTO", resultDTO);
		model.addAttribute("commentsList", resultDTO.getEntityList());

		if ("U".equals(request.getParameter("jCode"))) {
			model.addAttribute("boardDetail", entity);
			return "board/boardUpdate";
		} else {
			entity.setBoard_views(entity.getBoard_views() + 1);
			model.addAttribute("boardDetail", entity);
			boardService.save(entity);
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
				// rttr.addFlashAttribute("boardDetail", entity);
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
	public ResponseEntity<?> boardDelete(@PathVariable("board_id") int id) {

		Board boardEntity = boardService.selectDetail(id);
		List<Comments> commentsEntity = commentsService.selectListBasedOnBoard_id(id);

		if (boardEntity != null) {
			
			for (Comments comment : commentsEntity) {
			    comment.setComment_delyn('Y');
			    comment.setComment_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			    commentsService.save(comment);
			}
			
			boardEntity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			boardEntity.setBoard_delyn('Y');
			boardService.save(boardEntity);
		}

		return ResponseEntity.ok().build();
	} // boardDelete()

	// ==================================== 좋아요 기능 ========================================
	// Likes Insert =====================================================
	@ResponseBody
	@PostMapping("/likesInsert/{board_id}/{useremail}")
	public ResponseEntity<?> postLikesInsert(@PathVariable("board_id") int board_id,
			@PathVariable("useremail") String useremail) {
		Board boardEntity = boardService.selectDetail(board_id);

		if (boardEntity != null) {
			LikesId likesId = new LikesId(board_id, useremail);
			Likes existingLike = likesService.findById(likesId);

			if (existingLike != null) {
				// 이미 좋아요를 눌렀다면 삭제
				boardEntity.setBoard_likes(boardEntity.getBoard_likes() - 1);
				likesService.delete(existingLike);
				boardService.save(boardEntity);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 클라이언트에게 좋아요 삭제 상태 반환
			} else {
				// 좋아요를 누르지 않았다면 추가
				Likes newLike = new Likes();
				newLike.setUseremail(useremail);
				newLike.setBoard_id(board_id);
				boardEntity.setBoard_likes(boardEntity.getBoard_likes() + 1);
				likesService.save(newLike);
				boardService.save(boardEntity);
				return ResponseEntity.ok().build(); // 클라이언트에게 좋아요 추가 상태 반환
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
		}
	} // postLikesInsert()

	// ==================================== 댓글 기능 ========================================
	// Insert =====================================================
	@PostMapping("/commentsInsert")
	public String postCommentsInsert(RedirectAttributes rttr, Comments entity, HttpServletRequest request) {

		int board_id = entity.getBoard_id();

		String uri = "redirect:boardDetail?board_id=" + board_id;

		try {
			
			entity.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	        entity.setComment_delyn('N');
	        int tempEntity = commentsService.save(entity);
	         
	        entity.setComment_root(tempEntity);

			if (commentsService.save(entity) > 0) {
				rttr.addFlashAttribute("message", "comments insert success");
			} else {
				rttr.addFlashAttribute("message", "comments insert fail");
			}
		} catch (Exception e) {
			rttr.addFlashAttribute("message", "comments insert fail");
			System.out.println("BoardInsert Exception => " + e.toString());
		}

		return uri;
	} // postBoardInsert()

	// Update =====================================================
	@PostMapping("/updateComments")
	@ResponseBody
	public String updateComments(@RequestParam("comment_id") int comment_id, @RequestParam("comment_content") String updatedContent, HttpSession session, Model model) {
		try {
			// 댓글 엔티티를 불러와서 수정 메서드 호출
			Comments comment = commentsService.selectDetail(comment_id);
			
			if (comment != null) {
				comment.setComment_content(updatedContent);
				comment.setComment_moddate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				commentsService.save(comment);
				return "Success";
			} else {
				return "Fail"; // 댓글을 찾을 수 없는 경우
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("댓글 업데이트 중에 오류 발생: " + e.getMessage());
			model.addAttribute("error", "댓글 업데이트에 실패했습니다.");
			
			return "Error"; // 예외 발생 시
		}
	} // updateComments()
	
	// Delete =====================================================
	@DeleteMapping(value = "/deleteComments")
	@ResponseBody
	public ResponseEntity<?> deleteComment(@RequestParam("comment_id") int commentId) {
		Comments entity = commentsService.selectDetail(commentId);

		if (entity != null) {
			entity.setComment_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setComment_delyn('Y');
			commentsService.save(entity);
		}

		return ResponseEntity.ok().build();
	} // deleteComment()
	
	// ==================================== 무한 댓글 기능 ========================================
	@PostMapping("/commentReply")
	public String postCommentReply(RedirectAttributes rttr, Comments entity, HttpServletRequest request) {

		int board_id = entity.getBoard_id();
		String uri = "redirect:boardDetail?board_id=" + board_id;

		try {
			entity.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	        entity.setComment_delyn('N');
	         
	        Comments parents = commentsService.selectDetail(entity.getComment_root());
	        entity.setComment_root(parents.getComment_root());
	        entity.setComment_steps(entity.getComment_steps()+1);
	        entity.setComment_indent(entity.getComment_indent()+1);

			if (commentsService.save(entity) > 0) {
				commentsService.stepUpdate(entity);
				rttr.addFlashAttribute("message", "comments insert success");
			} else {
				rttr.addFlashAttribute("message", "comments insert fail");
			}
		} catch (Exception e) {
			rttr.addFlashAttribute("message", "comments insert fail");
			System.out.println("BoardInsert Exception => " + e.toString());
		}

		return uri;
	} // postCommentReply()

}
