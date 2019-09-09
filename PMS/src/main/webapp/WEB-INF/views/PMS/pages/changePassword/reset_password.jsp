<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="restPasswordApp">
<head>
<meta charset="UTF-8">
<title>RestPassword</title>
<script src="resources/common/jquery-1.11.0/jquery-1.11.0.min.js"></script>
<link type="text/css" href="resources/common/css/authenty.css"
	rel="stylesheet" />
<link type="text/css" href="resources/common/css/app.css"
	rel="stylesheet" />
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="resources/common/angularJS/angular-1.4.5/angular.min.js"></script>
<link
	href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.form-main {
	opacity: 0.9;
}

.form-footer {
	opacity: 0.9;
}

.btn-default {
	background-color: #d85347;
	border-color: #d85347;
}

.btn-default:hover {
	background-color: #de3a2b;
	border-color: #d85347;
}

.btn-primary.active, .btn-primary:active, .open>.dropdown-toggle.btn-primary
	{
	color: #fff;
	background-color: #de3a2b;
	border-color: #d85347;
}

.btn-primary:focus {
	color: #fff;
	background-color: #de3a2b;
	border-color: #d85347;
}

.btn-primary.active.focus, .btn-primary.active:focus, .btn-primary.active:hover,
	.btn-primary:active.focus, .btn-primary:active:focus, .btn-primary:active:hover,
	.open>.dropdown-toggle.btn-primary.focus, .open>.dropdown-toggle.btn-primary:focus,
	.open>.dropdown-toggle.btn-primary:hover {
	color: #fff;
	background-color: #de3a2b;
	border-color: #d85347;
}
.circlelogo_login {
	width: 180px;
	height: 180px;
	background: white;
	border-radius: 50%;
}

.headerLogoImg_login {
	margin-top: 42px !important;
	width: 114px;
	border-radius: 32%;
}
</style>
</head>
<!-- background-image: url(resources/common/images/banner-rooftop-solar13.jpg); -->
<body style="background-image: url(resources/common/images/PMS-tool-login-screen-background.jpg);" ng-controller="resetPasswordCtrl">
	<section id="authenty_preview">
		<section id="signin_main" class="authenty signin-main">
			<div class="section-content">
				<div class="wrap">
					<div class="container" style="margin-top: 50px;">
						<div class="form-wrap">
							<div class="row">
								<div class="bounceIn fail" id="form_1" data-animation="bounceIn">
									<!-- <div class="form-header">
										<img src="resources/common/images/logo-site.jpg" width="186px"
											style="cursor: pointer;" height="100px"/>
									</div> -->
									<div
										style="margin-left: 125px; margin-right: 125px; text-align: center;">
										<div class="circlelogo_login">
											<img class="headerLogoImg_login" src="${imagebasePath}"
												ng-click="toggleForgetPassword()" width="186px"
												style="cursor: pointer;" height="100px" style="float: left;">
										</div>
									</div>
									<div class="form-main">
									<div class="application_name">PERFORMANCE MANAGEMENT SYSTEM</div>
									<input type="hidden" value="${token}" id="token"/>
										<form role="form"
											name="resetPassword" ng-submit="submit()">
											<div class="col-md-12" id="resetPasswordAlert">{{message}}</div>
											<div class="form-group">
												<div class="input-group form-actions input-sm">
													<span class="input-group-addon"><i
														class="fa fa-envelope"></i></span> <input id="email"
														type="email" class="form-control" name="email" ng-model="email"
														placeholder="Enter registered email address" required>
												</div>
												<div class="input-group form-actions input-sm">
													<span class="input-group-addon"><i
														class="fa fa-lock"></i></span> <input id="newPassword"
														type="password" class="form-control" name="newPassword" ng-model="newPassword"
														placeholder="Enter new password" required>
												</div>
												<div class="input-group form-actions input-sm">
													<span class="input-group-addon"><i
														class="fa fa-lock"></i></span> <input id="confirmPassword"
														type="password" class="form-control" name="confirmPassword" ng-model="confirmPassword"
														placeholder="Enter confirm password" required>
												</div>
											</div>
											<div class="form-group">
												<div style="padding-left: 10px; padding-right: 10px;">
													<button type="submit"
														class="btn btn-block btn-primary btn-default">
														<spring:message code="button.save" />
													</button>
												</div>
											</div>
										</form>
								</div>

									<!-- <div class="form-footer" style="background-color:#436074;opacity: 0.7;">
										<div class="row">
											<div class="col-xs-12"
												style="text-align: center; color: white;">
												<span style="font-size:16px;">Human Resource Management System<br>Hero Future Energies<br>India
												</span>
											</div>
										</div>
									</div> -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
</body>
</html>
<script>
	var app = angular.module('restPasswordApp', []);
	app
			.controller(
					'resetPasswordCtrl',
					function($window, $scope, $timeout, $http) {
						console.log();
						$scope.submit = function() {
							if ($scope.newPassword != $scope.confirmPassword) {
								$scope.message = "New and Confirm password doesn't matched.";
								document
								.getElementById("resetPasswordAlert").className = "alert-dangers";
							} else {
							var input ={
									"email":$scope.email,
									"token":document.getElementById("token").value,
									"password":$scope.newPassword
							}
							var res = $http.post('/PMS/resetPassword',input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											document
													.getElementById("resetPasswordAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("resetPasswordAlert").className = "";
														$scope.message = "";
														$scope.email = null;
														$scope.newPassword = null;
														$scope.confirmPassword = null;
														$window.location.href = 'login';
													}, 5000);
										}
										else
											{
											$scope.message = $scope.result.string;
											document
													.getElementById("resetPasswordAlert").className = "alert-dangers";
											$timeout(
													function() {
														document
																.getElementById("resetPasswordAlert").className = "";
														$scope.message = "";
													}, 5000);
											}
									});
							}
						}

					});
</script>