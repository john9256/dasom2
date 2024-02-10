<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>다솜 로그인</title>
<style>
body {
	background-color: #f8f9fa;
}

.card {
	margin-top: 100px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.card-header {
	background-color: #f8a5c2;
	color: #fff;
}

.btn-primary {
	background-color: #f8a5c2;
	border-color: #f8a5c2;
}

.btn-primary:hover {
	background-color: #e687a6;
	border-color: #e687a6;
}

</style>
</head>
<body>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-4">
				<div class="card">
					<div class="card-header text-center">
						<h4>다솜 소개팅</h4>
					</div>
					<div class="card-body">
						<form action="/loginDo" method="post">
							<div class="form-group">
								<label for="userId">이름</label> <input type="text"
									class="form-control" id="userId" name="userId"
									placeholder="Enter username">
							</div>
							<div class="form-group">
								<label for="password">비밀번호</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="Enter password">
							</div>
							<button type="submit" class="btn btn-primary btn-block">로그인</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		

</body>
</html>
