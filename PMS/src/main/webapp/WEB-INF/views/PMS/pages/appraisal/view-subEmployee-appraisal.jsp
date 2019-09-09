<!-- <style>
h4 {
	font-size: 18px;
	background-color: #2e4053;
	color: white;
	text-align: center;
}

.tableText {
	text-align: left;
}
</style>
<div>
	<div class="section-tag">
		<span>Employee Appraisal</span>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="col-md-3">
				<span class="span-label">Employee Code : </span><span>{{subEmployeeDetails.empCode}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Employee Name : </span><span>{{subEmployeeDetails.empName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Email : </span><span>{{subEmployeeDetails.email}}</span>
			</div>

		</div>
		<div class="col-md-12">
			<div class="col-md-3">
				<span class="span-label">Designation : </span><span>{{subEmployeeDetails.designationName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Department : </span><span>{{subEmployeeDetails.departmentName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Date of Joining : </span><span>08-02-2018</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Qualification : </span><span>{{subEmployeeDetails.qualificationName}}</span>
			</div>


		</div>
		<div class="col-md-12">
			<div class="col-md-3">
				<span class="span-label">First Level Superior : </span><span>{{subEmployeeDetails.firstLevelSuperiorName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Second Level Superior : </span><span>{{subEmployeeDetails.secondLevelSuperiorName}}</span>
			</div>

			<div class="col-md-3">
				<span class="span-label">Location : </span><span>{{subEmployeeDetails.location}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Assessment for the period: </span><span>{{subEmployeeDetails.assessmentPeriod}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Mobile : </span><span>{{subEmployeeDetails.mobile}}</span>
			</div>
			<div class="col-md-12" style="text-align: right; color: blue;">
				<button type="button" ng-click="print()"
					class="btn btn-outline-secondary">
					<span class="glyphicon glyphicon-download-alt"></span> Print
				</button>
			</div>
		</div>
	</div>

	<div class="form-group">
		<h4 style="width: 103px;">
			<b>KRA</b>
		</h4>
		<table border="1">
			<thead>
				<tr>
					<th rowspan="2">S.No.</th>
					<th rowspan="2" style="width: 20%;">Smart Goal</th>
					<th rowspan="2" style="width: 15%;">Target</th>
					<th rowspan="2" style="width: 5%;">Achievement Date</th>
					<th rowspan="2">Weightage<br>A
					</th>
					<th colspan="3">Mid Year Review</th>
					<th colspan="3">Final Year Review</th>
					<th rowspan="2">30% of AR-MID and<br> 70% Of AR-Final<br>B
					</th>
					<th rowspan="2" style="width: 4%;">Final Score<br>A*B
					</th>
					<th rowspan="2">Remarks</th>
					<th rowspan="2"></th>
				</tr>
				<tr>
				   <th>Achievement</th>
					<th>Self Rating</th>
					<th>Appraisar Rating<br>AR-MID</th>
					<th>Achievement</th>
					<th>Self Rating</th>
					<th>Appraisar Rating<br>AR-FINAL</th>
				</tr>
			</thead>
			<tbody>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : A (FINANCIAL
						OBJECTIVE)</td>
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
					<td>{{$index+1}}</td>
					<td class="tableText">{{choice.smartGoal}}</td>
					<td class="tableText">{{choice.target}}</td>
					<td>{{choice.achievementDate | date : "dd.MM.y" }}</td>
					<td>{{choice.weightage}}
					</td>
					<td>{{choice.midYearAchievement}}</td>
					<td>{{choice.midYearSelfRating}}</td>
						<td>{{choice.midYearAppraisarRating}}</td>
						<td>{{choice.finalYearAchievement}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100) }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage * (choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100)}}</span>
					</td>
					<td>{{choice.remarks}}</td>

				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : B (BUSINESS
						DEVELOPMENT OBJECTIVES)</td>
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
					<td>{{$index+1}}</td>
					<td class="tableText">{{choice.smartGoal}}</td>
					<td class="tableText">{{choice.target}}</td>
					<td>{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td>{{choice.weightage}}
						</td>
					<td>{{choice.midYearAchievement}}</td>
					<td>{{choice.midYearSelfRating}}</td>
					<td>{{choice.finalYearAchievement}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100) }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage * (choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100)}}</span>
					</td>
					<td>{{choice.remarks}}</td>
				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : C (OPERATIONAL
						OBJECTIVE)</td>
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
					<td>{{$index+1}}</td>
					<td class="tableText">{{choice.smartGoal}}</td>
					<td class="tableText">{{choice.target}}</td>
					<td>{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td>{{choice.weightage}}
					</td>
					<td>{{choice.midYearAchievement}}</td>
					<td>{{choice.midYearSelfRating}}</td>
					<td>{{choice.midYearAppraisarRating}}</td>
						<td>{{choice.finalYearAchievement}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100) }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage * (choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100)}}</span>
					</td>
					<td>{{choice.remarks}}</td>
	
				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : D (ORGANIZATIONAL
						COMMITMENT)</td>
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
					<td>{{$index+1}}</td>
					<td class="tableText" >{{choice.smartGoal}}</td>
					<td class="tableText">{{choice.target}}</td>
					<td>{{choice.achievementDate | date : "dd.MM.y"}}</td>
					<td>{{choice.weightage}}
					</td>
					<td>{{choice.midYearAchievement}}</td>
					<td>{{choice.midYearSelfRating}}</td>
					<td>{{choice.midYearAppraisarRating}}</td>
						<td>{{choice.finalYearAchievement}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100) }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{choice.weightage * (choice.midYearAppraisarRating * 30/100) + (choice.finalYearAppraisarRating *70/100)}}</span>
					</td>
					<td>{{choice.remarks}}</td>
				</tr>
			</tbody>
		</table>
		<table border="1">
			<thead>
				<tr>
					<th rowspan="2">S.No.</th>
					<th rowspan="2" style="width: 20%;">Smart Goal</th>
					<th rowspan="2" style="width: 20%;">Target</th>
					<th rowspan="2" style="width: 5%;">Achievement Date</th>
					<th rowspan="2">Weightage<br>A
					</th>
					<th colspan="2">Mid Year Review</th>
					<th colspan="2">Final Year Review</th>
					<th rowspan="2">30% of AR-MID and 70% Of AR-Final<br>B
					</th>
					<th rowspan="2">Final Score<br>A*B
					</th>
					<th rowspan="2">Remarks</th>
				</tr>
				<tr>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
				</tr>
			</thead>
			<tbody>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : A (Financial
						Objective)</td>
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
				<tr data-ng-repeat="choice in subEmployeeKraDetails">
					<td ng-if="choice.sectionName ==='Section A' ">{{$index + 1}}</td>
					<td ng-if="choice.sectionName ==='Section A' " class="tableText">{{choice.smartGoal}}</td>
					<td ng-if="choice.sectionName ==='Section A' " class="tableText">{{choice.target}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.achievementDate
						| date : "dd.MM.y"}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.weightage}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.midYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.midYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.finalYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.finalYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.midFinal}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.finalScore}}</td>
					<td ng-if="choice.sectionName ==='Section A' ">{{choice.remarks}}</td>

				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : B (Bussiness
						Developement)</td>
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
				<tr data-ng-repeat="choice in subEmployeeKraDetails">
					<td ng-if="choice.sectionName ==='Section B' ">{{$index + 1}}</td>
					<td ng-if="choice.sectionName ==='Section B' " class="tableText">{{choice.smartGoal}}</td>
					<td ng-if="choice.sectionName ==='Section B' " class="tableText">{{choice.target}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.achievementDate
						| date : "dd.MM.y"}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.weightage}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.midYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.midYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.finalYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.finalYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.midFinal}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.finalScore}}</td>
					<td ng-if="choice.sectionName ==='Section B' ">{{choice.remarks}}</td>

				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : C (Operatinal
						Objectives)</td>
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
				<tr data-ng-repeat="choice in subEmployeeKraDetails">
					<td ng-if="choice.sectionName ==='Section C' ">{{$index + 1}}</td>
					<td ng-if="choice.sectionName ==='Section C' " class="tableText">{{choice.smartGoal}}</td>
					<td ng-if="choice.sectionName ==='Section C' " class="tableText">{{choice.target}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.achievementDate
						| date : "dd.MM.y"}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.weightage}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.midYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.midYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.finalYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.finalYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.midFinal}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.finalScore}}</td>
					<td ng-if="choice.sectionName ==='Section C' ">{{choice.remarks}}</td>

				</tr>
				<tr class="tr-color">
					<td></td>
					<td style="text-align: left;">Section : D (Organizational
						Commitment)</td>
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
				<tr data-ng-repeat="choice in subEmployeeKraDetails">
					<td ng-if="choice.sectionName ==='Section D' ">{{$index + 1}}</td>
					<td ng-if="choice.sectionName ==='Section D' " class="tableText">{{choice.smartGoal}}</td>
					<td ng-if="choice.sectionName ==='Section D' " class="tableText">{{choice.target}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.achievementDate
						| date : "dd.MM.y"}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.weightage}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.midYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.midYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.finalYearSelfRating}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.finalYearAppraisarRating}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.midFinal}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.finalScore}}</td>
					<td ng-if="choice.sectionName ==='Section D' ">{{choice.remarks}}</td>

				</tr>
			</tbody>
		</table>
	</div>

	<div class="form-group">
		<h4 style="width: 260px;">
			<b>Behavioural Competence</b>
		</h4>
		<table>
				<thead style="background-color: #315772; color: #ffffff;">
					<tr>
						<th rowspan="2" width="45%;"">BEHAVIOURAL COMPETENCE</th>
						<th rowspan="2" width="6%;">Weightage <br>A
						</th>
						<th colspan="2" width="6%;">Mid Year Review</th>
						<th colspan="2" width="6%;">Final Year Review</th>
						<th rowspan="2" width="6%;">30% of AR-MID and 70% of AR Final<br>B
						</th>
						<th rowspan="2" width="6%;">Final Weighted Rating <br>A*B
						</th>
					</tr>
					<tr>
						<th>Self Rating</th>
						<th>Appraisar Rating<br>AR-MID
						</th>
						<th>Self Rating</th>
						<th>Appraisar Rating<br>AR-FINAL
						</th>
					</tr>
				</thead>
				<tbody ng-repeat="x in formData">
					<tr>
						<td style="text-align: left;"><label><b>{{$index+1}}.&nbsp;{{x.name}}</b></label></td>
						<td align="center">{{x.weightage}}</td>

						<td valign="bottom" align="center">{{x.midYearSelfRating}}</td>
						<td align="center">{{x.midYearAssessorRating}}
						<td valign="bottom" align="center">{{x.finalYearSelfRating}}</td>
						<td align="center">{{x.finalYearAssessorRating}}</td>
						<td>{{(x.midYearAssessorRating
								* 30/100) + (x.finalYearAssessorRating *70/100) }}</td>
						<td>{{x.weightage
								* (x.midYearAssessorRating * 30/100) +
								(x.finalYearAssessorRating *70/100)}}
					</tr>
					<tr>
						<td colspan="8" v-align="middle" style="text-align: left;">

							<p style="margin: 5px;">{{x.description}}</p>
						</td>
					</tr>
					<tr>
						<td colspan="8"
							style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;text-align: left;">{{x.comments}}</td>
					</tr>
					<tr>
						<td colspan="8"
							style="border-top: 1px solid #ffffff; border-left: 1px solid #ffffff; border-right: 1px solid #ffffff; height: 25px;"></td>
					</tr>
				</tbody>
				<tr
					style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;">
					<td style="text-align: left; font-size: 14px; font-weight: 600;">TOTAL
						WEIGHTAGE</td>
					<td>100%</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		<table>
			<thead>
				<tr>
					<th rowspan="2" style="width: 45%;">BEHAVIOURAL COMPETENCE</th>
					<th rowspan="2">Weightage<br>A
					</th>
					<th colspan="2">Mid Year Review</th>
					<th colspan="2">Final Year Review</th>
					<th rowspan="2">30% of AR-MID and 70% of AR Final<br>B
					</th>
					<th rowspan="2">Final Weighted Rating<br>A*B
					</th>
				</tr>
				<tr>
					<th>Self Rating</th>
					<th>Assessor Rating</th>
					<th>Self Rating</th>
					<th>Assessor Rating</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat ="choice in subEmployeeBehaviouralCompetenceDetails">
					<td style="text-align: left;"><b>{{$index+1}}.{{choice.heading}}</b>
						<p>{{choice.details}}<br>
						</p> <span><b>Comments :</b></span>{{choice.comments}}</td>
					<td>{{choice.weightage}}%</td>
					<td>{{choice.midYearSelfRating}}</td>
					<td>{{choice.midYearAssessorRating}}</td>
					<td>{{choice.finalYearSelfRating}}</td>
					<td>{{choice.finalYearAssessorRating}}</td>
					<td>{{choice.midFinal}}</td>
					<td>{{choice.finalScore}}</td>
				
				</tr>
				<tr>
					<td style="text-align: left;"><b>1.INTEGRITY</b>
						<p>Basis honesty of charracter, adhere to the moral system of
							SELF and the Company.</p> <br> <span><b>Comments :</b></span>
						{{subEmployeeBehaviouralCompetenceDetails[0].comments}}</td>
					<td>15%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[0].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[0].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[0].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[0].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[0].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[0].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[0].midFinal
							*15 }}</span></td>
				<tr>
				<tr>
					<td style="text-align: left;"><b>2.ORGANIZATIONAL AND
							PLANNING</b>
						<p>Set priorities to optimize time usage .Engages in short and
							long term planning proposes milestones which allow progress to be
							adequately measured.Adheres to schedules and plans</p> <br> <span><b>Comments
								:</b></span> {{subEmployeeBehaviouralCompetenceDetails[1].comments}}</td>

					<td>20%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[1].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[1].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[1].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[1].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[1].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[1].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[1].midFinal
							* 20 }}</span></td>
				<tr>
					<td style="text-align: left;"><b>3.ANALYTICAL AND PROBLEM
							SOLVING</b>
						<p>Understands and defines problem clearly. Formulates
							realistic implementable solutions. Participates constructively in
							group problem solving Anticipates and prevnts occurance of
							problems</p> <span><b>Comments :</b></span>
						{{subEmployeeBehaviouralCompetenceDetails[2].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[2].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[2].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[2].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[2].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[2].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[2].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[2].midFinal
							* 10 }}</span></td>

				</tr>
				<tr>
					<td style="text-align: left;"><b>4.JUDGEMENT AND DECISION
							MAKING</b>
						<p>Consider relevent alternatives and the background before
							making decisions. Is able to take good decision within the time
							frame</p> <br> <span><b>Comments :</b></span>
						{{subEmployeeBehaviouralCompetenceDetails[3].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[3].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[3].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[3].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[3].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[3].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[3].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[3].midFinal
							* 10 }}</span></td>

				</tr>
				<tr>
					<td style="text-align: left;"><b>5.SELF IMPROVEMENT AND
							INITIATIVE</b>
						<p>Responds positively after receiving feedback from his/her
							reporting manager,peers and team mates. Takes initiatives to
							propoe improvement in self work,team work and others.</p> <span><b>Comments
								:</b></span> {{subEmployeeBehaviouralCompetenceDetails[4].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[4].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[4].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[4].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[4].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[4].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[4].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[4].midFinal
							* 10 }}</span></td>

				</tr>

				<tr>
					<td style="text-align: left;"><b>6.INNOVATION AND
							CREATIVITY</b>
						<p>Genrates good and feasible (implementable) ideas, concepts
							and techniques.Willing to attempt new approches. Simplifies
							and/or improves,techniques and process</p> <span><b>Comments
								:</b></span> {{subEmployeeBehaviouralCompetenceDetails[5].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[5].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[5].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[5].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[5].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[5].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[5].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[5].midFinal
							* 10 }}</span></td>

				</tr>

				<tr>
					<td style="text-align: left;"><b>7.PASSION</b>
						<p>IS Passionate about the work and the Organization.Degree to
							which employee is emotionally connected to the work and connected
							to the purpose,values and mission of the Organization and is
							committed to doing his/her best</p> <span><b>Comments :</b></span>
						{{subEmployeeBehaviouralCompetenceDetails[6].comments}}</td>

					<td>5%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[6].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[6].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[6].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[6].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[6].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[6].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[6].midFinal
							* 10 }}</span></td>

				</tr>
				<tr>
					<td style="text-align: left;"><b>8.COMMUNICATION</b>
						<p>Articulates ideas in a clear,concise and assertive manner
							so that ithers understand the talk.Is able to clearly communicate
							and understand the written and oral communication</p> <span><b>Comments
								:</b></span> {{subEmployeeBehaviouralCompetenceDetails[7].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[7].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[7].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[7].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[7].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[7].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[7].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[7].midFinal
							* 10 }}</span></td>

				</tr>
				<tr>
					<td style="text-align: left;"><b>9.TEAMWORK</b>
						<p>Assisstes others when needed .Participates effectively in
							the team work by offering ideas and implementing agreed plans.Is
							a patient Listener.Prevents or resolves conflict.Effectively
							manages team when needed</p> <span><b>Comments :</b></span>
						{{subEmployeeBehaviouralCompetenceDetails[8].comments}}</td>

					<td>10%</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[8].midYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[8].midYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[8].finalYearSelfRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[8].finalYearAssessorRating}}</td>
					<td>{{subEmployeeBehaviouralCompetenceDetails[8].midFinal}}</td>
					<td><span
						ng-if="subEmployeeBehaviouralCompetenceDetails[8].midFinal > '0'">{{subEmployeeBehaviouralCompetenceDetails[8].midFinal
							* 10 }}</span></td>

				</tr>
				<tr>
					<td style="text-align: left; font-size: 14px; font-weight: 600;">TOTAL
						WEIGHTAGE</td>

					<td>100%</td>
					<td></td>
					<td>FINAL SCORE OUT OF 5</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>


				</tr>
			</tbody>
		</table>

	</div>
	<div class="form-group">
		<h4 style="width: 260px;">
			<b>Career Aspirations</b>
		</h4>
		<table border="1" style="width: 100%">
			<thead>
				<tr>
					<th style="width: 5%;">S.No.</th>
					<th>Comments of Assessee</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td style="text-align: left;">{{employeeCareerAspirationDetails.comments}}</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="form-group">
		<h4 style="width: 260px;">
			<b>Training Needs</b>
		</h4>
		<table style="width: 100%;">
				<thead style="background-color: #315772; color: #ffffff;">
					<tr>
						<th>S.NO.</th>
						<th>Training/Course Topic</th>
						<th>Reason for choosing the topic /training and how ...this
							training will Improve his/her working and department working</th>
						<th>Proposed Training man-hours</th>
						<th>Approve/Reject</th>
						<th>Remarks</th>
						<th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="choice in trainingNeedsDetails">
						<th>{{$index+1}}</th>
						<td>{{choice.trainingTopic}}</td>
								<td>{{choice.trainingReasons}}</td>
								<td>{{choice.manHours}}</td>
								<td>{{choice.approvedReject}}
							</td>
								<td>{{choice.remarks}}</td>
				
						<td>
					</tr>
				</tbody>
			</table>
		<table style="width: 100%;">
			<thead>
				<tr>
					<th>S.NO.</th>
					<th>Training/Course Topic</th>
					<th>Reason for choosing the topic /training and how ...this
						training will Improve his/her working and department working</th>
					<th>Proposed Training man-hours</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="choice in subEmployeeTrainingNeedsDetails">
					<th>{{$index+1}}</th>
					<td style="text-align: left;">{{choice.topic}}</td>
					<td style="text-align: left;">{{choice.reasons}}</td>
					<td style="text-align: left;">{{choice.manHours}}</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="form-group">
		<h4 style="width: 260px;">
			<b>Final Review</b>
		</h4>
		<table border="1" style="width: 100%">
			<thead>
				<tr>
					<th style="width: 5%;">S.No.</th>
					<th>Comments of Assessor(First Level Superior)</th>
					<th>Comments of Assessor(second Level Superior)</th>
					<th>Comments of Assessee</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="choice in subEmployeeFinalReviewDetails">
					<th>{{$index+1}}</th>
					<td style="text-align: left;">{{choice.firstLevelSuperiorComments}}</td>
					<td style="text-align: left;">{{choice.secondLevelSuperiorComments}}</td>
					<td style="text-align: left;">{{choice.assesseeComments}}</td>
				</tr>
			</tbody>
		</table>

	</div>
</div> -->