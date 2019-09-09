<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="ISO-8859-1">
<link type="text/css" rel="stylesheet" media="all"
	href="resources/common/adminLT/AdminLTE.min.css" id="theme_color" />
<link
	href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/jquery.dataTables.min.css"
	rel="stylesheet">
<link
	href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/buttons.dataTables.min.css"
	rel="stylesheet">
<link type="text/css" rel="stylesheet" media="all"
	href="resources/common/css/index.css?v=0.13" id="theme_common" />
<link type="text/css" rel="stylesheet" media="all"
	href="resources/common/css/default.css?v=0.2" id="theme_color" />
<link type="text/css" rel="stylesheet" media="all"
	href="resources/common/adminLT/_all-skins.min.css" id="theme_color" />
<script src="resources/common/adminLT/jquery.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/jquery.dataTables.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/dataTables.buttons.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/buttons.flash.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/jszip.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/pdfmake.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/vfs_fonts.js"></script>
	<script
	src="resources/common/tableToExcel/table2excel.js"></script>
	
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/buttons.html5.min.js"></script>
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/buttons.print.min.js"></script>
<script
	src="https://cdn.datatables.net/fixedcolumns/3.2.6/js/dataTables.fixedColumns.min.js"></script>
	
<!-- Angular JS -->
<script src="resources/common/angularJS/angular-1.4.5/angular.min.js"></script>
<script src="resources/common/angularJS/angular-1.4.5/angular-touch.js"></script>
<script
	src="resources/common/angularJS/angular-1.4.5/angular-animate.js"></script>
<script src="resources/common/angularJS/angular-1.4.5/angular-route.js"></script>

<!-- Searchable dropdown -->
<script src="resources/common/adminLT/select2.full.min.js"></script>
<link rel="styleSheet" href="resources/common/adminLT/select2.min.css">
<!-- UI_GRID -->
<link rel="styleSheet"
	href="resources/common/ui-grid-3.0.0-RC.18-7774d30/ui-grid.min.css" />
<script
	src="resources/common/ui-grid-3.0.0-RC.18-7774d30/ui-grid.min.js"></script>
<!-- Grid Export -->
<script src="resources/common/grid-export/csv.js"></script>
<script src="resources/common/grid-export/pdfmake.js"></script>
<script src="resources/common/grid-export/vfs_fonts.js"></script>

<!-- Chart in Dashboard -->
<script src="resources/common/angularJS/angular-1.4.5/Chart.min.js"></script>
<script
	src="resources/common/angularJS/angular-1.4.5/angular-chart.min.js"></script>
<script src="resources/common/charts/Chart.bundle.min.js"></script>
<!-- Datepicker -->
<script
	src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/ui-bootstrap-tpls-0.14.3.min.js"></script>
<!-- <link rel="stylesheet"
	href="resources/common/datepicker/bootstrap-datepicker.min.css" />
<script src="resources/common/datepicker/bootstrap-datepicker.min.js"></script>
<script src="resources/common/datepicker/moment.min.js"></script> -->
<!-- Typeahead -->
<link rel="stylesheet"
	href="resources/common/ng-tags-input/ng-tags-input.css" />
<script src="resources/common/ng-tags-input/ng-tags-input.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/common/css/googleapis.css">
<script src="resources/common/charts/Chart.js"></script>
<!-- Sweet Alerts -->
<script src="resources/common/sweetAlert/sweetalert.min.js"></script>
<!-- AdminLTE Themes -->
<script src="resources/common/adminLT/adminlte.min.js"></script>

<!-- CK Editor -->
<!-- <script src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/ckeditor.js"></script> -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/ckeditor/4.2/ckeditor.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="resources/common/bootstrap/bootstrap-3.3.5-dist/js/bootstrap3-wysihtml5.all.min.js"></script>
<link rel="stylesheet" href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap3-wysihtml5.min.css">
<link rel="icon" href="resources/common/images/pms-favicon.ico" type="image/gif" sizes="16x16">

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css" rel="stylesheet" />

<title
	ng-bind="'<spring:message code="title.application" /> - ' + title"></title>
	
	
	<style>
	/* width */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
  
}

/* Track */
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px grey; 
 
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: #315772; 
  

}


	</style>
	
	
</head>
<body class="skin-blue" id="main_body">
	<div ng-controller="MainCtrl">
		<div class="container" ng-init="getCurrentAppraisalYearDetails()">
			<nav class="navbar navbar-fixed-top navbar-inverse nvcolor">
				<div class="navbar-brand navbrand leftMenuPart"
					style="display: inline;" id="menu-toggle"
					ng-click="changeStyleMap()">
					<span class="glyphicon glyphicon-tasks headerMenuLeft" style="">
					</span>
				</div>
				<%--  --%>
				<div class="circlelogo hidden-xs">
					<img class="headerLogoImg" src="${imagebasePath}" class="hidden-xs"
						style="float: left;">
				</div>
				<div class="circle_logo_small hidden-sm hidden-md hidden-lg">
					<img class="headerLogoImg_small" src="${imagebasePath}"
						class="hidden-sm hidden-md hidden-lg" style="float: left;">
				</div>
				<ul class="appInfo">
					<li class="appName"><span class="hidden-xs"><spring:message
								code="title.application" /></span> <span
						class="hidden-sm hidden-md hidden-lg"><spring:message
								code="title.shortapplication" /></span> <%-- <span class="appVersion">|
							VERSION ${appVersion} </span> --%></li>
					<%-- <li class="appVersion">VERSION ${appVersion}</li> --%>
				</ul>
				<a href="logoutPMS" id="" class="" ng-click="clearActiveYear()">
					<span class="glyphicon glyphicon-off headerMenuRight"></span>
				</a> <span class="userNameMenu hidden-xs"><span
					class="glyphicon glyphicon-user"></span>${empName}</span>
				<ul class="appInfo" style="float: right; margin-right: 30px;"
					id="appraisalYearID">
					<li class="appVersion" style="margin-top: -5px;"><select
						ng-model="appraisalYear" ng-change="setAppraisalYearDetails()"
						ng-options="c as c.name  for c in appraisalYearList track by c.id"
						class="form-control input-sm">
							<option value="">Select Year</option>
					</select></li>
				</ul>

				<!-- 	<ul class="appInfo list-inline" style="float: right;">
        <li>Stage</li>
        <li><input type="text" disabled
								class="form-control"  
								ng-model="stage"></li>
      </ul>
      -->
				<%--  <c:if test = "${subEmployeeCount > 0}">
		<ul class="nav navbar-nav">
        <!-- <li ><a href="#">Link</a></li>-->
       <!--  <li><a href="#/dashboard">Dashboard</a></li>  -->
        <li class="dropdown">
          <a  class="dropdown-toggle" data-toggle="dropdown">Appraisal <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#/team-appraisal">Team Appraisal</a></li>
          </ul>
        </li>
      </ul>
		
		
      </c:if> --%>

				<%--   <c:if test = "${userRoleName == 'ROLE_ADMIN'}">
		<ul class="nav navbar-nav">
        <li><a href="#/dashboard">Dashboard</a></li> 
        <li class="dropdown">
          <a  class="dropdown-toggle" data-toggle="dropdown">Appraisal <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#/appraisal-cycle">Appraisal Cycle</a></li>
          </ul>
        </li>
         <li class="dropdown">
          <a  class="dropdown-toggle" data-toggle="dropdown">Employee<b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#/add-employee">New Employee</a></li>
            <li><a>Add In Bulk</a></li>
          </ul>
        </li>
      </ul>
		
		
      </c:if>  --%>

				<!-- <ul >
			<li class="nav-li" style="margin-left: 110px;"><a href="#/financial_objective">KRA</a></li>
			<li class="nav-li active"><a href="#/bussiness_developement">Bussiness
					Developement</a></li>
			<li class="nav-li"><a href="#/operational_objective"> Operatinal Objectives</a></li>
			<li class="nav-li"><a href="#/organizational_commitment"> Organizational
					Commitment</a></li>

			<li class="nav-li"><a href="#/final_evaluation"> Final Evaluation</a></li>
		</ul> -->
			</nav>

			<!-- Read messages -->

			<input type="hidden" id="smartGoalOne"
				value="<spring:message code='kra.sectionD.smart.goal.one' />">
			<input type="hidden" id="smartGoalTwo"
				value="<spring:message code='kra.sectionD.smart.goal.two' />">
			<input type="hidden" id="smartGoalThree"
				value="<spring:message code='kra.sectionD.smart.goal.three' />">
			<input type="hidden" id="targetOne"
				value="<spring:message code='kra.sectionD.target.one' />"> <input
				type="hidden" id="targetTwo"
				value="<spring:message code='kra.sectionD.target.two' />"> <input
				type="hidden" id="targetThree"
				value="<spring:message code='kra.sectionD.target.three' />">
			<input type="hidden" id="weightageOne"
				value="<spring:message code='kra.sectionD.weightage.one' />">
			<input type="hidden" id="weightageTwo"
				value="<spring:message code='kra.sectionD.weightage.two' />">
			<input type="hidden" id="weightageThree"
				value="<spring:message code='kra.sectionD.weightage.three' />">
			<input type="hidden" id="menuDashboard"
				value="<spring:message code='heading.dashboard' />" /> <input
				type="hidden" id="menuLogout"
				value="<spring:message code='menu.logout' />" /> <input
				type="hidden" id="titleApplication"
				value="<spring:message code='title.application' />" /> <input
				type="hidden" id="alertSessionExpired"
				value="<spring:message code='alert.session.expired' />" /> <input
				type="hidden" id="alertAccessDenied"
				value="<spring:message code='text.access.denied' />" /><input
				type="hidden" id="globalUserName" ng-model="globalUserName"
				value="${userName}" /> <input type="hidden" id="globalUserRoleName"
				value="${userRoleName}" />
		</div>
		<div class="row">
			<div id="wrapper">
				<!------------------sidebar--------------->
				<div id="sidebar-wrapper">
					<%@ include file="/WEB-INF/views/PMS/pages/menu/left-menu.jsp"%>
				</div>
				<!--end sidebar-wrapper  -->

				<!--page-content-->
				<!-- style="padding-top: 0px !important" -->
				<div id="page-content-wrapper">
					<div class="container-fluid">
						<div ng-view ></div>
					</div>
				</div>
			</div>
		</div>
		<script>
			$("#menu-toggle").click(function(e) {
				e.preventDefault();
				$("#wrapper").toggleClass("toggled");
			});
		</script>

	</div>

</body>
</html>
<script src="resources/js/app.js"></script>


<style>
.list-inline>li {
	display: inline-block;
	padding-right: 5px;
	padding-left: 5px;
	margin-top: -7px;
}
/* for file upload */
.fileListItem {
	background-color: #AEB6BF;
	border: 1px solid #2E4053;
	margin-bottom: 5px;
	padding: 8px;
}

.fileListItem .fileName {
	word-wrap: break-word;
}

.removeButton {
	float: right;
}

.appInfo {
	float: left;
	color: white;
	height: 40px;
	list-style-type: none;
	padding-left: 22px;
	margin-left: 145px;
	padding-top: 25px;
}

.appName {
	line-height: 15px;
	font-size: 20px;
	font-weight: bold;
	color: #fbc95a;
}

.appVersion {
	line-height: 15px;
	font-size: 15px;
	font-weight: 100;
}

.userNameMenu {
	color: white;
	font-size: 16px;
	line-height: 65px;
	vertical-align: middle;
	float: right;
	padding-right: 5px;
}

.ui-grid-cell-focus {
	color: blue;
	font-weight: bold;
	background-color: #c9dde1 !important;
}

.navbar-default .navbar-nav>li.dropdown:hover>a, .navbar-default .navbar-nav>li.dropdown:hover>a:hover,
	.navbar-default .navbar-nav>li.dropdown:hover>a:focus {
	background-color: rgb(231, 231, 231);
	color: rgb(85, 85, 85);
}

li.dropdown:hover>.dropdown-menu {
	display: block;
}

.navbar-nav {
	float: left;
	margin: 0;
	margin-left: 8%;
}

.navbar-inverse .navbar-nav>li>a {
	color: white;
}

.navbar-inverse .navbar-nav>li>a:focus, .navbar-inverse .navbar-nav>li>a:hover
	{
	color: #2e4053;
	background-color: white;
}

.navbar-inverse .navbar-nav>li>a {
	color: white;
}
.container-fluid {
    padding-right: 0px !important;
    }
</style>