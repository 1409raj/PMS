<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span><spring:message code="heading.employee.viewEmployee" /></span>
</div>
<style>
.label{
    font-size: 14px;
     margin-bottom: 8px;
}
</style>
<div class="col-md-12">
	<div class="col-md-6">
		<label class="control-label">Employee Code : <span class="span-size">{{employeeDetails.empCode}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Employee Name : <span class="span-size">{{employeeDetails.empName}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Application Role :<span class="span-size">{{employeeDetails.roleName}}</span> </label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Organization Role :<span class="span-size">{{employeeDetails.organizationalRoleName}}</span> </label>
	</div>
<div class="col-md-6">
		<label class="control-label">Email :<span class="span-size">{{employeeDetails.email}}</span> </label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Designation :<span class="span-size">{{employeeDetails.designationName}}</span> </label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Department : <span class="span-size">{{employeeDetails.departmentName}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Date of Joining : <span class="span-size">{{employeeDetails.dateOfJoining
			| date : "dd.MM.y"}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Qualification : <span class="span-size">{{employeeDetails.qualificationName}}</span></label>
	</div>

	<div class="col-md-6">
		<label class="control-label">First Level Superior : <span class="span-size">{{employeeDetails.firstLevelSuperiorName}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Second Level Superior : <span class="span-size">{{employeeDetails.secondLevelSuperiorName}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Location : <span class="span-size">{{employeeDetails.location}}</span></label>
	</div>

	<div class="col-md-6">
		<label class="control-label">Mobile : <span class="span-size">{{employeeDetails.mobile}}</span></label>
	</div>
	<div class="col-md-6">
		<label class="control-label">Job Description : <span class="span-size">{{employeeDetails.jobDescription}}</span></label>
	</div>
   <div class="col-md-6">
				<button type="button"
					class="btn btn-outline-secondary" ng-click="back()"> Back</button>
	</div> 
</div>