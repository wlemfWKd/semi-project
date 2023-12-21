document.getElementById('productSelect').addEventListener('change', function () {
	var selectedOption = this.options[this.selectedIndex];
	var productNameInput = document.getElementById('productName');

	// 선택한 옵션의 productName 값을 hidden input에 설정
	productNameInput.value = selectedOption.text;
});
