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

.label {
	margin-bottom: 5px;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.employee.appraisal.list" /></span>
</div>
<div ng-init="getDataOnLoad()">
	<div id="employeeAppraisalListAlert">{{message}}</div>
</div>
<div id="grid1" ui-grid="gridOptions" class="gridStyle"
	 ui-grid-auto-resize style="margin-top: 5px;"></div>

<div class="row" style="margin-top: 15px;" ng-show="showAppraisalStage">
	<div class="col-md-12">
		<table width="100%" border-collapse="collapse" style="border: none;">
			<tr>
				<td style="border: none; text-align: left;"><label
					for="empCode" class="control-label"><spring:message
							code="label.employee.code" /> :&nbsp;</label> <label class="labelText">{{subEmployeeDetails.empCode}}</label></td>
				<td style="border: none; text-align: left;"><label for="name"
					class="control-label"><spring:message
							code="label.employee.name" /> :&nbsp;</label> <label class="labelText">{{subEmployeeDetails.empName}}</label></td>
				<td style="border: none; text-align: left;"><label
					for="firstLevelSuperiorEmpId" class="control-label"><spring:message
							code="label.employee.firstLevelSuperiorID" /> :&nbsp;</label><label
					class="labelText">{{subEmployeeDetails.firstLevelSuperiorName}}</label></td>
			</tr>
			<tr>

				<td style="border: none; text-align: left;"><label
					for="designation" class="control-label"><spring:message
							code="label.employee.designation" /> :&nbsp;</label> <label
					class="labelText">{{subEmployeeDetails.designationName}}</label></td>
				<td style="border: none; text-align: left;"><label
					for="department" class="control-label"><spring:message
							code="label.employee.department" /> :&nbsp;</label><label
					class="labelText">{{subEmployeeDetails.departmentName}}</label></td>
				<td style="border: none; text-align: left;"><label
					for="secondLevelSuperiorEmpId" class="control-label"><spring:message
							code="label.employee.secondLevelSuperiorID" /> :&nbsp;</label><label
					class="labelText">{{subEmployeeDetails.secondLevelSuperiorName}}</label></td>
			</tr>
		</table>
	</div>
	<form class="form-horizontal" role="form" name="appraisalStageForm"
					ng-submit="submit()">
	<div class="col-md-12">
		<input type="radio" ng-model="appraisalStage" value="Goal Setting"
			name='group' required>Goal Setting 
		<input type="radio"
			ng-model="appraisalStage" value="Goal Approval" name='group' required>Goal
		Approval
		 <input type="radio" ng-model="appraisalStage" value="Mid Term Review"
			name='group' required>Mid Term Review
		<input type="radio"
			ng-model="appraisalStage" value="Year End Assessment" name='group'
			required>Year Assessment 
		<input type="radio"
			ng-model="appraisalStage" value="Assessment Approval" name='group'
			required>Assessment Approval
	</div>
	<div class="col-md-3">
	<label for="expired" class="control-label">Submission
							Expired</label><input type="date" id="submissionExpired"
							 class="form-control"
							ng-model="submissionExpired" required>
	</div>
	<div class="col-md-12" style="margin-top: 15px;">
		<button type="submit" class="btn btn-outline-secondary">
			<spring:message code="button.save" />
		</button>
	</div>
	</form>

</div>
