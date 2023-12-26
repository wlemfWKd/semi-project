const userCode="imp67328214";
IMP.init(userCode);

const tosspayButton = document.getElementById("tosspayButton");
const kakaopayButton = document.getElementById("kakaopayButton");
const paycoButton = document.getElementById("paycoButton");
const danalButton = document.getElementById("danalButton");

const kakaoPay = async () => {
   	var name = "koreaShop";
   	var Mname = document.getElementById('name1').innerText;
   	var Mphone = document.getElementById('phone1').innerText;
   	var Maddr = document.getElementById('addr1').innerText;
   	var Pprice = document.getElementById('productPrice1').innerText;
   	var Pcount = document.getElementById('productCount1').innerText;
   	var Pname = document.getElementById('productName1').innerText;
   	var Pcode = document.getElementById('productCode1').innerText;

   
   console.log(Mname);
   console.log(Mphone);
   console.log(Maddr);
   console.log(Pprice);
   console.log(Pcount);
   console.log(Pname);
   IMP.request_pay( {
	  
      pg: "kakaopay",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
         alert("결제 완료: ", rsp);
         Pprice = Pprice.replace(/,/g, '').replace('원', '');
		    document.getElementById("name").value = Mname;
		   	document.getElementById("phone").value = Mphone;
		   	document.getElementById("addr").value = Maddr;
		   	document.getElementById("productName").value = Pname;
		   	document.getElementById("productCount").value = Pcount;
		   	document.getElementById("productPrice").value = Pprice;
		   	document.getElementById("productCode").value = Pcode;
         complete.submit();
      }else{
         alert("결제 실패: ", rsp);
      }
   });
};

const paycoPay = async () => {
	var name = "koreaShop";
   	var Mname = document.getElementById('name1').innerText;
   	var Mphone = document.getElementById('phone1').innerText;
   	var Maddr = document.getElementById('addr1').innerText;
   	var Pprice = document.getElementById('productPrice1').innerText;
   	var Pcount = document.getElementById('productCount1').innerText;
   	var Pname = document.getElementById('productName1').innerText;
   	var Pcode = document.getElementById('productCode1').innerText;
   IMP.request_pay({
      pg: "payco",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
		    document.getElementById("name").value = Mname;
		   	document.getElementById("phone").value = Mphone;
		   	document.getElementById("addr").value = Maddr;
		   	document.getElementById("productName").value = Pname;
		   	document.getElementById("productCount").value = Pcount;
		   	document.getElementById("productPrice").value = Pprice;
		   	document.getElementById("productCode").value = Pcode;
         complete.submit();
      }else{
         alert.log("결제 실패: ", rsp);
      }
   });
};

const danalPay = async () => {
	var name = "koreaShop";	
   	var Mname = document.getElementById('name1').innerText;
   	var Mphone = document.getElementById('phone1').innerText;
   	var Maddr = document.getElementById('addr1').innerText;
   	var Pprice = document.getElementById('productPrice1').innerText;
   	var Pcount = document.getElementById('productCount1').innerText;
   	var Pname = document.getElementById('productName1').innerText;
   	var Pcode = document.getElementById('productCode1').innerText;
   IMP.request_pay({
      pg: "danal",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
		    document.getElementById("name").value = Mname;
		   	document.getElementById("phone").value = Mphone;
		   	document.getElementById("addr").value = Maddr;
		   	document.getElementById("productName").value = Pname;
		   	document.getElementById("productCount").value = Pcount;
		   	document.getElementById("productPrice").value = Pprice;
		   	document.getElementById("productCode").value = Pcode;
         complete.submit();
      }else{
         alert.log("결제 실패: ", rsp);
      }
   });
};



























