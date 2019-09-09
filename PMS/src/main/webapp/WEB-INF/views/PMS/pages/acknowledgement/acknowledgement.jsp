<%-- <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.col-md-12 {
	margin-bottom: 10px !important;
}
</style>
<div class="section-tag" ng-controller="AcknowledgementController">
	<span style="font-size: 20px;"><spring:message
			code="heading.instructions" /></span>
</div>
<div class="row" style="margin-top: 15px;">
	<div class="col-md-12">
		<div class="modal-section-tag">
			<span>Section 1 :ASSESSMENT OF KRAs:<span> RATING TO BE
					GIVEN ON A SCALE OF 1 TO 5. TOTAL WEIGHTAGE (COMBINED) FOR ALL
					PARAMETRES IN THIS SECTION IS 100%.</span></span>
		</div>
		<div class="col-md-12">
			<span style="font-size: 11px;"> This section will include
				objectives set for different parameters as well as the job
				description of the employee. Reporting Manager should decide the
				Weightage for Each Section Viz. Financial Objective, Business
				Objective, Operational Objective as per the Job Requirement And
				Deliverables of the Individual and the same needs to be agreed by
				the assessee. </span><br> <span
				style="color: #ee413d; font-weight: bold;">Please note that
				organizational commitment is capped at 5% weightage. </span>
		</div>
	</div>
	<div class="col-md-12">
		<div class="modal-section-tag">
			<span>SECTION 2: EXTRAORDINARY INITIATIVES: RATING ON A SCALE
				OF 1 TO 5.</span>
		</div>
		<div class="col-md-12">
			<span style="font-size: 11px;">This section is for the work
				done by the employee over and above his/her job responsibilities
				i.e. special achievement beyond his/her job description.</span><br> <span
				style="color: #ee413d; font-weight: bold;">Please enumerate
				how the work helped in bringing a change/improvement in the
				organisations.</span>
		</div>
	</div>
	<div class="col-md-12">
		<div class="modal-section-tag">
			<span>SECTION 3: ASSESSMENT OF BEHAVIOURAL COMPETENCE:
				Weightages have to been fixed to the 9 competencesTotal weightage of
				these competences is 100%.</span>
		</div>
	</div>
	<div class="col-md-12">
		<div class="modal-section-tag">
			<span>SECTION 4: CAREER ASPIRATIONS:</span>
		</div>
		<!-- <div class="col-md-12">
			<span style="font-size: 11px;"> Career aspirations of employee
				(to be filled in by employee but to be supported by the Superior in
				context to the Organization and Department.</span><br>
		</div> -->
	</div>
	<div class="col-md-12">
		<div class="modal-section-tag">
			<span>SECTION 5: TRAINING NEEDS</span>
		</div>
		<div class="col-md-12">
			<span style="font-size: 11px;"> This section will be filled by
				the assessor after discussing and agreeing with the assessee.</span><br>
			<span style="color: #ee413d; font-weight: bold;"> Please note
				that the training needs have to be realistic and should be inline
				with the Organizational and Departmental Objectives </span>
		</div>
	</div>
	<div class="col-md-12">
		<form class="form-horizontal" role="form" name="acknowledgementForm"
			ng-submit="submit()">
			<div class="row">
				<div class="col-md-12">
					<label for="acknowledgementId" class="control-label">Acknowledgement
						:</label> <input type="checkbox" id="acknowledgementId"
						ng-model="acknowledgement" required>
				</div>
				<div class="col-md-12" style="margin-top: 15px;">
					<button type="submit" class="btn btn-outline-secondary">
						<spring:message code="button.save" />
					</button>
				</div>
			</div>
		</form>
		<div style="margin-top: 15px;" class="col-md-12"
			id="acknowledgementAlert">{{message}}</div>
	</div>
</div> --%>