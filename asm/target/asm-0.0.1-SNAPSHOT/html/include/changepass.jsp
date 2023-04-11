<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>


<!-- Modal -->
<div class="modal fade" id="changepass" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content bg-dark">
			<div class="modal-header">
				<h1 class="modal-title fs-5">Change pass</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="frmChange" action="/asm/account/changepass" method="post"
					style="width: 490px">
					<div class="row mb-4">
						<div class="row">
							<label class="col-3">Pass current : </label> 
							<input value="${current}" ${cErrorCurrentInput} name="current"
							id="cCurrent" class="col-9" placeholder="Pass current?" type="password">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${cErrorCurrent}</label>
						</div>
					</div>

					<div class="row mb-4">
						<div class="row">
							<label class="col-3">New pass : </label> 
							<input value="${newPass}" ${cErrorNewInput} name="newPass"
							id="cNew" class="col-9" placeholder="New pass?" type="password">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${cErrorNew}</label>
						</div>
					</div>

					<div class="row mb-2">
						<div class="row">
							<label class="col-3">Confirm : </label> 
							<input value="${confirm}" ${cErrorConfirmInput} name="confirm"
							id="cConfirm" class="col-9" placeholder="Confirm?" type="password">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${cErrorConfirm}</label>
						</div>
					</div>
					<div class="row">
						<label class="col-9 offset-3"> <input type="checkbox"
							id="cShowPass"> show pass
						</label>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="submitForm('frmChange')">Save changes</button>
			</div>
		</div>
	</div>
</div>
<script>
	const showPasswordField = document.getElementById("cShowPass");

	showPasswordField.addEventListener("change", function() {
		if (showPasswordField.checked) {
			document.getElementById("cCurrent").type = "text";
			document.getElementById("cNew").type = "text";
			document.getElementById("cConfirm").type = "text";
		} else {
			document.getElementById("cCurrent").type = "password";
			document.getElementById("cNew").type = "password";
			document.getElementById("cConfirm").type = "password";
		}
	});
</script>