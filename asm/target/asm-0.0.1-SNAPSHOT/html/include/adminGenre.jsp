<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<div style="width: 1100px; margin: 20px auto;"
	class="d-flex align-items-start">
	<c:url var="url" value="/manager/genre/"></c:url>
	<div style="width: 170px" class="nav flex-column nav-pills"
		id="v-pills-tab" role="tablist" aria-orientation="vertical">
		<button class="nav-link active" data-bs-toggle="pill"
			data-bs-target="#listGenre" type="button">Genre</button>

		<button class="nav-link" data-bs-toggle="pill"
			data-bs-target="#listGenreDontVideo" type="button" role="tab"
			aria-selected="false">Genre don't video</button>
		<button class="nav-link" data-bs-toggle="pill" id="updateGenre"
			data-bs-target="#editGenre" type="button" role="tab"
			aria-controls="v-pills-profile" aria-selected="true">Edit
			Genre</button>
		<button class="nav-link" id="v-pills-messages-tab"
			data-bs-toggle="pill" data-bs-target="#country" type="button"
			role="tab" aria-controls="v-pills-messages" aria-selected="false">Country</button>
	</div>
	<div style="width: 100%;" class="tab-content" id="v-pills-tabContent">
		<div class="tab-pane fade show active row" id="listGenre"
			role="tabpanel" aria-labelledby="v-pills-home-tab" tabindex="0">
			<c:forEach var="list" items="${genreAdmin}">
				<div class="row mb-4">
					<a class="col-4" href="/asm/genre/${list.id}"> <img
						style="height: 210px;" src="/asm/img/imggenre/${list.img}"
						class="card-img-top img-fluid" alt="...">
					</a>
					<div class="col-5">
						<div class="row mb-4">
							<h2 class="text-white">${list.names}</h2>
							<label class="row text-white">${list.descriptions} </label>
						</div>
					</div>

					<div class="col-2 text-white">
						<i class="fa-solid fa-eye"></i> : ${list.viewGenre}
					</div>

					<div class="col-1 text-white">
						<button onclick="editGenre('${list.id}', 'edit')">Edit</button>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="tab-pane fade show row" id="listGenreDontVideo"
			tabindex="0">
			<c:forEach var="list" items="${genreDontVideo}">
				<div class="row mb-4">
					<a class="col-4" href="/asm/genre/${list.id}"> <img
						style="height: 210px;" src="/asm/img/imggenre/${list.img}"
						class="card-img-top img-fluid" alt="...">
					</a>
					<div class="col-5">
						<div class="row mb-4">
							<h2 class="text-white">${list.names}</h2>
							<label class="row text-white">${list.descriptions} </label>
						</div>
					</div>


					<div class="col-1 text-white">
						<button onclick="editGenre('${list.id}', 'edit')">Edit</button>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="tab-pane fade row" id="editGenre" role="tabpanel"
			aria-labelledby="v-pills-profile-tab" tabindex="0">
			<form method="post" id="managerGenreVideo" action="${url}createGenre"
				class="row" enctype="multipart/form-data">
				<label class="col-4" for="managerGenreInput"> <img
					src="\lab6\img\imggenre\chonanh.png" class="row"
					style="height: 270px; width: 100%" id="managerGenreImg">
				</label> <input name="img" style="display: none" id="managerGenreInput"
					type="file" name="poster">
				<div class="col-7 offset-1">
					<div class="row mb-4 text-white">
						<label class="col-3">Name :</label> <input name="names"
							class="col-7" placeholder="Name">
					</div>
					<div class="row mb-4">
						<label class="col-3 text-white">Description :</label>
						<textarea name="descriptions" class="col-7" rows="" cols=""></textarea>
					</div>

					<div class="row">
						<div class="col-12">
							<button onclick="managerClick('createGenre')" type="button"
								class="btn btn-primary">Create</button>
							<label onclick="editGenre('0','reset')" class="btn btn-primary">Reset</label>
						</div>
					</div>
				</div>
			</form>

		</div>


		<div class="tab-pane fade row" id="country" role="tabpanel"
			aria-labelledby="v-pills-messages-tab" tabindex="0">
			<div class="d-flex flex-row-reverse mb-4">
				<div class="p-2">
					<button onclick="createCountry()">Create</button>
				</div>
			</div>

			<div class="col-8 offset-2">
				<div class="row mb-4">
					<label class="col-2">ID Country</label>

					<div class="col-8">
						Country : <label>Name Country</label>
					</div>

					<div class="col-2">Update</div>
				</div>

				<c:forEach var="list" items="${country}">
					<div class="row mb-4">
						<div class="col-2">${list.idCountry}</div>

						<div class="col-8">
							Country : <label onclick="editNameCountry(this)">${list.nameCountry}</label>
						</div>

						<div class="col-2">
							<button onclick="getNameCountryValue(this,${list.idCountry})">Update</button>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
function editNameCountry(label) {
	let name = prompt("Nhập tên bạn muốn sửa");
	if (name != null && name.length > 0) {
		label.innerHTML = name;
	}
}
</script>

<script>
	document.getElementById('managerGenreInput').addEventListener('change',
			addFile);

	function addFile(event) {
		let selectedFile = event.target.files[0];
		if (selectedFile) {
			let fileType = selectedFile.type;

			if (fileType.startsWith("image/")) {
				let filePath = URL.createObjectURL(selectedFile);
				console.log(filePath)
				document.getElementById("managerGenreImg").setAttribute("src",
						filePath);
			} else {
				alert("Chỉ cho phép chọn tệp tin ảnh!");
				this.value = ""; // Xóa giá trị đầu vào của phần tử input
			}
		}
	}
</script>

<script>
	function editGenre(value, functions) {
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '${urlAjax}');
		xhr.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			if (xhr.status === 200) {
				document.getElementById('managerGenreVideo').innerHTML = xhr.responseText
				document.getElementById('updateGenre').click()
				document.getElementById('managerGenreInput')
						.removeEventListener('change', addFile);
				document.getElementById('managerGenreInput').addEventListener(
						'change', addFile);
			} else {
				// Xử lý lỗi
				console.log('Lỗi: ' + xhr.statusText);
			}
		};
		xhr.onerror = function() {
			console.log('Lỗi kết nối');
		};
		var data
		if (functions == "edit") {
			data = 'ajax=yes&managerGenre=yes&value=' + value + '&edit=yes'
		} else {
			data = 'ajax=yes&managerGenre=yes&reset=yes'
		}

		xhr.send(data);
	}
</script>

<script>
	function managerClick(value) {
		let doc = document.getElementById('managerGenreVideo')
		doc.setAttribute('action', '${url}' + value)
		doc.submit()

	}
</script>

<script>
	function getNameCountryValue(button,id) {
		let parent = button.parentElement.parentElement;
		let labelValue = parent.querySelector("div>label").textContent;
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '${urlAjax}');
		xhr.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			if (xhr.status === 200) {
				alert(xhr.responseText)
			} else {
				console.log('Lỗi: ' + xhr.statusText);
			}
		};
		xhr.onerror = function() {
			console.log('Lỗi kết nối');
		};
		var data = 'ajax=yes&managerCountryUpdate=yes&id=' + id + '&name='+labelValue

		xhr.send(data);
	}
	
	
	function createCountry() {
		let name = prompt("Nhập tên bạn muốn sửa");
		if (name != null && name.length > 0) {
			let xhr = new XMLHttpRequest();
			xhr.open('POST', '${urlAjax}');
			xhr.setRequestHeader('Content-type',
					'application/x-www-form-urlencoded');
			xhr.onload = function() {
				if (xhr.status === 200) {
					alert(xhr.responseText)
				} else {
					console.log('Lỗi: ' + xhr.statusText);
				}
			};
			xhr.onerror = function() {
				console.log('Lỗi kết nối');
			};
			var data = 'ajax=yes&managerCountryCreate=yes&name='+name

			xhr.send(data);
		}else{
			alert("ban chua nhap")
		}
	}
</script>