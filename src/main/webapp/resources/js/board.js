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

document.addEventListener('DOMContentLoaded', function() {
    let editButtons = document.querySelectorAll('.edit-btn');

    editButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            let listItem = button.closest('li');
            let commentContentSpan = listItem.querySelector('.comment-content');
            let editCommentInput = listItem.querySelector('.edit-comment');

            if (button.classList.contains('update-btn')) {
                // 현재 버튼이 '수정 완료' 상태일 때
                let updatedComment = editCommentInput.value;
                commentContentSpan.textContent = updatedComment;
                commentContentSpan.style.display = 'inline';
                editCommentInput.style.display = 'none';
                button.textContent = '수정';
                button.classList.remove('update-btn');

                // Axios를 사용하여 수정된 댓글을 서버로 전송
                let comment_id = button.getAttribute('data-idx');
                updateCommentOnServer(comment_id, updatedComment);
            } else {
                // 현재 버튼이 '수정' 상태일 때
                commentContentSpan.style.display = 'none';
                editCommentInput.style.display = 'block';
                button.textContent = '수정 완료';
                button.classList.add('update-btn');
            }
        });
    });

    function updateCommentOnServer(comment_id, updatedComment) {
        let url = '/board/updateComments';
        let formData = new FormData();
        formData.append('comment_id', comment_id);
        formData.append('comment_content', updatedComment);

        axios.post(url, formData)
            .then(response => {
                console.log('댓글이 성공적으로 업데이트되었습니다.');
            })
            .catch(error => {
                console.error('댓글 업데이트에 실패했습니다:', error);
            });
    }
});

// 댓글 삭제
document.addEventListener('DOMContentLoaded', function() {
    let deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            handleDeleteButtonClick(button);
        });
    });

    function handleDeleteButtonClick(button) {
        let comment_id = button.getAttribute('data-idx');
        deleteCommentOnServer(comment_id);
    }
});

function deleteCommentOnServer(comment_id) {
    let url = '/board/deleteComments?comment_id=' + comment_id;

    axios.delete(url)
        .then(response => {
            console.log('댓글이 성공적으로 삭제되었습니다.');
            // 삭제 성공 시 적절한 화면 갱신 로직 추가
            alert('댓글이 삭제되었습니다.');
            window.location.reload();
        })
        .catch(error => {
            console.error('댓글 삭제에 실패했습니다:', error);
        });
}

function toggleReply(comment_id) {
    const replyContainer = document.getElementById(`reply-${comment_id}`);
    replyContainer.style.display = (replyContainer.style.display === 'none') ? 'block' : 'none';
}
