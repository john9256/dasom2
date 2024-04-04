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
    <title>메인페이지</title>
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
    <div class="full-screen-wrapper">
    <div class="button-container">
        <button id="dateSelectBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">날짜 선택</button>
        <button id="statusViewBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">현황 화면</button>
        <button id="matchingBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">두근두근 매칭</button>
    </div>
</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabel">소개팅 날짜 선택</h5>
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
$(document).ready(function() {
	
    $('#dateSelectBtn').click(function() {
    	dateSelectModalContent("${userId}");
    });
	
    $('#statusViewBtn').click(function() {
    	matchingModalContent("${userId}");
    });
    
    $('#matchingBtn').click(function() {
    	matchingModalContent("${userId}");
    });
    
    
    function matchingModalContent(userId) {
    	$(".modal-body").html("");
    	$.ajax({
            url: "/getMeetingSchedule",
            type: "POST",
            data: {
                userId: userId
            },
            success: function(data) {
                var content = "";
                var episode;
                var location;
                //var episode = "";
                $(".modal-body").html("");
                data.forEach(function(schedule) {
                	episode = schedule.episode;
                	location = schedule.location;
                	
                    // 선택 여부에 따라 버튼 텍스트 설정
                    var buttonText = "";
                    var selectedCss = " ";
                    if(schedule.userId === userId) {
                        buttonText = "선택함";
                        selectedCss = ' selectedBtn';
                    } else {
                        buttonText = "선택";
                    }
                    var buttonClass = schedule.userId === userId ? "btn-selected" : "";
                    
                    content += `<p>`+ episode +` (`+ location +`) 
                        <button type="button" class="btn btn-info btn-sm right-button` +selectedCss+ `" id="btn_`+episode+`" onclick="toggleSelection('${userId}', '`+episode+`')">`+buttonText+`</button>
                        </p>`;
                });

                $(".modal-body").html(content);
                $("#modal").modal('show');
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
                alert("스케줄을 불러오는데 실패했습니다.");
            }
        });
    }
    
 	function dateSelectModalContent(userId) {
 		$(".modal-body").html("");
      $.ajax({
          url: "/getMeetingSchedule",
          type: "POST",
          data: {
              userId: userId
          },
          success: function(data) {
              var content = "";
              var episode;
              var location;
              //var episode = "";
              
              data.forEach(function(schedule) {
              	episode = schedule.episode;
              	location = schedule.location;
              	
                  // 선택 여부에 따라 버튼 텍스트 설정
                  var buttonText = "";
                  var selectedCss = " ";
                  if(schedule.userId === userId) {
                      buttonText = "선택함";
                      selectedCss = ' selectedBtn';
                  } else {
                      buttonText = "선택";
                  }
                  var buttonClass = schedule.userId === userId ? "btn-selected" : "";
                  
                  content += `<p>`+ episode +` (`+ location +`) 
                      <button type="button" class="btn btn-info btn-sm right-button` +selectedCss+ `" id="btn_`+episode+`" onclick="toggleSelection('${userId}', '`+episode+`')">`+buttonText+`</button>
                      </p>`;
              });

              $(".modal-body").html(content);
              $("#modal").modal('show');
          },
          error: function(xhr, status, error) {
              console.error("Error: " + error);
              alert("스케줄을 불러오는데 실패했습니다.");
          }
      });
  }
    
});

function toggleSelection(userId, episode) {
    // 선택된 회차의 버튼 ID를 구성
    var btnId = "btn_" + episode;
    var episodeSelected = document.getElementById(btnId).textContent === '선택';

    // 사용자에게 스케줄 선택 확인 요청
    var userConfirmed;
    	if(episodeSelected){
    		userConfirmed = confirm("소개팅을 신청하시겠습니까?");
    		}
    	else{
    		userConfirmed = confirm("소개팅을 취소하시겠습니까?");
    	}

    // 사용자가 확인을 누른 경우에만 AJAX 통신 실행
    if(userConfirmed) {
        $.ajax({
            url: "/ScheduleSelection",
            type: "POST",
            data: {
                userId: userId,
                episode: episode,
                episodeSelected: episodeSelected
            },
            success: function(data) {
            	var btn = document.getElementById(btnId);
                // 성공 시 버튼 텍스트 업데이트
            	 if(data.status == "complete") {
           	        btn.textContent = '선택함';
           	        btn.className = "btn btn-info btn-sm right-button selectedBtn"; // 선택된 스타일 적용
           	        alert("소개팅 신청에 성공했습니다.");
           	    } else if (data.status == "full"){
           	        alert("남은 자리가 없습니다.");
           	    } else if(data.status == "cancel") {
           	        btn.textContent = '선택';
           	        btn.className = "btn btn-info btn-sm right-button"; // 기본 스타일로 복귀
           	        alert("소개팅 신청을 취소했습니다.");
           	    } else {
           	        alert("소개팅 신청에 실패했습니다.");
           	    }
            },
            error: function(xhr, status, error) {
                console.error("Selection update failed: " + error);
                alert("소개팅 신청에 실패했습니다.");
            }
        });
    }
}

</script>

</body>
</html>


