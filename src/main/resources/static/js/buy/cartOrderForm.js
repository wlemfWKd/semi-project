function doumPost() {
	new daum.Postcode({
		oncomplete: function (data) {
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


function push() {
	if (register.name.value === "") {
		alert("받는분 이름을 입력해주세요");
		register.name.focus();
	} else {
		var phone1 = $("#phone1").val() || "0";
		var phone2 = $("#phone2").val() || "0";
		var phone3 = $("#phone3").val() || "0";
		var phone = phone1 + "-" + phone2 + "-" + phone3;
		$("#phone").val(phone);

		var addr1 = $("#addr1").val() || "0";
		var addr2 = $("#addr2").val() || "0";
		var addr3 = $("#addr3").val() || "0";
		var addr = addr1 + "/" + addr2 + "/" + addr3;

		// 수정된 부분
		$("#addr").val(addr);

		register.submit();
	}
}

