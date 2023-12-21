function findPassword() {
	var id = $("#id").val();
	var email = $("#email").val();

	$.ajax({
		type: "POST",
		url: "/find-password",
		contentType: "application/json",
		data: JSON.stringify({"id": id, "email": email}),
		success: function (data) {
			$("#resultPassword").text("비밀번호 재설정 바랍니다");
			// 동적으로 폼 표시
			document.getElementById('resetPasswordForm').style.display = 'block';
			document.getElementById('idr').value = data;
		},
		error: function () {
			$("#resultPassword").text("일치하는 계정이 없습니다.");
			// 일치하는 계정이 없을 때 폼 숨기기
			document.getElementById('resetPasswordForm').style.display = 'none';
		}
	});
}
