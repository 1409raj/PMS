<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.ui-grid-coluiGrid-007 {
	width: 79px;
	text-align: center;
}
</style>
<div class="section-tag">
	<span><spring:message code="heading.behavioralComptence.list" /></span>
	<span style="float: right;"><span style="font-weight: 400;">Total
			weightage count : &nbsp;</span><span
		ng-style="weightageCount == 100 ? {'color':'green'} : {'color':'red'}">{{weightageCount}}</span></span>
</div>
<div class="row" >
	<div class="col-md-3">
		<form class="form-horizontal" role="form"
			name="createBehavioralComptence" ng-submit="save()">
			<div class="row">
			   <div class="col-md-12">
					<label for="userId" class="control-label">Employee Type</label> <select ng-model="user" id="userId" required
						ng-options="dp.name as dp.name  for dp in users" ng-change="getAllBehavioralComptenceList()"
						class="form-control">
						<option value="">Please select</option>
					</select>
				</div>
				<div class="col-md-12">
					<label for="nameId" class="control-label"><spring:message
							code="label.behavioralComptence.name" /></label> <input type="text"
						id="nameId" placeholder="Enter Behavioral Comptence"
						class="form-control" ng-model="name"
						style="text-transform: uppercase" required>
				</div>
				<div class="col-md-12">
					<label for="weightageId" class="control-label"><spring:message
							code="label.behavioralComptence.weightage" /> </label> <input
						type="text" id="weightageId" placeholder="Enter Weightage"
						class="form-control" ng-model="weightage"
						onkeypress='return event.charCode >= 48 && event.charCode <= 57'
						maxlength="2" required>
				</div>
				<div class="col-md-12">
					<label for="description"><spring:message
							code="label.description" /></label>
					<textarea class="form-control" rows="5" id="description"
						placeholder="Description" ng-model="description" required>
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
			id="behavioralComptenceAlert">{{message}}</div>
	</div>
	<div class="col-md-9">

		<div id="gridMaster" ui-grid="gridOptions" class="gridStyle"
			ui-grid-auto-resize></div>
	</div>
</div>

<!-- Modal Update Field -->
<div class="modal fade" id="behavioralComptenceUpdate" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message code="heading.behavioralComptence.modify" />
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form"
					name="updateBehavioralComptence" ng-submit="edit()">
					<div class="row">
						<div class="col-md-6">
							<label for="updateName" class="control-label"><spring:message
									code="label.behavioralComptence.name" /></label> <input type="text"
								id="updateName" placeholder="Enter Behavioral Comptence"
								class="form-control" ng-model="updateName"
								style="text-transform: uppercase" required>
						</div>
						<div class="col-md-6">
							<label for="updateWeightage" class="control-label"><spring:message
									code="label.behavioralComptence.weightage" /></label> <input
								type="text" id="updateWeightage" placeholder="Enter Weightage"
								class="form-control" ng-model="updateWeightage"
								onkeypress='return event.charCode >= 48 && event.charCode <= 57'
								maxlength="2" required>
						</div>
						<div class="col-md-12">
							<label for="updateDescription"><spring:message
									code="label.description" /></label>
							<textarea class="form-control" rows="5" id="updateDescription"
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

<!-- Modal confirmation for carry forward data to next year-->
<div class="modal fade" id="behavioraldataForwardNextYear" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<spring:message code="heading.behavioralComptence.carryFormad" />
				</h4>
			</div>
			<div class="modal-body">
				<input type="radio" ng-model="format" value="1"> <label><spring:message
						code="label.behavioralComptence.carryFormad" /></label> <br> <input
					type="radio" ng-model="format" value="2"><label><spring:message
						code="label.behavioralComptence.newFormat" /></label>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal" ng-click="carryForwardToNextYear()">
					<spring:message code="button.save" />
				</button>
				<button type="button" class="btn btn-sm btn-outline-secondary"
					data-dismiss="modal">
					<spring:message code="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<style>
.swal-text:first-child {
	margin-top: 15px;
	color: rgb(238, 65, 61);
	font-size: 18px;
}

.swal-button {
	background-color: #ee413d;
	color: white;
	border-radius: 0px;
}

.swal-button:hover {
	color: white;
	text-decoration: none;
	background-color: #315772;
}

.swal-footer {
	text-align: center;
	padding: 0px;
	padding-bottom: 15px;
}
</style>