function minusCount(cartCode) {
	var count = document.getElementById(cartCode); //code를 th:id="${items.cartCode}"로 줌으로써 code에 맞는 count를 가져옴
	$.ajax({
		type: 'POST',
		url: '/minusCart',
		data: {cartCode: cartCode},
		success: function () {
			var c = parseInt(count.innerText);
			count.innerText = --c;
			//위까지가 갯수바뀌는 코드
			productPrice(cartCode, parseInt(count.innerText));
			totalPrice();
		}
	});
}

function plusCount(cartCode) {
	var count = document.getElementById(cartCode);
	$.ajax({
		type: 'POST',
		url: '/plusCart',
		data: {cartCode: cartCode},
		success: function () {
			var c = parseInt(count.innerText);
			count.innerText = ++c;
			productPrice(cartCode, parseInt(count.innerText));
			totalPrice();
		}
	});
}

function productPrice(cartCode, count) {
	var cartPrice = document.getElementById('cartPrice' + cartCode); //ex) id="cartPricew-001" //총가격
	var price = document.getElementById('price' + cartCode); // 개별가격
	var price = (price.value) * count;
	cartPrice.innerText = price;
}

//onchange
function totalPrice() {
	var totalPrice = document.getElementById('totalPrice');
	var checked = document.getElementsByName('cartChecked');// name은 무조건 배열이기때문에 getElements
	var price = document.getElementsByName('cartPrice');
	var result = 0;
	for (var i = 0; i < checked.length; i++) { //상품의 갯수
		if (checked[i].checked) { // 상품이 체크되었는지
			result += parseInt(price[i].innerText); // 체크된 가격만 누적해서 result에 넣어줘
		}
	}
	totalPrice.innerText = result;
}

//모두 체크되어있을때
$(function () {
	$("#checkBoxs").click(function () {
		if ($("#checkBoxs").prop("checked")) {
			$("input:checkbox[name=cartChecked]").prop("checked", true);
			totalPrice();
		} else {
			$("input:checkbox[name=cartChecked]").prop("checked", false);
			totalPrice();
		}
	});
});
// 체크하나만 없애도 상단 체크가 없어지는거
$("input:checkbox[name=cartChecked]").click(function () {
	var tot = $("input:checkbox[name=cartChecked]").length;
	var checked = $("input:checkbox[name=cartChecked]:checked").length;
	if (tot == checked) {
		$("#checkBoxs").prop("checked", true);
	} else {
		$("#checkBoxs").prop("checked", false);
	}
});

//처음 로딩됏을때 다 체크되어있어라
function checkedLoad() {
	$("input:checkbox[name=cartChecked]").prop("checked", true);
	totalPrice();
}

//페이지 로딩이 다 끝났을때 checkedLoad()를 실행해
window.onload = function () {
	checkedLoad();
}

function buyCart() {
	// 모든 체크박스 가져오
	var checkedProducts = document.getElementsByName('cartChecked');
	// 객체형태(배열,리스트등등..)의 데이터 담을 그릇 생
	var products = [];

	for (var i = 0; i < checkedProducts.length; i++) {
		if (checkedProducts[i].checked) {
			console.log(i + "번째 : " + checkedProducts[i].value);
			// 내가 넣어줄 데이터 생성
			var count = document.getElementById(checkedProducts[i].value).innerText;
			var price = document.getElementById('cartPrice' + checkedProducts[i].value).innerText;
			var name = document.getElementById('cartName' + checkedProducts[i].value).innerText;
			console.log(i + "번째 : " + checkedProducts[i].value + "의 갯수 : " + count);

			var data = {
				//Json형태의 데이터로 생성
				bpCode: checkedProducts[i].value,
				bpCount: count,
				bpPrice: price,
				bpName: name
			}
			console.log("들어간 데이터 : " + data);
			products.push(data);
		}
	}
	console.log(products);

	var dataToSend = JSON.stringify(products);
	// 객체 직렬화,json데이터를 String형태로 변환
	var url = "/cartOrderForm?data=" + encodeURIComponent(dataToSend);

	// 새로운 페이지로 이동
	window.location.href = url;

}

