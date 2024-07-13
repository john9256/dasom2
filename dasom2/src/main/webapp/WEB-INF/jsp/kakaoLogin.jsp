<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kakao Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/kakaoLogin.css">
</head>
<body>
    <div class="header">
        카카오톡으로 로그인 하기
    </div>
    <div class="container">
        <a class="kakaoLoginFrame" href="https://kauth.kakao.com/oauth/authorize?&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=talk_message">
        <!-- <a class="kakaoLoginFrame" href="https://kauth.kakao.com/oauth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=talk_message"> -->
            <img id="loginBtn" class="kakaoLoginFrame" src='/image/kakao_login_large_wide.png'/>
        </a>
    </div>
</body>
</html>