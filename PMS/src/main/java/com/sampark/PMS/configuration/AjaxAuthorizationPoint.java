package com.sampark.PMS.configuration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxAuthorizationPoint extends LoginUrlAuthenticationEntryPoint {

	public AjaxAuthorizationPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setStatus(401);
		response.setContentType("text/html");
		response.getWriter().print("<!DOCTYPE html>" + " <html ng-app='loginApp'>" + " <head>"
				+ " <meta charset='UTF-8'>" + " <title>Login</title>"
				+ " <script src='resources/common/jquery-1.11.0/jquery-1.11.0.min.js'></script>"
				+ " <link rel='icon' href='resources/common/images/pms-favicon.ico' type='image/gif' sizes='16x16'> "
				+ " <link type='text/css' href='resources/common/css/authenty.css' rel='stylesheet' />"
				+ " <link type='text/css' href='resources/common/css/app.css' rel='stylesheet' />"
				+ " <script src='resources/common/bootstrap/bootstrap-3.3.5-dist/js/bootstrap.min.js'></script>"
				+ " <script src='resources/common/angularJS/angular-1.4.5/angular.min.js'></script>"
				+ " <script src='resources/js/login.js'></script>"
				+ " <link href='resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css' rel='stylesheet'>"
				+ " <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>"
				+ " <style>" + ".form-main {" + "	opacity: 0.9;" + "}" + ".form-footer {" + "	opacity: 0.9;" + "}"
				+ ".btn-default {" + "	background-color: #d85347;" + "	border-color: #d85347;" + "}"
				+ ".btn-default:hover {" + "	background-color: #de3a2b;" + "	border-color: #d85347;" + "}"
				+ ".btn-primary.active, .btn-primary:active, .open .dropdown-toggle.btn-primary" + "	{"
				+ "	color: #fff;" + "	background-color: #de3a2b;" + "	border-color: #d85347;" + "}"
				+ ".btn-primary:focus {" + "	color: #fff;" + "	background-color: #de3a2b;"
				+ "	border-color: #d85347;" + "}"
				+ ".btn-primary.active.focus, .btn-primary.active:focus, .btn-primary.active:hover,"
				+ "	.btn-primary:active.focus, .btn-primary:active:focus, .btn-primary:active:hover,"
				+ "	.open.dropdown-toggle.btn-primary.focus, .open .dropdown-toggle.btn-primary:focus,"
				+ "	.open.dropdown-toggle.btn-primary:hover {" + "	color: #fff;" + "	background-color: #de3a2b;"
				+ "	border-color: #d85347;" + "}" + ".circlelogo_login {" + "	width: 180px;" + "	height: 180px;"
				+ "	background: white;" + "	border-radius: 50%;" + "}" + "" + " .headerLogoImg_login {"
				+ " margin-top: 42px !important;" + "width: 114px;" + "border-radius: 32%;" + "}" + " </style>"
				+ " </head>"
				+ " <body style='background-image: url(resources/common/images/PMS-tool-login-screen-background.jpg);' ng-controller='loginCtrl'>"
				+ " <section id='authenty_preview'>" + " <section id='signin_main' class='authenty signin-main'>"
				+ " <div class='section-content'>" + " <div class='wrap'>"
				+ " <div class='container' style='margin-top: 50px;'>" + " <div class='form-wrap'>"
				+ " <div class='row'>" + " <div class='bounceIn fail' id='form_1' data-animation='bounceIn'>" +
				/*
				 * " <div class='form-header'>" +
				 * " <img src='http://95.216.153.174:8080/PMS/images?image=logo.jpg&folder=images' width='186px' style='cursor: pointer;' height='100px' ng-click='toggleForgetPassword()' />"
				 * + " </div>" +
				 */

		"<div style='margin-left: 125px; margin-right: 125px; text-align: center;'> " + "<div class='circlelogo_login'>"
				+ "	<img class='headerLogoImg_login' src='https://pms-hfe.com/PMS/images?image=logo.jpg&folder=images' width='186px'"
				+ "		ng-click='toggleForgetPassword()' width='186px'"
				+ "		style='cursor: pointer;' height='100px' style='float: left;'>" + "		</div>" + "		</div>"
				+

		" <div class='form-main'>" + " <div class='application_name'>PERFORMANCE MANAGEMENT SYSTEM</div> "
				+ " <form name='loginForm' action='login' method='POST' ng-show='forgetPassword'>"
				+ " <div class='form-group'>"
				+ " <input type='text' class='form-control' id='username' name='username' placeholder='USERNAME' required>"
				+ " </div>" + " <div class='form-group'>"
				+ " <input type='password' class='form-control' id='password' name='password' placeholder='PASSWORD' required>"
				+ " </div>"
				+ " <input type='hidden' style='border-radius: 0px;' name='${_csrf.parameterName}' value='${_csrf.token}' />"
				+ " <div class='form-group' style='border-radius: 0px;'>"
				+ " <input type='submit' class='btn btn-block btn-primary btn-default'value='LOGIN' style='font-weight: bold;'>"
				+ " </div>" + " <div style='float: right;'>"
				+ " <span style='cursor: pointer; color: white;' ng-click='toggleForgetPassword()'>Forget Password?</span>"
				+ " </div>" + " </form>"
				+ " <form role='form' ng-hide='forgetPassword' name='forgetPassword' ng-submit='submit()'>"
				+ " <div class='form-group'>" +

		" <input id='email' type='text' class='form-control' name='email' ng-model='email' placeholder='Enter registered email address' required>"
				+

		" </div>" + " <div class='form-group'>" +

		" <button type='submit' style='font-weight: bold;' class='btn btn-block btn-primary btn-default'>" + "SUBMIT"
				+ " </button>" + " </div>" + " </form>" + " </div>" + " </div>" + " </div>" + " </div>" + " </div>"
				+ " </div>" + " </div>" + " </section>" + " </section>" + " </body>" + " </html>");
		response.getWriter().flush();
	}
}