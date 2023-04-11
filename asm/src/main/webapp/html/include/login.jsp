<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div class="modal fade" id="login" aria-hidden="true">
	<c:url var="url" value="/account/"></c:url>
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content  bg-dark">
			<div class="modal-header">
				<h1 class="modal-title fs-5">Login</h1>
				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body row">
				<form action="${url}login" style="width: 490px" id="frmLogin" method="post">
					<div class="row mb-4">
						<div class="row">
							<label class="col-3">User : </label> 
							<input value="${formLg.id}" ${lgErrorUserInput} name="id" class="col-9" placeholder="User?">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${lgErrorUser}</label>
						</div>
					</div>

					<div class="row mb-2">
						<div class="row">
							<label class="col-3">Pass : </label> 
							<input value="${formLg.pass}" ${lgErrorPassInput} name="pass" class="col-9"placeholder="Pass?" type="password">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${lgErrorPass}</label>
						</div>
					</div>

					<div class="row mb-3">
						<label class="col-9 offset-3"> 
							<input type="checkbox" name="remember" ${rememberLogin}>
							Remember me?
						</label>
					</div>
					
					<div class="row">
						<div class="col-9 offset-3">
							<a id="aRegister" href="" data-bs-target="#register" data-bs-toggle="modal">
								Register?
							</a>
							<a id="aForgot" href="" data-bs-target="#forgot" data-bs-toggle="modal">
								Forgot pass?
							</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="submitForm('frmLogin')">Login</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="register" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content  bg-dark">
			<div class="modal-header">
				<h1 class="modal-title fs-5">Registration</h1>
				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="frmRe" action="${url}register" style="width: 490px;margin: 0px auto" method="post">
					<div class="row mb-4">
						<div class="col-5">
							<div class="row">
								<label class="row">UserName?</label> 
								<input value="${formRe.id}" ${reErrorUserInput} class="row" name="id" placeholder="UserName?">
								<label class="row">${reErrorUser}</label>
							</div>
						</div>

						<div class="col-5 offset-1">
							<div class="row">
								<label class="row">Password?</label> 
								<input value="${formRe.pass}" ${reErrorPassInput} class="row" name="pass" placeholder="PassWord?" type="password">
								<label class="row">${reErrorPass}</label>
							</div>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-5">
							<div class="row">
								<label class="row">FullName?</label> 
								<input value="${formRe.fullname}" ${reErrorNameInput} class="row" name="fullname" placeholder="FullName?">
								<label class="row">${reErrorName}</label>
							</div>
						</div>

						<div class="col-5 offset-1">
							<div class="row">
								<label class="row">Email Address?</label> 
								<input value="${formRe.email}" ${reErrorEmailInput} class="row" name="email" placeholder="Email?">
								<label class="row">${reErrorEmail}</label>
							</div>
						</div>
					</div>
					
					<div class="row mb-4">
						<div class="col-5">
							<div class="row">
								<label class="row">Mã code?</label> 
								<input value="${MaCode}" ${reErrorMaCodeInput} class="row" name="macode" placeholder="Code?">
								<label class="row">${reErrorMaCode}</label>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-9 offset-3">
							<a href="" data-bs-target="#login" data-bs-toggle="modal">login?</a>
							<a href="" data-bs-target="#forgot"
								data-bs-toggle="modal">Forgot pass?</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="submitForm('frmRe')">Register</button>
			</div>
		</div>
	</div>
</div>





<div class="modal fade" id="forgot" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content bg-dark">
			<div class="modal-header">
				<h1 class="modal-title fs-5">Forgot Password</h1>
				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body row">
				<form id="frmForgot" action="/asm/account/forgot" method="post" style="width: 490px">
					<div class="row mb-4">
						<div class="row">
							<label class="col-3">User : </label> 
							<input value="${formF.id}" ${FErrorUserInput} name="id"
							class="col-9" placeholder="User?">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${FErrorUser}</label>
						</div>
					</div>

					<div class="row mb-2">
						<div class="row">
							<label class="col-3">Email : </label> 
							<input value="${formF.email}" ${FErrorEmailInput} name="email" 
							class="col-9" placeholder="email?">
						</div>
						<div class="row">
							<label class="col-9 offset-3">${FErrorEmail}</label>
						</div>
					</div>

					<div class="row mb-3">
						<div class="col-9 offset-3">
							<a href="" data-bs-target="#login" data-bs-toggle="modal">Login?</a>
							<a href="" data-bs-target="#register"
								data-bs-toggle="modal">Register?</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" onclick="submitForm('frmForgot')">Confirm</button>
			</div>
		</div>
	</div>
</div>