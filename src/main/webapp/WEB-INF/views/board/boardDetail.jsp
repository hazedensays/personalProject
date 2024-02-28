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
         
         <div>
            <div>
               <ul style="list-style: none; display: flex; flex-direction: row;">
                  <li style="margin-right: 10px;">댓글번호</li>
                  <li style="margin-right: 50px;">작성자</li>
                  <li style="margin-right: 30px;">글</li>
                  <li style="margin-right: 10px;">등록일</li>
               </ul>
   
               <c:if test="${not empty requestScope.commentsList}">
                  	<ul style="list-style: none; display: flex; flex-direction: row;">
                  		<c:forEach var="c" items="${requestScope.commentsList}">
                        	<li>
                      			<form action="commentsUpdate" method="POST">
	                                	<span>${c.comment_id}</span>
		                                <input type="hidden" name="comment_id" id="comment_id" value="${c.comment_id}" />
		                                <input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
		                                <input type="hidden" name="comment_delyn" id="comment_delyn" value="${c.comment_delyn}" />
		                                <input type="hidden" name="comment_root" id="comment_root" value="${c.comment_root}" />
		                                <input type="hidden" name="comment_regdate" id="comment_regdate" value="${c.comment_regdate}" />
		                                <input type="hidden" name="comment_steps" id="comment_steps" value="${c.comment_steps}" />
		                              	
		                              
		                                <span>${c.useremail}</span>
		                                <input type="hidden" name="useremail" id="useremail" value="${c.useremail}"/>
		                              
		                               <span data-comment-id="${c.comment_id}">${c.comment_content}</span>
		                               <input type="text" id="comments_content" name="comment_content" value="${c.comment_content}" style="display: none;" data-comment-id="${c.comment_id}"/>
                           
	                               	<c:if test="${sessionScope.loginUser.useremail == c.useremail}">
										<div class="modifyBtn" style="display: ${buttonState ? 'block' : 'none'};">
										    <button>수정</button>
										    <button>삭제</button>
										</div>
										<div class="completeBtn" style="display: ${buttonState ? 'none' : 'block'};">
										    <button>완료</button>
										    <button>취소</button>
										</div>
	                               	</c:if>
                      			</form>
                           	</li>
                  		</c:forEach>
                    </ul>
               </c:if>
            </div>
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