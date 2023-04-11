<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>



<div style="width: 1100px; margin: 20px auto;"
	class="d-flex align-items-start">
	<c:url var="url" value="/manager/account/"></c:url>
	<div style="width: 170px" class="nav flex-column nav-pills"
		id="v-pills-tab" role="tablist" aria-orientation="vertical">
		<button class="nav-link active" data-bs-toggle="pill"
			data-bs-target="#listUser" type="button">List User</button>
		<button id="adUpdate" class="nav-link" data-bs-toggle="pill"
			data-bs-target="#editUser" type="button" role="tab"
			aria-controls="v-pills-profile" aria-selected="true">Edit
			User</button>

	</div>
	<div style="width: 100%;" class="tab-content" id="v-pills-tabContent">
		<div class="tab-pane fade show active row" id="listUser"
			role="tabpanel" aria-labelledby="v-pills-home-tab" tabindex="0">
			<div class="row">
				<table class="row">
					<tr class="row mb-3">
						<th class="col-2">Username</th>
						<th class="col-2">Pass word</th>
						<th class="col-3">Full name</th>
						<th class="col-3">Email</th>
						<th class="col-1">Role</th>
						<th class="col-1"></th>
					</tr>

					<c:forEach var="list" items="${listUser}">
						<tr class="row mb-3">
							<th class="col-2">${list.id }</th>
							<th class="col-2">${list.pass }</th>
							<th class="col-3">${list.fullname }</th>
							<th class="col-3">${fn:substring(list.email, 0, 22)}</th>
							<th class="col-1">${list.admins ? 'Admin' : 'User'}</th>
							<th class="col-1"><a href="${url}edit/${list.id}">Edit</a></th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="tab-pane fade row" id="editUser" role="tabpanel"
			aria-labelledby="v-pills-profile-tab" tabindex="0">
			<form id="updateInformation" class="row" action="${url}update" method="post">

				<div class="col-11 offset-1">
					<div class="row">
						<div class="col-5">
							<div class="row mb-3">
								<label class="row">User : </label> <input id="userAccount" disabled="disabled"
									value="${adUpAc.id}" name="id" class="row" placeholder="User?">
							</div>

							<div class="row mb-3">
								<label class="row">Email : </label> <input disabled="disabled"
									value="${adUpAc.email}" class="row"
									placeholder="Email?">
							</div>

							<div class="row mb-3">
								<label class="row">Pass : </label> <input ${adUpErrorPassInput}
									value="${adUpAc.pass}" name="pass" class="row"
									placeholder="Pass?"> <label class="row">${adUpErrorPass }</label>
							</div>

							<div class="row mb-3">
								<label class="col-3">Admin : </label>
								<div class="col-9">
									<label><input ${adUpAc.admins ? 'checked' : ''}
										type="radio" name="admins" value="true">Admin</label> <label><input
										${adUpAc.admins ? '' : 'checked'} type="radio" name="admins"
										value="false">Customer</label>
								</div>
							</div>

							<div class="row mb-3">
								<label class="row">DateCreate : </label> <input
									 class="row" disabled="disabled"
									value="${adUpAc.dateCreate}">
							</div>

						</div>

						<div class="col-5 offset-1">
							<div class="row mb-3">
								<label class="row">FullName : </label> <input
									${adUpErrorNameInput} value="${adUpAc.fullname}"
									name="fullname" class="row" placeholder="FullName?"> <label
									class="row">${adUpErrorName}</label>
							</div>

							<div class="row mb-3">
								<label class="row">Phone : </label> <input name="phone"
									class="row" placeholder="Phone?" value="${adUpAc.phone}">
								<label class="row"></label>
							</div>

							<div class="row mb-3">
								<label class="row">Birth : </label> <input name="birth"
									type="date" class="row" placeholder="Birth?"
									value="${adUpAc.getBirth()}"> <label class="row"></label>
							</div>

							<div class="row mb-3">
								<label class="col-3">Gender : </label>
								<div class="col-9">
									<label><input name="gender"
										${adUpAc.gender ? 'checked="checked"' : ''} type="radio"
										value="1">Male</label> <label><input name="gender"
										${adUpAc.gender ? '' : 'checked="checked"'} type="radio"
										value="0">Female</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="col-6 offset-3">
				<button onclick="update()" class="btn btn-primary">Login</button>
			</div>
			<script>
				function update() {
					document.getElementById('userAccount').removeAttribute(
							"disabled");
					document.getElementById('updateInformation').submit()
				}
			</script>
		</div>
	</div>
</div>
