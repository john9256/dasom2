<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verification Result</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* 간단한 반응형 디자인을 위한 CSS */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            flex-direction: column;
        }
        .message {
            font-size: 18px;
            margin: 10px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<c:choose>
    <c:when test="${successFlag == 'Y'}">
        <div class="message">인증에 성공했습니다.<br>이 창은 3초 후에 닫힙니다.</div>
    </c:when>
    <c:otherwise>
        <div class="message">인증에 실패했습니다. 아이디 및 이메일을 확인 바랍니다.<br>이 창은 3초 후에 닫힙니다.</div>
    </c:otherwise>
</c:choose>

<script>
    // 3초 후에 창을 닫는 함수 정의
    setTimeout(function() {
        window.close();
    }, 3000); // 3000 밀리초 = 3초 후에 실행
    
</script>
</body>
</html>
