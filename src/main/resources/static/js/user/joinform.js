function doumPost() {
   new daum.Postcode({
      oncomplete: function(data) {
         var addr = "";
         if (data.userSelectedType === 'R') {
            addr = data.roadAddress;
         } else {
            addr = data.jibunAddress;
         }
         $("#addr1").val(data.zonecode);
         $("#addr2").val(addr);
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
   } else if (register.email1.value === "") {
      alert("이메일을 입력해주세요");
      register.email1.focus();
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

      var chid = document.getElementById('id').value;
      $.ajax({
         url: "/Idcheck",
         type: "get",
         data: { id: chid },
         success: function(data) {
            if (data == 1) {
               alert("중복된 아이디입니다.");
            } else {
               register.submit();
               alert("회원가입을 축하합니다");
            }
         }
      });
   }
}

function idcheck() {
   var chid = document.getElementById('id').value;
   var idJ = /^[a-z0-9]{5,}$/;
   if (!chid) {
      alert('아이디를 입력해주세요.');
      register.id.focus();
      return;
   } else if (!idJ.test(register.id.value)) {
      alert("영어, 숫자만 가능합니다 최소 5글자 이상 입력하셔야합니다");
      register.id.focus();
   } else {
      var checkIdpop = window.open('/checkId?id=' + chid, '아이디 중복 확인', 'width=400,height=200');
      checkIdpop.focus();
   }
}