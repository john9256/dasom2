<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>다솜 메인</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .navbar {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Main Page</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <c:out value="${userId}"/>님 반갑습니다.
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <form action="/logout" method="get">
                            <button type="submit" class="dropdown-item">로그아웃</button>
                        </form>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <c:choose>
            <c:when test="${surveyCheck == '0'}">
                <h1>소개팅 준비를 해주세요!</h1>
                <a class="btn" href="/survey">설문조사 하기</a>
                <!-- Add survey form here -->
            </c:when>
            <c:otherwise>
                <h1>소개팅 상대를 찾고 있습니다!</h1>
                <a class="btn" href="/survey">설문조사 수정하기</a>
            </c:otherwise>
        </c:choose>
        
        <c:if test="${userId == 'ksw'}">
            <a class="btn" href="/excel">Excel 업로드</a>
            <a class="btn" href="/surveyResult">회원 리스트</a>
        </c:if>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
