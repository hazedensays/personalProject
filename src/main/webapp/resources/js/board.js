function toggleLike(board_id, useremail) {
        console.log(board_id, useremail);
        
        let url = "/board/likesInsert/" + board_id + "/" + useremail;

        axios.post(url)
            .then(response => {
                let likeCountElement = document.getElementById('likeCount');
                let currentLikeCount = parseInt(likeCountElement.textContent);
                
                if (response.status === 200) {
                    likeCountElement.textContent = currentLikeCount + 1; // 좋아요 추가
                } else if (response.status === 204) {
                    likeCountElement.textContent = currentLikeCount - 1; // 좋아요 삭제
                }
            })
            .catch(error => {
                console.error('Error toggling like:', error);
            });
    }

/*==================================================================*/
function axboardDelete(id) {
   let url = "/board/boardDelete/" + id;

   if (confirm("삭제하시겠습니까?")) {
      axios.post(url
      ).then(response => {
         alert("삭제되었습니다.");
         window.location.href = "/board/boardPage";
      }).catch(err => {
         if (err.response && err.response.status === 502) {
            alert("[삭제 오류]" + err.response.data);
         } else {
            alert("[시스템 오류]" + err.message);
         }
      });
   } else {
      alert("취소되었습니다.");
   }
}


/*==================================================================*/
function axboardUpdate(event, comment_id) {
   event.preventDefault();
   
    let spanElements = document.querySelectorAll('span[data-comment-id="' + comment_id + '"]');
    let inputElements = document.querySelectorAll('input[data-comment-id="' + comment_id + '"]');
    let buttonElement = document.querySelector('button[data-comment-id="' + comment_id + '"]');

    spanElements.forEach(function(span) {
        span.style.display = 'none';
    });
    
    inputElements.forEach(function(input) {
        input.style.display = 'block';
    });

}




