<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
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
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <h2>Admin Page</h2>
        <div class="btn-group mb-4" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-primary" id="userInfoBtn">유저정보</button>
            <button type="button" class="btn btn-secondary" id="scheduleInfoBtn">스케줄 정보</button>
            <button type="button" class="btn btn-success" id="matchingInfoBtn">매칭 정보</button>
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

    <script>
    $(document).ready(function() {
        var userInfoColumns = ["userId", "userName", "sex", "height", "birthday", "jobDivision", "residence", "passFlag", "chance", "phoneNumber"];
        var scheduleInfoColumns = ["episode", "userId", "userName", "location", "sex", "birthday", "jobDivision"];
        var matchingInfoColumns = ["episode", "userId", "pick", "pickedUserId",  "createtime"];

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

        function createTable(data, columnOrder) {
            // 기존 DataTable 삭제
            if ($.fn.DataTable.isDataTable('#adminTable')) {
                $('#adminTable').DataTable().clear().destroy();
            }

            // 테이블 헤더와 본문 비우기
            $('#adminTable thead').empty();
            $('#adminTable tbody').empty();

            // 데이터가 없는 경우
            if (!data || data.length === 0) {
                $('#dataTable').html('<p>No data available</p>');
                return;
            }

            // 테이블 헤더 생성
            var tableHeader = '<tr>';
            for (var i = 0; i < columnOrder.length; i++) {
                tableHeader += '<th>' + columnOrder[i] + '</th>';
            }
            tableHeader += '</tr>';
            $('#adminTable thead').html(tableHeader);

            // 테이블 데이터 생성
            var tableData = data.map(function(row) {
                return columnOrder.map(function(key) {
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
            });

            // DataTable 초기화
            $('#adminTable').DataTable({
                destroy: true,
                data: tableData,
                columns: columnOrder.map(function(key) {
                    return { title: key };
                }),
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
                        var passFlag = $(this).data('passflag');
                        
                        if (passFlag === "Y") {
                            newPassFlag = "N";
                        } else {
                            newPassFlag = "Y";
                        }
                        $.ajax({
                            url: '/changePassFlag',
                            type: 'POST',
                            data: { userId: userId, passFlag: passFlag },
                            success: function(response) {
                                var cell = $(this).closest('td');
                                cell.contents().first()[0].textContent = newPassFlag;
                                alert(userId + ' 유저의 패스 플래그가 변경되었습니다.');
                            }.bind(this)
                        });
                    });
                }
            });
        }
    });
</script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
