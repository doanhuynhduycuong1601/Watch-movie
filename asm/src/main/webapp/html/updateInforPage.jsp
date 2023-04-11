<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="/asm/fontawesome-free-6.2.1-web/css/all.min.css">
<link rel="stylesheet" href="/asm/css/run.css">
<link rel="stylesheet" href="/asm/css/heart.css">
</head>
<body style="position: relative;" class="bg-dark">
	<div class="convenient-bar bg-danger sticky-top">
		<%@ include file="include/fixed.jsp"%>
	</div>

	<div class="container">
		<%@ include file="include/updateInfor.jsp"%>
	</div>


	<%@ include file="include/login.jsp"%>


	<script>
		function submitForm(id) {
			document.getElementById(id).submit()
		}
	</script>
	<script>
		window.onload = function() {
		  ${adUpdate}
		  ${btnLogin}
		  ${aRegister}
		}
	</script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
		crossorigin="anonymous"></script>
</body>
</html>