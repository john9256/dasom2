<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <div class="message">인증에 성공했습니다.</div>
    </c:when>
    <c:otherwise>
        <div class="message">인증에 실패했습니다.</div>
    </c:otherwise>
</c:choose>

</body>
</html>
