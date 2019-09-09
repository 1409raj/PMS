<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span><spring:message code="heading.empFormConfig.list" /></span>
</div>
<div class="row">
	<div class="col-md-12" ng-init="getEmployeeDynamicFormDetails()">
		<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
			ui-grid-auto-resize></div>
	</div>
</div>

<!-- Modal Update Field -->
<div class="modal fade" id="employeeConfigurationUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message code="heading.empFormConfig.modify" />
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="updateColumnName"
					ng-submit="save()">
					<div class="row">
						<div class="col-md-6">
							<label for="updateName" class="control-label"><spring:message
									code="label.empFormConfig.name" /></label> <input type="text"
								id="updateName" placeholder="Enter Name" class="form-control"
								ng-model="updateName" required>
						</div>
						<div class="col-md-6">
							<label for="updateDescription"><spring:message
									code="label.description" /></label>
							<textarea class="form-control" rows="3" id="updateDescription"
								placeholder="Description" ng-model="updateDescription" required>
                    </textarea>
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

