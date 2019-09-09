<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-007 {
	width: 79px;
	text-align: center;
}
</style>

<div class="section-tag">
	<span><spring:message code="heading.trainingNeeds.list" /></span>
</div>

<div class="row" ng-init="getAllEmployeeList()">

<div class="col-md-2">
		<label for="selectedEmployeeId" class="control-label">Employee
			Type</label> <select class="form-control input-sm" ng-model="type"  ng-change="employeeType()"
			style="width: 100%;" required>
			<option value=2>All</option>
			<option value=1>Active</option>
			<option value=0>Deleted</option>
		</select>
	</div>
	<div class="col-md-4">
		<label for="selectedEmployeeId" class="control-label">Employee
			Select</label>
			 <select class="form-control" id="selectedEmployeeId"
			style="width: 100%;" required>
			<option value="">Please select</option>
		</select>
	</div>
	
	
	
				<div class="col-md-2" style="margin-top: 15px;">
				<label for="allEmployee" class="control-label"
						style="margin-right: 10px;">ALL</label>
				 <input type="checkbox" ng-change="allEmployeeTrainingNeeds('ALL')" 
						ng-model="all" id="allEmployee">
				
				</div>
</div>

<div class="row" style="margin-top: 15px;">
	<div class="col-md-4">
		<label for="empCode" class="control-label"><spring:message
				code="label.employee.code" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.empCode}}</label>
	</div>
	<div class="col-md-4">
		<label for="name" class="control-label"><spring:message
				code="label.employee.name" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.empName}}</label>
	</div>
	<div class="col-md-4">
		<label for="email" class="control-label"><spring:message
				code="label.employee.email" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.email}}</label>
	</div>
	<div class="col-md-4">
		<label for="mobile" class="control-label"><spring:message
				code="label.employee.mobile" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.mobile}}</label>
	</div>
	<div class="col-md-4">
		<label for="designation" class="control-label"><spring:message
				code="label.employee.designation" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.designationName}}</label>
	</div>
	<div class="col-md-4">
		<label for="department" class="control-label"><spring:message
				code="label.employee.department" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.departmentName}}</label>
	</div>
	<div class="col-md-4">
		<label for="dateOfJoining" class="control-label"><spring:message
				code="label.employee.doj" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.dateOfJoining
			| date : "dd.MM.y" }}</label>
	</div>
	<div class="col-md-4">
		<label for="qualification" class="control-label"><spring:message
				code="label.employee.qualification" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.qualificationName}}</label>
	</div>
	<div class="col-md-4">
		<label for="firstLevelSuperiorEmpId" class="control-label"><spring:message
				code="label.employee.firstLevelSuperiorID" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.firstLevelSuperiorName}}</label>
	</div>
	<div class="col-md-4">
		<label for="secondLevelSuperiorEmpId" class="control-label"><spring:message
				code="label.employee.secondLevelSuperiorID" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.secondLevelSuperiorName}}</label>
	</div>
	<div class="col-md-4">
		<label for="location" class="control-label"><spring:message
				code="label.employee.location" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{employeeDetails.location}}</label>
	</div>
</div>
<div class="form-group" style="margin-top: 15px;">
		<div class="section-tag">
			<b><spring:message code="heading.trainingNeeds" /></b>
		</div>
		<input type="button" class="btn btn-sm btn-outline-secondary" value="Export" ng-click="Export()" style="float: right;
    margin-bottom: 5px;" />
		<table border="1" style="width: 100%;border-collapse: collapse;"  id="tblTrainingNeeds" >
			<thead style="background-color: #315772; color: #ffffff;">
				<tr style="height: 25px;">
					<th width ="2%;">S.NO.</th>
					<th width ="5%;">EMP CODE</th>
					<th width ="15%;">EMP NAME</th>
					<th width ="15%;">DEPARTMENT</th>
					<th width ="15%;">DESIGNATION</th>
					<th width ="20%;">Training/Course Topic</th>
					<th width ="20%;">Reason for choosing the topic /training and how ...this
						training will Improve his/her working and department working</th>
					<th width ="5%;">Proposed Training man-hours</th>
					<th width ="5%;">Approve/Reject</th>
					<th width ="15%;">Remarks</th>
					<!-- <th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th> -->
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="choice in trainingNeedsDetails">
					<th>{{$index+1}}</th>
					<td style="text-align: left;height: 35px;">{{choice.empCode}}</td>
					<td style="text-align: left;height: 35px;">{{choice.empName}}</td>
					<td style="text-align: left;height: 35px;">{{choice.departmentName}}</td>
					<td style="text-align: left;height: 35px;">{{choice.designationName}}</td>
					<td style="text-align: left;height: 35px;">{{choice.trainingTopic}}</td>
					<td  style="text-align: left;height: 35px;">{{choice.trainingReasons}}</td>
					<td  style="text-align: left;height: 35px;">{{choice.manHours}}</td>
					<td  style="text-align: left;height: 35px;">{{choice.approvedReject}}</td>
					<td  style="text-align: left;height: 35px;">{{choice.remarks}}</td>

				</tr>
			</tbody>
		</table>
	</div>

<!-- Modal Update Field -->
<%-- <div class="modal fade" id="trainingNeedsUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"><spring:message code="heading.trainingNeeds.modify"/></h4>
			</div>
			<div class="modal-body">		
			<form class="form-horizontal" role="form"  name="updateTrainingNeeds" ng-submit="save()">
				<div class="row">
					<div class="col-md-6">
						<label for="updateName" class="control-label"><spring:message code="label.trainingNeeds.name" /></label> 
						<input type="text" id="updateName"
							placeholder="Enter Training Needs" class="form-control" ng-model="updateName" required>
					</div>
					<div class="col-md-6">
						<label for="updateDescription" ><spring:message code="label.description" /></label>
						<textarea class="form-control" rows="3" id="updateDescription"
							placeholder="Description" ng-model="updateDescription">
                    </textarea>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button  type="submit"
							class="btn btn-sm btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
				<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal"><spring:message code="button.cancel" /></button>
					</div>
				</div>
			</form>	
			
			</div>
		</div>
	</div>
</div> --%>

