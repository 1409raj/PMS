<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<style>
h4 {
	font-size: 18px;
	background-color: #2e4053;
	color: white;
	text-align: center;
}

/* td {
	height: 25px !important;
	word-break: break-all;
} */


</style>
<div class="section-tag">
	<span><spring:message code="menu.view.appraisal" /></span>
</div>
<!-- <div class="col-md-12" style="text-align: right; color: blue;">
	<button type="button" ng-click="print()"
		class="btn btn-outline-secondary">
		<span class="glyphicon glyphicon-download-alt"></span> Print
	</button>

</div> -->
<div id="viewAppraisal">
	<table width="100%" border-collapse="collapse" style="border:1px solid;">
		<tr>
			<td style="border:none;text-align: left;"><label for="empCode" class="control-label"><spring:message code="label.employee.code" /> :&nbsp;</label>
				<label class="labelText">{{subEmployeeDetails.empCode}}</label></td>
			<td style="border:none;text-align: left;"><label for="name" class="control-label"><spring:message code="label.employee.name" /> :&nbsp;</label>
				<label class="labelText">{{subEmployeeDetails.empName}}</label></td>
			<td style="border:none;text-align: left;"><label for="email" class="control-label"><spring:message code="label.employee.email" /></label>
				:&nbsp;<label class="labelText">{{subEmployeeDetails.email}}</label></td>
			<td style="border:none;text-align: left;"><label for="mobile" class="control-label"><spring:message code="label.employee.mobile" /></label>
				:&nbsp;<label class="labelText">{{subEmployeeDetails.mobile}}</label></td>
		<tr>

			<td style="border:none;text-align: left;"><label for="designation" class="control-label"><spring:message code="label.employee.designation" />
				:&nbsp;</label><label class="labelText"> {{subEmployeeDetails.designationName}}</label></td>
			<td style="border:none;text-align: left;"><label for="department" class="control-label"><spring:message code="label.employee.department" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.departmentName}}</label></td>
			<td style="border:none;text-align: left;"><label for="qualification" class="control-label"><spring:message code="label.employee.qualification" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.qualificationName}}</label></td>
			<td style="border:none;text-align: left;"><label for="dateOfJoining" class="control-label"><spring:message code="label.employee.doj" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.dateOfJoining | date : "dd.MM.y" }}</label></td>
		
		</tr>
		<tr>

			<td style="border:none;text-align: left;"><label for="firstLevelSuperiorEmpId" class="control-label"><spring:message code="label.employee.firstLevelSuperiorID" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.firstLevelSuperiorName}}</label></td>
			<td style="border:none;text-align: left;"><label for="secondLevelSuperiorEmpId" class="control-label"><spring:message code="label.employee.secondLevelSuperiorID" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.secondLevelSuperiorName}}</label></td>
			<td style="border:none;text-align: left;"><label for="location" class="control-label"><spring:message code="label.employee.location" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.location}}</label></td>
			<td style="border:none;text-align: left;"><label for="location" class="control-label"><spring:message code="label.employee.company" />
				:&nbsp;</label><label class="labelText">{{subEmployeeDetails.company}}</label></td>
			

		</tr>
		<tr>
		<td style="border:none;text-align: left;"><label for="mobile" class="control-label"><spring:message code="label.employee.dob" /></label>
				:&nbsp;<label class="labelText">{{subEmployeeDetails.dateOfBirth | date : "dd.MM.y"}}</label></td>

		</tr>
	</table>



	<div class="form-group table-responsive" style="overflow: auto;">
		<div class="section-tag">
			<span><spring:message code="heading.kra" /></span>
		</div>
		<table border="1" style="border-collapse: collapse;width: 2000px; overflow: scroll;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th rowspan="2" style="width:2%;"><spring:message code="th.kra.sr.no" /></th>
					<th rowspan="2" style="width:10%"><spring:message code="th.kra.smart.goal" /></th>
					<th rowspan="2" style="width:7%;"><spring:message code="th.kra.targrt" /></th>
					<th rowspan="2"  style="width:">ACHIEVEMENT<br>DATE</th>
					<th rowspan="2" style="width:"><spring:message code="th.weightage" /><br>A <br>(%)
					</th>
					<th colspan="4" style="width:5%;"><spring:message code="th.mid.year.review" /></th>
					<th colspan="4"><spring:message code="th.final.year.review" /></th>
					<th rowspan="2" style="width:5%;">ATTACHMENT</th>
					<th rowspan="2" style="width:5%;">MID YEAR<br> RATING <br> A*B</th>
					<th rowspan="2"  style="width:5%;">Year END <br>RATING <br> A*C
					</th>
					<th rowspan="2" ><spring:message
							code="th.final.score" /><br>30% of A*B <br>and 70% of A*C</th>
					<!-- <th rowspan="2">Average</th> -->
					<%-- <th rowspan="2"><spring:message code="th.remrks" /></th> --%>
					
					
				</tr>
				<tr>
					<th style="width:15%;"><spring:message code="th.achievement" /></th>
					<th style="width:7%;"><spring:message code="th.self.rating" /></th>
					<th style="width:5%;"><spring:message code="th.appraisar.rating" /><br>	B</th>
					<th style="width: 10%;">MID YEAR <br> ASSESSSMENT REMARKS</th>
					<th style="width:10%;"><spring:message code="th.achievement" /></th>
					<th style="width:5%;"><spring:message code="th.self.rating" /></th>
					<th style="width:2%;"><spring:message code="th.appraisar.rating" /><br>
					C</th>
					<th style="width: 12%;">YEAR END <br>ASSESSMENT REMARKS</th>
				</tr>
			</thead>
			<tbody>
				<tr style="background-color: #315772; color: white; height: 25px;">
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
				<tr ng-repeat="choice in sectionAList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y" }}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top"style="text-align: left;word-break: break-all;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<td align="center"><span ng-if="choice.midYearAppraisarRating != null">
					{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">
					{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating != null && choice.midYearAppraisarRating == null">
					{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
					</td>
				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
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
				<tr data-ng-repeat="choice in sectionBList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top"style="text-align: left;word-break: break-all;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating
							* 30/100) + (choice.finalYearAppraisarRating *70/100)  | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100)) | number: 2}}</span></td>
							
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					
					<td align="center"><span ng-if="choice.midYearAppraisarRating != null">
					{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">
					{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating != null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating != null && choice.midYearAppraisarRating == null">
					{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
				    </td>
					
				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
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
				<tr data-ng-repeat="choice in sectionCList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating
							* 30/100) + (choice.finalYearAppraisarRating *70/100) | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))  | number: 2}}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td align="center"><span ng-if="choice.midYearAppraisarRating != null">
					{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">
					{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating != null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating != null && choice.midYearAppraisarRating == null">
					{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
					</td>

				</tr>
				<tr style="background-color: #315772; color: white; height: 25px;">
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
				<tr data-ng-repeat="choice in sectionDList track by $index">
					<td align="center">{{$index+1}}</td>
					<td class="tableText" valign="top">{{choice.smartGoal}}</td>
					<td class="tableText" valign="top">{{choice.target}}</td>
					<td align="center">{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td align="center">{{choice.weightage}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAchievement}}</td>
					<td align="center">{{choice.midYearSelfRating}}</td>
					<td align="center">{{choice.midYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.midYearAssessmentRemarks}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.finalYearAchievement}}</td>
					<td align="center">{{choice.finalYearSelfRating}}</td>
					<td align="center">{{choice.finalYearAppraisarRating}}</td>
					<td valign="top" style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>
					<td><a ng-href={{choice.fileName}} style="color:red;" ng-if="choice.fileName != null">Download</a></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating
							* 30/100) + (choice.finalYearAppraisarRating *70/100) | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))  | number: 2}}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td align="center"><span ng-if="choice.midYearAppraisarRating != null">
					{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td align="center"><span ng-if="choice.finalYearAppraisarRating != null">
					{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
					<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->
					<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating != null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
					<span ng-if="choice.finalYearAppraisarRating != null && choice.midYearAppraisarRating == null">
					{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
					</td>
					
				</tr>
				<tr style="height: 25px;">
					<td colspan="4"></td>
					<td style="font-weight: bold;" align="center">{{WeightageCount}}</td>
					<td colspan="11"></td>
					<td><strong ng-if="viewKRATotalCalculation > 0"> {{viewKRATotalCalculation | number: 2 }}</strong></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="form-group">
		<div class="section-tag">
			<span>EXTRAORDINARY INITIATIVES</span>
		</div>
		<table border="1" width="100%" style="border-collapse: collapse;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th rowspan="2"><spring:message code="th.trainingNeeds.sr.no" /></th>
					<th rowspan="2"><spring:message code="th.contribution" /></th>
					<th rowspan="2"><spring:message code="th.contribution.details" /></th>
					<th rowspan="2" style="width: 7%;"><spring:message
									code="th.weightage" /><br>%</th>
					<th colspan="2"><spring:message code="th.final.year.review" /></th>
					<th rowspan="2"><spring:message code="th.remrks" /></th>
					<th rowspan="2"><spring:message
							code="th.trainingNeeds.finalscore" /></th>
					<!-- <th rowspan="2" ng-repeat="dyna in dynamicExtraOrdinaryDetails">{{dyna.labelName}}</th> -->
					<!-- <th rowspan="2">Weighted Rating</th> -->
					
					<!-- <th  rowspan="2"></th> -->
				</tr>
				<tr>
					<th><spring:message code="th.self.rating" /></th>
					<th><spring:message code="th.appraisar.rating" /></th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="choice in extraOrdinaryDetails"
					ng-init="outerIndex=$index">
					<td align="center">{{$index+1}}</td>
					<td  style="text-align: left;    word-break: break-all;">{{choice.contributions}}</td>
					<td  style="text-align: left;    word-break: break-all;">{{choice.contributionDetails}}</td>
					<td>{{choice.weightage}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td  style="text-align: left;    word-break: break-all;">{{choice.remarks}}</td>
					<td>{{(choice.weightage * choice.finalYearAppraisarRating)*10/100}}</td>
					<!-- <td>{{choice.weightage * choice.finalScore}}</td> -->
					<!-- Dynamic Column populate -->
					<!-- <td ng-repeat="dynamicChoice in choice.child" ng-init="innerIndex=$index">
								<input type="text" class="form-control input-sm" 
								ng-model="dynamicChoice.textData"  ></td> -->
					<!-- ng-disabled="loggedUserKraDetails.finalYear == null" -->
					
				</tr>
				<tr style="height: 25px;">
							<td colspan="3"></td>
							<td style="font-weight: bold;">{{viewExtraOrdinaryWeightageCount}}</td>
							<td colspan="3"></td>
							<td><strong ng-if="ExtraOrdinaryTotalCalculation > 0">{{ExtraOrdinaryTotalCalculation *10 | number: 2 }}</strong></td>
							

						</tr>
			</tbody>
		</table>
	</div>
	<div class="form-group">
		<div class="section-tag">
			<span>BEHAVIOURAL COMPETENCE</span>
		</div>
		<table border="1" style="border-collapse: collapse;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
						<th rowspan="2" width="45%;"><spring:message
								code="th.behavioural.competence" /></th>
						<th rowspan="2" width="6%;"><spring:message
								code="th.weightage" /><br>A <br> %</th>
						<th colspan="2" width="6%;"><spring:message
								code="th.mid.year.review" /></th>
						<th colspan="2" width="6%;"><spring:message
								code="th.final.year.review" /></th>
						<th rowspan="2" width="6%;">Mid Term Rating <br> A*B
						</th>
						<th rowspan="2" width="6%;">Year End Rating <br> A*C
						</th>
						<th rowspan="2" width="6%;"><spring:message
								code="th.final.score" /><br>30% of A*B and 70% of A*C</th>
					</tr>
				<tr>
						<th><spring:message code="th.self.rating" /></th>
						<th><spring:message code="th.appraisar.rating" /><br>
						<spring:message code="th.ar.mid" /></th>
						<th><spring:message code="th.self.rating" /></th>
						<th><spring:message code="th.appraisar.rating" /><br>
						<spring:message code="th.ar.final" /></th>
					</tr>
			</thead>
			<tbody ng-repeat="x in formData">
				<tr>
					<td style="text-align: left;"><label><b>{{$index+1}}.&nbsp;{{x.name}}</b></label></td>
					<td align="center">{{x.weightage}}</td>

					<td align="center">{{x.midYearSelfRating}}</td>
					<td align="center">{{x.midYearAssessorRating}}
					<td align="center">{{x.finalYearSelfRating}}</td>
					<td align="center">{{x.finalYearAssessorRating}}</td>
					<td><span ng-if="x.midYearAssessorRating !=null">{{((x.midYearAssessorRating
								* x.weightage)/100) | number: 2 }}</span></td>
						<td><span ng-if="x.finalYearAssessorRating !=null ">{{((x.weightage
								* x.finalYearAssessorRating)/100) | number: 2}}</span></td>
						<td><span ng-if="x.finalYearAssessorRating !=null && x.midYearAssessorRating !=null">{{((((x.midYearAssessorRating
								* x.weightage )/100) *30/100) + (((x.finalYearAssessorRating *
								x.weightage)/100) * 70/100)) | number: 2 }}</span>
						<span ng-if="x.finalYearAssessorRating !=null && x.midYearAssessorRating == null">{{((x.weightage
								* x.finalYearAssessorRating)/100) | number: 2}}</span>
						</td>
				</tr>
				<tr>
					<td colspan="8" v-align="middle" style="text-align: left;">

						<p style="margin: 5px;">{{x.description}}</p>
					</td>
				</tr>
				<tr>
					<td colspan="9"
						style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black; text-align: left;">{{x.comments}}</td>
				</tr>
				<tr>
					<td colspan="9"
						style="border-top: 1px solid #ffffff; border-left: 1px solid #ffffff; border-right: 1px solid #ffffff; height: 25px;"></td>
				</tr>
			</tbody>
			<tr style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;">
					<td style="text-align: left; font-size: 14px; font-weight: 600;">TOTAL
						WEIGHTAGE</td>
					<td>100%</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><strong ng-if="viewBehavioralComptenceTotalCalculation > 0">{{viewBehavioralComptenceTotalCalculation | number: 2 }}</strong></td>
				</tr>
		</table>
	</div>
	<div class="form-group">
		<div class="section-tag">
			<b><spring:message code="heading.careerAspirations" /></b>
		</div>
		<table border="1" style="width: 100%;border-collapse: collapse;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr style="height: 25px;">
					<th style="width: 5%;">S.No.</th>
					<th>Comments of Assessee</th>
					<th>Manager Review</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="center">1</td>
					<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.initializationComments}}</td>
					<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.initializationManagerReview}}</td>
				</tr>
				<tr ng-if="loggedUserAppraisalDetails.midYear == 1 || loggedUserAppraisalDetails.finalYear == 1">
					<td  align="center">2</td>
					<td ng-if="loggedUserAppraisalDetails.midYear == 1 || loggedUserAppraisalDetails.finalYear == 1"
						style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.midYearComments}}</td>
					<td  style="text-align: left;word-break: break-all;" ng-if="loggedUserAppraisalDetails.midYear == 1 || loggedUserAppraisalDetails.finalYear == 1">
					{{employeeCareerAspirationDetails.midYearCommentsManagerReview}}</td>
				</tr>
				<tr ng-if="loggedUserAppraisalDetails.finalYear == 1">
					<td align="center">3</td>
					<td ng-if="loggedUserAppraisalDetails.finalYear == 1"
						style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.yearEndComments}}</td>
					<td style="text-align: left;word-break: break-all;" ng-if="loggedUserAppraisalDetails.finalYear == 1">
					{{employeeCareerAspirationDetails.yearEndCommentsManagerReview}}</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="form-group">
		<div class="section-tag">
			<b><spring:message code="heading.trainingNeeds" /></b>
		</div>
		<table border="1" style="width: 100%;border-collapse: collapse;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr style="height: 25px;">
					<th width ="2%;">S.NO.</th>
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
					<td style="text-align: left;word-break: break-all;">{{choice.trainingTopic}}</td>
					<td  style="text-align: left;word-break: break-all;">{{choice.trainingReasons}}</td>
					<td  style="text-align: left;">{{choice.manHours}}</td>
					<td  style="text-align: left;word-break: break-all;">{{choice.approvedReject}}</td>
					<td  style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>

				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="form-group" style="margin-top: 15px;">
		<button type="button" ng-click="sendToManager()"
			ng-disabled="loggedUserAppraisalDetails.initializationYear == null ||
			 loggedUserAppraisalDetails.employeeIsvisible == 0"
			class="btn btn-outline-secondary">Send to Manager</button>
	</div>