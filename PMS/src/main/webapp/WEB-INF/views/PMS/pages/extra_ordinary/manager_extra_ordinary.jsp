<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span>Extra Ordinary</span>
</div>
<!-- 	<div class="row" style="margin-top: 15px;">
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
				<span>SECTION 2: EXTRAORDINARY INITIATIVES: RATING ON A SCALE OF 1 TO 5.</span>
			</div>
		</div>
		<div class="col-md-12">
			<span style="font-size: 11px;">This section is for the work
				done by the employee over and above his/her job responsibilities
				i.e. special achievement beyond his/her job description.</span><br> <span
				style="color: #ee413d; font-weight: bold;">Please enumerate
				how the work helped in bringing a change/improvement in the organisations.</span>
		</div>
	</div> -->
	<div class="row" ng-init="getExtraOrdinaryDetails()">
		<div class="col-md-12" id="extraOrdinaryAlert">{{message}}</div>
		<div class="col-md-12">
			<form class="form-horizontal" role="form">
				<table class="table" border="1">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr>
							<th rowspan="2">S.No.</th>
							<th rowspan="2">Contribution</th>
							<th rowspan="2">Contribution Details</th>
							<th colspan="2">Mid Year Review</th>
							<th colspan="2">Final Year Review</th>
							<th rowspan="2">Final Score</th>
							<th rowspan="2" ng-repeat="dyna in dynamicExtraOrdinaryDetails">{{dyna.labelName}}</th>
							<th rowspan="2">Remarks</th>
							<th  rowspan="2"></th>
						</tr>
						<tr>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
				</tr>
					</thead>
					<tbody>
						<tr ng-repeat="choice in extraOrdinaryDetails" ng-init="outerIndex=$index">
							<td>{{$index+1}}</td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.contributions"  ng-disabled="loggedUserKraDetails.appraisalYearId == null"></td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.contributionDetails"  ng-disabled="loggedUserKraDetails.appraisalYearId == null"></td>
							<td><select class="form-control input-sm"
								ng-model="choice.midSelfRating"
								ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.midYear == null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.midAppraisarRating"
								ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.midYear == null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalSelfRating"
								ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.finalYear == null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalAppraisarRating"
								ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.finalYear == null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalScore"
								ng-options="item for item in selects" ng-disabled="loggedUserKraDetails.finalYear == null"></select></td>
								
							<td ng-repeat="dynamicChoice in choice.child" ng-init="innerIndex=$index">
								<input type="text" class="form-control input-sm" 
								ng-model="dynamicChoice.fileName"></td>
								
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.remarks" name="" ng-disabled="loggedUserKraDetails.finalYear == null"></td>
							<td ng-if="$index =='0'"><i
								class="fa fa-plus btn plus-button"
								style="border: 2px solid lightgray;" ng-click="addNewChoice(outerIndex)"></i>
								<i ng-if="$index > '0'" class="fa fa-minus btn minus-button"
								ng-click="removeNewChoice($index)"></i></td>
							<td ng-if="$index > '0'"><i
								class="fa fa-minus btn minus-button"
								ng-click="removeNewChoice($index)"></i></td>
							
						</tr>
					</tbody>
				</table>
				<button type="button" class="btn btn-outline-secondary"
					ng-click="save()">Save</button>
			</form>
		</div>
	</div>

	<div class="modal fade" id="extra_ordinary-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >Read Instruction Carefully</h4>
      </div>
      <div class="modal-body">
      <div class="row">
      <div class="col-md-12">
			<div class="modal-section-tag">
				<span>SECTION 2: EXTRAORDINARY INITIATIVES: RATING ON A SCALE OF 1 TO 5.</span>
			</div>
		</div>
		<div class="col-md-12">
			<span style="font-size: 11px;">This section is for the work
				done by the employee over and above his/her job responsibilities
				i.e. special achievement beyond his/her job description.</span><br> <span
				style="color: #ee413d; font-weight: bold;">Please enumerate
				how the work helped in bringing a change/improvement in the organisations.</span>
		</div>
	<div class="col-md-12">
	    <input type="checkbox" ng-model="checkboxEO"><span><spring:message code='agree.text'/></span>
	    </div>
	    </div>
      </div>
      <div class="modal-footer">
       
        <button type="button" class="btn btn-outline-secondary"  ng-disabled="!checkboxEO"   data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>