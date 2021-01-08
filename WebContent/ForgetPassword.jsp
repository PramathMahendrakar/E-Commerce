<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>E - Commerce</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="LandingPage.jsp">E - Commerce</a>

			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="LandingPage.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="Login.jsp">Sign
						In</a></li>

			</ul>
		</div>

	</nav>


	<form action="covid.do" method="post">

		<br> <br>
		<form class="row g-3">
			<div class="col-lg-4 m-auto d-block">

				<div class="col-sm-12">
					<label for="inputEmail4" class="form-label">Email</label> <input
						type="email" class="form-control" id="inputEmail4">
				</div>

				<br>
				<div class="col-12">
					<button type="submit" class="btn btn-primary">Reset</button>
				</div>
				<br>

			</div>
		</form>
		<br> <br>

		<footer class="footer" />
		<div class="fixed-bottom">
			<div class="container-fluid ">
				<div class="card-footer">
					<div class="row text-center">
						<div class="col-12">© Copy Right - Xworkz 2021</div>
					</div>
				</div>
			</div>
		</div>
		</footer>
	</form>


</body>
</html>