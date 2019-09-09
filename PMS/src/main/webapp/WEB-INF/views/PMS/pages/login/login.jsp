<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="loginApp">
<head>
<meta charset="UTF-8">
<title>Login</title>
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
	<link rel="icon" href="resources/common/images/pms-favicon.ico" type="image/gif" sizes="16x16">
<style>
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
<body
	style="background-image: url(resources/common/images/PMS-tool-login-screen-background.jpg);"
	ng-controller="loginCtrl">
	<section id="authenty_preview">
		<section id="signin_main" class="authenty signin-main">
			<div class="section-content">
				<div class="wrap">
					<div class="container" style="margin-top: 50px;">
						<div class="form-wrap">
							<div class="row">
								<div class="bounceIn fail" id="form_1" data-animation="bounceIn">
									<!-- <div class="form-header"> -->
									<%-- ${imagebasePath} --%>
									<!-- resources/common/images/logo.jpg -->
									<div
										style="margin-left: 125px; margin-right: 125px; text-align: center;">
										<div class="circlelogo_login">
											<img class="headerLogoImg_login" src="${imagebasePath}"
												ng-click="toggleForgetPassword()" width="186px"
												style="cursor: pointer;" height="100px" style="float: left;">
										</div>
									</div>
									
									<%-- <img src="${imagebasePath}" width="186px"
											style="cursor: pointer;" height="100px"
											ng-click="toggleForgetPassword()" /> --%>
									<!-- </div> -->
									<div class="form-main">
									<div class="application_name">PERFORMANCE MANAGEMENT SYSTEM</div>
										<c:if test="${not empty error}">
											<div class="error">
												<span
													style="color: red; margin-left: 10px; font-size: small;">${error}</span>
											</div>
										</c:if>
										<c:if test="${not empty msg}">
											<div class="msg">${msg}</div>
										</c:if>

										<form name='loginForm' action="<c:url value='/login' />"
											method='POST' ng-show="forgetPassword">

											<div class="form-group">
												<!-- <label class="input-group-addon" style="border-radius: 0px;"
													for="username"><i class="fa fa-user"></i></label> -->
												<input type="text" class="form-control" id="username"
													name="username" placeholder="USERNAME" required>
											</div>
											<div class="form-group">
												<!-- <label class="input-group-addon" style="border-radius: 0px;"
													for="password"><i class="fa fa-lock"></i></label> -->
												<input type="password" class="form-control" id="password"
													name="password" placeholder="PASSWORD" required>
											</div>
											<input type="hidden" style="border-radius: 0px;"
												name="${_csrf.parameterName}" value="${_csrf.token}" />

											<div class="form-group" style="border-radius: 0px;">
												<input type="submit"
													class="btn btn-block btn-primary btn-default" value="LOGIN"
													style="font-weight: bold;">
											</div>
											<div style="float: right;">
												<span style="cursor: pointer;color: white;"
													ng-click="toggleForgetPassword()">FORGET PASSWORD</span>
											</div>
										</form>

										<form role="form" ng-hide="forgetPassword"
											name="forgetPassword" ng-submit="submit()">
											<div class="col-md-12" id="forgetPasswordAlert">{{message}}</div>
											<div class="form-group">
												<!-- <div class="input-group form-actions input-sm"> -->
													<!-- <span class="input-group-addon"><i
														class="glyphicon glyphicon-user"></i></span>  --><input id="email"
														type="text" class="form-control" name="email"
														ng-model="email"
														placeholder="Enter registered email address" required>
												<!-- </div> -->
											</div>
											<div class="form-group">
												
													<button type="submit" style="font-weight: bold;"
														class="btn btn-block btn-primary btn-default">
														<spring:message code="button.save" />
													</button>
												
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
	var app = angular.module('loginApp', []);
	app
			.controller(
					'loginCtrl',
					function($window, $scope, $timeout, $http) {

						$scope.toggleForgetPassword = function() {
							$scope.forgetPassword = !$scope.forgetPassword;
						}

						$scope.submit = function() {
							var res = $http.post('/PMS/forgetPassword',
									$scope.email);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											document
													.getElementById("forgetPasswordAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("forgetPasswordAlert").className = "";
														$scope.message = "";
													}, 5000);
											$scope.email = null;
										} else {
											$scope.message = $scope.result.string;
											document
													.getElementById("forgetPasswordAlert").className = "alert-dangers";
											$timeout(
													function() {
														document
																.getElementById("forgetPasswordAlert").className = "";
														$scope.message = "";
													}, 5000);
										}
									});
						}

					});
</script>