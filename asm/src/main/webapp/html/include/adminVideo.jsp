<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="container">
	<div style="margin: 20px auto;" class="d-flex align-items-start">
		<c:url var="url" value="/manager/video/"></c:url>
		<div class="nav flex-column nav-pills me-3 row" id="v-pills-tab"
			role="tablist" aria-orientation="vertical">
			<button class="nav-link active" id="v-pills-home-tab"
				data-bs-toggle="pill" data-bs-target="#listVideo" type="button">Video</button>

			<button class="nav-link" id="editVideo" data-bs-toggle="pill"
				data-bs-target="#v-pills-messages" type="button" role="tab"
				aria-controls="v-pills-messages" aria-selected="false">Edit
				Video</button>

		</div>
		<div style="width: 100%;" class="tab-content" id="v-pills-tabContent">
			<div class="tab-pane fade show active row" id="listVideo"
				role="tabpanel" aria-labelledby="v-pills-home-tab" tabindex="0">
				<c:forEach var="list" items="${listVideo}">
					<div class="row mb-4">
						<a class="col-4" href="/asm/detailVideo/${list.idVideo }"> <img
							style="height: 210px;" src="/asm/img/imgposter/${list.poster }"
							class="card-img-top img-fluid" alt="...">
						</a>
						<div class="col-7">
							<div class="row mb-4">
								<h2 class="col-6 text-white">${list.title}</h2>
								<h2 class="col-3 text-white">
									<i class="fa-solid fa-eye"></i> ${list.viewVideo}
								</h2>

								<h2 class="col-3 text-white"></h2>
							</div>

							<div class="text-white">
								Thể loại :
								<c:forEach var="listGenreV" items="${list.genres}">
									<a href="/asm/search/genre/${listGenreV.id}/1"
										style="margin-left: 20px;" class="text-white">${listGenreV.names}</a>
								</c:forEach>
							</div>


							<div class="text-white">
								Country : <a
									href="/asm/search/country/${list.country.idCountry}/1">
									${list.country.nameCountry }</a>
							</div>
						</div>

						<div class="col-1 text-white">
							<a href="${url}edit/${list.idVideo}"> Edit</a>
						</div>
					</div>
				</c:forEach>

				<div class="row">
					<div class="col-6 offset-3">
						<c:if test="${pageCurrent > 1}">
							<a href="${url}page/1" class="btn btn-outline-secondary"><i
								class="fa-solid fa-backward-step"></i></a>
						</c:if>
						<c:if test="${pageCurrent > 1}">
							<a href="${url}page/${pageCurrent-1}"
								class="btn btn-outline-secondary">${pageCurrent-1}</a>
						</c:if>

						<a href="" class="btn btn-primary">${pageCurrent }</a>

						<c:if test="${pageLast - pageCurrent >= 1}">
							<a href="${url}page/${pageCurrent+1}"
								class="btn btn-outline-secondary">${pageCurrent+1}</a>
						</c:if>
						<c:if test="${pageLast - pageCurrent >= 2}">
							<a href="${url}page/${pageLast}" class="btn btn-outline-secondary">${pageLast}</a>
						</c:if>
						<c:if test="${pageLast - pageCurrent >= 1}">
							<a href="${url}page/${pageLast}" class="btn btn-outline-secondary"><i
								class="fa-solid fa-forward-step"></i></a>
						</c:if>
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="v-pills-messages" role="tabpanel"
				aria-labelledby="editVideo" tabindex="0">
				<form action="${url}update" class="row mb-4" method="post"
					enctype="multipart/form-data">
					<input style="display: none" name="idVideo"
						value="${editVideo.idVideo}">
					<div class="col-4">
						<label class="row" for="managerVideoInput"> <img
							src="/asm/img/imgposter/${editVideo.poster}" class="row"
							style="height: 300px;" id="managerVideoImg">
						</label> <input style="display: none" id="managerVideoInput" type="file"
							name="poster">

					</div>
					<div class="col-7">
						<div class="row mb-4">
							<div class="row">
								<label class="col-3">Name</label> <input
									value="${editVideo.title}" ${addVideoErrorTitleInput}
									name="title" class="col-7" placeholder="Title?">
							</div>
							<div class="row">
								<label class="offset-3 col-7">${addVideoErrorTitle} </label>
							</div>
						</div>

						<div class="row mb-4">
							<div class="row">
								<label class="col-3">Time</label> <input name="times"
									${addVideoErrorTimesInput} class="col-7" placeholder="Time?"
									value="${editVideo.times}">
							</div>
							<div class="row">
								<label class="offset-3 col-7">${addVideoErrorTimes} </label>
							</div>
						</div>

						<div class="row mb-4">
							<label class="col-3">Exists</label>
							<div class="col-7">
								<label> <input
									${editVideo.active ? 'checked="checked"' : ''} type="radio"
									name="active" value="1">Active
								</label> <label> <input
									${editVideo.active ?  '' : 'checked="checked"'} type="radio"
									name="active" value="0">Deleted
								</label>
							</div>
						</div>


						<div class="row mb-4">
							<label class="col-3">Country</label> <select class="col-7"
								name="countryID">
								<c:forEach var="list" items="${country}">
									<option
										${editVideo.country.idCountry ==  list.idCountry ? 'selected' : ''}
										value="${list.idCountry}">${list.nameCountry}</option>
								</c:forEach>
							</select>
						</div>



						<div class="row mb-4">
							<label class="col-2">Genre :</label>
							<div class="col-5" id="genreShow">
								<c:forEach var="genre" items="${editVideo.genres}">
									<label style="margin-right: 10px; cursor: pointer;"><input
										onclick="genreRemove(this)" name="genreAddData"
										value="${genre.id}" type="checkbox" hidden checked="checked">${genre.names}</label>
								</c:forEach>
							</div>
							<select class="col-3" id="genreSelect">
								<c:forEach var="genre" items="${genre }">
									<option value="${genre.id}">${genre.names}</option>
								</c:forEach>
							</select> <label class="col-1 btn btn-primary" onclick="addGenre()">Add</label>
						</div>


						<div class="row mb-4">
							<label class="col-2">Link :</label>
							<div class="col-8" id="linkShow">
								<c:forEach var="link" items="${editVideo.links}">
									<label style="margin-right: 10px; cursor: pointer;"> <input
										onclick="linkRemove(this)" name="link"
										value="${link.urlVideo }" type="checkbox" hidden
										checked="checked">${link.urlVideo}
									</label>
								</c:forEach>
							</div>
							<label class="col-1 btn btn-primary" onclick="addLink()">Add</label>
						</div>

						<div class="row mb-4">
							<label class="col-3">Description</label>
							<textarea name="descriptions" class="col-7" rows="" cols="">${editVideo.descriptions}</textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<button formaction="${url}create">Create</button>
							<button formaction="${url}update">Update</button>
							<button formaction="${url}remove">Remove</button>
							<button formaction="${url}reset">Reset</button>
						</div>
					</div>
				</form>

				<script>
			const listLink = []
			function addLink() {
				let link = prompt("Please enter your name:");
				if (link != null && link != "") {
					if(listLink.includes(link)){
						alert("Bạn đã add Phim này")
						return;
					}
					document.getElementById('linkShow').innerHTML += '<label style="margin-right: 10px;cursor: pointer;"><input onclick="linkRemove(this)" name="link" value="'+link+'" type="checkbox" hidden checked="checked">'+link+'</label>'
				}else{
					alert("Bạn chưa nhập");
				}
			}
			
			function linkRemove(input) {
				let result = confirm("Are you sure you want to delete that?"); // Hiển thị hộp thoại xác nhận
				  if (result) {
				    let index = listLink.indexOf(input.value);
				    listGenre.splice(index, 1);
				    document.getElementById('linkShow').removeChild(input.parentNode)
				  }else{
					  input.checked = true
				  }
			}
			
			function genreRemove(input) {
				let result = confirm("Are you sure you want to delete that?"); // Hiển thị hộp thoại xác nhận
				  if (result) {
				    let index = listGenre.indexOf(input.value);
				    listGenre.splice(index, 1);
				    document.getElementById('genreShow').removeChild(input.parentNode)
				  }else{
					  input.checked = true
				  }
			}
			const listGenre = []
			function addGenre() {
				let mySelect = document.getElementById('genreSelect');
				let selectedValue = mySelect.value;
				let selectedText = getSelectedText(mySelect);
				if(listGenre.includes(selectedValue)){
					alert("Bạn đã add genre này")
					return;
				}
				listGenre.push(selectedValue);
				document.getElementById('genreShow').innerHTML += '<label style="margin-right: 10px;cursor: pointer;"><input onclick="genreRemove(this)" name="genreAddData" value="'+selectedValue+'" type="checkbox" hidden checked="checked">'+selectedText+'</label>'
			}
			
			
			function starRemove(input) {
				let result = confirm("Are you sure you want to delete that?"); // Hiển thị hộp thoại xác nhận
				  if (result) {
					  let index = listStar.indexOf(input.value);
					  listStar.splice(index, 1);
				    document.getElementById('starShow').removeChild(input.parentNode)
				  }else{
					  input.checked = true
				  }
			}
			
			const listStar = []
			function addStar() {
				let mySelect = document.getElementById('starSelect');
				let selectedValue = mySelect.value;
				let selectedText = getSelectedText(mySelect);
				if(listStar.includes(selectedValue)){
					alert("Bạn đã add Star này")
					return;
				}
				listStar.push(selectedValue);
				document.getElementById('starShow').innerHTML += '<label style="margin-right: 10px;"><input onclick="starRemove(this)" name="star" value="'+selectedValue+'" checked="checked" type="checkbox" hidden>'+selectedText+'</label>'
			}
			
			function getSelectedText(select) {
				  let options = select.options;
				  for (let i = 0; i < options.length; i++) {
				    if (options[i].value === select.value) {
				      return options[i].text;
				    }
				  }
				}
		</script>
			</div>



		</div>
	</div>
</div>

<script>
	document.getElementById('managerVideoInput').addEventListener('change', (event) => {
		let selectedFile = event.target.files[0];
		if (selectedFile) {
            let fileType = selectedFile.type;

            if (fileType.startsWith("image/")) {
                let filePath = URL.createObjectURL(selectedFile);
                document.getElementById("managerVideoImg").setAttribute("src", filePath);
            } else {
                alert("Chỉ cho phép chọn tệp tin ảnh!");
                this.value = ""; // Xóa giá trị đầu vào của phần tử input
            }
        }
	});
	
	${editVideoGenre}

</script>