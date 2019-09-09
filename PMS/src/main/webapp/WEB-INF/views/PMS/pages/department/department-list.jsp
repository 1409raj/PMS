<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-006
{
text-align:center;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.department.list" /></span>
</div>
<div class="row" ng-init="getAllDepartmentList()">
		<div class="col-md-3">
			<form class="form-horizontal" role="form" name="createDepartment" ng-submit="save()">
				<div class="row">
					<div class="col-md-12">
						<label for="nameId" class="control-label"><spring:message code="label.department.name" /></label> <input type="text" id="nameId"
							placeholder="Enter Department" class="form-control" ng-model="name" required>
					</div>
					<div class="col-md-12">
						<label for="description"><spring:message code="label.description" /></label>
						<textarea class="form-control" rows="3" id="description"
							placeholder="Description" ng-model="description">
                    </textarea>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit"
							class="btn btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
					</div>
				</div>
			</form>
			<div style="margin-top: 15px;" class="col-md-12" id="departmentAlert">{{message}}</div>
		</div>
		<div class="col-md-9">
			<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
				ui-grid-auto-resize ></div>
		</div>
</div>

<!-- Modal Update Field -->
<div class="modal fade" id="departmentUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"><spring:message code="heading.department.modify" /></h4>
			</div>
			<div class="modal-body">		
			<form class="form-horizontal" role="form" name="updateDepartment" ng-submit="edit()">
				<div class="row">
					<div class="col-md-6">
						<label for="updateName" class="control-label"><spring:message code="label.department.name" /></label> 
						<input type="text" id="updateName"
							placeholder="Enter Department" class="form-control" ng-model="updateName" required>
					</div>
					<div class="col-md-6">
						<label for="updateDescription" ><spring:message code="label.description" /></label>
						<textarea class="form-control" rows="3" id="updateDescription"
							placeholder="Description" ng-model="updateDescription">
                    </textarea>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit"
							class="btn btn-sm btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
				<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal"><spring:message code="button.cancel" /></button>
					</div>
				</div>
			</form>	
			</div>
		</div>
	</div>
</div>

