<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/faviconHeart.png" sizes="32x32">
    <!-- <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
     -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    
	<title>Admin Page</title>
    <style>
        .container-fluid {
            width: 100%;
            padding-left: 10px;
            padding-right: 10px;
        }
        .chance-buttons {
            display: inline-flex;
            margin-left: 10px;
        }
        .chance-buttons button {
            margin-left: 5px;
        }
        .delete-button {
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <h2>Admin Page</h2>
        <div class="btn-group mb-4" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-primary" id="userInfoBtn">유저 정보</button>
            <button type="button" class="btn btn-secondary" id="scheduleInfoBtn">스케줄 정보</button>
            <button type="button" class="btn btn-success" id="matchingInfoBtn">매칭 정보</button>
            <button type="button" class="btn btn-warning" id="scheduleBtn">스케줄 관리</button>
            <button type="button" class="btn btn-danger"  id="logBtn">일반 로그</button>
            <button type="button" class="btn btn-danger"  id="errorLogBtn">에러 로그</button>
        </div>
        <div id="dataTable" class="table-responsive">
            <table id="adminTable" class="display" style="width:100%">
                <thead>
                    <tr>
                        <!-- 테이블 헤더는 자바스크립트에서 동적으로 설정됩니다 -->
                    </tr>
                </thead>
                <tbody>
                    <!-- 테이블 데이터는 자바스크립트에서 동적으로 설정됩니다 -->
                </tbody>
            </table>
        </div>
        
    </div>
    
	
	<!-- Modal -->
	<div class="modal fade" id="addScheduleModal" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="exampleModalLabel">Add New Schedule</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <form id="addScheduleForm">
	                    <div class="form-group">
	                        <label for="newEpisode">Episode</label>
	                        <input type="text" class="form-control" id="newEpisode" required>
	                    </div>
	                    <!-- <div class="form-group">
	                        <label for="newCompleteFlag">Complete Flag</label>
	                        <input type="text" class="form-control" id="newCompleteFlag" required>
	                    </div> -->
	                    <div class="form-group">
	                        <label for="newHeadCount">Head Count</label>
	                        <input type="number" class="form-control" id="newHeadCount" required>
	                    </div>
	                    <div class="form-group">
	                        <label for="newLocation">Location</label>
	                        <input type="text" class="form-control" id="newLocation" required>
	                    </div>
	                </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	                <button type="button" class="btn btn-primary" id="saveScheduleBtn">Save</button>
	            </div>
	        </div>
	    </div>
	</div>
	
    <script>
    $(document).ready(function() {
        var userInfoColumns = ["userId", "userName", "sex", "height", "birthday", "jobDivision", "residence", "passFlag", "chance", "phoneNumber"];
        var scheduleInfoColumns = ["episode", "userId", "userName", "location", "sex", "birthday", "jobDivision"];
        var matchingInfoColumns = ["episode", "userId", "userName", "nickName", "pick", "pickedUserId",  "createtime"];
        var scheduleColumns = ["episode", "completeFlag", "headCount", "location"];
        var logColumns = ["userId", "userName", "sex", "phoneNumber", "target", "logType", "adminDivision", "createTime"];
        var errorLogColumns = ["methodName", "userId", "errorMessage", "createTime"];

        var episodeColors = {};
        var colors = ['#FF000033', '#FFA50033', '#FFFF0033', '#00800033', '#0000FF33'];
        var colorIndex = 0;

        function getNextColor() {
            var color = colors[colorIndex];
            colorIndex = (colorIndex + 1) % colors.length;
            return color;
        }
		
       
        
        function createTable(data, columnOrder, isScheduleTable = false) {
            // 기존 DataTable 삭제
            if ($.fn.DataTable.isDataTable('#adminTable')) {
                $('#adminTable').DataTable().clear().destroy();
            }

            // 테이블 헤더와 본문 비우기
            $('#adminTable thead').empty();
            $('#adminTable tbody').empty();
            $('#addScheduleBtn').remove();
            $('#data-inform').remove();
            
         	// 스케줄 관리 탭인 경우
            if (isScheduleTable) {
                var scheduleBtnContent = `
                    <div id="scheduleControl" class="mt-4">
                        <button type="button" class="btn btn-primary" id="addScheduleBtn">스케줄 추가</button>
                    </div>`;
                $('#dataTable').append(scheduleBtnContent);
            }
         	
            $("#addScheduleBtn").click(function() {
                $('#addScheduleModal').modal('show');
            });
            
            // 데이터가 없는 경우
            if (!data || data.length === 0) {
            	var dataContent = `<p id ="data-inform">No data available</p>`;
                $('#dataTable').append(dataContent);
                return;
            }

            // 테이블 헤더 생성
            var tableHeader = '<tr>';
            for (var i = 0; i < columnOrder.length; i++) {
                tableHeader += '<th>' + columnOrder[i] + '</th>';
            }
            tableHeader += '<th>Action</th>'; // Action 헤더 추가
            tableHeader += '</tr>';
            $('#adminTable thead').html(tableHeader);

            // 테이블 데이터 생성
            var tableData = data.map(function(row) {
                var rowData = columnOrder.map(function(key) {
                    if (key === 'chance') {
                        return (row[key] !== undefined ? row[key] : '') +
                            '<div class="chance-buttons">' +
                            '<button class="btn btn-sm btn-success increase-chance" data-userid="' + row['userId'] + '">+</button>' +
                            '<button class="btn btn-sm btn-danger decrease-chance" data-userid="' + row['userId'] + '">-</button>' +
                            '</div>';
                    } else if (key === 'passFlag') {
                        return (row[key] !== undefined ? row[key] : '') +
                            '<div class="chance-buttons">' +
                            '<button class="btn btn-sm btn-success change-passFlag" data-passflag="' + row['passFlag'] + '" data-userid="' + row['userId'] + '">o</button>' +
                            '</div>';
                    } else {
                        return row[key] !== undefined ? row[key] : '';
                    }
                });

                // 삭제 버튼 추가 (스케줄 관리 테이블일 때만 추가)
                if (isScheduleTable) {
                    rowData.push('<button class="btn btn-sm btn-danger delete-schedule" data-episode="' + row['episode'] + '">삭제</button>');
                } else {
                    rowData.push(''); // 빈 셀 추가
                }

                return rowData;
            });

            // DataTable 초기화
            $('#adminTable').DataTable({
                destroy: true,
                data: tableData,
                columns: columnOrder.map(function(key) {
                    return { title: key };
                }).concat([{ title: 'Action' }]), // Action 컬럼 추가
                rowCallback: function(row, data, index) {
                    var episode = data[0];
                    if (!episodeColors[episode]) {
                        episodeColors[episode] = getNextColor();
                    }
                    $(row).css('background-color', episodeColors[episode]);
                },
                initComplete: function() {
                    // chance 증가/감소 버튼 이벤트 핸들러 추가
                    $('.increase-chance').on('click', function() {
                        var userId = $(this).data('userid');
                        $.ajax({
                            url: '/increaseChance',
                            type: 'POST',
                            data: { userId: userId },
                            success: function(response) {
                                if(response.status == "increase"){
                                    var cell = $(this).closest('td');
                                    var chanceValue = parseInt(cell.text(), 10);
                                    cell.contents().first()[0].textContent = chanceValue + 1;
                                    alert(userId + ' 유저의 티켓이 증가했습니다.');
                                }
                            }.bind(this),
                            error: function(xhr, status, error) {
                                console.error("Selection update failed: " + error);
                                alert("티켓 증가처리에 실패했습니다.");
                            }
                        });
                    });

                    $('.decrease-chance').on('click', function() {
                        var userId = $(this).data('userid');
                        $.ajax({
                            url: '/decreaseChance',
                            type: 'POST',
                            data: { userId: userId },
                            success: function(response) {
                                if(response.status == "decrease"){
                                    var cell = $(this).closest('td');
                                    var chanceValue = parseInt(cell.text(), 10);
                                    cell.contents().first()[0].textContent = chanceValue - 1;
                                    alert(userId + ' 유저의 티켓이 감소했습니다.');
                                }
                            }.bind(this),
                            error: function(xhr, status, error) {
                                console.error("Selection update failed: " + error);
                                alert("티켓 감소처리에 실패했습니다.");
                            }
                        });
                    });

                    $('.change-passFlag').on('click', function() {
                        var userId = $(this).data('userid');
                        var button = $(this); // 클릭한 버튼 요소를 참조
                        var currentFlag = button.data('passflag'); // 현재 data-passflag 값을 가져옴
                        var newPassFlag;

                        // 화면에 표시된 텍스트 값과 data-passflag 속성 값을 모두 변경
                        if (currentFlag === "Y") {
                            newPassFlag = "N";
                        } else {
                            newPassFlag = "Y";
                        }

                        $.ajax({
                            url: '/changePassFlag',
                            type: 'POST',
                            data: { userId: userId, passFlag: currentFlag }, // 반전된 값을 서버에 전송
                            success: function(response) {
                                var cell = button.closest('td');
                                cell.contents().first()[0].textContent = newPassFlag; // 화면에서 값 반영
                                button.data('passflag', newPassFlag); // data-passflag 속성 값 업데이트
                                alert(userId + ' 유저의 패스 플래그가 변경되었습니다.');
                            }
                        });
                    });

                    // 삭제 버튼 이벤트 핸들러 추가 (스케줄 관리 테이블일 때만 추가)
                    if (isScheduleTable) {
                        $('.delete-schedule').on('click', function() {
                            var episode = $(this).data('episode');
                            if (confirm(episode + ' 스케줄을 삭제하시겠습니까?')) {
                                $.ajax({
                                    url: '/deleteSchedule',
                                    type: 'POST',
                                    data: { episode: episode },
                                    success: function(response) {
                                        if(response.status == "complete"){
                                            alert('스케줄이 삭제되었습니다.');
                                            $('#scheduleBtn').click(); // 스케줄 리스트 새로고침
                                        } else {
                                            alert('스케줄 삭제에 실패했습니다.');
                                        }
                                    },
                                    error: function(xhr, status, error) {
                                        console.error("Schedule deletion failed: " + error);
                                        alert("스케줄 삭제에 실패했습니다.");
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }

        $('#userInfoBtn').click(function() {
            $.post('/getUserInfoAdmin', function(data) {
                createTable(data, userInfoColumns);
            });
        });

        $('#scheduleInfoBtn').click(function() {
            $.post('/getScheduleInfoAdmin', function(data) {
                createTable(data, scheduleInfoColumns);
            });
        });

        $('#matchingInfoBtn').click(function() {
            $.post('/getMatchingInfoAdmin', function(data) {
                createTable(data, matchingInfoColumns);
            });
        });

        $('#scheduleBtn').click(function() {
            $.post('/getScheduleAdmin', function(data) {
                createTable(data, scheduleColumns, true); // 스케줄 테이블임을 나타내는 플래그 전달
            });
        });
        
        $('#logBtn').click(function() {
            $.post('/getLogAdmin', function(data) {
                createTable(data, logColumns); // 스케줄 테이블임을 나타내는 플래그 전달
            });
        });
        
        $('#errorLogBtn').click(function() {
            $.post('/getErrorLogAdmin', function(data) {
                createTable(data, errorLogColumns); // 스케줄 테이블임을 나타내는 플래그 전달
            });
        });
        
        // 스케줄 추가
        $('#saveScheduleBtn').click(function() {
            var newEpisode = $('#newEpisode').val();
            // var newCompleteFlag = $('#newCompleteFlag').val();
            var newHeadCount = $('#newHeadCount').val();
            var newLocation = $('#newLocation').val();

            if (newEpisode && newHeadCount && newLocation) {
                $.ajax({
                    url: '/addSchedule',
                    type: 'POST',
                    data: { episode: newEpisode, headCount: newHeadCount, location: newLocation },
                    success: function(response) {
                        if(response.status == "complete"){
                            alert('스케줄이 추가되었습니다.');
                            $('#scheduleBtn').click(); // 스케줄 리스트 새로고침
                        } else {
                            alert('스케줄 추가에 실패했습니다.');
                        }
                        $('#addScheduleModal').modal('hide');
                    },
                    error: function(xhr, status, error) {
                        console.error("Schedule addition failed: " + error);
                        alert("스케줄 추가에 실패했습니다.");
                        $('#addScheduleModal').modal('hide');
                    }
                });
            } else {
                alert("모든 필드를 입력해주세요.");
            }
        });
        
    });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
