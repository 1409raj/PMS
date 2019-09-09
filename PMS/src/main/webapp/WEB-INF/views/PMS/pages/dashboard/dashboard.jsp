<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div ng-controller="DashboardController">
	<div class="row">
		<div class="col-md-6 section-tag">
			<span><spring:message code="menu.dashboard" /></span>
		</div>
	</div>
	<div class="row">
	
	<div class="col-md-3">
		<label for="levelId" class="control-label">Employee Type</label> <select
			ng-model="levelType" id="levelId" ng-change="getAppraisalData()"
			ng-options="dp as dp  for dp in employeeLevel"
			 class="form-control">
			<option value="">Please select</option>
		</select>
	</div>
	
	
		<div class="col-md-2">
		<label for="appraisalId" class="control-label">Appraisal Type</label>
			<select ng-model="selectedAppraisalStage"
				ng-options="x for x in appraisalStages" class="form-control"
				required data-ng-change="getDashBoardDetails()">
				<option value="">Please select</option>
			</select>

		</div>
		<div class="col-md-12" style="margin-top:15px;">
		 <!--<div class="col-md-6"  ng-show="chartLayout"  -->
				<div class="col-md-6"
				style="border: 1px solid #d4d4d4; font-weight: bold; padding: 0px;">
				<div
					style="text-align: center; font-size: 15px; background-color: #315772; color: white;">
					<span>OVER ALL</span>
				</div>
				<canvas id="firstManagerChart" ng-show="!hideCanvas"></canvas>
				<canvas id="secondManagerChart" ng-show="hideCanvas"></canvas>
			</div>
			<div class="col-md-6"></div>
		</div>
		<div class="col-md-6" ng-if="chartData.length == 0"
			style="text-align: center; margin-top: 15px;">
			<span style="color: red; font-weight: bold;">No Data Found</span>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{modalHeading}}</h4>
				</div>
				<div class="modal-body">
					<div id="grid" ui-grid="gridOptions" class="gridStyle"
						ui-grid-auto-resize ui-grid-exporter></div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary"
						data-dismiss="modal" style="float: right;">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>