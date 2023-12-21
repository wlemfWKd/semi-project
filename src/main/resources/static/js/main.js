$(function() {

	// Sticky JS
	// 상단 네비게이션 고정 설정하기
	$(window).scroll(function() {

		var scrollPos = $(window).scrollTop();

		if (scrollPos > 20) {
			$('#header').addClass('fixed');
		} else {
			$('#header').removeClass('fixed');
		}

		if (scrollPos < 20) {
			$('#header #gnb li:first-child a').removeClass('active');
		}

	});

	var mainMenu = $('#gnb>ul>li');
	var mainMenuItem = $('#gnb>ul>li>a');
	var subMenu = $('#gnb ul ul');

	mainMenu.hover(
		function() {
			subMenu.slideUp(400);
			$(this).children('a').addClass('act').next().stop().slideDown(400);
		},
		function() {
			subMenu.stop().slideUp(400);
			mainMenuItem.removeClass('act');
		}
	);

});
//------------------------------------------------------------------------------
const cards = document.querySelectorAll('.card');

cards.forEach((card) => {
	card.addEventListener('click', function() {
		const reviewImage = card.getAttribute('data-reviewImage');
		const reviewContent = card.getAttribute('data-reviewContent');

		const rating = card.getAttribute('data-rating');
		const reviewWriter = card.getAttribute('data-reviewWriter');
		const reviewDate = card.getAttribute('data-reviewDate');
		const product = card.getAttribute('data-product');

		showModal(reviewImage, reviewContent, rating, reviewWriter, reviewDate, product);
	});
});

function showModal(imageUrl, reviewContent, rating, reviewWriter, reviewDate, product) {
	var modal = document.getElementById("myModal");
	var modalImage = document.getElementById("modalImage");
	var modalReviewContent = document.getElementById("modalReviewContent");

	var modalRating = document.getElementById("modalRating");
	var modalReviewWriter = document.getElementById("modalReviewWriter");
	var modalDate = document.getElementById("modalDate");
	var modalProduct = document.getElementById("modalProduct");

	modalImage.src = imageUrl;
	modalReviewContent.textContent = reviewContent;
	modalProduct.textContent = product;

	modalRating.textContent = rating;
	modalReviewWriter.textContent = reviewWriter + ' 님';

	// 날짜에서 시, 분, 초를 제거하여 표시
	const date = new Date(reviewDate);
	const dateString = date.toLocaleDateString();
	modalDate.textContent = dateString;

	modal.style.display = "block";
}

function hideModal() {
	var modal = document.getElementById("myModal");
	modal.style.display = "none";
}

//------------------------------------------------------------------

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center: new kakao.maps.LatLng(37.50005, 127.03557), // 지도의 중심좌표
		level: 1, // 지도의 확대 레벨
		mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
	};

// 지도를 생성한다 
var map = new kakao.maps.Map(mapContainer, mapOption);

// 지도에 마커를 생성하고 표시한다
var marker = new kakao.maps.Marker({
	position: new kakao.maps.LatLng(37.50005, 127.03557), // 마커의 좌표
	map: map // 마커를 표시할 지도 객체
});
//--------------------------------------------------------------------

