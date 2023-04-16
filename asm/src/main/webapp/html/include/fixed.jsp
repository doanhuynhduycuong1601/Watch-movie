<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<div class="container">
	<c:url var="url" value="/manager/"></c:url>

	<div class="row">
		<div class="d-flex justify-content-between">
			<div class="left">
				<nav class="navbar navbar-expand-lg">
					<div class="collapse navbar-collapse" id="navbarNav">
						<ul class="navbar-nav">
							<!--List category-->
							<li class="nav-item">
								<div class="dropdown">
									<button style="border: none; padding: 8px"
										class="btn btn-outline-light dropdown-toggle" type="button"
										data-bs-toggle="dropdown" aria-expanded="false">
										Genre</button>
									<div
										style="width: 800px; background-color: rgba(52, 58, 64, 0.7);"
										class="dropdown-menu container">
										<div style="margin: 0px" class="row">
											<c:forEach var="list" items="${genre}">
												<a style="text-decoration: none;"
													class="col-3 mb-3 text-white"
													href="/asm/search/genre/${list.id}/1">${list.names}</a>
											</c:forEach>
										</div>
									</div>
								</div>
							</li>

							<li class="nav-item">
								<div class="dropdown">
									<button style="border: none; padding: 8px"
										class="btn btn-outline-light dropdown-toggle" type="button"
										data-bs-toggle="dropdown" aria-expanded="false">
										Country</button>
									<div
										style="width: 800px; background-color: rgba(52, 58, 64, 0.7);"
										class="dropdown-menu container">
										<div style="margin: 0px" class="row">
											<c:forEach var="list" items="${country}">
												<a style="text-decoration: none;"
													class="col-3 mb-2 text-white"
													href="/asm/search/country/${list.idCountry}/1">${list.nameCountry}</a>
											</c:forEach>
										</div>
									</div>
								</div>
							</li>



						</ul>
					</div>
				</nav>
			</div>
			<div></div>
			<div class="right">
				<nav class="navbar navbar-expand-lg">
					<div class="collapse navbar-collapse" id="navbarNav">
						<ul class="navbar-nav">
							<fmt:setLocale value="${sessionScope.lang}" scope="request"/>
							<fmt:setBundle basename="nav" scope="request"/>
							<li class="nav-item dropdown">
									<button style="border: none; padding: 8px"
										class="btn btn-outline-light dropdown-toggle" type="button"
										data-bs-toggle="dropdown" aria-expanded="false">English</button>
									<div class="dropdown-menu container">
										<div class="list-group">
											<a href="?lang=vi"
												class="list-group-item list-group-item-action">Tiếng việt
											</a> 
											<a href="?lang=en"
												class="list-group-item list-group-item-action">English
											</a>
										</div>
									</div>
								</li>
								
							
							<c:if test="${account != null && account.admins}">
								<li class="nav-item dropdown">
									<button style="border: none; padding: 8px"
										class="btn btn-outline-light dropdown-toggle" type="button"
										data-bs-toggle="dropdown" aria-expanded="false">
											<fmt:message key="nav.admin"></fmt:message>
										</button>
									<div class="dropdown-menu container">
										<div class="list-group">
											<a href="${url}genre/view"
												class="list-group-item list-group-item-action">Genre</a> <a
												href="${url}video/view"
												class="list-group-item list-group-item-action">Video</a> <a
												href="${url}account/view"
												class="list-group-item list-group-item-action">Account</a> <a
												href="${url}thong_ke/view"
												class="list-group-item list-group-item-action">Thống kê</a>
												<a href="${url}advertising/view"
												class="list-group-item list-group-item-action">Advertising</a>
										</div>
									</div>
								</li>
							</c:if>

							<c:if test="${account!=null }">
								<li class="nav-item dropdown">
									<button style="border: none; padding: 8px"
										class="btn btn-outline-light dropdown-toggle" type="button"
										data-bs-toggle="dropdown" aria-expanded="false"> <fmt:message key="nav.account"></fmt:message> </button>
									<div class="dropdown-menu container">
										<div class="list-group">
											<a id="aChangePass" href="#"
												class="list-group-item list-group-item-action"
												data-bs-toggle="modal" data-bs-target="#changepass">Change
												pass</a> <a href="/asm/account/update/view"
												class="list-group-item list-group-item-action">Edit
												profile</a>
											<form action="/asm/home" method="post">
												<input name="logout" value="false" hidden readonly>
												<button class="list-group-item list-group-item-action">
													Log out</button>
											</form>
										</div>
									</div>
								</li>
							</c:if>

							<c:if test="${account!=null}">
								<li class="nav-item"><a href="/asm/myfavorities/1"
									class="nav-link"> <fmt:message key="nav.favorite"></fmt:message> </a></li>
							</c:if>

							<c:if test="${account==null}">
								<li class="nav-item">
									<!-- Button trigger modal -->
									<button id="btnLogin" style="border: none;" type="button"
										class="btn btn-outline-light nav-link" data-bs-toggle="modal"
										href="#login" role="button">
										<i style="margin-right: 8px;" class="fa-solid fa-user"></i>
										<fmt:message key="nav.login"></fmt:message>
									</button>
								</li>
							</c:if>

						</ul>
					</div>
				</nav>

			</div>
		</div>
	</div>
	<div class="below row">
		<nav class="navbar navbar-expand-lg">
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mb-2 mb-lg-0">
					<li style="margin-left: 15px;" class="nav-item"><a id="home"
						class="nav-link" aria-current="page" href="/asm/home"> Cuongee
					</a></li>

					<li class="nav-item">
						<div class="input-group">
							<form class="form-control" method="post">
								<input id="searchByName" onkeyup="searchKeyUp(event)"
									style="width: 700px; border: none; outline: none;" type="text"
									placeholder="Search" name="search">
							</form>
							<span class="input-group-text">
								<button style="border: none; outline: none; outline-color: none"
									onclick="searchVideoByName()">
									<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</span>
						</div>
					</li>

				</ul>
			</div>
		</nav>
	</div>
</div>
<script>
	var input = document.getElementById("searchByName");
	var formSearch = input.parentNode
	formSearch.addEventListener("submit", function(event) {
		event.preventDefault(); // Ngăn chặn việc submit form mặc định
	});
	function searchVideoByName() {
		value = input.value
		formSearch.setAttribute('action', '/asm/search/name/' + value + '/1')
		formSearch.submit()
	}

	function searchKeyUp(event) {
		if (event.keyCode === 13) {
			searchVideoByName()
		}
	}
</script>