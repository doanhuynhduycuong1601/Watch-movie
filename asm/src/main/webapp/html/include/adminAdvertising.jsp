<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="container">
	<c:url var="url" value="/manager/advertising/"></c:url>
	<div class="offset-1 col-10">
		<div class="tab-pane fade show active row" id="listVideo"
		role="tabpanel" aria-labelledby="v-pills-home-tab" tabindex="0">
		<div class="row">
			<div class="row col-8 offset-2">
				<div class="row">Tìm video yêu thích theo tên video</div>
				<div class="input-group mb-3">
					<span class="input-group-text">Search</span> <input type="text"
						class="form-control" id="searchLink"> <span
						class="input-group-text">
						<button style="border: none; outline: none; outline-color: none"
							onclick="searchLink()">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button>
					</span>
				</div>

				<table class="row">
					<tr class="row mb-4">
						<th class="col-3">Names</th>
						<th class="col-3">Dates</th>
						<th class="col-3">Times</th>
						<th class="col-3">Edit</th>
					</tr>

					<c:forEach var="list" items="${listAdver}">
						<tr class="row mb-4">
							<th class="col-3">${list.names}</th>
							<th class="col-3">${list.getDate()}</th>
							<th class="col-3">${list.times}</th>
							<th class="col-3"><a href="${url}edit/${list.id}" class="btn btn-primary">Edit</a></th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>


	</div>

	<form action="${url}update" class="row mb-4" method="post"
		enctype="multipart/form-data">
		<input style="display: none" name="id" value="${editLink.id}">
		<div class="col-4 mb-4">
			<label class="row" for="managerVideoInput"> <img
				src="/asm/img/imgAdver/${editLink.img}" class="row"
				style="height: 300px;" id="managerVideoImg">
			</label> <input style="display: none" id="managerVideoInput" type="file"
				name="img" value="${editLink.img}">

		</div>
		<div class="col-7 mb-4">
			<div class="row mb-4">
				<div class="row">
					<label class="col-3">Name</label> <input value="${editLink.names}"
						${addAdverErrorNamesInput } name="names" class="col-7"
						placeholder="Title?">
				</div>
				<div class="row">
					<label class="offset-3 col-7">${addAdverErrorNames} </label>
				</div>
			</div>

			<div class="row mb-4">
				<div class="row">
					<label class="col-3">Link</label> <input name="linkURL"
						class="col-7" placeholder="URL?" ${addAdverErrorLinksInput}
						value="${editLink.linkURL}">
				</div>
				<div class="row">
					<label class="offset-3 col-7">${addAdverErrorLinks} </label>
				</div>
			</div>

			<div class="row mb-4">
				<div class="row">
					<label class="col-3">Date</label> <input name="dates" class="col-7"
						placeholder="Date?" ${addAdverErrorDateInput }
						value="${editLink.getDate()}">
				</div>
				<div class="row">
					<label class="offset-3 col-7">${addAdverErrorDate} </label>
				</div>
			</div>

			<div class="row mb-4">
				<div class="row">
					<label class="col-3">Time</label> <input name="times" class="col-7"
						placeholder="Time?" value="${editLink.times}"
						${addAdverErrorTimesInput}>
				</div>
				<div class="row">
					<label class="offset-3 col-7">${addAdverErrorTimes} </label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="offset-2 col-8">
				<button formaction="${url}create">Create</button>
				<button formaction="${url}update">Update</button>
				<button formaction="${url}remove">Remove</button>
				<button formaction="${url}reset">Reset</button>
			</div>
		</div>
	</form>
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