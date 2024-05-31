<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
    <div class="container mt-4">
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
            $('#userInfoBtn').click(function() {
                $.post('/getUserInfoAdmin', function(data) {
                    createTable(data);
                });
            });

            $('#scheduleInfoBtn').click(function() {
                $.post('/getScheduleInfoAdmin', function(data) {
                    createTable(data);
                });
            });

            $('#matchingInfoBtn').click(function() {
                $.post('/getMatchingInfoAdmin', function(data) {
                    createTable(data);
                });
            });

            function createTable(data) {
                if (!data || data.length === 0) {
                    $('#dataTable').html('<p>No data available</p>');
                    return;
                }

                let table = '<table class="table table-bordered table-striped">';
                table += '<thead class="thead-dark"><tr>';
                
                // 테이블 헤더 생성
                Object.keys(data[0]).forEach(function(key) {
                    table += '<th>' + key + '</th>';
                });
                table += '</tr></thead><tbody>';

                // 테이블 바디 생성
                data.forEach(function(row) {
                    table += '<tr>';
                    Object.values(row).forEach(function(value) {
                        table += '<td>' + value + '</td>';
                    });
                    table += '</tr>';
                });
                table += '</tbody></table>';

                $('#dataTable').html(table);
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
