<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<div class="row mb-5">
	<h2>Search : ${nameVideo}</h2>
</div>
<div class="row mb-4">
	<c:url var="url" value="/search/name/${nameVideo}/"></c:url>
	<c:forEach var="list" items="${listVideo}">
		<div class="col-3 mb-4">
			<div class="row">
				<div class="bg-dark col-10 offset-1">
					<div class="row divBig" style="height: 210px;">
						<a href="/asm/detailVideo/${list.idVideo}"> <img
							style="height: 210px; width: 100%"
							src="/asm/img/imgposter/${list.poster }"
							class="card-img-top img-fluid" alt="...">
						</a>
						<div class="divButton">
							<a class="btn btn-primary row"
								href="/asm/detailVideoView/${list.idVideo}">Xem ngay</a>
						</div>
					</div>
					<div>
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
			</div>
		</div>
	</c:forEach>
	
	<div class="row">
		<div class="col-6 offset-3">
			<c:if test="${pageCurrent > 1}">
				<a href="${url}1" class="btn btn-outline-secondary"><i class="fa-solid fa-backward-step"></i></a>
			</c:if>
			<c:if test="${pageCurrent > 1}">
				<a href="${url}${pageCurrent-1}" class="btn btn-outline-secondary">${pageCurrent-1}</a>
			</c:if>
			
			<a href="" class="btn btn-primary">${pageCurrent }</a>
			
			<c:if test="${pageLast - pageCurrent >= 1}">
				<a href="${url}${pageCurrent+1}" class="btn btn-outline-secondary">${pageCurrent+1}</a>
			</c:if>
			<c:if test="${pageLast - pageCurrent >= 2}">
				<a href="${url}${pageLast}" class="btn btn-outline-secondary">${pageLast}</a>
			</c:if>
			<c:if test="${pageLast - pageCurrent >= 1}">
				<a href="${url}${pageLast}" class="btn btn-outline-secondary"><i class="fa-solid fa-forward-step"></i></a>
			</c:if>
		</div>
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