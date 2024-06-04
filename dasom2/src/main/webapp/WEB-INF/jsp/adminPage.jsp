<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        .container-fluid {
            width: 100%;
            padding-left: 10px;
            padding-right: 10px;
        }
        .filter-input {
            width: 100px;
        }
        .table-responsive {
            margin-left: 10px;
            margin-right: 10px;
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
            <!-- 테이블이 여기에 동적으로 삽입됩니다 -->
        </div>
    </div>

    <script>
    $(document).ready(function() {
        const userInfoColumns = ["userId", "userName", "sex", "height", "birthday", "jobDivision", "residence", "passFlag", "chance", "phoneNumber"];
        const scheduleInfoColumns = ["episode", "userId", "userName", "location", "sex", "birthday", "jobDivision"];
        const matchingInfoColumns = ["userId", "pick", "pickedUserId", "episode", "createtime"];

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
            if (!data || data.length === 0) {
                $('#dataTable').html('<p>No data available</p>');
                return;
            }

            let table = '<table class="table table-bordered table-striped">';
            table += '<thead class="thead-dark"><tr>';
            
            // 테이블 헤더 및 필터 입력 상자 생성
            columnOrder.forEach(function(key) {
                table += '<th>' + key + '<br><input type="text" class="filter-input form-control" data-column="' + key + '"></th>';
            });
            table += '</tr></thead><tbody>';

            // 테이블 바디 생성
            data.forEach(function(row) {
                table += '<tr>';
                columnOrder.forEach(function(key) {
                    if (key === 'chance') {
                        table += '<td>' + (row[key] !== undefined ? row[key] : '') +
                            '<div class="chance-buttons">' +
                            '<button class="btn btn-sm btn-success increase-chance" data-userid="' + row['userId'] + '">+</button>' +
                            '<button class="btn btn-sm btn-danger decrease-chance" data-userid="' + row['userId'] + '">-</button>' +
                            '</div></td>';
                    } else {
                        table += '<td>' + (row[key] !== undefined ? row[key] : '') + '</td>';
                    }
                });
                table += '</tr>';
            });
            table += '</tbody></table>';

            $('#dataTable').html(table);

            // 필터 기능 추가
            $('.filter-input').on('keyup', function() {
                var column = $(this).data('column');
                var value = $(this).val().toLowerCase();
                filterTable(column, value);
            });

            // chance 증가/감소 버튼 이벤트 핸들러 추가
            $('.increase-chance').on('click', function() {
                var userId = $(this).data('userid');
                $.ajax({
                    url: '/increaseChance',
                    type: 'POST',
                    data: { userId: userId },
                    success: function(response) {
                        alert('Chance increased for user ' + userId);
                        // 성공 시, 테이블을 다시 로드하거나 변경된 값을 업데이트
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
                        alert('Chance decreased for user ' + userId);
                        // 성공 시, 테이블을 다시 로드하거나 변경된 값을 업데이트
                    }
                });
            });
        }

        function filterTable(column, value) {
            $('table tbody tr').filter(function() {
                $(this).toggle($(this).find('td').filter(function() {
                    return $(this).index() === $('th:contains("' + column + '")').index();
                }).text().toLowerCase().indexOf(value) > -1);
            });
        }
    });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
