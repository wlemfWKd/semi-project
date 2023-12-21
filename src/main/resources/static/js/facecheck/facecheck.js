document.addEventListener("DOMContentLoaded", function () {
	// 폼 서브밋 이벤트 리스너 추가
	document.forms["Result"].addEventListener("submit", function (event) {
		// 선택 여부 확인
		var typeChecked = document.querySelector('input[name="type"]:checked');
		var troubleChecked = document.querySelector('input[name="trouble"]:checked');
		var dotChecked = document.querySelector('input[name="dot"]:checked');
		var colorChecked = document.querySelector('input[name="color"]:checked');
		var olderChecked = document.querySelector('input[name="older"]:checked');
		var genderChecked = document.querySelector('input[name="gender"]:checked');

		// 아무 것도 선택되지 않았을 때
		if (!typeChecked || !troubleChecked || !dotChecked || !colorChecked || !olderChecked || !genderChecked) {
			alert("모든 항목을 선택해주세요.");
			event.preventDefault(); // 서브밋 방지
		}
	});
});
