<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
	<span><spring:message code="heading.competencies" /></span>
</div>
<div class="sub_text">Assessment of Behavioural Competence : Total
	weightage of these competencies is 100%</div>
<div class="col-md-12" id="behavioralComptenceAlert">{{message}}</div>
<div class="row" ng-init="getBehavioralComptenceDetails()">
	<div class="col-md-12" style="overflow: auto; margin-top: 15px;">
		<form class="form-horizontal" style="font-size: 12px;" role="form">
			<table>
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

						<td valign="bottom" align="center"><select
							class="form-control input-sm" ng-model="x.midYearSelfRating"
							ng-options="item for item in selects"
							ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></select></td>
						<td align="center">{{x.midYearAssessorRating}}
						<td valign="bottom" align="center"><select
							class="form-control input-sm" ng-model="x.finalYearSelfRating"
							ng-options="item for item in selects"
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></select></td>
						<td align="center">{{x.finalYearAssessorRating}}</td>
						<td><span ng-if="x.midYearAssessorRating !=null">{{((x.midYearAssessorRating
								* x.weightage)/100) | number: 2 }}</span></td>
						<td><span ng-if="x.finalYearAssessorRating !=null">{{((x.weightage
								* x.finalYearAssessorRating)/100) | number: 2}}</span></td>
						<td><span ng-if="x.finalYearAssessorRating !=null && x.midYearAssessorRating !=null">{{((((x.midYearAssessorRating
								* x.weightage )/100) *30/100) + (((x.finalYearAssessorRating *
								x.weightage)/100) * 70/100)) | number: 2 }}</span>
						<span ng-if="x.finalYearAssessorRating!=null && x.midYearAssessorRating ==null">{{((x.weightage
								* x.finalYearAssessorRating)/100) | number: 2}}</span></td>

					</tr>
					<tr>
						<td colspan="9" v-align="middle" style="text-align: left;">

							<p style="margin: 5px;">{{x.description}}</p>
						</td>
					</tr>
					<tr>
						<td colspan="9"
							style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;"><textarea
								ng-model="x.comments"
								class="form-control input-sm ng-pristine ng-valid ng-touched"
								style="" placeholder="Comments" rows="4"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea></td>
					</tr>
					<tr>
						<td colspan="9"
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
					<td><strong ng-if="BehavioralComptenceTotalCalculation > 0">{{BehavioralComptenceTotalCalculation | number: 2 }}</strong></td>
				</tr>
			</table>
			<br>
			<!--  -->
			<button type="button" ng-click="save('SAVE')"
				ng-disabled="loggedUserAppraisalDetails.midYear == null ||  buttonDisabled"
				class="btn btn-outline-secondary">Save</button>
			<button type="button" ng-click="submit('SUBMIT')"
				ng-disabled="loggedUserAppraisalDetails.midYear == null ||  buttonDisabled"
				class="btn btn-outline-secondary">Submit</button>
			
		</form>
	</div>
	<div class="col-md-12" style="margin-top: 15px;">
			<span class="next_button">Move next to Training Needs</span>
			</div>
</div>