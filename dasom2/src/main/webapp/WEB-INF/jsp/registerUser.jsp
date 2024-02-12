<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>회원 가입</title>

<style>
body {
	background-color: #f8f9fa;
}

.card {
	margin-top: 100px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.card-header {
	background-color: #f8a5c2;
	color: #fff;
}

.btn-primary {
	background-color: #f8a5c2;
	border-color: #f8a5c2;
}

.btn-primary:hover {
	background-color: #e687a6;
	border-color: #e687a6;
}

</style>

</head>
<body>
<form action="/register" method="post" enctype="multipart/form-data">
    아이디: <input type="text" name="userId" required /><br/>
    비밀번호: <input type="password" name="password" required /><br/>
    이름: <input type="text" name="userName" required /><br/>
    전화번호: <input type="text" name="phoneNumber" placeholder="숫자만 입력해주세요" required /><br/>
    이메일: <input type="text" name="email" required /><br/>
    <button type="button" id="sendEmailVerification">이메일 인증</button><br/>
    성별: 
    <select name="sex" required >
        <option value="남">남</option>
        <option value="녀">녀</option>
    </select><br/>
    생일:
    <select name="birthdayYear" required >
        <% for (int year = 1980; year <= 2020; year++) { %>
            <option value="<%= year %>"><%= year %></option>
        <% } %>
    </select>
    <br/>
    키:
    <select name="height" required required >
        <% for (int height = 150; height <= 190; height++) { %>
            <option value="<%= height %>"><%= height %>cm</option>
        <% } %>
    </select><br/>
    직업 구분: <input type="text" name="jobDivision"  required /><br/>
    거주지: <select name="residence" id="residence" required >
        <option value="">거주지를 선택해주세요</option>
    </select><br/>
    직장 위치: <select name="jobResidence" id="jobResidence" required >
        <option value="">직장 위치를 선택해주세요</option>
    </select><br/>
    신분증 이미지: <input type="file" name="idCardImage" required /><br/>
    명함 이미지: <input type="file" name="businessCardImage" required /><span>현재 재직중인 직장의 명함으로 부탁합니다</span><br/>
    본인 사진: <input type="file" name="selfImage" required /><span>최근 1년 안의 사진으로 부탁합니다</span><br/>
    <input type="submit" value="회원가입" />
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

$(document).ready(function() {
    var isUserIdValid = false; // 아이디 중복 체크 상태
    var isEmailVerified = false; // 이메일 인증 상태
	
 // 거주지 데이터
    $.ajax({
        type: 'GET',
        url: '/getCriteriaData',
        data: { criteria: "residence" },
        success: function(data) {
            $.each(data, function(i, item) {
                $('#residence').append($('<option>', { 
                    value: item.value,
                    text : item.text 
                }));
            });
        }
    });
    
    // 아이디 중복 체크
    $("#userId").blur(function() {
        var userId = $(this).val();
        $.ajax({
            url: "/checkUserId",
            type: "GET",
            data: { userId: userId },
            success: function(isValid) {
                if (isValid) {
                    isUserIdValid = true;
                } else {
                    alert("이미 사용 중인 아이디입니다.");
                    isUserIdValid = false;
                }
            }
        });
    });

    // 이메일 인증 요청
    $("#sendEmailVerification").click(function() {
        var email = $("#email").val();
        $.ajax({
            url: "/sendEmailVerification",
            type: "GET",
            data: { email: email },
            success: function(isRequested) {
                if (isRequested) {
                    alert("이메일 인증을 요청했습니다. 이메일을 확인해 주세요.");
                    isEmailVerified = true;
                } else {
                    alert("이메일 인증 요청에 실패했습니다.");
                    isEmailVerified = false;
                }
            }
        });
    });

    // 폼 제출 전 검증
    $("form").submit(function(e) {
        if (!isUserIdValid || !isEmailVerified) {
            e.preventDefault(); // 폼 제출 중지
            alert("아이디 중복 체크와 이메일 인증을 완료해주세요.");
        }
    });
});
</script>
</body>
</html>
