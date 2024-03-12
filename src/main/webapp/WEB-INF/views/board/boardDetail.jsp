<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/board.js"></script>
<link rel="stylesheet" href="/resources/css/boardDetail.css" />
</head>
<body>
	<div id="wrap">
		<ul class="ulboardDetail">
			<li>
				<span>Board Number</span>
				<span>${requestScope.boardDetail.board_id}</span>
			</li>

			<li>
				<span>Views</span>
				<span>${requestScope.boardDetail.board_views}</span>
			</li>

			<li>
				<span>Board title</span>
				<span>${requestScope.boardDetail.board_title}</span>
			</li>

			<li>
				<span>등록일</span>
				<span>${requestScope.boardDetail.board_regdate}</span>
			</li>

			<li>
				<span>Writer</span>
				<span>${requestScope.boardDetail.useremail}</span>
			</li>

			<li>
				<span>Content</span>
				<span>${requestScope.boardDetail.board_content}</span>
			</li>
		</ul>

		<div class="like-container">
			<button id="likeButton" onclick="toggleLike(${requestScope.boardDetail.board_id}, '${sessionScope.loginUser.useremail}')">💙</button>
			<span id="likeCount">${requestScope.boardDetail.board_likes}</span>
		</div>
		<hr />

		<!-- 댓글 기능 ===================== -->
		<div class="wrapComments">
			<div class="submitComments">
				<form action="commentsInsert" method="POST">
					<ul>
						<li><span>UserEmail</span> <span>${sessionScope.loginUser.useremail}</span>
							<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}">
							<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id}" required />
						</li>
						
						<li>
							<span>Content</span>
							<span>
								<input type="text" id="comments_content" name="comment_content" required />
							</span>
						</li>
						
						<li class="buttonList">
							<button type="reset">reset</button>
							<button type="submit">submit</button>
						</li>
					</ul>
				</form>
			</div>
			<hr/>

			<div class="comments">
				<ul>
<!-- 					<li class="commentsTitle">
						<span>작성자</span>
						<span>댓글 내용</span>
						<span>등록일</span>
						<span>수정/삭제</span>
						<span>reply</span>
					</li> -->

					<c:if test="${not empty requestScope.commentsList}">
						<c:forEach var="c" items="${requestScope.commentsList}">
							<li class="commentList" style="margin-left:${c.comment_indent}rem">
								<span>${c.useremail}</span>
								<span class="comment-content">${c.comment_content}</span>
								<span>
									<input type="text" class="edit-comment" style="display: none;" value="${c.comment_content}">
								</span>
								<span>${c.comment_regdate}</span>
								
								<c:if test="${sessionScope.loginUser.useremail == c.useremail}">
									<span>
										<button data-idx="${c.comment_id}" class="edit-btn">수정</button>
										<button data-idx="${c.comment_id}" class="delete-btn">삭제</button>
									</span>
								</c:if>
								
								<a id="reply-btn" onclick="toggleReply(${c.comment_id})">답글달기</a>
							</li>

							<li id="reply-${c.comment_id}" style="display: none;">
								<form action="commentReply" method="post">
									<span>${sessionScope.loginUser.useremail}</span>
									<input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
									<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail }" />
									<input type="hidden" id="comment_root" name="comment_root" value="${c.comment_id}" />
									<input type="hidden" id="comment_steps" name="comment_steps" value="${c.comment_steps}" />
									<input type="hidden" id="comment_indent" name="comment_indent" value="${c.comment_indent}" />
									<input type="text" id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required />
									<button>등록</button>
								</form>
							</li>
						</c:forEach>
					</c:if>

					<c:if test="${empty requestScope.commentsList}">
						<li>
							<span colspan="4">게시글에 작성된 댓글이 없습니다.</span>
						</li>
					</c:if>
				</ul>
			</div>

			<div class="pageNation">
				<c:choose>
					<c:when test="${resultDTO.start != resultDTO.page}">
						<a class="firstB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.start}">처음</a>
						<a class="ltB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.page-1}">&LT;</a>
					</c:when>
					
					<c:otherwise>
						<span class="firstB">처음</span>
						<span class="ltB">&LT;</span>
					</c:otherwise>
				</c:choose>

				<c:forEach var="i" items="${resultDTO.pageList}">
					<c:if test="${i==resultDTO.page}">
						<span><strong>${i}</strong></span>&nbsp;
    				</c:if>
    				
					<c:if test="${i!=resultDTO.page}">
						<a href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${i}">${i}</a>&nbsp;
    				</c:if>
				</c:forEach>

				<c:choose>
					<c:when test="${resultDTO.end != resultDTO.page}">
						<a class="gtB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.page+1}">&GT;</a>
						<a class="lastB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.end}">마지막</a>
					</c:when>
					
					<c:otherwise>
						<span class="gtB">&GT;</span>
						<span class="lastB">마지막</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<hr />

		<div class="nav_box">
			<c:if test="${sessionScope.loginUser.useremail == requestScope.boardDetail.useremail}">
				<span>
					<a class="m_button" href="boardDetail?jCode=U&board_id=${requestScope.boardDetail.board_id}">수정</a>
				</span>
				<span>
					<button onclick="axboardDelete(${requestScope.boardDetail.board_id})" id="${requestScope.boardDetail.board_id}">삭제</button>
				</span>
			</c:if>
			
			<span>
				<a class="m_button" href="boardPage">게시판 목록</a>
			</span>
			<span>
				<a href="/home">Home</a>
			</span>
		</div>
	</div>
</body>
</html>