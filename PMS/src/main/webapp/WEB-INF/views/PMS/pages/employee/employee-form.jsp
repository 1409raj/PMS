<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row" ng-init="getCurrentEmployeeDynamicFormDetails()">
<div class="col-md-12">
<div id="addEmployeeAlert">{{message}}</div>
</div>
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<form class="form-horizontal" role="form" name="empForm"
					ng-submit="save()">
					<div class="col-md-4">
						<div class="col-md-12">
							<label for="empCode" class="control-label"><spring:message
									code="label.employee.code" /></label> <input type="text" id="empCode"
								placeholder="Employee Code" class="form-control"  ng-disabled={{modifyEmployee}}
								ng-model="empCode" required>
						</div>
						<div class="col-md-12">
							<label for="name" class="control-label"><spring:message
									code="label.employee.name" /></label> <input type="text" id="name"
								placeholder="Employee Name" class="form-control" ng-model="name" required>
						</div>
						
						
						<div class="col-md-12">
							<label for="jobDescription" class="control-label"><spring:message
									code="label.employee.jobDescription" /></label>
							<textarea rows="20" cols="50" id="jobDescription"
								placeholder="Job Description" class="form-control"
								ng-model="jobDescription" required>
									</textarea>
						</div>
						<div class="col-md-12">
							<label for="usr">Choose File:</label> <input type="file" 
								id="file1" file-model="file1" accept="doc, .docx">
								<a ng-href="{{fileName}}" style="color:red;" download="file.doc" id ="documentDownload" >Download</a>
						</div>
						<div class="col-md-12" style="margin-top: 15px;">
							<button type="submit" class="btn btn-outline-secondary"
								style="align: left; float: left;" id="saveEmployee" data-loading-text="<i class='fa fa-spinner fa-spin '></i> Submitting">
								<spring:message code="button.save" />
							</button>
						</div>
					</div>
					<div class="col-md-4">
					<div class="col-md-12">
							<label for="email" class="control-label"><spring:message
									code="label.employee.email" /></label> <input type="email" id="email" 
								placeholder="Employee Email" class="form-control"
								ng-model="email" required>
						</div>
					<div class="col-md-12">
							<label for="dateOfJoining" class="control-label"><spring:message
									code="label.employee.doj" /></label> <input type="date"
								id="dateOfJoining" placeholder="Date of Joining"  ng-disabled={{modifyEmployee}}
								class="form-control" ng-model="dateOfJoining" required>
						</div>
					<div class="col-md-12">
							<label for="dateOfBirth" class="control-label"><spring:message
									code="label.employee.dob" /></label> <input type="date"
								id="dateOfBirth"  
								class="form-control" ng-model="dateOfBirth" required>
						</div>
					<div class="col-md-12">
							<label for="company" class="control-label"><spring:message
									code="label.employee.company" /></label> <input type="text" id="company"
								 placeholder="Company" 
								class="form-control" ng-model="company" required>
						</div>
						<div class="col-md-12">
							<label for="mobile" class="control-label"><spring:message
									code="label.employee.mobile" /></label> <input type="text" id="mobile"
								onkeypress='return event.charCode >= 48 && event.charCode <= 57'
								maxlength="10" placeholder="Employee mobile"
								class="form-control" ng-model="mobile" required>
						</div>
						<div class="col-md-12">
							<label for="department" class="control-label"><spring:message
									code="label.employee.department" /></label> <select
								ng-model="department" id="department"
								ng-change="getAllOrganizationRoles()"
								ng-options="dp as dp.name  for dp in departmentList  | orderBy:'name' track by dp.id"
								class="form-control" required>
								<option value="">Please select</option>
							</select>
						</div>
						<div class="col-md-12">
							<label for="organizationRole" class="control-label"><spring:message
									code="label.employee.organizationRole" /></label> <select
								ng-model="organizationRoleId" id="organizationRole"
								ng-options="dp as dp.name  for dp in organizationRolesList  | orderBy:'role' track by dp.id"
								class="form-control" required>
								<option value="">Please select</option>
							</select>
						</div>
						<div class="col-md-12">
							<label for="role" class="control-label"><spring:message
									code="label.employee.applicationRole" /></label> <select
								ng-model="roleId" id="role"
								ng-options="dp as dp.name  for dp in applicationRolesList  | orderBy:'role' track by dp.id"
								class="form-control" required>
								<option value="">Please select</option>
							</select>
						</div>
						<div class="col-md-12">
									<input type="radio" ng-model="employeeType" value="NON-HOD" name='group' required>NON-HOD
									<input type="radio" ng-model="employeeType" value="HOD" name='group' required>HOD
						</div>
						
					</div>
					<div class="col-md-4">
					
					<div class="col-md-12">
							<label for="designation" class="control-label"><spring:message
									code="label.employee.designation" /></label> <select
								ng-model="designationId" id="designation"
								ng-options="dg as dg.name  for dg in designationList  | orderBy:'name' track by dg.id"
								class="form-control" required>
								<option value="">Please select</option>
							</select>
						</div>
						<div class="col-md-12">
							<label for="qualification" class="control-label"><spring:message
									code="label.employee.qualification" /></label> <input type="text" id="qualification"
								placeholder="Employee Qualification" class="form-control" ng-model="qualification" required>
						</div>
						
						<div class="col-md-12">
							<label for="location" class="control-label"><spring:message
									code="label.employee.location" /></label> <input type="text"
								id="location" ng-model="location" placeholder="Location"
								class="form-control" required>
						</div>

						<div class="col-md-12">
							<label for="firstLevelSuperiorEmpId" class="control-label"><spring:message
									code="label.employee.firstLevelSuperiorID" /></label> <select
								class="form-control" id="firstLevelSuperiorEmpId"
								style="width: 100%;" required>
								<option value="">Please select</option>
							</select>

						</div>
						<div class="col-md-12">
							<label for="secondLevelSuperiorEmpId" class="control-label"><spring:message
									code="label.employee.secondLevelSuperiorID" /></label> <select
								class="form-control" id="secondLevelSuperiorEmpId"
								style="width: 100%;" required>
								<option value="">Please select</option>
							</select>
						</div>
						<div class="col-md-12" ng-repeat="x in employeeDynamicFormDetails">
							<label class="control-label" ng-if="x.status =='1'">{{x.labelName}}</label>
							<input type="text" ng-if="x.status =='1'" class="form-control"
								ng-model="x.data" required>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
</div>
<style>
.select2-selection--single {
	border-radius: 0px !important;
	font-size: 12px;
}

.select2-selection__rendered {
	color: #808080cc;
}
</style>