<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>소개팅 준비하기</title>
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
                    <form id="registerForm" action="/register" method="post" enctype="multipart/form-data">
                        
                        <div class="form-group">
                            <label for="userName">이름:</label>
                            <input type="text" class="form-control" name="userName" id="userName" required>
                            <div class="invalid-feedback">이름은 다섯 글자 이내로 입력해주세요.</div>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">전화번호:</label>
                            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="숫자만 입력해주세요" required>
                            <div class="invalid-feedback">전화번호는 20 글자 이내로 입력해주세요.</div>
                        </div>
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
                                <% for (int height = 150; height <= 200; height++) { %>
                                    <option value="<%= height %>"><%= height %>cm</option>
                                <% } %>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="jobDivision">직업:</label>
                            <input type="text" class="form-control" name="jobDivision" id="jobDivision" placeholder="대기업 IT 개발자, 중견기업 인사팀, 대학원생" required>
                            <div class="invalid-feedback">직업은 20 글자 이내로 입력해주세요.</div>
                        </div>
                        <div class="form-group">
                            <label for="residence">거주지:</label>
                            <select class="form-control" name="residence" id="residence" required>
                                <option value="">거주지를 선택해주세요</option>
                                <!-- Dynamic options should be loaded here -->
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">회원가입</button>
                        <a href="/mainPage" class="btn btn-primary">취소</a>
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
	
	var isUpdate = "${isUpdate}";
    // 거주지 데이터 로드
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
            });
        }
    });
	
 // 업데이트 모드 확인 및 데이터 로드
    if(isUpdate == "Y") {
        $.ajax({
            type: 'GET',
            url: '/getUserInfoForUpdate',
            success: function(data) {
                // 데이터를 각각의 필드에 설정
                if (data.userName) {
                    $("#userName").val(data.userName);
                }
                if (data.phoneNumber) {
                    $("#phoneNumber").val(data.phoneNumber);
                }
                if (data.height) {
                    $("#height").val(data.height);
                }
                if (data.jobDivision) {
                    $("#jobDivision").val(data.jobDivision);
                }
            },
            error: function() {
                // 오류 처리 (옵션)
                console.error("Error loading user data");
            }
        });
    }

    
    // 폼 제출 전 검증
    $("#registerForm").submit(function(e) {
        // 기본 제출 동작 방지
        e.preventDefault();

        // 입력값 검증
        let isValid = true;

        // 이름 검증
        const userName = $("#userName").val();
        if (userName.length > 5) {
            $("#userName").addClass("is-invalid");
            isValid = false;
        } else {
            $("#userName").removeClass("is-invalid");
        }

        // 전화번호 검증
        const phoneNumber = $("#phoneNumber").val();
        if (phoneNumber.length > 20) {
            $("#phoneNumber").addClass("is-invalid");
            isValid = false;
        } else {
            $("#phoneNumber").removeClass("is-invalid");
        }

        // 직업 검증
        const jobDivision = $("#jobDivision").val();
        if (jobDivision.length > 20) {
            $("#jobDivision").addClass("is-invalid");
            isValid = false;
        } else {
            $("#jobDivision").removeClass("is-invalid");
        }

        // 모든 검증이 통과되었을 때 폼 데이터 제출
        if (isValid) {
            this.submit(); // 폼 데이터를 실제로 제출
        }
    });
});
</script>
</body>
</html>
