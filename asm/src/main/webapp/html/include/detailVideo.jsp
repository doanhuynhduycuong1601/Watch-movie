<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="container">
	<div class="row">
		<c:url var="url" value="/detailVideo/"></c:url>
		<div class="col-8">
			<div class="row">
				<div class="col-7 row">
					<img style="height: 450px" class="row"
						src="/asm/img/imgposter/${vDetail.poster}">
				</div>

				<div class="col-5">
					<div class="row">
						<label class="row mb-3 text-light">Name : ${vDetail.title}
						</label> <label class="row mb-3 text-light">Lượt xem :
							${vDetail.viewVideo} </label> <label class="row mb-3 text-light">Năm
							chiếu : <fmt:formatDate value="${vDetail.dateUpload}"
								pattern="yyyy" />
						</label> <label class="row mb-3 text-light">Thời lượng :
							${vDetail.times}</label>


						<div style="padding: 0px" class="row mb-3 text-light">
							<label>Quốc gia : <a
								href="/asm/search/country/${vDetail.country.idCountry}/1">${vDetail.country.nameCountry }</a>
							</label>
						</div>


						<div style="padding: 0px" class="row mb-3 text-light">
							<label>Genre : <c:forEach var="genre"
									items="${vDetail.genres}">
									<a href="/asm/search/genre/${genre.id}/1">${genre.names}</a>
								</c:forEach>
							</label>
						</div>


						<div class="row">
							<div class="d-flex flex-row-reverse">
								<div class="p-2">
									<a href="/asm/detailVideoView/${vDetail.idVideo}"
										class="btn btn-primary">Xem ngay</a>
								</div>
								<div class="p-2">
									<button onclick="modelSendMail(${vDetailLike.idVideo})"
										type="button" class="btn btn-primary" data-bs-toggle="modal"
										data-bs-target="#sendGmail">Share</button>
								</div>
								<div class="p-2">
									<label class="heart-btn"> <input
										${account==null? 'disabled="disabled"' : '' } type="checkbox"
										onchange="likes(this,'${vDetail.idVideo}')"
										class="heart-checkbox"
										${account != null && like.likes != null && like.likes == true ? 'checked="checked"' : ''}>
										<img src="/asm/img/icon/white.png" alt="white heart"
										class="heart-icon white"> <img
										src="/asm/img/icon/red.png" alt="red heart"
										class="heart-icon red">
									</label>
								</div>
							</div>
						</div>
						<c:if test="${account != null && account.admins}">
							<div class="">
								<a class="btn btn-primary col-5"
									href="/asm/manager/video/edit/${vDetail.idVideo}">Edit
									Video </a>
							</div>
						</c:if>
					</div>
				</div>
			</div>


			<div class="row">
				<h2 class="row text-light">${vDetail.descriptions}</h2>
			</div>
		</div>



		<div class="col-4">
			<c:forEach var="list" items="${videoListDetail}">
				<div style="border: 2px solid white;" class="row mb-4 bg-dark">
					<div class="col-4">
						<a href="/asm/detailVideo/${list.idVideo}"> <img
							style="height: 120px; width: 100%"
							src="/asm/img/imgposter/${list.poster }"
							class="card-img-top img-fluid" alt="...">
						</a>
					</div>

					<div class="col-8">
						<div>${list.title}</div>
						<div>Lượt xem: ${list.views}</div>
						<div class="d-flex flex-row-reverse">
							<div class="p-2">
								<button onclick="modelSendMail(${list.idVideo})" type="button"
									class="btn btn-primary" data-bs-toggle="modal"
									data-bs-target="#sendGmail">Share</button>
							</div>
							<div class="p-2">
								<label class="heart-btn"> <input
									${account==null? 'disabled="disabled"' : '' }
									class="heart-checkbox" type="checkbox"
									onchange="likes(this,${list.idVideo})"
									${account != null && list.like != null && list.like == true ? 'checked="checked"' : ''}>
									<img src="/asm/img/icon/white.png" alt="white heart"
									class="heart-icon white"> <img
									src="/asm/img/icon/red.png" alt="red heart"
									class="heart-icon red">
								</label>
							</div>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script>
		function likes(input, value) {
			let xhr = new XMLHttpRequest();
			xhr.open('POST', '${urlAjax}');
			xhr.setRequestHeader('Content-type',
					'application/x-www-form-urlencoded');
			xhr.onload = function() {
				if (xhr.status === 200) {
					// Xử lý phản hồi từ server
					console.log(xhr.responseText);
				} else {
					// Xử lý lỗi
					console.log('Lỗi: ' + xhr.statusText);
				}
			};
			xhr.onerror = function() {
				console.log('Lỗi kết nối');
			};
			var data;
			if (input.checked) {
				data = 'ajax=yes&heart=yes&value=' + value + '&like=true'
			} else {
				data = 'ajax=yes&heart=yes&value=' + value + '&like=false'
			}

			xhr.send(data);
		}
	</script>

	<div class="move-up" style="width: 50%; margin: 0px auto">
		<div class="move-up-btn">
			<button onclick="closeAdvertising(this)"
				class="btn-close btn-close-white"></button>
			<img onclick="openAdvertising(this)" class="move-up img-fluid"
				style="height: 120px; width: 50%" alt=""
				src="/asm/img/imgAdver/${adver.img}">
		</div>
	</div>
</div>
<script>
	function closeAdvertising(btn) {
		let a = btn.parentNode.parentNode
		a.style.display = "none"
		setTimeout(
			function() { a.style.display = "block" },
			'${adver.alltime()}'
		)
	}
	
	function openAdvertising(img) {
		let a = img.parentNode.parentNode
		a.style.display = "none"
		setTimeout(
				function() { a.style.display = "block" },
				'${adver.alltime()}'
		)
		window.open('${adver.linkURL}', "FaceBook");
	}
	
	
</script>