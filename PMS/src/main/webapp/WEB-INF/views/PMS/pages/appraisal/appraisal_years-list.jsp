<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-008 {
     text-align: center;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.appraisalYear.list" /></span>
</div>
<div class="row" ng-init="getAllAppraisalYearList()">
	<div class="col-md-3">
		<form class="form-horizontal" role="form" name="createYear"
			ng-submit="save()">
			<div class="row">
				<div class="col-md-12">
					<label for="nameId" class="control-label"><spring:message
							code="label.appraisalYear.name" /></label> <input type="text" id="nameId"
						placeholder="Enter Name" class="form-control"
						ng-model="name" required>
				</div>
				<div class="col-md-12" style="margin-top: 15px;">
					<button type="submit" class="btn btn-outline-secondary">
						<spring:message code="button.save" />
					</button>
				</div>
			</div>
		</form>
		<div style="margin-top: 15px;" class="col-md-12"
			id="appraisalYearListAlert">{{message}}</div>
	</div>
	<div class="col-md-9">

		<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
			ui-grid-auto-resize></div>
	</div>
</div>

<!-- Modal Update Field -->
<div class="modal fade" id="appraisalYearUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message code="heading.appraisalYear.modify" />
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="updateYear"
					ng-submit="edit()">
					<div class="row">
						<div class="col-md-6">
							<label for="updateName" class="control-label"><spring:message
									code="label.appraisalYear.name" /></label> <input type="text"
								id="updateName" placeholder="Enter Name" class="form-control"
								ng-model="updateName" required>
						</div>
						<div class="col-md-12" style="margin-top: 15px;">
							<button type="submit" class="btn btn-sm btn-outline-secondary">
								<spring:message code="button.save" />
							</button>
							<button type="button" class="btn btn-sm btn-outline-secondary"
								data-dismiss="modal">
								<spring:message code="button.cancel" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

