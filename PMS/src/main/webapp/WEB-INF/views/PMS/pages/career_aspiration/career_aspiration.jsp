<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.section-tag {
	text-decoration: none;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.careerAspirations" /> : </span><span
		class="sub_text">Career aspirations of employee (to be filled
		in by employee but to be supported by the Superior in context to the
		Organization and Department.</span>
</div>

<div class="row" ng-init="getCareerAspirationDetails()">
	<form class="form-horizontal" role="form">
		<div class="col-md-4">
			<div>
				<textarea
					ng-model="employeeCareerAspirationDetails.initializationComments"
					rows="10" style="width: 90%;"
					ng-disabled="loggedUserAppraisalDetails.employeeIsvisible == 0 || employeeCareerAspirationDetails.isValidatedByEmployee == 1">
					</textarea>
			</div>
			<div>
				<label for="empCode" class="control-label">Appraiser Review
					:</label>
			</div>
			<div>
				<textarea
					ng-model="employeeCareerAspirationDetails.initializationManagerReview"
					rows="10" style="width: 90%;" disabled>
					</textarea>
			</div>
			<div style="margin-top: 15px;">
				<button type="button" class="btn btn-outline-secondary"
					ng-disabled="loggedUserAppraisalDetails.initializationYear == null || isCareerAspirationsSubmitted"
					ng-click="save('SAVE')">Save</button>
				<button type="button" ng-click="submit('SUBMIT')"
					ng-disabled="loggedUserAppraisalDetails.initializationYear == null || isCareerAspirationsSubmitted"
					class="btn btn-outline-secondary">Submit</button>

				
			</div>
			<div style="margin-top: 15px;">
			<span class="next_button">Move next to Extra Ordinary Initiative</span>
			</div>
		</div>
		<div class="col-md-4">
			<div>
				<textarea ng-model="employeeCareerAspirationDetails.midYearComments"
					ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear == 1 || employeeCareerAspirationDetails.midYearComments !=null"
					rows="10" style="width: 90%;"
					ng-disabled="employeeCareerAspirationDetails.midYearCommentsStatus === 1 || loggedUserAppraisalDetails.finalYear == 1 "
					placeholder="">
					</textarea>
			</div>
			<div ng-if="loggedUserAppraisalDetails.midYear == 1 || loggedUserAppraisalDetails.finalYear == 1 || employeeCareerAspirationDetails.midYearComments !=null">
				<label for="empCode" class="control-label">Appraiser Mid Year Review :</label>
			</div>
			<div>
				<textarea
					ng-model="employeeCareerAspirationDetails.midYearCommentsManagerReview"
					ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear == 1 || employeeCareerAspirationDetails.midYearComments !=null"
					rows="10" style="width: 90%;" disabled
					placeholder="">
					</textarea>
			</div>
		</div>
		<div class="col-md-4">
			<div>
				<textarea ng-model="employeeCareerAspirationDetails.yearEndComments"
					ng-if="loggedUserAppraisalDetails.finalYear == 1" rows="10"
					style="width: 90%;"
					ng-disabled="employeeCareerAspirationDetails.yearEndCommentsStatus === 1"
					placeholder="">
					</textarea>
			</div>
			<div ng-if="loggedUserAppraisalDetails.finalYear == 1">
				<label for="empCode" class="control-label">Appraiser Year
					End Review :</label>
			</div>
			<div>
				<textarea
					ng-model="employeeCareerAspirationDetails.yearEndCommentsManagerReview"
					ng-if="loggedUserAppraisalDetails.finalYear == 1" rows="10"
					style="width: 90%;" disabled
					placeholder="">
					</textarea>
			</div>
		</div>
	</form>
</div>
