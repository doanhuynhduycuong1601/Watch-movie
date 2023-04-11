<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="row">
	<c:url var="url" value="/detailVideo/"></c:url>
	<div class="col-8">
		<iframe id="viewVideo" width="680" height="315"
			src="https://www.youtube.com/embed/TvJFRM9VO2Masdsadsad"
			title="YouTube video player" frameborder="0"
			allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
			allowfullscreen></iframe>

		<div class="row">
			<div class="d-flex">
				<div class="p-2 flex-grow-1">
					<c:forEach var="link" items="${links}">
						<button onclick="updateLink('${link.urlVideo}')">${link.id}</button>
					</c:forEach>
				</div>
				
				<div class="p-2">
					<label class="heart-btn"> <input
						${account==null? 'disabled="disabled"' : '' } type="checkbox"
						onchange="like(this,'${vDetail.idVideo}')" class="heart-checkbox"
						${account != null && like.likes != null && like.likes == true ? 'checked="checked"' : ''}>
						<img src="/asm/img/icon/white.png" alt="white heart"
						class="heart-icon white"> <img src="/asm/img/icon/red.png"
						alt="red heart" class="heart-icon red">
					</label>
					<button onclick="modelSendMail(${vDetailLike.idVideo})"
						type="button" class="btn btn-primary" data-bs-toggle="modal"
						data-bs-target="#sendGmail">Share</button>
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
								<label class="heart-btn"> 
									<input ${account==null? 'disabled="disabled"' : '' } class="heart-checkbox" 
									type="checkbox" onchange="likes(this,${list.idVideo})" 
									${account != null && list.like != null && list.like == true ? 'checked="checked"' : ''}> 
									<img src="/asm/img/icon/white.png" alt="white heart" class="heart-icon white">
									<img src="/asm/img/icon/red.png" alt="red heart" class="heart-icon red">
								</label>
							</div>
							
						</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>

<script>
	function updateLink(value) {
		let src = "https://www.youtube.com/embed/"+value
		document.getElementById('viewVideo').setAttribute('src',src);
	}
	
	window.onload = function() {
		let linkVideo = "https://www.youtube.com/embed/"+'${linkOne}'
		document.getElementById('viewVideo').setAttribute('src',linkVideo);
	}
</script>
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