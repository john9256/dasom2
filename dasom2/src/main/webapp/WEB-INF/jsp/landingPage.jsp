<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="/image/faviconHeart.png" sizes="32x32">
    <title>다솜 소개팅</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/commonCss.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPageCss.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
    <style>
        body {
            background-color: #ffc0cb;
            transition: background-color 1s ease-in-out;
        }

        .jumbotron {
            background-color: #ffc0cb;
            border-color: #e687a6;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 80vh;
        }

        .btn-primary {
            background-color: #e687a6;
            border-color: #e687a6;
            margin-top: 10px;
            width: 25%;
        }

        .btn-primary:hover {
            background-color: #d17285;
            border-color: #d17285;
        }

        /* 추가된 CSS 애니메이션 효과 */
        @keyframes slideIn {
            0% {
                transform: translateY(100%);
                opacity: 0;
            }
            100% {
                transform: translateY(0);
                opacity: 1;
            }
        }

        .animate-slideIn {
            animation: slideIn 1s ease-in-out;
        }
        #text-rights-foot{
        	text-align: center;
        	margin-top:50px;
        }
        
        @media (max-width: 800px) {	 /* 핸드폰 화면 크기에 따라 조정 */
	    	.btn-primary {
	        	width: 80%;
	        	margin-top : 0px;
	    	}
	    	
	    	.jumbotron {
	            background-color: #ffc0cb;
	            border-color: #e687a6;
	            display: flex;
	            flex-direction: column;
	            justify-content: center;
	            align-items: center;
	            height: 80vh;
	            margin-top : 50px;
	        }
		}
        
    </style>
</head>

<body>

    <div class="jumbotron text-center" style="height: 80vh;">
        <h1 id="welcomeText" class="display-4 animate-slideIn">다솜 소개팅</h1>
        <p id="welcomeText" class="lead animate-slideIn">여러분의 건강한 사랑을 응원합니다</p> 
        <hr class="my-4">
        
        <button id="getParticipantList" type="button" class="btn btn-primary btn-lg animate-slideIn" data-toggle="modal" data-target="#modalParticipantList">소개팅 현황</button>
        <br>        
        <a class="btn btn-primary btn-lg animate-slideIn" href="/login/kakao" role="button">카카오로 로그인</a>        
                
        <!-- 아래는 기존방법 -->
        <!-- <a class="btn btn-primary btn-lg animate-slideIn" href="https://kauth.kakao.com/oauth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=talk_message" role="button">카카오로 로그인</a> -->
        <!-- <a class="btn btn-primary btn-lg animate-slideIn" href="/loginPage" role="button">로그인</a> -->
        <!-- <a class="btn btn-primary btn-lg animate-slideIn" href="register.user" role="button">회원가입</a> -->
        
    </div>
	
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
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<footer>
    	<p id ="text-rights-foot">&copy; 2024 Dasom meeting. All rights reserved.</p>
	</footer>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-color/2.1.2/jquery.color.min.js"></script>
    <script>
        $(document).ready(function() {
            animateText();
        });

        function animateText() {
            $('.animate-slideIn').each(function(index) {
                $(this).delay(500 * index).queue(function(next) {
                    $(this).addClass('animated').css('opacity', 0);
                    next();
                }).animate({opacity: 1}, 1000);
            });
        }
        
        $('#getParticipantList').click(function() {
        	getParticipantList();
        });
        
     // 소개팅 현황 모달
        function getParticipantList() {
        // 모달 본문 초기화
        $("#modalParticipantList-body").html("");
        $("#modalLabel2").html("소개팅 현황");
        $.ajax({
            url: "/getParticipantListIndexPage",
            type: "POST",
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
                        var height = participant.height;
                        
                        if(participant.nickname != null){
                        	nickname = participant.nickname;
                        }
                        else{
                        	nickname = "";
                        }
                        
                        if (!contentByEpisode[episode]) {
                        	contentByEpisode[episode] = { male: "", female: "", location: location, nickname: nickname };
                        }
                        
                        if (sex === "남성") {
                            contentByEpisode[episode].male += "<p class='mobile-font2'>" + year + "/" + height + "/" + jobDivision + "</p>";
                        } else {
                            contentByEpisode[episode].female += "<p class='mobile-font2'>" + year + "/" + height + "/" + jobDivision + "</p>";
                        }
                    });
                    
                    var modalBodyContent = "";
                    
                    for (var episode in contentByEpisode) {
                    	nickname = "";
                    	if(contentByEpisode[episode].nickname != ""){
                    		nickname = contentByEpisode[episode].nickname;
                    	}
                        modalBodyContent += 
                            "<div class = 'text-head text-center'>" + formatDateTime(episode) + " (" + contentByEpisode[episode].location + ")" + "</div>"; 
                            
                            if(nickname != ""){
                            	modalBodyContent += "<div class = 'text-head text-center notice'><img class='emoji-icon' src='/image/faviconHeart.png'>" + " 당신은 " + nickname + " 입니다.</div>"; 
                            }
                            
                        modalBodyContent +=
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
     
       
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    <!-- <footer style="position: fixed; bottom: 0; width: 100%;">
        <p style="text-align: center; margin-top: 20px;">COPYRIGHT DASOM SOGAETING SERVICE. ALL RIGHTS RESERVED</p>
    </footer> -->

</body>

</html>
