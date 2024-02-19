function axboardDelete(id) {
	let url = "/board/boardDelete/" + id;

	if (confirm("삭제하시겠습니까?")) {
		axios.post(url, { board_delyn: 'Y'}
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