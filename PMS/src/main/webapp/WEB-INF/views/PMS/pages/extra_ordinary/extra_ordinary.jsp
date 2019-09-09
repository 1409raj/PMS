<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
td {
	word-break: break-all;
}

textarea {
	resize: none;
}
</style>
<div class="section-tag">
	<span><spring:message code="menu.extraOrdinary" /></span>
</div>
<div class="sub_text">
	This section is for the work done by employee over and above his/ her
	job responsibilities i.e special achievement beyond his / her job
	description. Please enumerate how the work helped in bringing a change
	/ improvement in the organization.
	</div>
	<div class="col-md-12" id="extraOrdinaryAlert">{{message}}</div>
	<div class="row" ng-init="getExtraOrdinaryDetails()">

		<div class="col-md-12" style="overflow: auto;margin-top: 15px;">
			<form class="form-horizontal" role="form">
				<table border="1" width="100%">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr>
							<th rowspan="2"><spring:message
									code="th.trainingNeeds.sr.no" /></th>
							<th rowspan="2"><spring:message code="th.contribution" /></th>
							<th rowspan="2"><spring:message
									code="th.contribution.details" /></th>
							<th rowspan="2" style="width: 7%;"><spring:message
									code="th.weightage" /> <br> %</th>
							<th colspan="2"><spring:message code="th.final.year.review" /></th>
							<th rowspan="2"><spring:message
									code="th.trainingNeeds.finalscore" /></th>
							<!-- <th rowspan="2" ng-repeat="dyna in dynamicExtraOrdinaryDetails">{{dyna.labelName}}</th> -->
							<!-- <th rowspan="2">Weighted Rating</th> -->
							<th rowspan="2" width="25%;"><spring:message
									code="th.remrks" /></th>
							<th rowspan="2"></th>
						</tr>
						<tr>
							<th><spring:message code="th.self.rating" /></th>
							<th><spring:message code="th.appraisar.rating" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="choice in extraOrdinaryDetails"
							ng-init="outerIndex=$index">
							<td>{{$index+1}}</td>
							<td><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.contributions"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"></textarea></td>
							<td><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.contributionDetails"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"></textarea></td>
							<td><input type="text" class="form-control input-sm"
								style="text-align: center;height:74px;" ng-change="countWeightage()"
								ng-model="choice.weightage"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"
								only-digits></td>
							<td><select class="form-control input-sm" style="height:74px;"
								ng-model="choice.finalYearSelfRating"
								ng-options="item for item in selects"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"></select></td>

							<td>{{choice.finalYearAppraisarRating}}</td>
							<td>{{(choice.weightage * choice.finalYearAppraisarRating)*10/100}}</td>
							<!-- <td>{{choice.weightage * choice.finalScore}}</td> -->
							<!-- Dynamic Column populate -->
							<!-- <td ng-repeat="dynamicChoice in choice.child" ng-init="innerIndex=$index">
								<input type="text" class="form-control input-sm" 
								ng-model="dynamicChoice.textData"  ></td> -->
							<!-- ng-disabled="loggedUserKraDetails.finalYear == null" -->
							<td>
							<textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.remarks"
									ng-disabled="choice.remarks == null"></textarea>
							
							</td>
							<td ng-if="$index =='0'"><i
								ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"
								class="fa fa-plus btn plus-button"
								style="border: 2px solid lightgray;"
								ng-click="addNewChoice(outerIndex)"></i> <i ng-if="$index > '0'"
								class="fa fa-minus btn minus-button"
								ng-click="removeNewChoice($index)"></i></td>
							<td ng-if="$index > '0'"><i
								ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled"
								class="fa fa-minus btn minus-button"
								ng-click="removeNewChoice($index)"></i></td>
						</tr>
						<tr style="height: 25px;">
							<td colspan="3"></td>
							<td style="font-weight: bold;">{{WeightageCount}}</td>
							<td colspan="2"></td>
							<td><strong ng-if="ExtraOrdinaryTotalCalculation > 0">{{ExtraOrdinaryTotalCalculation  | number: 2 }}</strong></td>
							<td colspan="3"></td>

						</tr>
					</tbody>
				</table>
				<div style="margin-top: 15px">
					<button type="button" class="btn btn-outline-secondary"
						ng-click="save('SAVE')"
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled">Save</button>
					<button type="button" ng-click="submit('SUBMIT')"
						class="btn btn-outline-secondary"
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || buttonDisabled">Submit</button>
				</div>
				<div style="margin-top: 15px">
				<span class="next_button">Move next to Performance Appraisal Form for final submission</span>
				</div>
			</form>
		</div>
	</div>