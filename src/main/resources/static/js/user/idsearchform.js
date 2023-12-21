function findUsername() {
	var email = $("#email").val();
	var name = $("#name").val();

	$.ajax({
		type: "POST",
		url: "/find-username",
		data: {
				email: email,
				name: name
				},
		success: function (result) {
			if(result != "없음"){
				$("#usernameResult").html('ID : '+result);
			}else {
				alert("해당하는 아이디가 없습니다");
			}
				
		},
		error: function (error) {
			console.log("Error: " + error);
		}
	});
}
