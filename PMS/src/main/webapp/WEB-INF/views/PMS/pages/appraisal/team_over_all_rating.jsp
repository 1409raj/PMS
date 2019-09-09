<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-008 {
	text-align: center;
}

td {
	height: 45px !important;
	font-weight: bold;
}

tr {
	height: 45px !important;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.over.all.rating" /></span>
</div>
<div class="row">
	<div class="col-md-3">
		<label for="levelId" class="control-label">Employee Type</label> <select
			ng-model="levelType" id="levelId" required
			ng-options="dp as dp  for dp in employeeLevel"
			ng-change="getEmployeeLists()" class="form-control">
			<option value="">Please select</option>
		</select>
	</div>
	<!-- ng-change="getEmployeeBasicDetails()" -->
	<div class="col-md-4">
		<label for="subEmployeeListId" class="control-label">Select
			Employee</label><select ng-model="subEmployeeCode" id="subEmployeeListId"
			ng-change="getEmployeeOverAllRatingData()"
			ng-options="em as em.empCodeWithName  for em in subEmployeeList"
			class="form-control" required>
			<option value="">Please select</option>
		</select>
	</div>
</div>
<div class="row" style="margin-top: 10px;">
	<div class="col-md-12">
		<div class="col-md-12 overAllRating">
			OVERALL RATING FOR THE ASSESSMENT PERIOD {{appraisalYearId.name}} <br>
			(To be taken from Assessment form of the Individual for the Year)
		</div>
		<table border="1" width="100%">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th></th>
					<th>MAX. RATING</th>
					<th>WEIGHTAGE(FIXED)<br>B
					</th>
					<th>AVERAGE RATING BY APPRAISAR<br>A
					</th>
					<th>WEIGHTED RATING<br>A*B
					</th>
					<th>FINAL SCORE <br> (OVERALL GROSS AVERAGE RATING)
					</th>
				</tr>
			</thead>
			<tbody>
				 <tr>
					<!-- <td class="tableText">SECTION 1:KRA's</td>
					<td>5</td>
					<td>75%</td>
					<td>{{((employeeOverAllKRARatingData[0].averageRatingSectionA * employeeOverAllKRARatingData[0].sectionAWeightage) +
					      (employeeOverAllKRARatingData[1].averageRatingSectionB * employeeOverAllKRARatingData[1].sectionBWeightage) +
					      (employeeOverAllKRARatingData[2].averageRatingSectionC * employeeOverAllKRARatingData[2].sectionCWeightage) +
					      (employeeOverAllKRARatingData[3].averageRatingSectionD * employeeOverAllKRARatingData[3].sectionDWeightage))/ employeeOverAllKRARatingData[0].totalWeightage | number: 2  }}</td>
					<td>{{averageKraRating/
						employeeOverAllKRARatingData[0].totalWeightage | number: 2 }}</td>
					<td>{{(averageKraRating/
						employeeOverAllKRARatingData[0].totalWeightage ) * 75/100 |
						number: 2 }}</td>
					<td rowspan="3">{{(((averageKraRating/
						employeeOverAllKRARatingData[0].totalWeightage ) * 75/100) +
						((employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating
						)*10/100) + ((employeeOverAllBCRatingData[0].BCCalculationAverage
						/ employeeOverAllBCRatingData[0].BCWeightage) * 15/100)) | number:
						2}}</td> -->
				</tr>
				<td class="tableText">SECTION 1:KRA's</td>
					<td>5</td>
					<td>75%</td>
					<td>{{averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage | number: 2 }}</td>
					<td>{{ (averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage ) * 75/100 |
						number: 2 }}</td>
					<td rowspan="3">{{(((averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage ) * 75/100) +
						((employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)*10/100) +
						((employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) * 15/100)) | number:
						2}}</td>
				<tr>
					<td class="tableText">SECTION 2:EXTRA ORDINARY INITIATIVES</td>
					<td>5</td>
					<td>10%</td>
					<td>{{(employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)
						| number: 2 }}</td>
					<td>{{(employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)*10/100
						| number: 2}}</td>

				</tr>
				<tr>
					<td class="tableText">SECTION 3:BEHAVIOURAL COMPETENCE
						ASSESSMENT</td>
					<td>5</td>
					<td>15%</td>
					<td>{{(employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) | number: 2 }}</td>
					<td>{{((employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) * 15/100) | number: 2
						}}</td>

				</tr>
				<tr>
					<td class="tableText">TOTAL SCORE</td>
					<td></td>
					<td>100%</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

			</tbody>
		</table>
	</div>
	<div class="col-md-12">
		<input type="checkbox" id="employeeTeamApprovalId"
			ng-disabled="isEmployeeTeamApproval == 1"
			ng-model="employeeTeamApproval"> <label
			for="employeeTeamApprovalId" class="control-label">I
			acknowledge my year end assessment rating.</label>
	</div>
</div>