<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
</head>
<link rel="stylesheet" href="/resources/css/boardInsert.css" />
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
						<input type="hidden" id="board_likes" name="board_likes" value="${requestScope.boardDetail.board_likes}"/>
						<input type="hidden" id="board_views" name="board_views" value="${requestScope.boardDetail.board_views}"/>
					</td>
				</tr>
				
				<tr>
					<th>Board title</th>
					<td>
						<input type="text" id="board_title" name="board_title" value="${requestScope.boardDetail.board_title}" required/>
					</td>
				</tr>
				
				<tr>
					<th>등록일</th>
					<td>
						${requestScope.boardDetail.board_regdate}
						<input type="hidden" id="board_regdate" name="board_regdate" value="${requestScope.boardDetail.board_regdate}"/>
					</td>
				</tr>

				<tr>
					<th>Writer</th>
					<td>
						${requestScope.boardDetail.useremail}
						<input type="hidden" id="useremail" name="useremail" value="${requestScope.boardDetail.useremail}"/>
					</td>
				</tr>

				<tr>
					<th>Content</th>
					<td>
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