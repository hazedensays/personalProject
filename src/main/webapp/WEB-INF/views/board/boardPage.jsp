<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="/resources/css/boardPage.css" />
</head>
<body>
	<div id="wrap">
		<div class="parent-container">
			<div class="searchBox">
	             <form action="boardPage" method="get">
	                 <select name="searchType" id="searchType" onchange="keywordClear()">
	                     <option value="all" ${requestScope.searchType == 'all' ? "selected" : "" }>전체</option>
	                     <option value="useremail" ${requestScope.searchType == 'useremail' ? "selected" : "" }>userEmail</option>
	                     <option value="board_title" ${requestScope.searchType == 'board_title' ? "selected" : "" }>title</option>
	                     <option value="board_content" ${requestScope.searchType == 'board_content' ? "selected" : "" }>content</option>
	                 </select>
	                 
	                 <input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요." value="${requestScope.keyword}" />
	                 <button type="submit" id="searchBtn">Search</button>
	             </form>
	        </div>

			<div class="register-link">
				<a href="boardInsert">등록</a>
			</div>
		</div>

		<div>
			<table>
				<tr>
					<th>글번호</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>제목</th>
					<th>좋아요</th>
					<th>조회수</th>
				</tr>

				<c:if test="${not empty requestScope.selectList}">
					<c:forEach var="b" items="${requestScope.selectList}">
						<tr>
							<td>${b.board_id}</td>
							<td>${b.useremail}</td>
							<td>${b.board_regdate}</td>
							<td><a href="boardDetail?board_id=${b.board_id}">${b.board_title}</a></td>
							<td>${b.board_likes}</td>
							<td>${b.board_views}</td>
						</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${empty requestScope.selectList}">
						<tr>
							<td colspan="7">작성된 게시글이 없습니다.</td>
						</tr>
				</c:if>
			</table>

			<div class="pageNation">
				<c:choose>
					<c:when test="${resultDTO.start != resultDTO.page}">
						<a class="firstB"
							href="boardPage?page=${resultDTO.start}&searchType=${searchType}&keyword=${keyword}">처음</a>
						<a class="ltB"
							href="boardPage?page=${resultDTO.page-1}&searchType=${searchType}&keyword=${keyword}">&LT;</a>
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
						<a
							href="boardPage?page=${i}&searchType=${searchType}&keyword=${keyword}">${i}</a>&nbsp;
						</c:if>
				</c:forEach>

				<c:choose>
					<c:when test="${resultDTO.end != resultDTO.page}">
						<a class="gtB"
							href="boardPage?page=${resultDTO.page+1}&searchType=${searchType}&keyword=${keyword}">&GT;</a>
						<a class="lastB"
							href="boardPage?page=${resultDTO.end}&searchType=${searchType}&keyword=${keyword}">마지막</a>
					</c:when>
					<c:otherwise>
						<span class="gtB">&GT;</span>
						<span class="lastB">마지막</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

</body>
</html>