<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div>
	<div class="section-tag">
		<span>Final Review</span>
	</div>
	<div class="col-md-12">
	<div id="finalReviewAlert">{{message}}</div>
	<form class="form-horizontal" style="font-size: 10px;" role="form">
		<div class="col-md-6"
			style="height: 510px; font-size: 12px; border: 1px solid black;">
			<span style="font-weight: 600;">Comments of Assessor(First Level Superior)</span>
			<textarea rows="5" style="width: 90%;" ng-model="firstLevelSuperiorComments"></textarea>
			<br> <br>
			<p>
				<u><span style="font-weight: 600;">Decalartion:</span></u>Based on the rating by the Appraisee and as given
				by the Appraiser in respect of KRAs, Exceptional Initiavtie and
				Competence in this form, the Appraisee has been counseled so as to
				bridge the gap between the Assessor Rating and the Assessee rating
				and his/her perception/self rating.
			</p>
			<br> <br>
			<p style="font-weight: 600;">Signarure</p>
			<span style="font-weight: 600;">Date</span>
			<p style="border: 1px solid black;"></p>
			<span style="font-weight: 600;">Comments of Assessor(second Level Superior)</span>
			<textarea rows="5" ng-model="secondLevelSuperiorComments" style="width: 90%;"></textarea>
			<br> <br> <br> <br>
			<p style="font-weight: 600;">Signature</p>
			<span style="font-weight: 600;">Date</span>
		</div>
		<!-- this right comment -->
		<div class="col-md-6"
			style="height: 510px; font-size: 12px; border: 1px solid black;">
			<span style="font-weight: 600;">Comments of Assessee</span>
				<textarea rows="12" style="width: 90%;" ng-model="assesseeComments"></textarea>
				<button style="margin-top: 15px;" type="button"
						class="btn btn-outline-secondary" ng-click="save()">Save</button>
			<br> <br> <br> <br>
			<p style="font-weight: 600;">Signature</p>
			<span style="font-weight: 600;">Date</span>
		</div>
		</form>
	</div>
</div>
