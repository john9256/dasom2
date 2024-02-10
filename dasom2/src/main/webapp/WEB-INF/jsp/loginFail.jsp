<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Failed</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f2f2;
      text-align: center;
      padding: 30px;
    }
    
    h1 {
      color: #333333;
      font-size: 24px;
      margin-bottom: 30px;
    }
    
    p {
      color: #666666;
      font-size: 16px;
      margin-bottom: 30px;
    }
    
    .btn {
      display: inline-block;
      background-color: #f8a5c2;
      border-color: #f8a5c2;
      padding: 10px 30px;
      text-decoration: none;
      color: #fff;
      border-radius: 4px;
      font-size: 16px;
      transition: background-color 0.3s;
    }
    
    .btn:hover {
      background-color: #e687a6;
      border-color: #e687a6;
    }
    
  </style>
</head>
<body>
  <h1>로그인 실패</h1>
  <p>잘못된 사용자 이름 또는 비밀번호입니다.</p>
  <a class="btn" href="/loginPage">로그인 화면으로 돌아갑니다.</a>
</body>
</html>
