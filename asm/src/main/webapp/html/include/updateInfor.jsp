<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>



<form id="updateInformation" class="row" action="/asm/account/update/update" method="post">
	<div class="col-11 offset-1">
		<div class="row">
			<div class="col-5">
				<div class="row mb-3">
					<label class="row">User : </label> <input id="userAccount" value="${adUpAc.id}"
						name="id" class="row" placeholder="User?" disabled="disabled"> <label
						class="row"></label>
				</div>

				<div class="row mb-3">
					<label class="row">Email : </label> <input ${adUpErrorEmailInput} id="emailAccount"
						value="${adUpAc.email}" class="row" disabled="disabled"
						placeholder="Email?"> <label class="row">${adUpErrorEmail}</label>
				</div>

				<div class="row mb-3">
					<label class="row">Pass : </label> <input ${adUpErrorPassInput}
						value="${adUpAc.pass}" name="pass" class="row" placeholder="Pass?">
					<label class="row">${adUpErrorPass }</label>
				</div>


				<div class="row mb-3">
					<label class="row">DateCreate : </label> <input class="row"
						disabled="disabled" value="${adUpAc.dateCreate}">
				</div>

			</div>

			<div class="col-5 offset-1">
				<div class="row mb-3">
					<label class="row">FullName : </label> <input ${adUpErrorNameInput}
						value="${adUpAc.fullname}" name="fullname" class="row"
						placeholder="FullName?"> <label class="row">${adUpErrorName}</label>
				</div>

				<div class="row mb-3">
					<label class="row">Phone : </label> <input class="row" name="phone"
						placeholder="Phone?" value="${adUpAc.phone}"> <label
						class="row"></label>
				</div>

				<div class="row mb-3">
					<label class="row">Birth : </label> <input class="row"
						placeholder="Birth?" value="${adUpAc.getBirth()}" type="date" name="birth"> <label
						class="row"></label>
				</div>

				<div class="row mb-3">
					<label class="col-3">Gender : </label>
					<div class="col-9">
						<label><input ${adUpAc.gender ? 'checked="checked"' : ''}
							type="radio" value="1" name="gender">Male</label> <label><input name="gender"
							${adUpAc.gender ? '' : 'checked="checked"'} type="radio"
							value="0">Female</label>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<div class="col-6 offset-3">
	<button onclick="update()" class="btn btn-primary">Update</button>
</div>
<script>
	function update() {
		document.getElementById('userAccount').removeAttribute("disabled");
		document.getElementById('updateInformation').submit()
	}
</script>
