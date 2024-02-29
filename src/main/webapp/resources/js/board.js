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

document.addEventListener('DOMContentLoaded', function() {
	let editButtons = document.querySelectorAll('.edit-btn');

	editButtons.forEach(function(button) {
		button.addEventListener('click', function(event) {
			event.preventDefault();

			let row = button.closest('tr');
			let commentContentSpan = row.querySelector('.comment-content');
			let editCommentInput = row.querySelector('.edit-comment');

			if (button.classList.contains('update-btn')) {
				// 현재 버튼이 '수정 완료' 상태일 때
				let updatedComment = editCommentInput.value;
				commentContentSpan.textContent = updatedComment;
				commentContentSpan.style.display = 'inline';
				editCommentInput.style.display = 'none';
				button.textContent = '수정';
				button.classList.remove('update-btn');

				// Ajax를 사용하여 수정된 댓글을 서버로 전송
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
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '/board/updateComments', true);
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

		// 수정된 댓글 데이터를 서버로 전송
		let formData = 'comment_id=' + comment_id + '&comment_content=' + encodeURIComponent(updatedComment);
		xhr.send(formData);
	}
});


//댓글 삭제 
// 삭제 버튼 클릭 시
document.addEventListener('DOMContentLoaded', function () {
    let deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
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
    let xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/board/deleteComments?comment_id=' + comment_id, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // 삭제할 댓글의 ID를 서버로 전송
    let formData = 'comment_id=' + comment_id;
    xhr.send(formData);

    // 서버 응답 확인
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log('댓글이 성공적으로 삭제되었습니다.');
                // 삭제 성공 시 적절한 화면 갱신 로직 추가
                	alert("댓글이 삭제되었습니다.");
                    window.location.reload();
            } else {
                console.error('댓글 삭제에 실패했습니다.');
            }
        }
    };
}

