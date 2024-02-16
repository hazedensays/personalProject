<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wrap">
		<form action="boardInsert" method="POST">
			<table>
				<tr>
					<th>UserEmail</th>
					<td>
						${sessionScope.loginUser.useremail}
						<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}">
					</td>
				</tr>
				<tr>
					<th>Title</th>
					<td>
						<input type="text" id="board_title" name="board_title" required/>
					</td>
				</tr>
				<tr>
					<th>Content</th>
					<td>
						<textarea id="board_content" name="board_content" required></textarea>
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