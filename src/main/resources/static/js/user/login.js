Kakao.init('d551a6c9a1c82fbd78214af07fec592f'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakaoLogin() {
	Kakao.Auth.login({
		success: function (response) {
			Kakao.API.request({
				url: '/v2/user/me',
				success: function (response) {
					var kakaoid = String(response.id);  // 카카오ID를 문자열로 변환
					$.ajax({
						url: '/kakaoLogin',
						type: 'POST',
						data: JSON.stringify({
							kakao: kakaoid,
						}),
						contentType: 'application/json',
						success: function (data) {
							location.href = "/";
						},
						error: function (error) {
							alert("없는 아이디 입니다. 회원가입을 해주세요");
							location.href = "/kakaojoinform?kakao=" + kakaoid;
						},
					});
				},
				fail: function (error) {
					console.log(error);
				},
			});
		},
		fail: function (error) {
			console.log(error);
		},
	});
}

