<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div style="margin: 20px auto;" class="d-flex align-items-start">
	<c:url var="url" value="/manager/video/"></c:url>
	<div class="nav flex-column nav-pills me-3 row" id="v-pills-tab"
		role="tablist" aria-orientation="vertical">
		<button class="nav-link active" id="v-pills-home-tab"
			data-bs-toggle="pill" data-bs-target="#listCustomerFavor"
			type="button">Favor count</button>
		<button class="nav-link" id="v-pills-profile-tab"
			data-bs-toggle="pill" data-bs-target="#customerFavor" type="button"
			role="tab" aria-controls="v-pills-profile" aria-selected="true">Customer
			favorite</button>
		<button class="nav-link" id="v-pills-messages-tab"
			data-bs-toggle="pill" data-bs-target="#v-pills-messages"
			type="button" role="tab" aria-controls="v-pills-messages"
			aria-selected="false">Video Favor</button>

		<button class="nav-link" data-bs-toggle="pill"
			data-bs-target="#videoHaveFavor" type="button" role="tab"
			aria-controls="videoHaveFavor" aria-selected="false">Favor or not Favor</button>


	</div>
	<div style="width: 100%;" class="tab-content" id="v-pills-tabContent">
		<div class="tab-pane fade show active row" id="listCustomerFavor"
			role="tabpanel" aria-labelledby="v-pills-home-tab" tabindex="0">
			<h1>Video được ưa thích</h1>
			<div class="row">
				<table class="col-8 offset-2">
					<tr class="row mb-3">
						<th class="col-3">ID</th>
						<th class="col-3">Favor count</th>
						<th class="col-3">Newest Date</th>
						<th class="col-3">Oldest Date</th>
					</tr>
					<c:forEach var="list" items="${favorCount}">
						<tr class="row mb-3">
							<th class="col-3">${list.group }</th>
							<th class="col-3">${list.likes }</th>
							<th class="col-3">${list.newest }</th>
							<th class="col-3">${list.oldest }</th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="tab-pane fade" id="customerFavor" role="tabpanel"
			aria-labelledby="v-pills-profile-tab" tabindex="0">
			<div class="row">
				<div class="row col-8 offset-2">
					<div class="row">Tìm video yêu thích theo mã khách hàng</div>
					<div class="input-group mb-3">
						<span class="input-group-text">Search</span> <input type="text"
							class="form-control" id="searchFavorUser"> <span
							class="input-group-text">
							<button style="border: none; outline: none; outline-color: none"
								onclick="searchUserFavorVideo()">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>
						</span>
					</div>
				</div>


				<div id="search" class="row"></div>


			</div>
		</div>
		<div class="tab-pane fade" id="v-pills-messages" role="tabpanel"
			aria-labelledby="v-pills-messages-tab" tabindex="0">
			<div class="row">
				<div class="row col-8 offset-2">
					<div class="row">Tìm video yêu thích theo tên video</div>
					<div class="input-group mb-3">
						<span class="input-group-text">Search</span> <input type="text"
							class="form-control" id="searchVideoFavor"> <span
							class="input-group-text">
							<button style="border: none; outline: none; outline-color: none"
								onclick="searchVideoFavor()">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>
						</span>
					</div>
				</div>


				<div id="showSearchVideoFavor" class="row"></div>


			</div>
		</div>

		<div class="tab-pane fade" id="videoHaveFavor" role="tabpanel"
			tabindex="0">
			<h2>Video được ưa thích và không được ưa thích</h2>
			<div class="row">
				<label class="col-3"> <input type="radio" name="option"
					checked="checked" onclick="favorOrDont('favor')"> Favor
				</label> <label class="col-3"> <input type="radio" name="option"
					onclick="favorOrDont('dfavor')"> Not Favor
				</label>

			</div>
			<div class="row" id="videoFavor">
				<table class="row">
					<tr class="row mb-3">
						<th class="col-2">ID</th>
						<th class="col-4">Title</th>
						<th class="col-4">View</th>
						<th class="col-2">Active</th>
					</tr>
					<c:forEach var="list" items="${videoHaveFavor}">
						<tr class="row mb-3">
							<td class="col-2">${list.idVideo}</td>
							<td class="col-4">${list.title}</td>
							<td class="col-4">${list.viewVideo}</td>
							<td class="col-2">${list.active ? 'True' : 'Fail'}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

	</div>
</div>


<script>
	function searchUserFavorVideo() {
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '${urlAjax}');
		xhr.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			if (xhr.status === 200) {
				document.getElementById('search').innerHTML = xhr.responseText
			} else {
				// Xử lý lỗi
				alert(console.log('Lỗi: ' + xhr.statusText))
			}
		};
		xhr.onerror = function() {
			alert('Lỗi kết nối')
		};
		let inputSearch = document.getElementById("searchFavorUser")
		var data = 'ajax=yes&tkSearchFavor=yes&value=' + inputSearch.value

		xhr.send(data);
	}
</script>

<script>
	function searchVideoFavor() {
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '${urlAjax}');
		xhr.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			if (xhr.status === 200) {
				document.getElementById('showSearchVideoFavor').innerHTML = xhr.responseText
			} else {
				// Xử lý lỗi
				alert(console.log('Lỗi: ' + xhr.statusText))
			}
		};
		xhr.onerror = function() {
			alert('Lỗi kết nối')
		};
		let inputSearch = document.getElementById("searchVideoFavor")
		var data = 'ajax=yes&tkSearchVideoFavor=yes&value=' + inputSearch.value

		xhr.send(data);
	}
</script>
<script>
	function favorOrDont(value) {
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '${urlAjax}');
		xhr.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			if (xhr.status === 200) {
				document.getElementById('videoFavor').innerHTML = xhr.responseText
			} else {
				// Xử lý lỗi
				alert(console.log('Lỗi: ' + xhr.statusText))
			}
		};
		xhr.onerror = function() {
			alert('Lỗi kết nối')
		};
		var data;
		if (value == 'favor') {
			data = 'ajax=yes&tkVideoFavor=yes&favor=true'
		} else {
			data = 'ajax=yes&tkVideoFavor=yes&favor=false'
		}

		xhr.send(data);
	}
</script>