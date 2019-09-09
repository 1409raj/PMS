<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
td {
	word-break: break-all;
}

textarea {
	resize: none;
}
textarea#note {
	width:100%;
    direction:ltr;
	display:block;
	max-width:100%;
	line-height:1.5;
	border-radius:3px;
	transition:box-shadow 0.5s ease;
	box-shadow:0 4px 6px rgba(0,0,0,0.1);
	font-smoothing:subpixel-antialiased;
    height:100%;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.trainingNeeds" /></span>
</div>
<div class="sub_text">Training needs should be align with
	organizational and departmental objective and should be a
	mix of: on the job learning , learning from others , formal training /
	development.</div>
<div class="row" ng-init="getTrainingNeedsDetails()">
	<div class="col-md-12" style="overflow: auto; margin-top: 15px;">
		<form class="form-horizontal" role="form">
			<table style="width: 100%;">
				<thead style="background-color: #315772; color: #ffffff;">
					<tr style="height: 25px;">
						<th><spring:message code="th.trainingNeeds.sr.no" /></th>
						<th width="30%;"><spring:message
								code="th.trainingNeeds.course" /></th>
						<th width="30%;"><spring:message
								code="th.trainingNeeds.topic.choose" /></th>
						<th width="10%;"><spring:message
								code="th.trainingNeeds.proposed.training" /></th>
						<th width="5%;"><spring:message
								code="th.trainingNeeds.approve.reject" /></th>
						<th width="25%;"><spring:message code="th.remrks" /></th>
						<!-- <th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th> -->
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="choice in trainingNeedsDetails">
						<th>{{$index+1}}</th>
						<td><textarea id="note" rows="4" cols="50"
								class="form-control input-sm" ng-model="choice.trainingTopic"
								ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0  || choice.isValidatedByEmployee == 1"></textarea></td>
						<td><textarea id="note" rows="4" cols="50"
								class="form-control input-sm" ng-model="choice.trainingReasons"
								ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0  || choice.isValidatedByEmployee == 1"></textarea></td>
						<td><textarea  rows="4" cols="50"
								class="form-control input-sm" ng-model="choice.manHours"
								ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0  || choice.isValidatedByEmployee == 1"></textarea></td>
						<td>{{choice.approvedReject}}</td>
						<td>
						<textarea id="note" rows="4" cols="50"
								class="form-control input-sm" ng-model="choice.remarks"
								ng-disabled="choice.remarks == null"></textarea>
						
						</td>
						<td>
						<td ng-if="$index =='0'"><i
							class="fa fa-plus btn plus-button"
							ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0 ||  buttonDisabled"
							style="border: 2px solid lightgray;" ng-click="addNewChoice()"></i>
							<i ng-if="$index > '0'" class="fa fa-minus btn minus-button"
							ng-click="removeNewChoice($index)"></i></td>

						<td ng-if="$index > '0'"><i
							ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0 ||  choice.isValidatedByEmployee == 1"
							class="fa fa-minus btn minus-button"
							ng-click="removeNewChoice($index)"></i></td>

					</tr>
				</tbody>
			</table>
			<div style="margin-top: 15px;">
				<!-- ng-disabled="loggedUserAppraisalDetails.appraisalYearId == null" -->
				<button type="button" class="btn btn-outline-secondary"
					ng-click="save('SAVE')"
					ng-disabled="loggedUserAppraisalDetails.initializationYear == null || buttonDisabled|| loggedUserAppraisalDetails.employeeIsvisible == 0">Save</button>
				<button type="button" ng-click="submit('SUBMIT')"
					ng-disabled="loggedUserAppraisalDetails.initializationYear == null || buttonDisabled|| loggedUserAppraisalDetails.employeeIsvisible == 0"
					class="btn btn-outline-secondary">Submit</button>

				<span ng-if="loggedUserAppraisalDetails.midYear == 1"
					style="color: red; cursor: pointer; margin-left: 15px;"
					ng-click="editField()">ADD</span> 
			</div>
			<div style="margin-top: 15px;">
			<span class="next_button">Move next to Career Aspiration</span>
			</div>
		</form>
	</div>
</div>
<%-- <div class="modal fade" id="training-needs-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
			<span>SECTION 5: TRAINING NEEDS</span>
		</div>
	</div>
	<div class="col-md-12">
		<span style="font-size: 11px;"> This section will be filled by
			the assessor after discussing and agreeing with the assessee.</span><br>
		<span style="color: #ee413d; font-weight: bold;"> Please
			note that the training needs have to be realistic and should be
			inline with the Organizational and Departmental Objectives </span>
	</div>
	    <div class="col-md-12">
	    <input type="checkbox" ng-model="checkboxTN"><span><spring:message code='agree.text'/></span>
	    </div>
      </div>
       
      </div>
      <div class="modal-footer">
       
        <button type="button" class="btn btn-outline-secondary" ng-disabled="!checkboxTN"   data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
 --%>