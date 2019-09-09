<div ng-controller="HRDashboardController">
	<div class="row">
		<div class="col-md-6 section-tag">
			<span>HR Dashboard</span>
		</div>
	</div>
	<div class="row">
	<div class="col-md-2">
		<select ng-model="selectedAppraisalStage"
			ng-options="x for x in appraisalStages" class="form-control" required data-ng-change="getDashBoardDetails()">
			<option value="">Please select</option>
		</select>

	</div>
	<div class="col-md-12"
			style="font-weight: bold;margin-top: 15px;" ng-show='showCharts'>
			<div
				style="text-align: center; font-size: 15px; background-color: #315772; color: white;">
				<span>DEPARTMENT WISE</span>
			</div>
			<canvas id="barChart"></canvas>
			<!-- <div ng-if="barChartDataEmployeePending.length == 0 && barChartDataManagerPending.length == 0 && barChartDataClosed.length == 0 " style="text-align:center;margin-top:15px;"><span style="color:red;font-weight:bold;">No Data Found</span></div> -->
		</div>
	
	<div class="col-md-12"
		ng-if="overAllDepartmentData.pendingWithEmployee == 0 && overAllDepartmentData.pendingWithManager == 0 && overAllDepartmentData.closed == 0 "
		style="text-align: center; margin-top: 15px;">
		<span style="color: red; font-weight: bold;">No Data Found</span>
	</div>
    <div class="col-md-12" ng-show="showLoader" ng-click="ShowHide()">
		<div class="loader"></div>
	</div>
	<div class="col-md-12" ng-show="IsVisible" style="margin-top: 15px;"
		id="department_hrDashboard">
		
		<div style="margin-top: 15px;" ng-show='showCharts'>
		<div class="col-md-4"
			style="font-weight: bold;">
			<div
				style="text-align: center; font-size: 15px; background-color: #315772; color: white;">
				<span>OVER ALL KRA STATISTICS</span>
			</div>
			<canvas id="myChart"></canvas>
		</div>
	</div>
		
		
		<div class="col-md-4" ng-repeat="x in departmentWiseBarChatData">
			<div
				style="text-align: center; font-size: 15px; background-color: #315772; color: white;">
				<span>{{x.name}}</span>
			</div>
			<canvas
				ng-if="selectedAppraisalStage == 'In Planning' && (x.goalSetting > 0 || x.goalApproval > 0)"
				id="myChart-{{x.id}}" ng-bind="bindCanvas(x)">
		</canvas>
		<canvas
				ng-if="selectedAppraisalStage == 'In Review' && x.inReview > 0"
				id="myChart-{{x.id}}" ng-bind="bindCanvas(x)">
		</canvas>
		<canvas
				ng-if="selectedAppraisalStage == 'In Process' && (x.yearEndAssessment > 0 || x.assessmentApproval > 0)"
				id="myChart-{{x.id}}" ng-bind="bindCanvas(x)">
		</canvas>
			<div
				ng-if="x.goalSetting == 0 && x.goalApproval == 0"
				style="text-align: center; margin-top: 15px;">
				<span style="color: red; font-weight: bold;">No Data Found</span>
			</div>
			<div
				ng-if="x.inReview == 0"
				style="text-align: center; margin-top: 15px;">
				<span style="color: red; font-weight: bold;">No Data Found</span>
			</div>
			<div
				ng-if="x.yearEndAssessment == 0 && x.assessmentApproval == 0"
				style="text-align: center; margin-top: 15px;">
				<span style="color: red; font-weight: bold;">No Data Found</span>
			</div>
		</div>
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
					<div id="grid1" ui-grid="gridOptions" class="gridStyle"
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