<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
td {
	height: 25px !important;
	font-weight: bold;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.employe.promotion" /></span>
	<span style="float:right;color:red;" ng-if="(employeePromotionDetails[0].ceoCheck == null || employeePromotionDetails[0].ceoCheck == 0)  && employeePromotionDetails.length > 0">In Process</span>
	<span style="float:right;color:green;" ng-if="employeePromotionDetails[0].ceoCheck == 1">Approved</span>
</div>
<div class="row" ng-init="getEmployeeLists()">
	<div class="col-md-4">
		<label for="subEmployeeListId" class="control-label">Select
			Employee</label><select ng-model="subEmployeeCode" id="subEmployeeListId"
			ng-change="getEmployeeBasicDetails()"
			ng-options="em as em.empCodeWithName  for em in subEmployeeList"
			class="form-control" required>
			<option value="">Please select</option>
		</select>
	</div>
</div>
<div class="row" style="margin-top: 10px;">
	<div class="col-md-4">
		<label for="empCode" class="control-label"><spring:message
				code="label.employee.code" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.empCode}}</label>
	</div>
	<div class="col-md-4">
		<label for="name" class="control-label"><spring:message
				code="label.employee.name" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.empName}}</label>
	</div>
	<div class="col-md-4">
		<label for="email" class="control-label"><spring:message
				code="label.employee.email" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.email}}</label>
	</div>
	<div class="col-md-4">
		<label for="mobile" class="control-label"><spring:message
				code="label.employee.mobile" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.mobile}}</label>
	</div>
	<div class="col-md-4">
		<label for="designation" class="control-label"><spring:message
				code="label.employee.designation" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.designationName}}</label>
	</div>
	<div class="col-md-4">
		<label for="department" class="control-label"><spring:message
				code="label.employee.department" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.departmentName}}</label>
	</div>
	<div class="col-md-4">
		<label for="dateOfJoining" class="control-label"><spring:message
				code="label.employee.doj" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.dateOfJoining
			| date : "dd.MM.y" }}</label>
	</div>
	<div class="col-md-4">
		<label for="qualification" class="control-label"><spring:message
				code="label.employee.qualification" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.qualificationName}}</label>
	</div>
	<div class="col-md-4">
		<label for="firstLevelSuperiorEmpId" class="control-label"><spring:message
				code="label.employee.firstLevelSuperiorID" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.firstLevelSuperiorName}}</label>
	</div>
	<div class="col-md-4">
		<label for="secondLevelSuperiorEmpId" class="control-label"><spring:message
				code="label.employee.secondLevelSuperiorID" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.secondLevelSuperiorName}}</label>
	</div>
	<div class="col-md-4">
		<label for="location" class="control-label"><spring:message
				code="label.employee.location" /> :&nbsp;</label><label
			class="control-label font_weight_100">{{subEmployeeDetails.location}}</label>
	</div>
</div>
<!-- <div class="row" style="margin-top: 10px;">
	<div class="col-md-12">
		<div class="col-md-12 overAllRating">
			OVERALL RATING FOR THE ASSESSMENT PERIOD 2017 - 2018 <br> (To be
			taken from Assessment form of the Individual for the Year)
		</div>
		<table border="1" width="100%">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th></th>
					<th>MAX. RATING</th>
					<th>ASSESSEE RATING</th>
					<th>ASSESSOR RATING <br>A
					</th>
					<th>Weightage(FIXED)<br>B
					</th>
					<th>Weighted Score</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="tableText">SECTION 1:KRA's</td>
					<td>5</td>
					<td></td>
					<td></td>
					<td>75%</td>
					<td></td>
				</tr>
				<tr>
					<td class="tableText">SECTION 2:EXTRA ORDINARY INITIATIVES</td>
					<td>5</td>
					<td></td>
					<td></td>
					<td>10%</td>
					<td></td>
				</tr>
				<tr>
					<td class="tableText">SECTION 3:BEHAVIOURAL COMPETENCE
						ASSESSMENT</td>
					<td>5</td>
					<td></td>
					<td></td>
					<td>15%</td>
					<td></td>
				</tr>
				<tr>
					<td class="tableText">TOTAL SCORE</td>
					<td></td>
					<td></td>
					<td></td>
					<td>100%</td>
					<td></td>
				</tr>

			</tbody>
		</table>
	</div>
</div> -->
<!-- <div class="row" style="margin-top: 10px;">
	<div class="col-md-12">
		<table border="1" width="100%">
			<tr>
				<td class="tableText" width="50%">RATING OF 2016-2017</td>
				<td width="50%"></td>
			</tr>
			<tr>
				<td class="tableText" width="50%">RATING OF 2015-2016</td>
				<td width="50%"></td>
			</tr>

		</table>
	</div>
</div> -->
<div class="row">
	<form class="form-horizontal" role="form" name="empForm"
		ng-submit="save()">
		<div style="margin-top: 10px;">
			<div class="col-md-12">
				<table border="1" width="100%">
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="recommendedDesignationId"
								placeholder="RECOMMENDED LEVEL/DESIGNATION:"
								ng-model="recommendedDesignation">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="specificAchievementsId"
								placeholder="INDIVIDUAL's SPECIFIC ACHIEVEMENTS THIS YEAR AND IN LAST 2 YEARS"
								ng-model="specificAchievements">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="expectationsId"
								placeholder="PLEASE ENUMERATE HOW HIS/HER QUALIFICATION AND COMPETENCE MEET THE EXPECTATIONS OF THE RECOMMEDED POSITION?"
								ng-model="expectations">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="promotionImpactId"
								placeholder="HOW WILL THE INDIVIDUAL's PROMOTION IMPACT HIS/HER JOB RESPONSIBILITY AND CONTRIBUTION TO DEPARTMENT/ORGANISATION?"
								ng-model="promotionImpact">
								</textarea> <textarea class="form-control" rows="3"
								id="jobResponsibilityId" placeholder="JOB RESPONSIBILITY:"
								ng-model="jobResponsibility">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="departmentLevelId"
								placeholder="DEPARTMENTAL LEVEL:" ng-model="departmentLevel">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="organisationLevelId"
								placeholder="ORGANISATIONAL LEVEL:" ng-model="organisationLevel">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="additionalTrainingId"
								placeholder="ADDITONAL TRAINING THAT WILL BE REQUIERD TO GROOM HIM/HER IN T HE RECOMMENDED ROLE:"
								ng-model="additionalTraining">
								</textarea></td>
					</tr>
					<tr>
						<td class="tableText" width="50%"><textarea
								class="form-control" rows="3" id="nextFiveYearsId"
								placeholder="WHERE DO YOU SEE HIM/HER IN NEXT 5 YEARS ?"
								ng-model="nextFiveYears">
								</textarea></td>
					</tr>
				</table>
			</div>
		</div>

		<div style="margin-top: 10px;">
			<div class="col-md-12">
				<table border="1" width="100%">
					<tr>
						<td colspan="2" class="tableText">RECOMMENDED BY</td>
					</tr>
					<tr>
						<td class="tableText" width="50%">First Level Superior 
						<textarea class="form-control" rows="3"
								id="firstLevelSuperiorSignatureId" placeholder="Comments"
								ng-model="firstLevelSuperiorComments" disabled>
								</textarea> 
								<!-- <br> <span ng-if="employeePromotionDetails[0].firstLevelSuperiorSignatureDate == null">Date : {{currentDate | date : "dd.MM.y" }}</span>
								<span ng-if="employeePromotionDetails[0].firstLevelSuperiorSignatureDate != null">Date : {{employeePromotionDetails[0].firstLevelSuperiorSignatureDate | date : "dd.MM.y" }}</span> -->
						</td>
						<td class="tableText" width="50%">Second Level Superior <textarea class="form-control" rows="3" placeholder="Comments"
								id="secondLevelSuperiorCommentsId"
								ng-model="secondLevelSuperiorComments" disabled>
								</textarea>
								<!-- <br><span ng-if="employeePromotionDetails[0].secondLevelSuperiorSignatureDate == null"> Date : {{currentDate | date : "dd.MM.y" }}</span>
								<span ng-if="employeePromotionDetails[0].secondLevelSuperiorSignatureDate != null"> Date : {{employeePromotionDetails[0].secondLevelSuperiorSignatureDate | date : "dd.MM.y" }}</span> -->
						</td>
					</tr>

				</table>
			</div>
		</div>

		<div style="margin-top: 10px;">
			<div class="col-md-12">
				<table border="1" width="100%">
					<tr>
						<td class="tableText">HR</td>
					<tr>
						<td><textarea class="form-control" rows="3" id="hrCommentsId" placeholder="Comments" disabled
								ng-model="hrComments">
								</textarea> <br></td>
					</tr>
					<!-- <tr>
						<td>
							<p class="tableText">HR Signature</p> <textarea class="form-control"
								rows="3" id="hrSignatureId" ng-model="hrSignature">
								</textarea>
							<p class="tableText" ng-if="employeePromotionDetails[0].hrDate == null" >Date : {{currentDate | date : "dd.MM.y"}}</p>
							<p class="tableText" ng-if="employeePromotionDetails[0].hrDate != null" >Date : {{employeePromotionDetails[0].hrDate | date : "dd.MM.y"}}</p>
						</td>
					</tr> -->

				</table>
			</div>
		</div>

		<div style="margin-top: 10px;">
			<div class="col-md-12">
				<table border="1" width="100%">
					<tr>
						<td class="tableText">APPROVED BY </td>
					<tr>
						<td><textarea class="form-control" rows="3" id="approvedByCommentsId" placeholder="Comments"
								ng-model="approvedByComments">
								</textarea> <br></td>
					</tr>
					<!-- <tr>
						<td>
							<p class="tableText">CEO & ED</p>
							<p class="tableText">Signature</p> <textarea class="form-control"
								rows="3" id="approvedBySignatureId"
								ng-model="approvedBySignature">
								</textarea>
							<p class="tableText" ng-if="employeePromotionDetails[0].approvedByDate == null">Date : {{currentDate | date : "dd.MM.y"}}</p>
							<p class="tableText" ng-if="employeePromotionDetails[0].approvedByDate != null">Date : {{employeePromotionDetails[0].approvedByDate | date : "dd.MM.y"}}</p>
						</td>
					</tr> -->
				</table>
			</div>
		</div>

		<div class="col-md-12" style="margin-top: 10px;">
			<button type="submit" class="btn btn-outline-secondary" ng-disabled ="employeePromotionDetails[0].ceoCheck == 1"
				style="align: left; float: left;" id="saveHrEmployeePromotionDetails"
				data-loading-text="<i class='fa fa-spinner fa-spin '></i> Submitting">
				<spring:message code="button.save" />
			</button>
			
			<button type="button" class="btn btn-outline-secondary" ng-disabled ="employeePromotionDetails[0].ceoCheck == 1"
			    ng-click="rejectEmployeePromotion()" 
				style="float: left;margin-left: 5px;" id="rejectEmployeePromotionDetails"
				data-loading-text="<i class='fa fa-spinner fa-spin '></i> Submitting">
				<spring:message code="button.reject" />
			</button>
			
		</div>

	</form>
</div>