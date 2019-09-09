<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-007 {
	width: 79px;
	text-align: center;
}
table th, table td{
  width: 80px !important;
}
</style>

<div class="section-tag">
	<span><spring:message code="heading.kra.list" /></span>
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
			Wise</label> <select class="form-control" id="selectedEmployeeId"
			style="width: 100%;" required>
			<option value="">Please select</option>
		</select>
	</div>
	<div class="col-md-4">
				<label for="department" class="control-label"><spring:message
									code="label.employee.department" /> Wise</label> <select
								ng-model="department" id="department" ng-change="getAllDepartmentWiseKRAList()"
								ng-options="dp as dp.name  for dp in departmentList  | orderBy:'name' track by dp.id"
								class="form-control" required>
								<option value="">Please select</option>
							</select>
				
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
<div class="form-group" style="overflow: auto;" >
		<input type="button" class="btn btn-sm btn-outline-secondary" value="Export" ng-click="Export()" style="float: right;
    margin-bottom: 5px;" />
		<table border="1" style="border-collapse: collapse;width: max-content !important;" id="tblEmployeeKRAList">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th rowspan="2" style="width: 50px;"><spring:message code="th.kra.sr.no" /></th>
					<th rowspan="2" style="width: 200px !important;">EMPLOYEE NAME</th>
					<th rowspan="2" style="width: 200px !important;">DEPARTMENT</th>
					<th rowspan="2" style="width: 200px !important;">DESIGNATION</th>
					<th rowspan="2" style="width: 400px !important;"><spring:message
							code="th.kra.smart.goal" /></th>
					<th rowspan="2" style="width: 400px !important;"><spring:message
							code="th.kra.targrt" /></th>
					<th rowspan="2"><spring:message
							code="th.kra.achievement.date" /></th>
					<th rowspan="2"><spring:message code="th.weightage" />&nbsp;&nbsp;(%)<br>A
					</th>
					<th colspan="4"><spring:message code="th.mid.year.review" /></th>
					<th colspan="4"><spring:message code="th.final.year.review" /></th>
					<th rowspan="2" style="width: 200px !important">ATTACHMENT</th>
					<th rowspan="2" >MID YEAR RATING <br> A*B</th>
					<th rowspan="2" >Year END RATING <br> A*C
					</th>
					<th rowspan="2"><spring:message
							code="th.final.score" /><br>30% of A*B and 70% of A*C</th>
					<!-- <th rowspan="2">Average</th> -->
					<%-- <th rowspan="2"><spring:message code="th.remrks" /></th> --%>
				</tr>
				<tr>
					<th style="width: 300px !important;"><spring:message code="th.achievement" /></th>
					<th style="width: 100px;"><spring:message code="th.self.rating" /></th>
					<th style="width: 100px;"><spring:message code="th.appraisar.rating" /><br>
					B</th>
					<th style="width: 300px !important">MID YEAR <br> ASSESSSMENT REMARKS</th>
					<th style="width: 300px !important"><spring:message code="th.achievement" /></th>
					<th ><spring:message code="th.self.rating" /></th>
					<th ><spring:message code="th.appraisar.rating" /><br>
					C</th>
					<th style="width: 300px !important">YEAR END <br>ASSESSMENT REMARKS</th>
				</tr>
			</thead>
			<tbody ng-repeat="tKD in teamKRADetails track by $index">
				<tr style="background-color: #315772; color: white; height: 25px;">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionA" /></td>
					<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
				</tr>
				<tr ng-repeat="choice in tKD.sectionAList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.empName}}</td>
					<td class="tableText" valign="top">{{choice.departmentName}}</td>
					<td class="tableText" valign="top">{{choice.designationName}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y" }}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top"style="text-align: left;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					

				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionB" /></td>
					<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>

				</tr>
				<tr data-ng-repeat="choice in tKD.sectionBList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.empName}}</td>
					<td class="tableText" valign="top">{{choice.departmentName}}</td>
					<td class="tableText" valign="top">{{choice.designationName}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top"style="text-align: left;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span>
					</td>
					
					
					
				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionC" /></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>

				</tr>
				<tr data-ng-repeat="choice in tKD.sectionCList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.empName}}</td>
					<td class="tableText" valign="top">{{choice.departmentName}}</td>
					<td class="tableText" valign="top">{{choice.designationName}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span>
					</td>
					

				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionD" /></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>

				</tr>
				<tr data-ng-repeat="choice in tKD.sectionDList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.empName}}</td>
					<td class="tableText" valign="top">{{choice.departmentName}}</td>
					<td class="tableText" valign="top">{{choice.designationName}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span>
					</td>
					
				</tr>
				<tr style="height: 25px;">
					<td colspan="7"></td>
					<td style="font-weight: bold;" align="center">{{tKD.WeightageCount}}</td>
					<td colspan="11"></td>
					<td><strong ng-if="tKD.viewKRATotalCalculation > 0"> {{tKD.viewKRATotalCalculation | number: 2 }}</strong></td>
				</tr>
			</tbody>
		</table>
	</div>