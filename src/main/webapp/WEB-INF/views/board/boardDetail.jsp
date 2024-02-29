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
      <table border="1">
		    <tr>
		        <th>Board Number</th>
		        <td>${requestScope.boardDetail.board_id}</td>
		        <th>Views</th>
		        <td>${requestScope.boardDetail.board_views}</td>
		    </tr>
		
		    <tr>
		        <th colspan="3">Board title</th>
		        <td colspan="3">${requestScope.boardDetail.board_title}</td>
		    </tr>
		
		    <tr>
		        <th colspan="3">등록일</th>
		        <td colspan="3">${requestScope.boardDetail.board_regdate}</td>
		    </tr>
		
		    <tr>
		        <th colspan="3">Writer</th>
		        <td colspan="3">${requestScope.boardDetail.useremail}</td>
		    </tr>
		
		    <tr>
		        <th colspan="3">Content</th>
		        <td colspan="3">${requestScope.boardDetail.board_content}</td>
		    </tr>
		</table>
		
		<div class="like-container">
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
                     <th>UserEmail</th>
                     <td>
                        ${sessionScope.loginUser.useremail}
                        <input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}">
		                <input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id}" required/>
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
         
         <div class="comments">
			<form action="updateComments" method="post">
				<table>
					<tr>
						<th>댓글 번호</th>
						<th>작성자</th>
						<th>댓글 내용</th>
						<th>등록일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>

					<c:if test="${not empty requestScope.commentsList}">
						<c:forEach var="c" items="${requestScope.commentsList}">

							<tr>
								<td>${c.comment_id}</td>
								<td>${c.useremail}</td>
								<td>
									<span class="comment-content">${c.comment_content}</span>
									<input type="text" class="edit-comment" style="display: none;" value="${c.comment_content}">
								</td>
								<td>${c.comment_regdate}</td>
								
								<c:if test="${sessionScope.loginUser.useremail == c.useremail}">
									<td>
										<button data-idx="${c.comment_id}" class="edit-btn">수정</button>
									</td>
									
									<td>
										<button data-idx="${c.comment_id}" class="delete-btn">삭제</button>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>

<%-- 					<c:if test="${empty requestScope.commentsList}">
						<tr>
							<th colspan="4">게시글에 작성된 댓글이 없습니다.</th>
						</tr>
					</c:if> --%>
					
				</table>
			</form>
		</div>

         <div class="pageNation">
                 <c:choose>
                      <c:when test="${resultDTO.start != resultDTO.page}">
                           <a class ="firstB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.start}">처음</a>
                           <a class ="ltB" href="boardDetail?board_id=${requestScope.boardDetail.board_id}&page=${resultDTO.page-1}">&LT;</a>
                      </c:when>
                      <c:otherwise>
                           <span class ="firstB">처음</span>
                           <span class ="ltB">&LT;</span>
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