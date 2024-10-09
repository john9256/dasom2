<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/faviconHeart.png" sizes="32x32">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/commonCss.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPageCss.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>메인페이지</title>
    <style>
    
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

	<div class="text-right">
		<span id="ticket" class="text-head"></span>
		    <c:if test="${checkAdmin != null && checkAdmin == 'Y'}">
		        <a href="/adminPage" class="text-head" >ADMIN</a>
		    </c:if>
		<a href="/register.user?param='y'" class="text-head" >회원 정보 수정</a>
        <!-- <a href="/test" class="text-head" >자주 묻는 질문</a> -->
        <!-- <a href="/logout" class="text-head" >로그아웃</a> -->
        <!-- <a href="/" class="text-head" >카카오 로그아웃</a> -->
        
    </div>
    
    <div class="top-text"><a href="/mainPage">다솜 소개팅</a></div>
    
    <div class="full-screen-wrapper">
	    <div class="button-container">
	        <button id="dateSelectBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">날짜 선택</button>
	        <button id="getParticipantList" type="button" class="btn btn-main" data-toggle="modal" data-target="#modalParticipantList">소개팅 현황</button>
	        <button id="matchingBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">두근두근 매칭</button>
	        <button id="matchingResultBtn" type="button" class="btn btn-main" data-toggle="modal" data-target="#modal">매치 결과</button>
	    </div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <div class="modal-title" id="modalLabel"></div>
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

<!-- 소개팅 인원 현황 모달 -->
<div class="modal fade" id="modalParticipantList" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <div class="modal-title" id="modalLabel2"></div>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div id="modalParticipantList-body">
        <!-- 동적으로 생성된 콘텐츠가 여기에 삽입됩니다. -->
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
	
    $('#getParticipantList').click(function() {
    	getParticipantList("${userId}");
    });
    
    $('#matchingBtn').click(function() {
    	matchingModalContent("${userId}");
    });
    
    $('#matchingResultBtn').click(function() {
    	matchingResultModalContent("${userId}");
    });
    
    
    
    $.ajax({
        url: "/getTicket",
        type: "POST",
        data: {
            userId: "${userId}"
        },
        success: function(data) {
            if(data.length === 0){
            	$('#ticket').text("티켓: 0");
    		}
            else{
            	$('#ticket').html("티켓: " + data.ticket);
            }
        },
        error: function() {
            // 오류 처리 (옵션)
            console.log("Error getTicket");
        }
    });
    
    
 	// 날짜 선택 모달
	function dateSelectModalContent(userId) {
		$(".modal-body").html("");
		$("#modalLabel").html("소개팅 날짜 선택");
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
             if(data.length === 0){
     			$("#modalLabel").html("진행 예정인 소개팅이 없습니다.");
     		}
             else{
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
                  
                  content += `<p class="mobile-font">`+ episode +` (`+ location +`) 
                      <button type="button" class="btn btn-info btn-sm right-button` +selectedCss+ `" id="btn_`+episode+`" onclick="toggleSelectionSchedule('${userId}', '`+episode+`')">`+buttonText+`</button>
                      </p>`;
              });
             }

             $(".modal-body").html(content);
             $("#modal").modal('show');
         },
         error: function(xhr, status, error) {
             console.error("Error: " + error);
             alert("스케줄을 불러오는데 실패했습니다.  \n 새로고침 후 이용해주세요.");
         }
     });
  }  
    
  
 // 소개팅 현황 모달
    function getParticipantList(userId) {
    // 모달 본문 초기화
    $("#modalParticipantList-body").html("");
    $("#modalLabel2").html("소개팅 현황");
    $.ajax({
        url: "/getParticipantList",
        type: "POST",
        data: {
            userId: "${userId}"
        },
        success: function(data) {
            var contentByEpisode = {};
            var nickname = "";
            $("#modalParticipantList-body").html("");
            
            if (data.length === 0) {
                $("#modalLabel2").html("현재 진행중인 소개팅이 없습니다.");
            } else {
                data.forEach(function(participant) {
                    var episode = participant.episode;
                    var sex = participant.sex;
                    var jobDivision = participant.jobDivision;
                    var year = participant.year;
                    var location = participant.location;
                    
                    if(participant.nickname != null){
                    	nickname = participant.nickname;
                    }
                    
                    if (!contentByEpisode[episode]) {
                    	contentByEpisode[episode] = { male: "", female: "", location: location };
                    }
                    
                    if (sex === "남성") {
                        contentByEpisode[episode].male += "<p class='mobile-font2'>" + year + " " + jobDivision + "</p>";
                    } else {
                        contentByEpisode[episode].female += "<p class='mobile-font2'>" + year + " " + jobDivision + "</p>";
                    }
                });
                
                var modalBodyContent = "";
                
                if(nickname != ""){
                	modalBodyContent = "<div class = 'text-head text-center notice'><img class='emoji-icon' src='/image/faviconHeart.png'>" + " 당신은 " + nickname + " 입니다.</div>";
                }
                
                for (var episode in contentByEpisode) {
                    modalBodyContent += 
                        "<div class = 'text-head text-center'>" + formatDateTime(episode) + " (" + contentByEpisode[episode].location + ")" + "</div>" +
                        "<div class='row'>" +
                            "<div class='col-md-6'>" +
                                "<div class='text-center'><img class='emoji-icon' src='/image/1F466_color.png' alt='Boy Face'>남자</div>" +
                                "<div class='text-center'>" + contentByEpisode[episode].male + "</div>" +
                            "</div>" +
                            "<div class='col-md-6'>" +
                                "<div class='text-center'><img class='emoji-icon' src='/image/1F467_color.png' alt='Girl Face'>여자</div>" +
                                "<div class='text-center'>" + contentByEpisode[episode].female + "</div>" +
                            "</div>" +
                        "</div>" +
                        "<hr>";
                }
                
                $("#modalParticipantList-body").html(modalBodyContent);
                $("#modalParticipantList").modal('show');
            }
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
            alert("데이터를 불러오는데 실패했습니다. \n새로고침 후 이용해주세요.");
        }
    });
}
 
 
 
    
    // 두근두근 매칭 모달
    function matchingModalContent(userId) {
    	$(".modal-body").html("");
    	$("#modalLabel").html("호감이 가는 이성 두명을 골라주세요~");
    	$.ajax({
            url: "/getMatchingInfo",
            type: "POST",
            data: {
                userId: userId
            },
            success: function(data) {
                var content = "";
                let nickName = "";
                let episode;
                $(".modal-body").html("");
                var userName = "${userName}";t
                
                if(data.length === 0){
        			$("#modalLabel").html("매칭은 소개팅 이후 가능합니다.");
        		}
                else{
	                data.forEach(function(matchInfo) {
	                	
	                	nickName = matchInfo.nickName;
	                	episode = matchInfo.episode;
	                	
	                    // 선택 여부에 따라 버튼 텍스트 설정
	                    var buttonText = "";
	                    var selectedCss = " ";
	                    if(matchInfo.pick != null) {
	                       buttonText = "선택함";
	                       selectedCss = ' selectedBtn';
	                    } else {
	                       buttonText = "선택";
	                    }
	                    
	                    content += `<p class="mobile-font">`+ nickName +` 
	                        <button type="button" class="btn btn-info btn-sm right-button` +selectedCss+ `" id="btn_`+nickName+`" onclick="toggleSelectionMatch('${userId}', '`+nickName+`', '`+episode+`')">`+buttonText+`</button>
	                        </p>`;
	                });
            	}
                $(".modal-body").html(content);
                $("#modal").modal('show');
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
                alert("데이터를 불러오는데 실패했습니다.  \n새로고침 후 이용해주세요.");
            }
        });
    }
    
    
 	// 매치 결과 모달
    function matchingResultModalContent(userId) {
    	$(".modal-body").html("");
    	
    	var content = "";
        let nickName = "";
        let episode;
    	
    	$.ajax({
    		// 본인을 선택 한 사람 전부
            url: "/getMatchingResultInfo2",
            type: "POST",
            data: {
                userId: userId
            },
            success: function(data) {
				if(data.length === 0){
        			$("#modalLabel").html("매치 결과가 없습니다.");
        		}
				else{
                	 $("#modalLabel").html("기다리시던 매치 결과가 나왔습니다! <br>");
                	 $("#modalLabel").append("이성에게 총 " + data.length + "표 받으셨어요! <br>");
                	 $("#modalLabel").append("서로 선택한 이성의 정보만 공개됩니다.");
            	}
				
                $.ajax({
                	// 본인과 정확히 매칭된 사람만
                    url: "/getMatchingResultInfo",
                    type: "POST",
                    data: {
                        userId: userId
                    },
                    success: function(data) {
                        if(data.length != 0){
                        	
        	                data.forEach(function(matchInfo) {
        	                	
        	                	nickName = matchInfo.nickName;
        	                	phoneNumber = matchInfo.phoneNumber;
        	                	
        	                    content += `<p class="mobile-font">`+ nickName + "님 " + formatPhoneNumber(phoneNumber) +` 
        	                        </p>`;
        	                });
                		}
                        $(".modal-body").html(content);
                        $("#modal").modal('show'); 
                    },
                    error: function(xhr, status, error) {
                        console.error("Error: " + error);
                        alert("데이터를 불러오는데 실패했습니다. \n새로고침 후 이용해주세요.");
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
                alert("데이터를 불러오는데 실패했습니다. \n새로고침 후 이용해주세요.");
            }
        });
    }
 	
    
    
});


function toggleSelectionSchedule(userId, episode) {
    // 선택된 회차의 버튼 ID를 구성
    var btnId = "btn_" + episode;
    var episodeSelected = document.getElementById(btnId).textContent === '선택';

    // 사용자에게 스케줄 선택 확인 요청
    var userConfirmed;
    	if(episodeSelected){
    		userConfirmed = confirm("소개팅 24 시간전 취소는 불가능합니다. \n소개팅을 신청하시겠습니까?");
    		}
    	else{
    		userConfirmed = confirm("소개팅을 취소하시겠습니까?");
    	}

    // 사용자가 확인을 누른 경우에만 AJAX 통신 실행
    if(userConfirmed) {
        $.ajax({
            url: "/ScheduleSelectionInsert",
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
           	    } else if (data.status == "noChance"){
           	        alert("남은 티켓이 없습니다 \n티켓을 구매셨다면 관리자에게 문의해주세요.");
           	    } else if (data.status == "full"){
           	        alert("남은 자리가 없습니다.");
           	    } else if (data.status == "continuity"){
           	        alert("소개팅 중복 매칭 방지를 위해 \n주 1회 참여 가능합니다.");
           	    } else if (data.status == "double"){
           	        alert("소개팅 중복 매칭 방지를 위해 \n하루 1회 참여 가능합니다.");
           	    } else if (data.status == "hack"){
           	        alert("잘못된 접근입니다. 새로고침 후 이용해주세요.");
           	    } else if (data.status == "expire"){
           	        alert("소개팅 하루전 취소는 불가능합니다.");
           	    } else if(data.status == "cancel") {
           	        btn.textContent = '선택';
           	        btn.className = "btn btn-info btn-sm right-button"; // 기본 스타일로 복귀
           	        alert("소개팅 신청을 취소했습니다.");
           	    } else if (data.status == "duplicate"){
           	    	userConfirmDuplicate = confirm("이전에 함께 참여 했던 이성이 " + data.duplicateCount + " 명 존재합니다. \n그럼에도 참여를 하시겠습니까?");
           	    		if(userConfirmDuplicate){
           	    			$.ajax({
           	    	            url: "/ScheduleSelectionDuplicateInsert",
           	    	            type: "POST",
           	    	            data: {
           	    	                userId: userId,
           	    	                episode: episode,
           	    	                episodeSelected: episodeSelected
           	    	            },
           	    	            success: function(data) {
           	    	            	btn = document.getElementById(btnId);
           	    	                // 성공 시 버튼 텍스트 업데이트
           	    	            	 if(data.status == "complete") {
	           	    	           	        btn.textContent = '선택함';
	           	    	           	        btn.className = "btn btn-info btn-sm right-button selectedBtn"; // 선택된 스타일 적용
	           	    	           	        alert("소개팅 신청에 성공했습니다.");
           	    	           	    	}
           	    	            	else {
           	    	           	        alert("소개팅 신청에 실패했습니다. \n새로고침 후 이용해주세요.");
           	    	           	    }
           	    	            },
           	    	            error: function(xhr, status, error) {
           	    	                console.error("Selection update failed: " + error);
           	    	                alert("소개팅 신청에 실패했습니다. \n새로고침 후 이용해주세요.");
           	    	            }
           	    	       })
           	    		}
           	    } else {
           	        alert("소개팅 신청에 실패했습니다. \n새로고침 후 이용해주세요.");
           	    }
            },
            error: function(xhr, status, error) {
                console.error("Selection update failed: " + error);
                alert("소개팅 신청에 실패했습니다. \n새로고침 후 이용해주세요.");
            }
        });
    }
}



function toggleSelectionMatch(userId, nickName, episode) {
    // 선택된 회차의 버튼 ID를 구성
    var btnId = "btn_" + nickName;
    var nickNameSelected = document.getElementById(btnId).textContent === '선택';

    // 매칭하기
    var userConfirmed;
    	if(nickNameSelected){
    		userConfirmed = confirm("매칭 선택은 취소가 불가능합니다. \n선택 하시겠습니까?");
    		}
    	else{
    		alert("매칭 취소는 불가능합니다.");
    		userConfirmed == false;
    		// userConfirmed = confirm("선택을 취소 하시겠습니까?");
    	}

    // 사용자가 확인을 누른 경우에만 AJAX 통신 실행
    if(userConfirmed) {
        $.ajax({
            url: "/matchSelection",
            type: "POST",
            data: {
                userId: userId,
                episode: episode,
                nickName : nickName,
                nickNameSelected: nickNameSelected
            },
            success: function(data) {
            	var btn = document.getElementById(btnId);
                // 성공 시 버튼 텍스트 업데이트
            	 if(data.status == "complete") {
           	        btn.textContent = '선택함';
           	        btn.className = "btn btn-info btn-sm right-button selectedBtn"; // 선택된 스타일 적용
           	        //alert("선택을 완료했습니다.");
           	    } else if (data.status == "full"){
           	        alert("이미 두명을 선택했습니다.");
           	    } else if (data.status == "hack"){
           	        alert("잘못된 접근입니다.");
           	    }else if(data.status == "cancel") {
           	        btn.textContent = '선택';
           	        btn.className = "btn btn-info btn-sm right-button"; // 기본 스타일로 복귀
           	        // alert("선택을 취소했습니다.");
           	    } else {
           	        alert("선택을 실패했습니다. \n새로고침 후 이용해주세요.");
           	    }
            },
            error: function(xhr, status, error) {
                console.error("Selection update failed: " + error);
                alert("선택을 실패했습니다.  \n새로고침 후 이용해주세요.");
            }
        });
    }
}


// 날짜 형식 변환 함수
function formatDateTime(input) {
    // Date 객체로 변환
    var date = new Date(input);

    // 요일 배열
    var days = ['(일)', '(월)', '(화)', '(수)', '(목)', '(금)', '(토)'];
    var dayName = days[date.getDay()];

    // 년, 월, 일, 시간, 분 추출
    var year = String(date.getFullYear()).slice(2); // '2024' -> '24'
    var month = date.getMonth() + 1; // 0부터 시작하므로 +1 필요
    var day = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();

    // 오전/오후 결정
    var ampm = hour >= 12 ? '오후' : '오전';
    hour = hour % 12;
    hour = hour ? hour : 12; // 0시를 12시로 변환

    // 포맷된 문자열 반환
    return year + "년 " + month + "월 " + day + "일 " + dayName + " " + ampm + " " + hour + "시";
}

function formatPhoneNumber(phoneNumber) {
    if (phoneNumber.length === 11) {
        // 11자리 숫자일 경우: 010-1234-1234
        return phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    } else if (phoneNumber.length === 10) {
        // 10자리 숫자일 경우: 010-123-1234
        return phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
    } else {
        // 형식이 맞지 않는 경우 그대로 반환
        return phoneNumber;
    }
}

</script>

</body>
</html>
