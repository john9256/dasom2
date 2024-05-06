<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/commonCss.css">
    <title>대기 페이지</title>
    <style>
    .full-screen-wrapper {
        display: flex;
        justify-content: center; /* Horizontally centers the child */
        align-items: center; /* Vertically centers the child */
        height: 100vh; /* Full viewport height */
    }

    .button-container {
        display: flex;
        flex-direction: column; /* Stacks buttons vertically */
        align-items: center; /* Centers buttons horizontally */
        gap: 25px; /* Space between buttons */
        width: 80%; /* .button-container의 너비를 화면의 80%로 설정 */
    	max-width: 1500px; /* 최대 너비를 500px로 제한 */
    	justify-content: flex-end; /* 오른쪽 정렬 */
    }

    .button-container button.btn-main {
        width: 80%;
        padding: 17px 0;
        font-size: 22px;
        background-color: #ff85a2 !important;
        border-color: #ff85a2;
        color: white;
    }

    .button-container button.btn-main:hover {
        background-color: pink !important;
        border-color: pink;
    }
    
    .top-text{
     font-size: 40px;
     text-align: center;
     color: #ff85a2;
     text-decoration: none; /* 밑줄을 제거하여 링크가 클릭 가능함을 보여줍니다. */
     font-weight: bold;
    }
    
    .second-text{
     font-size: 20px;
     text-align: center;
     color: #ff85a2;
     text-decoration: none; /* 밑줄을 제거하여 링크가 클릭 가능함을 보여줍니다. */
     font-weight: bold;
    }
    
    .top-text a:link, .top-text a:visited {
    color: #ff85a2; /* 방문하지 않은 링크 */
    text-decoration: none; /* 밑줄 제거 */
	}
	
    .modal-dialog {
       display: flex;
       align-items: center;
       justify-content: center;
       height: 100vh;
    }
    
    .modal-content {
        margin-top: auto;
        margin-bottom: auto;
    }
	
	.second-text{
		margin-top : 20px;
	}
	
</style>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!-- <script src="dashboard.js"></script>  -->
    
</head>
<body>
<div class="container mt-3">
    <div class="top-text"><a href="/mainPage">다솜 소개팅</a></div>
    <div class="second-text">호스트가 회원가입 검토중에 있습니다.<br>조금만 기다려주세요!</div>
    <div class="full-screen-wrapper">
    <div class="button-container">
    	<button id="guideBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">소개팅 가이드</button>
    </div>
</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabel"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <!-- 내용은 JavaScript를 통해 동적으로 로드 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>




<script>



</script>

</body>
</html>


