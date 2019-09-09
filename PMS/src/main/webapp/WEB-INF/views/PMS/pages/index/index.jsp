<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.col-md-12 {
	margin-bottom: 10px !important;
}

td {
	height: 35px !important;
	font-weight: bold;
}

tr {
	height: 35px !important;
}
</style>
<div ng-init="onLoadIndex()">
	<div class="section-tag">
		<span style="font-size: 18px;"><spring:message
				code="heading.instructions" /></span>
	</div>
	<div class="row" style="margin-top: 15px;">
		<div class="col-md-12">
			<table style="width: 100%;">
				<thead style="background-color: #315772; color: #ffffff;">
					<tr style="height: 25px;">
						<th>Appraisal Status</th>
						<th>Substatus</th>
						<th>Appraiser Name</th>
						<th>Year </th>
					</tr>
				</thead>
				<tbody>
					<tr style="height: 28px;">
						<th ng-if="loggedUserAppraisalDetails.initializationYear != null">{{stage}}</th>
						<th ng-if="loggedUserAppraisalDetails.initializationYear == null">In
							Planning</th>
						<th ng-if="loggedUserAppraisalDetails.initializationYear == null">Goal
							Setting</th>
						<th ng-if="((loggedUserAppraisalDetails.initializationYear == '1') && ((loggedUserAppraisalDetails.employeeIsvisible == '1' || loggedUserAppraisalDetails.employeeIsvisible == '0')) &&
							 loggedUserAppraisalDetails.secondLevelIsvisibleCheck == '0' && loggedUserAppraisalDetails.firstLevelIsvisible =='0' && loggedUserAppraisalDetails.secondLevelIsvisible =='0')">Goal
							Setting</th>
						<th
							ng-if="(loggedUserAppraisalDetails.initializationYear == '1' && loggedUserAppraisalDetails.employeeIsvisible == 0 && (loggedUserAppraisalDetails.firstLevelIsvisible ==1 || loggedUserAppraisalDetails.secondLevelIsvisible ==1))">Goal
							Approval</th>
						<th ng-if="((loggedUserAppraisalDetails.initializationYear == '1' && loggedUserAppraisalDetails.secondLevelIsvisibleCheck == 1 ) || (loggedUserAppraisalDetails.midYear == 1 && loggedUserAppraisalDetails.secondLevelIsvisibleCheck == 0) )">Mid Year
							Review</th>
						<th
							ng-if="((loggedUserAppraisalDetails.midYear == '1' && loggedUserAppraisalDetails.secondLevelIsvisibleCheck == 1) || (loggedUserAppraisalDetails.finalYear == 1 && (loggedUserAppraisalDetails.employeeIsvisible == 1 || loggedUserAppraisalDetails.firstLevelIsvisible == 1 ||
							loggedUserAppraisalDetails.secondLevelIsvisible == '1') && loggedUserAppraisalDetails.secondLevelIsvisibleCheck == 0))">Year
							End Assessment</th>
						<th
							ng-if="(loggedUserAppraisalDetails.finalYear == 1 && loggedUserAppraisalDetails.secondLevelIsvisibleCheck == 1)">Assessment
							approval</th>
						<th>${appraiserName}</th>
						<th>{{yearName}}</th>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="col-md-12" style="margin-top:20px;">
     <div class="section-tag">
		<span style="font-size: 18px;">PEROFORMANCE APPRAISAL PROCESS</span>
	 </div>
	 </div>
		<div class="col-md-12">
			<div>
				<span class="modal-section-tag">1.This appraisal process consists of 5 sections as hereunder :</span> <br> <br> <span
					class="modal-section-tag">1.1 SECTION 1: Assessment of KRA's.</span><br>
				<span class="sub_text">This section will include objectives set for different parameters as well as the job description of the employee.Reporting Manager should decide the Weightage for Each
					Section Viz. Financial Obj., Business Obj. and Operational Obj. as
					per the Job Requirement and Deliverables of the Individual.</span>
			</div>
			<br>
			<div>
				<span class="sub_text"> <span class="modal-section-tag">1.1.1
						Financial</span> <br>This section covers the targets towards
					Organisational Profit, cost reduction/ optimization. Put N.A for
					employees where not applicable for eg.
					<div class="col-md-12">
						<br>For an Investment person - Raising funds at ...%. <br>For
						an Accounts person - Ensuring investments so that atelast ...% is
						earned annualy <br>For HR - Bringing down operations cost by
						...%. <br>For Admin - Bringing down new assesst cost by ...%
					</div>
				</span> <br>
				<!-- <span style="color: #ee413d; font-weight: bold;">Please note that
					organizational commitment is capped at 5% weightage. </span> -->
			</div>
			<div>
				<span class="sub_text"> <span class="modal-section-tag">1.1.2
						Business Development</span> <br> This section covers targets towards
					business growth of the Company. Put N.A for employees where not
					applicable.
					<div class="col-md-12">
						<br>For a BD person - To achvieve ...MW by ... <br>For a
						Finance person - To align with funding partners to ensure... % on
						every...
					</div>
				</span> <br>
				<!-- <span style="color: #ee413d; font-weight: bold;">Please note that
					organizational commitment is capped at 5% weightage. </span> -->
			</div>
			<div>
				<span class="sub_text"> <span class="modal-section-tag">1.1.3
						Operational</span> <br> This section is for function related
					objectives. This covers key points from JD of the profile but these
					have to be put in definition of SMART. This section also covers
					targets towards enhancement of respective department functioning.
					For eg. Implementing some process/software that reduces time and
					increases efficiency.
				</span>
			</div>
			<br>
			<div>
				<span class="sub_text"> <span class="modal-section-tag">1.1.4
						Organisational Commitment</span> <br> This section carries weightage
					towards Ethics, Code of Conduct, Organisational culture, Green
					Code, Branding and Internal Communication. This should be weighed
					5% for all.
				</span>
			</div>
			<br>
		</div>
		<div class="col-md-12">
			<div>
				<span class="modal-section-tag">1.2 SECTION 2 : EXTRAORDINARY
					INITIATIVES -</span> <br> <span class="sub_text"> THIS SECTION
					IS FOR THE WORK DONE BY EMPLOYEE OVER AND ABOVE HIS/HER
					RESPONSIBILITIES IE. BEYOND THE LIMITS OF KRA. THIS HAS TO BE RATED
					ON A SCALE OF 1 TO 5.</span>
			</div>
		</div>
		<div class="col-md-12">
			<div>
				<span class="modal-section-tag">1.3 SECTION 3 : ASSESSMENT OF
					BEHAVIOURAL COMPETENCE -</span><span class="sub_text"> Assessors
					are expected to take this section for giving constructive feedback
					to the assessee w.r.t each competence.</span>
			</div>
		</div>
		<div class="col-md-12">
			<div>
				<span class="modal-section-tag">1.4 SECTION 4: Assessee's
					career aspirations - </span><span class="sub_text"> Assessee's
					careers aspirations should be noted here. If the aspirations are
					not inline with Organisational domain, then it should be explained
					clearly to the person.</span>
			</div>
		</div>
		<div class="col-md-12">
			<div>
				<span class="modal-section-tag">1.5 SECTION 5: Training Needs
					- </span><span class="sub_text">This section will be filled by the
					assessor after discussing and agreeing with the assessee on the
					training needs. Please note that the training needs have to be
					realistic and should be inline with the Organsiational Objectives.</span>
			</div>

		</div>

		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">
				<span>2. Following is the weighage of the sections that will
					be rated.</span>
			</div>
			<table border="1" width="100%">
				<thead style="background-color: #315772; color: #ffffff;">
					<tr>
						<th></th>
						<th>MAX. RATING</th>
						<th>WEIGHTAGE(FIXED)<br>B
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="tableText">SECTION 1:KRA's</td>
						<td>5</td>
						<td>75%</td>
					</tr>
					<tr>
						<td class="tableText">SECTION 2:EXTRA ORDINARY INITIATIVES</td>
						<td>5</td>
						<td>10%</td>
					</tr>
					<tr>
						<td class="tableText">SECTION 3:BEHAVIOURAL COMPETENCE
							ASSESSMENT</td>
						<td>5</td>
						<td>15%</td>
					</tr>
					<tr>
						<td class="tableText">TOTAL SCORE</td>
						<td></td>
						<td>100%</td>
					</tr>

				</tbody>
			</table>

		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">
				<span>3. Performance Appraisal is an important exercise to:</span>
			</div>
			<div class="col-md-12 sub_text">3.1 Give feedback to employees
				on their performance and assess their performance in a formal way.</div>
			<div class="col-md-12 sub_text">3.2 Identify training needs</div>
			<div class="col-md-12 sub_text">3.3 Competency Mapping and
				Organisational diagnosis.</div>
			<div class="col-md-12 sub_text">3.4 Facilitate communication
				between the Assesse and the assessor.</div>
			<div class="col-md-12 sub_text">3.5 Define basis for further
				decisions regarding an employee as career planning, job rotation
				etc.</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div>
				<span class="modal-section-tag">4. Not to forget that -</span><span
					class="sub_text">It is very important that Superiors give
					feedback to their team members on a regular basis. It is a simple
					thing that can be done anyday and anytime.</span>
			</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">5. Before the Appraisal meeting,
				First level superior should prepare his assessment list along with
				points that need to be highlighted during the meeting. He /she needs
				to keep the Second Level Superior informed of the same. This is to
				ensure that there is parity between First level superior and second
				level superior's assessment.</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">6. Assesse will do his/her self
				appraisal first.</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">7. Preparation for the Appraisal
				meeting as well as commitment from the people involved are key to
				the success of this activity.</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">8. All assessors will ensure
				that the assessments done by them are :</div>
			<div class="col-md-12">
				<span class="sub_text">8.1 Based on observed facts to the
					extent possible, subjective judgement should be avoided.</span><br> <span
					class="sub_text">8.2 Related to specific contexts,
					resources, timeframes and situations encountered.</span><br> <span
					class="sub_text">8.3 Constructive ie. Geared towards
					improving performance and defining progress measures to motivate
					the employee.</span>
			</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">9. Please devote enough time for
				the Appraisal meetings.</div>
		</div>
		<div class="col-md-12" style="margin-top: 10px;">
			<div class="modal-section-tag">10. Rating Description :</div>
			<div class="sub_text">10.1 Rating 1 - Unsatisfactory - Less than 80% of targets achieved</div>
			<br>
			<div class="col-md-12 sub_text">10.1.1 The Assessee did not
				perform the assigned tasks satisfactorily; he/she did not meet the
				requirements of his/her Department. The agreed objectives have not
				been achieved. Inspite of the feedback provided to the person during
				the year and providing necessary support, he/she did not perform or
				even didnt show motivation to do the job. The employee did not
				contribute to maintaining the team spirit in his section and failed
				to integrate in the team.</div>
			<div class="sub_text">10.2 Rating 2 - Partially Achieved - 80% of targets achieved</div>
			<br>
			<div class="col-md-12 sub_text">10.2.1 The assesse remained in
				a 'comfort zone' all year in terms of time management and
				commitment. There was no sign of innovation and creativity. He/she
				just managed to reach the objectives and just managed permanent
				responsibilities somehow. There is enough scope of improvement. May
				require more supervision than expected for assignment.</div>
			<div class="sub_text">10.3 Rating 3 - Fully Achieved - 100% of targets achieved</div>
			<br>
			<div class="col-md-12 sub_text">10.3.1 The assesse fully met
				the main requirements of his/her function, the objectives set were
				fully achieved and the permanent responsibilities were fully
				achieved. The employee met the deadlines and completed the set
				tasks. But he / she did not show any motivation to do tasks beyond
				the expectations, he/she did not take any initaitive to bring
				improvement in departmental/self working.</div>
			<div class="sub_text">10.4 Rating 4 - Exceeded some
				Expectations - 120% of targets achieved</div>
			<br>

			<div class="col-md-12 sub_text">10.4.1 The assessee exceeded
				some expectations of his/her function, he/she stepped well outside
				his/her 'comfort zone' and displayed initiatives and propsoed
				improvements in his/ her function and other areas of business.
				He/she exceeded the objectives and permanent responsibilities beyond
				the set ones. Performance level throughtout the year remained high
				and positive.</div>
			<div class="sub_text">10.5 Rating 5 - Significantly exceeded
				Expectations - More than 120% of target achieved along with a demonstratable financial gain to the Company/Project or demonstrated work that is differently innovative</div>
			<br>

			<div class="col-md-12 sub_text">10.5.1 The assesse exceeded
				extensivley the requirements of his/her function and the permanent
				responsibilities, the performance level was beyond all expectations.
				The person fully contributed to the improvement of work methods in
				the Department and Organisation. Brought in good and implementable
				ideas and worked towards builidng a positve and motivating culture
				in the Departmen and Organisation. Performance level of the person
				raimained very high, motivating and positive throughtout the year.</div>

		</div>
		<div class="col-md-12">
			<form class="form-horizontal" role="form" name="acknowledgementForm"
				ng-submit="submit()">
				<div class="row">
					<div class="col-md-12">
						<label for="acknowledgementId" class="control-label sub_text">Acknowledgement
							:</label> <input type="checkbox" id="acknowledgementId"
							ng-disabled="isUserFirstTimeLogin == 1"
							ng-model="acknowledgement" required>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit" class="btn btn-outline-secondary"
							ng-disabled="isUserFirstTimeLogin == 1">
							<spring:message code="button.save" />
						</button>
					</div>
					<div class="col-md-12">
				<span class="next_button">Move next to KRA's</span>	
					</div>
				</div>
			</form>
			<!-- <div style="margin-top: 15px;" class="col-md-12"
				id="acknowledgementAlert">{{message}}</div> -->
		</div>

	</div>


</div>

<!-- Modal Confirmation -->
<div class="modal fade" id="masterPageConfirmation" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h5 class="modal-title">Are you sure want to delete
					{{parameter.name}} ?</h5>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<button type="button" class="btn btn-sm btn-outline-secondary"
							data-dismiss="modal" ng-click="deleteParameter()">Yes</button>
						<button type="button" class="btn btn-sm btn-outline-secondary"
							data-dismiss="modal">No</button>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>

<!-- Modal Change Password Field -->
<div class="modal fade" id="updatePasswordModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">First Time Login Please Change Password</h4>
			</div>
			<div class="modal-body">
				<div class="col-md-12" id="indexAlert">{{message}}</div>
				<form class="form-horizontal" role="form" name="updatePassword"
					ng-submit="save()">
					<div class="row">
						<div class="col-md-12">
							<label for="currentPasswordId" class="control-label"><spring:message
									code="label.currentPassword" /></label> <input type="password"
								id="currentPasswordId" placeholder="Enter current password"
								class="form-control" ng-model="currentPassword" required>
						</div>
						<div class="col-md-12">
							<label for="newPasswordId" class="control-label"><spring:message
									code="label.newPassword" /></label> <input type="password"
								id="newPasswordId" placeholder="Enter new password"
								class="form-control" ng-model="newPassword" required>
						</div>
						<div class="col-md-12">
							<label for="confirmPasswordId" class="control-label"><spring:message
									code="label.confirmPassword" /></label> <input type="password"
								id="confirmPasswordId" placeholder="Enter confirm password"
								class="form-control" ng-model="confirmPassword" required>
						</div>
						<div class="col-md-12" style="margin-top: 15px;">
							<button type="submit" class="btn btn-sm btn-outline-secondary">
								<spring:message code="button.save" />
							</button>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
</div>
