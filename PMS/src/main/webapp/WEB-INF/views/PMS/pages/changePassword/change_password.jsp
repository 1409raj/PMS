<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-006
{
text-align:center;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.changePassword" /></span>
</div>
<div class="row">
		<div class="col-md-4">
		<div class="col-md-12" id="changePasswordAlert">{{message}}</div>
		
			<form class="form-horizontal" role="form" name="changePassword" ng-submit="save()">
				<div class="row">
					<div class="col-md-12">
						<label for="currentPasswordId" class="control-label"><spring:message code="label.currentPassword" /></label> <input type="password" id="currentPasswordId"
							placeholder="Enter current password" class="form-control" ng-model="currentPassword" required>
					</div>
					<div class="col-md-12">
						<label for="newPasswordId" class="control-label"><spring:message code="label.newPassword" /></label> <input type="password" id="newPasswordId"
							placeholder="Enter new password" class="form-control" ng-model="newPassword" required>
					</div>
					<div class="col-md-12">
						<label for="confirmPasswordId" class="control-label"><spring:message code="label.confirmPassword" /></label> <input type="password" id="confirmPasswordId"
							placeholder="Enter confirm password" class="form-control" ng-model="confirmPassword" required>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit"
							class="btn btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
					</div>
				</div>
			</form>
		</div>
</div>


