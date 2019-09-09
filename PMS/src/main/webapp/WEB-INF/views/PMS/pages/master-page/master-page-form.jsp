<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<div class="col-md-12" ng-init="getAllParameters()">
		<div class="col-md-3">
			<form class="form-horizontal" role="form">
				<div class="row">
					<div class="col-md-12">
						<label for="parameterId" class="control-label">Select
							Module</label> <select ng-model="parameter" id="parameterId"
							ng-change="getAllParameterData()"
							ng-options="dp as dp.name  for dp in parametersList track by dp.id"
							class="form-control">
							<option value="">Please select</option>
						</select>
					</div>
					<div class="col-md-12" ng-show=addRole>
						<label for="moduleId" class="control-label">Select
							Department</label> <select ng-model="department" id="department"
							ng-change="getAllOrganizationalRoles()"
							ng-options="dp.id as dp.name  for dp in departmentList"
							class="form-control">
							<option value="">Please select</option>
						</select>
					</div>
					<div class="col-md-12">
						<label for="roleId" class="control-label">Create New
							{{parameter.name}}</label> <input type="text" id="roleId"
							placeholder="Enter Name" class="form-control" ng-model="name"
							capitalize-first>
					</div>

					<div class="col-md-12">
						<label>Description</label>
						<textarea class="form-control" rows="3"
							placeholder="Description Details" ng-model="description"
							capitalize-first>
         </textarea>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button ng-click="save()" type="submit"
							class="btn btn-outline-secondary">
							<spring:message code="button.save" />
						</button>

					</div>
				</div>
			</form>
			<div style="margin-top: 15px;" class="col-md-12" id="masterPageAlert">{{message}}</div>
		</div>
		<div class="col-md-9">

			<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
				ui-grid-auto-resize></div>
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
				<h5 class="modal-title">Are you sure want to delete {{parameter.name}} ?</h5>
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

<!-- Modal Update Field -->
<div class="modal fade" id="masterPageUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h5 class="modal-title">Update {{parameter.name}} </h5>
			</div>
			<div class="modal-body">		
			<form class="form-horizontal" role="form">
				<div class="row">
					<div class="col-md-6">
						<label for="roleId" class="control-label">Update Name
							{{parameter.name}}</label> <input type="text" id="roleId"
							placeholder="Enter Name" class="form-control" ng-model="updateName"
							capitalize-first>
					</div>

					<div class="col-md-6">
						<label>Update Description</label>
						<textarea class="form-control" rows="3"
							placeholder="Description Details" ng-model="updateDescription"
							capitalize-first>
                    </textarea>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button ng-click="edit()" type="submit" data-dismiss="modal"
							class="btn btn-sm btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
				<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal">No</button>
					</div>
				</div>
			</form>	
			
			</div>
			<!-- <div class="modal-footer">
			<div class="row">
					<div class="col-md-12">
					<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal" ng-click="deleteParameter()">Yes</button>
				
					</div>
				</div>
			</div>	 -->	
		</div>
	</div>
</div>

