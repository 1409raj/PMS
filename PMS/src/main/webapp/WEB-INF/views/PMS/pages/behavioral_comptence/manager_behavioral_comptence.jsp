<%-- <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
select.input-sm {
	height: 32px;
	line-height: 32px;
}

.input-sm {
	height: 32px;
}
b, strong {
    font-weight: 700;
    margin-left: 5px;
}
</style>
<div class="section-tag">
	<span>Behavioral Comptence</span>
</div>
<!-- <div class="row" style="margin-top: 15px;">
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-3">
				<span class="span-label">Employee ID : </span><span>{{loggedUserDetails.empId}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Employee Name : </span><span>{{loggedUserDetails.empName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Employee Code : </span><span>{{loggedUserDetails.empCode}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Email : </span><span>{{loggedUserDetails.email}}</span>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-3">
				<span class="span-label">Designation : </span><span>{{loggedUserDetails.designationName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Department : </span><span>{{loggedUserDetails.departmentName}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Date of Joining : </span><span>08-02-2018</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Qualification : </span><span>{{loggedUserDetails.qualificationName}}</span>
			</div>
		</div>

	</div>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-3">
				<span class="span-label">First Level Superior : </span><span>{{loggedUserDetails.superiorName}}</span>
			</div>

			<div class="col-md-3">
				<span class="span-label">Location : </span><span>{{loggedUserDetails.location}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Assessment for the period : </span><span>{{loggedUserDetails.assessmentPeriod}}</span>
			</div>
			<div class="col-md-3">
				<span class="span-label">Mobile : </span><span>{{loggedUserDetails.mobile}}</span>
			</div>
		</div>
	</div>
</div>
<hr> -->
<!-- <div class="row">

	<div class="col-md-12">
		<div class="section-tag">
			<span>SECTION 3: ASSESSMENT OF BEHAVIOURAL COMPETENCE:
				Weightages have to been fixed to the 9 competences.</span>
		</div>
	</div>
	<div class="col-md-12">

		<span style="color: #ee413d; font-weight: bold;">Please note
			that Total weighage of these competences is 100%.</span>
	</div>
</div> -->
<div class="row" ng-init="getBehavioralComptenceDetails()">
	<div class="col-md-12" id="behavioralComptenceAlert">{{message}}</div>
	<div class="col-md-12">
		<form class="form-horizontal" style="font-size: 12px;" role="form">

			<table>
				<thead style="background-color: #315772; color: #ffffff;">
					<tr>
						<th rowspan="2" width="45%;"">BEHAVIOURAL COMPETENCE</th>
						<th rowspan="2" width="6%;">weightage</th>
						<th colspan="2" width="6%;">Mid Year Review</th>
						<th colspan="2" width="6%;">Final Year Review</th>
						<th rowspan="2" width="6%;">30% of AR-MID and 70% of AR Final</th>
						<th rowspan="2" width="6%;">Final Weighted Rating</th>
					</tr>
					<tr>
						<th>Self Rating</th>
						<th>Appraisar Rating</th>
						<th>Self Rating</th>
						<th>Appraisar Rating</th>
					</tr>
				</thead>
				<tbody ng-repeat="x in formData">
					<tr>
						<td style="text-align: left;"><label><b>{{$index+1}}.&nbsp;{{x.name}}</b></label></td>
						<td  align="center">{{x.weightage}}</td>

						<td valign="bottom" align="center"><select
							class="form-control input-sm" ng-model="x.midYearSelfRating"
							ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.midYear == null"></select></td>
						<td valign="bottom" align="center"><select
							class="form-control input-sm"
							ng-model="x.midYearAssessorRating"
							ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.midYear == null"></select></td>
						<td valign="bottom" align="center"><select
							class="form-control input-sm" ng-model="x.finalYearSelfRating"
							ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.finalYear == null"></select></td>
						<td valign="bottom" align="center"><select
							class="form-control input-sm"
							ng-model="x.finalYearAssessorRating"
							ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.finalYear == null"></select></td>
						<td valign="bottom" align="center"><input type="text"
							class="form-control input-sm" ng-model="x.final" disabled></td>
						<td valign="bottom" align="center"><input type="text"
							class="form-control input-sm" ng-model="x.finalWeighted"
							disabled></td>
					</tr>
					<tr>
						<td colspan="8" v-align="middle" style="text-align: left;">

							<p style="margin: 5px;">{{x.description}}</p>
						</td>
					</tr>
					<tr>
						<td colspan="8"
							style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;"><textarea
								ng-model="x.comments"
								class="form-control input-sm ng-pristine ng-valid ng-touched"
								style="" placeholder="Comments" rows="4" ng-disabled="loggedUserKraDetails.appraisalYearId == null"></textarea></td>
					</tr>


					<tr>
						<td colspan="8"
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
					</tr>
			</table>
			<br>
			<button type="button" ng-click="save()"
				class="btn btn-outline-secondary">Save</button>
				<button type="button" ng-click="submit()"
					class="btn btn-outline-secondary">Submit</button>
		</form>
	</div>
</div>

<div class="modal fade" id="behavioral_comptenceModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Read Instruction Carefully</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="modal-section-tag">
							<span>SECTION 3: ASSESSMENT OF BEHAVIOURAL COMPETENCE:
								Weightages have to been fixed to the 9 competences.</span>
						</div>
					</div>
					<div class="col-md-12">

						<span style="color: #ee413d; font-weight: bold;">Please
							note that Total weighage of these competences is 100%.</span>
					</div>
					<div class="col-md-12">
						<input type="checkbox" ng-model="checkboxBC"><span><spring:message
								code='agree.text' /></span>
					</div>
				</div>

			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-outline-secondary"
					ng-disabled="!checkboxBC" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
 --%>