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
            margin-top: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .card-header, .btn-primary {
            background-color: #f8a5c2;
            color: #fff;
        }

        .btn-primary:hover {
            background-color: #e687a6;
            border-color: #e687a6;
        }

        .form-control:focus {
            border-color: #f8a5c2;
            box-shadow: 0 0 0 0.2rem rgba(248, 165, 194, 0.25);
        }

        .custom-file-input:focus ~ .custom-file-label {
            border-color: #f8a5c2;
            box-shadow: 0 0 0 0.2rem rgba(248, 165, 194, 0.25);
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">소개팅 준비를 해주세요!</div>
                <div class="card-body">
                    <form action="/register" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="userId">아이디:</label>
                            <input type="text" class="form-control" name="userId" id="userId" required>
                        </div>
                        <div class="form-group">
                            <label for="password">비밀번호:</label>
                            <input type="password" class="form-control" name="password" id="password" required>
                        </div>
                        <div class="form-group">
                            <label for="userName">이름:</label>
                            <input type="text" class="form-control" name="userName" id="userName" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">전화번호:</label>
                            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="숫자만 입력해주세요" required>
                        </div>
                        <div class="form-group">
                            <label for="email">이메일:</label>
                            <input type="text" class="form-control" name="email" id="email" required>
                        </div>
						<div class="form-group">
						    <button type="button" id="sendEmailVerification" class="btn btn-primary">이메일 인증</button>
						</div>
						<div id="loadingMessage" style="display: none;">이메일 인증을 요청중입니다...</div>
						<div class="form-group">
						    <label for="sex">성별:</label>
						    <select class="form-control" name="sex" id="sex" required>
						        <option value="남성">남성</option>
						        <option value="여성">여성</option>
						    </select>
						</div>
						<div class="form-group">
						    <label for="birthday">생일:</label>
						    <select class="form-control" name="birthday" id="birthday" required>
						        <% for (int year = 1980; year <= 2010; year++) { %>
						            <option value="<%= year %>"><%= year %></option>
						        <% } %>
						    </select>
						</div>
						<div class="form-group">
						    <label for="height">키:</label>
						    <select class="form-control" name="height" id="height" required>
						        <% for (int height = 150; height <= 190; height++) { %>
						            <option value="<%= height %>"><%= height %>cm</option>
						        <% } %>
						    </select>
						</div>
						<div class="form-group">
						    <label for="companyName">직장명:</label>
						    <input type="text" class="form-control" name="companyName" id="companyName" required>
						</div>
						<div class="form-group">
						    <label for="jobDivision">직무:</label>
						    <input type="text" class="form-control" name="jobDivision" id="jobDivision" placeholder="IT 개발자, " required>
						</div>
						<div class="form-group">
						    <label for="residence">거주지:</label>
						    <select class="form-control" name="residence" id="residence" required>
						        <option value="">거주지를 선택해주세요</option>
						        <!-- Dynamic options should be loaded here -->
						    </select>
						</div>
						<div class="form-group">
						    <label for="jobResidence">직장 위치:</label>
						    <select class="form-control" name="jobResidence" id="jobResidence" required>
						        <option value="">직장 위치를 선택해주세요</option>
						        <!-- Dynamic options should be loaded here -->
						    </select>
						</div>
						<div class="form-group">
						    <label for="idCardImage">신분증 이미지:</label>
						    <input type="file" class="form-control-file" name="idCardImage" id="idCardImage" required>
						</div>
						<div class="form-group">
						    <label for="businessCardImage">명함 이미지:</label>
						    <input type="file" class="form-control-file" name="businessCardImage" id="businessCardImage" required>
						    <small class="form-text text-muted">현재 재직중인 직장의 명함으로 부탁합니다</small>
						</div>
						<div class="form-group">
						    <label for="selfImage">본인 사진:</label>
						    <input type="file" class="form-control-file" name="selfImage" id="selfImage" required>
						    <small class="form-text text-muted">최근 1년 안의 사진으로 부탁합니다</small>
						</div>
                        <button type="submit" class="btn btn-primary">회원가입</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

$(document).ready(function() {
    var isUserIdValid = false; // 아이디 중복 체크 상태
    var isEmailSended = false; // 이메일 송부 여부
	
 // 거주지 데이터
    $.ajax({
    type: 'GET',
    url: '/getCriteriaData',
    data: { criteria: "residence" },
    success: function(data) {
        data.forEach(function(item) {
            $('#residence').append($('<option>', { 
                value: item.value1, 
                text: item.value1
            }));
            $('#jobResidence').append($('<option>', { 
                value: item.value1, 
                text: item.value1
            }));
        });
    }
});
    
    // 아이디 중복 체크
    $("#userId").blur(function() {
        var userId = $("#userId").val();
        $.ajax({
            url: "/checkUserId",
            type: "GET",
            data: { userId: $("#userId").val() },
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
    	if ($("#userId").val() == null || $("#userId").val() === "") {
            alert("ID를 먼저 입력해주세요.");
            return false; // 버튼 동작 막기		
    	}
    	$("#loadingMessage").show();
	        $.ajax({
	            url: "/sendEmailVerification",
	            type: "POST",
	            data: { email: $("#email").val(),
	            		userId: $("#userId").val()},
	            success: function(isRequested) {
	                if (isRequested) {
	                    alert("이메일 인증을 요청했습니다.\n 이메일을 확인해 주세요.");
	                    isEmailSended = true;
	                } else {
	                    alert("이메일 인증 요청에 실패했습니다.\n 잘못된 이메일 혹은 이메일이 중복되어 있지 않은지 확인하세요.");
	                    isEmailSended = false;
	                }
	            },
	            error: function() {
	                alert("이메일 인증 요청 중 오류가 발생했습니다.");
	            },
	            complete: function() {
	                // Hide loading message
	                $("#loadingMessage").hide();
	            }
	        });
    });

    // 폼 제출 전 검증
 // 폼 제출 이벤트 핸들러
    $("#registerForm").submit(function(e) {
        // 기본 제출 동작 방지
        e.preventDefault();

        // 아이디 중복 체크 및 이메일 인증 요청 상태 확인
        if (!isUserIdValid || !isEmailSended) {
            alert("아이디 중복 체크 또는 이메일 인증 요청을 완료해주세요.");
            return; // 폼 제출 중단
        }

        // 모든 검증이 통과됐다면 폼 데이터를 서버로 전송
        this.submit(); // 폼 데이터를 실제로 제출
    });
});
</script>
</body>
</html>
