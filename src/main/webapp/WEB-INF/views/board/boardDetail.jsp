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
		
		<div>
			<button id="likeButton" onclick="toggleLike(${requestScope.boardDetail.board_id}, '${sessionScope.loginUser.useremail}')">❤️👍</button>
            <span id="likeCount">${requestScope.boardDetail.board_likes}</span>
		</div>
		<hr/>
		
		
		<!-- 댓글 기능 ===================== -->
		<div>
			<div>
				<form action="commentsInsert" method="POST">
					<table>
						<tr>
							<td>
								<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id}" required/>
							</td>
						</tr>
						
						<tr>
							<th>UserEmail</th>
							<td>
								${sessionScope.loginUser.useremail}
								<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}">
							</td>
						</tr>

						<tr>
							<th>Content</th>
							<td>
								<input type="text" id="comments_content" name="comment_content" required/>
							</td>
						</tr>
		
						<tr>
							<td colspan="2">
								<button type="reset">reset</button>
								<button type="submit">submit</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div class="container">
				<table border=1px>
					<tbody>
					<%-- <c:if test="${not empty requestScope.comments}"> --%>
						<tr style="text-align: center;">
<!-- 							<th></th> -->
							<th>닉네임</th>
							<th>댓글내용</th>
							<th>작성일</th>
						</tr>
						<c:forEach items="${rList }" var="comments" varStatus="i\c">
							<tr>
<%-- 								<td>
									<input type="hidden" id="reviewIdx" value="${review.idx }"/>
								</td> --%>
								<td>${sessionScope.loginUser.useremail}</td>
								<td>
									<input class="review_content" type="text" value="${review.content}" autofocus disabled></td>
								<td>
<%-- 									<c:if test="${info.name eq review.name }"> --%>
										<input data-idx="${review.idx }"
											style="float: left; width: 50%;" type="image" class="edit"
											value="수정">
										<input data-idx="${review.idx }"
											style="float: left; width: 50%;" type="image" class="delete"
											value="삭제">
<%-- 									</c:if> --%>
									<br>

									<label>${review.createdate}</label>
								</td>
							</tr>
						</c:forEach>
<%-- 						</c:if> --%>
					</tbody>
				</table>
			</div>
		</div>
		<hr/>
		
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