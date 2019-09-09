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
	<span style="font-size: 20px;">ASSESSMENT RATING :</span>
</div>
<div class="section-tag" style="margin-top: 50px;">
	<span>MID YEAR</span>
</div>
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" role="form"
			name="employeeMidYearApprovalForm"
			ng-submit="employeeMidYearApprovalFun()">
			<div class="row">
			
				<div class="col-md-12">
				<!-- ng-disabled="isEmployeeMidYearApproval == 1" -->
				<!--Main ng-disabled="employeeMidYearApproval" -->
					<input type="checkbox" id="employeeMidYearApprovalId"
						ng-disabled="employeeMidYearApproval"
						ng-model="employeeMidYearApproval" required><label
						for="employeeMidYearApprovalId" class="control-label">I
						acknowledge my mid year rating.</label>
				</div>
				<!-- ng-disabled="isEmployeeMidYearApprovalSubmit" -->
				<!-- ng-disabled="loggedUserAppraisalDetails.employeeMidYearApproval == 1 || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.finalYear == 0" -->
				<div class="col-md-12" style="margin-top: 15px;">
					<button type="submit" class="btn btn-outline-secondary"
					ng-disabled="isEmployeeMidYearApprovalSubmit"
						>
						<spring:message code="button.save" />
					</button>
				</div>
			</div>
		</form>
	</div>
</div>
<hr>
<div class="section-tag" style="margin-top: 50px;">
	<span><spring:message code="heading.over.all.rating" /></span>
</div>
<div class="row" style="margin-top: 10px; overflow: auto;"
	ng-init="getEmployeeOverAllRatingData()">
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
					<td class="tableText">SECTION 1:KRA's</td>
					<td>5</td>
					<td>75%</td>
					<td>
					{{averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage | number: 2 }}</td>
					<td>{{ (averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage ) * 75/100 | number: 2
						}}</td>
					<td rowspan="3">{{(((averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage ) * 75/100) +
						((employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)*10/100)
						+ ((employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) * 15/100)) | number:
						2}}
						<input type="hidden" id="kraTotalCalculation" value="{{ (averageKraRating/
						employeeOverAllKRARatingDataTotalWeightage ) * 75/100 | number: 2
						}}">
						</td>
				</tr>
				<tr>
					<td class="tableText">SECTION 2:EXTRA ORDINARY INITIATIVES</td>
					<td>5</td>
					<td>10%</td>
					<td>{{(employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating) | number: 2 }}</td>
					<td>{{((employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)*10/100) | number: 2}}
					<input type="hidden" id="extraOrdinaryTotalCalculation" value="{{((employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating)*10/100) | number: 2}}">
					</td>

				</tr>
				<tr>
					<td class="tableText">SECTION 3:BEHAVIOURAL COMPETENCE
						ASSESSMENT</td>
					<td>5</td>
					<td>15%</td>
					<td>{{(employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) | number: 2 }}</td>
					<td>{{((employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) * 15/100) | number:
						2}}
						<input type="hidden" id="behaviouralCompetenceTotalCalculation" value="{{((employeeOverAllBCRatingData[0].BCCalculationAverage /
						employeeOverAllBCRatingData[0].BCWeightage) * 15/100) | number:
						2}}">
						</td>

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
		<form class="form-horizontal" role="form" name="employeeApprovalForm"
			ng-submit="employeeApprovalFun()">
			<div class="row">
				<div class="col-md-12">
					<input type="checkbox" id="employeeApprovalId"
						ng-disabled="isEmployeeApproval == 1" ng-model="employeeApproval"
						required><label for="employeeApprovalId"
						class="control-label">I acknowledge my year end assessment
						rating.</label>
				</div>
				<div class="col-md-12" style="margin-top: 15px;">
					<button type="submit" class="btn btn-outline-secondary"
						ng-disabled="isEmployeeApproval == 1 || loggedUserAppraisalDetails.initializationYear == 1 ||
					   loggedUserAppraisalDetails.midYear == 1">
						<spring:message code="button.save" />
					</button>
				</div>
			</div>
		</form>
	</div>
</div>
