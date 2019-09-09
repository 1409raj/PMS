<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span><spring:message code="heading.organizationRole.list"/></span>
</div>
<div class="row" ng-init="getAllDepartmentList()">
	<div class="col-md-3">
		<form class="form-horizontal" role="form"
			name="createOrganizationRole" ng-submit="save()">
			<div class="row">
				<div class="col-md-12">
					<label for="moduleId" class="control-label"><spring:message code="label.organizationRole.select"/></label> <select ng-model="department" id="department" required
						ng-change="getAllOrganizationalRolesList()"
						ng-options="dp.id as dp.name  for dp in departmentList"
						class="form-control">
						<option value="">Please select</option>
					</select>
				</div>
				<div class="col-md-12">
					<label for="nameId" class="control-label"><spring:message code="label.organizationRole.name"/></label> <input type="text" id="nameId"
						placeholder="Enter Organization Role"
						class="form-control" ng-model="name" required>
				</div>
				<div class="col-md-12">
					<label><spring:message code="label.description"/></label>
					<textarea class="form-control" rows="3"
						placeholder="Description" ng-model="description">
                    </textarea>
				</div>
				<div class="col-md-12" style="margin-top: 15px;">
					<button type="submit" class="btn btn-outline-secondary">
						<spring:message code="button.save" />
					</button>
				</div>
			</div>
		</form>
		<div style="margin-top: 15px;" class="col-md-12"
			id="organizationRolesAlert">{{message}}</div>
	</div>
	<div class="col-md-9">
		<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
			ui-grid-auto-resize></div>
	</div>
</div>


<!-- Modal Update Field -->
<div class="modal fade" id="organizationRolesUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><spring:message code="heading.organizationRole.modify"/></h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form"
					name="updateOrganizationRole" ng-submit="edit()">
					<div class="row">
						<div class="col-md-6">
							<label for="updateName" class="control-label"><spring:message code="label.organizationRole.name"/></label> <input type="text" id="updateName"
								placeholder="Enter Organization Role" class="form-control"
								ng-model="updateName" required>
						</div>
						<div class="col-md-6">
							<label for="updateDescription"><spring:message code="label.description"/></label>
							<textarea class="form-control" rows="3" id="updateDescription"
								placeholder="Description" ng-model="updateDescription">
                    </textarea>
						</div>
						<div class="col-md-12" style="margin-top: 15px;">
							<button type="submit" class="btn btn-sm btn-outline-secondary">
								<spring:message code="button.save" />
							</button>
							<button type="button" class="btn btn-sm btn-outline-secondary"
								data-dismiss="modal"><spring:message code="button.cancel"/></button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

