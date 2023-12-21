const userCode="imp67328214";
IMP.init(userCode);

const tosspayButton = document.getElementById("tosspayButton");
const kakaopayButton = document.getElementById("kakaopayButton");
const paycoButton = document.getElementById("paycoButton");
const danalButton = document.getElementById("danalButton");


const kakaoPay = async () => {
   	var name = "koreaShop";
   	
   	var Pprice = document.getElementById('totalPrice').innerText;
    var Mname = document.getElementById('name1').innerText;
   	var Mphone = document.getElementById('phone1').innerText;
   	var Maddr = document.getElementById('addr1').innerText;

   IMP.request_pay( {
	  
      pg: "kakaopay",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
         alert("결제 완료: ", rsp);
	  	 document.getElementById("name").value = Mname;
	   	 document.getElementById("phone").value = Mphone;
	   	 document.getElementById("addr").value = Maddr;
         complete.submit();
      }else{
         alert("결제 실패: ", rsp);
      }
   });
};

const paycoPay = async () => {
   IMP.request_pay({
      pg: "payco",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
         alert("결제 완료: ", rsp);
	  	 document.getElementById("name").value = Mname;
	   	 document.getElementById("phone").value = Mphone;
	   	 document.getElementById("addr").value = Maddr;
         complete.submit();
      }else{
         alert.log("결제 실패: ", rsp);
      }
   });
};

const danalPay = async () => {
   IMP.request_pay({
      pg: "danal",
      pay_method: "card",
      amount: Pprice,
      name: name,
      merchant_uid: "",
   }, (rsp) => {
      if(rsp.success){
         alert("결제 완료: ", rsp);
	  	 document.getElementById("name").value = Mname;
	   	 document.getElementById("phone").value = Mphone;
	   	 document.getElementById("addr").value = Maddr;
         complete.submit();
      }else{
         alert.log("결제 실패: ", rsp);
      }
   });
};
