function doumPost() {
	new daum.Postcode({
		oncomplete: function(data) {
			var addr = "";
			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}
			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			$("#addr1").val(data.zonecode);
			$("#addr2").val(addr);
			// 커서를 상세주소 필드로 이동한다.
			$("#addr3").focus();
		}
	}).open();
}


var nameJ = /^[가-힣a-zA-Z]{2,6}$/;
var idJ = /^[a-z0-9]{5,}$/;
var pwJ = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
var phoneJ1 = /^[0-9]{3,3}$/;
var phoneJ2 = /^[0-9]{4,4}/;
var phoneJ3 = /^[0-9]{4,4}$/;

function push() {
	if (register.id.value === "") {
		alert("아이디를 입력해주세요");
		register.id.focus();
	} else if (!idJ.test(register.id.value)) {
		alert("영어, 숫자만 가능합니다 최소 5글자 이상 입력하셔야합니다");
		register.id.focus();
	} else if (register.password.value === "") {
		alert("비밀번호를 입력해주세요");
		register.password.focus();
	} else if (register.password.value !== register.passwordc.value) {
		alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요");
		register.passwordc.focus();
	} else if (!pwJ.test(register.password.value)) {
		alert("비밀번호 최소 8자리, 숫자,문자,특수문자 최소 1개만 가능합니다");
		register.password.focus();
	} else if (register.name.value === "") {
		alert("이름을 입력해주세요");
		register.name.focus();
	} else if (!nameJ.test(register.name.value)) {
		alert("이름은 한글, 영문만 가능합니다");
		register.name.focus();
	} else if (!phoneJ1.test(register.phone1.value)) {
		alert("숫자 3자만 가능합니다");
		register.phone1.focus();
	} else if (!phoneJ2.test(register.phone2.value)) {
		alert("숫자 4자만 가능합니다");
		register.phone2.focus();
	} else if (!phoneJ3.test(register.phone3.value)) {
		alert("숫자 4자만 가능합니다");
		register.phone3.focus();
	} else {
		var email1 = $("#email1").val() || "";
		var email2 = $("#email2").val() || "";
		var email = email1 + email2;
		$("#email").val(email);

		var phone1 = $("#phone1").val() || "";
		var phone2 = $("#phone2").val() || "";
		var phone3 = $("#phone3").val() || "";
		var phone = phone1 + "-" + phone2 + "-" + phone3;
		$("#phone").val(phone);

		var addr1 = $("#addr1").val() || "";
		var addr2 = $("#addr2").val() || "";
		var addr3 = $("#addr3").val() || "";
		var addr = addr1 + "/" + addr2 + "/" + addr3;

		$("#addr").val(addr);
		register.submit();

	}
}

function out() {
	var id = register.id.value;
	var name = register.name.value;
	$.ajax({
		url: "/out",
		type: "get",
		data: {
			id: id,
			name: name
		},
		success: function(data) {
			if (data == 1) {
				alert("회원탈퇴 완료되었습니다.");
				location.href = "/"
				return;
			} else {
				alert("회원탈퇴 실패하였습니다.");
				return;
			}
		}

	});
}




















