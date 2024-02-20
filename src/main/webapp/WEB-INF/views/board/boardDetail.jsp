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
</head>
<body>
	<div id="wrap">
		<table border=1px>
			<tr>
				<th>Board Number</th>
				<td>
					${requestScope.boardDetail.board_id}
				</td>
				<th>Views</th>
				<td>
					${requestScope.boardDetail.board_views}
				</td>
			</tr>

			<tr>
				<th colspan="3">Board title</th>
				<td  colspan="3">
					${requestScope.boardDetail.board_title}
				</td>
		
				<th colspan="3">등록일</th>
				<td  colspan="3">
					${requestScope.boardDetail.board_regdate}
				</td>
			</tr>

			<tr>
				<th  colspan="3">Writer</th>
				<td  colspan="3">
					${requestScope.boardDetail.useremail}
				</td>
			</tr>
			
			<tr>
				<th colspan="3">Content</th>
				<td colspan="3">
					${requestScope.boardDetail.board_content}
				</td>
			</tr>
		</table>
		<hr/>
		
		<div>
<%-- 			<button>❤️👍</button>
			<span>${requestScope.boardDetail.board_likes}</span> --%>
			
			<button id="likeButton" onclick="toggleLike(${requestScope.boardDetail.board_id})">❤️👍</button>
            <span id="likeCount">${requestScope.boardDetail.board_likes}</span>
		
		</div>

		<div class="nav_box">
		
			<c:if test="${sessionScope.loginUser.useremail == requestScope.boardDetail.useremail}">
				<a class="m_button" href="boardDetail?jCode=U&board_id=${requestScope.boardDetail.board_id}">수정</a>
				<button onclick="axboardDelete(${requestScope.boardDetail.board_id})" id="${requestScope.boardDetail.board_id}">삭제</button>
			</c:if>
			<a class="m_button" href="boardPage">게시판 목록</a>
			<a href="/home">Home</a>
		</div>
	</div>
</body>
</html>