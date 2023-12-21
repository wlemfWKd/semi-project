function searchByProductCode(productCode) {
	document.getElementById('searchAttr').value = 'productCode';
	document.getElementById('searchKeyword').value = productCode;
	var form = document.querySelector('form');
	form.submit();
}
