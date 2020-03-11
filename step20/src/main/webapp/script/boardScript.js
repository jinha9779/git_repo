function checkBoardWrite() {
	var frm = document.boardWriteForm;
	if(!frm.subject.value) {
		alert("제목을 입력하세요.");
		frm.subject.focus();
	} else if(!frm.content.value) {
		alert("내용을 입력하세요.");
		frm.content.focus();
	} else {
		frm.submit();
	}
}