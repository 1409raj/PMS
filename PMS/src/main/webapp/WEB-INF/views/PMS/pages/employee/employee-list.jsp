<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.btn-primary {
	border-radius: 50%;
}
.btn-primary:hover {
	color: #fff;
	background-color: #88c566;
	border-color: #88c566;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.employee.list" /></span>
</div>
<div ng-init="getDataOnLoad()">
	<div id="employeeListAlert">{{message}}</div>
</div>
<!------------------------------- for grid  --------------------------------->

<div class="row">
<div class="col-md-2">
		<label for="selectedEmployeeId" class="control-label">Employee
			Type</label> <select class="form-control input-sm" ng-model="type"  ng-change="employeeType()"
			style="width: 100%;" required>
			<option value=2>All</option>
			<option value=1>Active</option>
			<option value=0>Deleted</option>
		</select>
	</div>

</div>



<div id="grid1" ui-grid="gridOptions" class="gridStyle"
	ui-grid-auto-resize style="margin-top: 5px;" ui-grid-exporter></div>

