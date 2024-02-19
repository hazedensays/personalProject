<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
</head>
			
<body>
	<div id="wrap">
		<form action="boardUpdate" method="POST">
			<table>
				<tr>
					<th>Board Number</th>
					<td>
						${requestScope.boardDetail.board_id}
						<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id}"/>
						<input type="hidden" id="board_delyn" name="board_delyn" value="${requestScope.boardDetail.board_delyn}"/>
					</td>
					<th>Views</th>
					<td>
						${requestScope.boardDetail.board_views}
						<input type="hidden" id="board_views" name="board_views" value="${requestScope.boardDetail.board_views}"/>
					</td>
					<th>Likes</th>
					<td>
						${requestScope.boardDetail.board_likes}
						<input type="hidden" id="board_likes" name="board_likes" value="${requestScope.boardDetail.board_likes}"/>
					</td>
				</tr>
				
				<tr>
					<th colspan="3">Board title</th>
					<td  colspan="3">
						<input type="text" id="board_title" name="board_title" value="${requestScope.boardDetail.board_title}" required/>
					</td>
					
					<th colspan="3">등록일</th>
					<td  colspan="3">
						${requestScope.boardDetail.board_regdate}
						<input type="hidden" id="board_regdate" name="board_regdate" value="${requestScope.boardDetail.board_regdate}"/>
					</td>
				</tr>
				
				<tr>
					<th  colspan="3">Writer</th>
					<td  colspan="3">
						${requestScope.boardDetail.useremail}
						<input type="hidden" id="useremail" name="useremail" value="${requestScope.boardDetail.useremail}"/>
					</td>
				</tr>
				
				<tr>
					<th colspan="3">Content</th>
					<td colspan="3">
						<textarea id="board_content" name="board_content" required>${requestScope.boardDetail.board_content}</textarea>		
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
		<hr/>

		<div class="nav_box">
			<a class="m_button" href="boardPage">게시판 목록</a>
			<a href="/home">Home</a>
		</div>
	</div>


</body>
</html>