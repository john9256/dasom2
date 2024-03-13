<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <title>Dashboard</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!-- <script src="dashboard.js"></script>  -->
    
</head>
<body>
<div class="container mt-3">
    <h2>대시보드</h2>
    <div class="btn-group" role="group">
        <button id="dateSelectBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal">날짜 선택</button>
        <button id="statusViewBtn" type="button" class="btn btn-success" data-toggle="modal" data-target="#modal">현황 화면</button>
        <button id="matchingBtn" type="button" class="btn btn-info" data-toggle="modal" data-target="#modal">두근두근 매칭</button>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabel">모달 제목</h5>
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


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $('#dateSelectBtn').click(function() {
    	dateSelectModalContent('dateSelect');
    });

    $('#statusViewBtn').click(function() {
    	statusModalContent('statusView');
    });

    $('#matchingBtn').click(function() {
    	matchingModalContent('matching');
    });

    function dateSelectModalContent() {
        $.ajax({
            url: "/getMeetingSchedule", // 서버에 매칭 정보를 요청할 URL
            type: "POST", // HTTP 메소드
            data:{
            	userId: ${userId}
            },
            success: function(data) {
                var content = "";
                // 서버로부터 받은 데이터(매칭 정보)를 반복 처리
                data.forEach(function(schedule) {
                    // 매칭 정보를 사용해 HTML 컨텐츠 생성
                    content += `<p>${schedule.episode}
                    <button type="button" class="btn btn-info btn-sm" id="btn_${schedule.episode}" onclick="toggleSelection('${schedule.episode}')">선택</button>
                    </p>`;
                });
                // 생성된 HTML 컨텐츠를 모달의 바디에 삽입
                $(".modal-body").html(content);
                // 모달 창 표시
                $("#matchModal").modal('show');
            },
            error: function(xhr, status, error) {
                // 오류 처리 로직
                console.error("Error: " + error);
            }
        });
    }
});

function toggleSelection(userId) {
    const index = selectedMatches.indexOf(userId);
    if (index > -1) {
        // 이미 선택된 항목이면 선택 해제
        selectedMatches.splice(index, 1);
        document.getElementById(`btn_${userId}`).textContent = '상세 보기';
    } else {
        if (selectedMatches.length >= 2) {
            // 선택된 항목이 이미 두 개인 경우 경고 메시지 표시
            alert('두 개까지만 선택할 수 있습니다.');
        } else {
            // 새로운 항목 선택
            selectedMatches.push(userId);
            document.getElementById(`btn_${userId}`).textContent = '선택 함';
        }
    }
}
</script>

</body>
</html>


