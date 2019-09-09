<div class="row">
	<div class="col-md-12">
		<div class="section-tag">
			<span>Appraisal Panel</span>
		</div>
	</div>
</div>
<div class="row" ng-init="onLoad()">
	<div class="col-md-12" style="margin-top: 15px;">
		<uib-tabset active="activeJustified" justified="true"> <uib-tab
			index="0" heading="KRA Finalization" select="getActiveTabId(0)">
		<form class="form-horizontal" role="form" name="startForm"
			ng-submit="start()">
			<div class="row" style="margin-top: 15px;">

				<div class="section-tag">
					<div class="col-md-2">
						<label for="start" class="control-label">Appraisal Year</label> <select
							ng-model="formData.appraisalYear"
							ng-options="c as c.name  for c in appraisalYearList track by c.id" disabled="true"
							class="form-control input-sm">
							<option value="">Select Year </option>
						</select>
					</div>
					 <div class="col-md-3" style="color: red;">
						<label for="eligibility" class="control-label">Appraisal
							Eligibility Criteria</label><input type="date" id="eligibility"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.eligibility" required>
					</div> 
					<div class="col-md-2">
						<label for="start" class="control-label">Submission Start</label><input
							type="date" id="start" placeholder="Date of Joining"
							class="form-control" ng-model="formData.startDateStart" required>
					</div>
					<div class="col-md-2">
						<label for="expired" class="control-label">Submission
							Expired</label><input type="date" id="expired"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.endDateStart" required>
					</div>
					<div class="col-md-2">
					<label for="all" class="control-label"
						style="margin-right: 10px;">ALL</label> <input type="checkbox" ng-disabled="true"
						ng-model="all" id="all">
					<!-- ng-change="checkedAll(formData.allStart)" -->
				</div>
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				
				 <div class="col-md-6">
					<label for="initialYearSubject" class="control-label">Subject</label>
					<input type="text" id="initialYearSubject"
							placeholder="Subject" class="form-control" ng-model="formData.initialYearSubject">
				</div>
				<div class="col-md-1" >
						<label class="control-label" style="margin-top: 50px;"></label>
						<button type="submit" 
							class="btn btn-outline-secondary" data-dismiss="modal" data-loading-text="<i class='fa fa-spinner fa-spin '></i> Starting">Start </button>
					</div>
			<!--	<div class="col-md-3">
					<label for="designationIdStart" class="control-label">Designation</label>
					<select data-placeholder="Select a Designation"
						ng-disabled="isStartCheckedAll" style="width: 100%;" tabindex="-1"
						aria-hidden="true" id="designationIdStart" style="width: 100%;"
						multiple>
						<option value="">Please select</option>
					</select>
				</div>
				<div class="col-md-5">
					<label for="nameStart" class="control-label">Search by
						Individual Employee</label>
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Enter Employee Code" ng-disabled="isStartCheckedAll"
							ng-model="formData.nameStart" id="nameStart">
						<div class="input-group-btn">
							<button class="btn btn-outline-secondary" type="button"
								ng-disabled="isStartCheckedAll">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</div> -->
			</div>
			<div class="row">
			<div class="col-md-12">
					<div><label for="content" class="control-label">Content</label></div>
					<div>
					<textarea id="editor1" name="editor1" rows="10" cols="80">                        
                    </textarea>
					</div>
				</div>
			</div>
			
		</form>
		
		<div id="gridMaster" style="margin-top:15px;" ui-grid="gridOptionsInitial" class="gridStyle" ng-show="showAppraisedEmployeeInitial"
				ui-grid-auto-resize  ui-grid-exporter></div>
				
		</uib-tab> <uib-tab index="1" heading="Mid Year Assessment" select="getActiveTabId(1)">
		<form class="form-horizontal" role="form" name="midForm"
			ng-submit="start()">
			<div class="row" style="margin-top: 15px;">

				<div class="section-tag">
					<div class="col-md-2">
						<label for="start" class="control-label">Appraisal Year</label> <select
							ng-model="formData.appraisalYear"
							ng-options="c as c.name  for c in appraisalYearList track by c.id" disabled="true"
							class="form-control input-sm">
							<option value="">Select Year </option>
						</select>
					</div>
					 <div class="col-md-3" style="color: red;">
						<label for="midTermeligibility" class="control-label" style="text-align: left;">Mid Term Appraisal
							Eligibility Criteria</label><input type="date" id="midTermeligibility"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.midTermeligibility" required>
					</div> 
					<div class="col-md-2">
						<label for="start" class="control-label">Submission Start</label><input
							type="date" id="start" placeholder="Date of Joining"
							class="form-control" ng-model="formData.startDateMid" required>
					</div>
					<div class="col-md-2">
						<label for="expired" class="control-label">Submission
							Expired</label><input type="date" id="expired"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.endDateMid" required>
					</div>
					<div class="col-md-2">

					<label for="all" class="control-label"
						style="margin-right: 10px;">ALL</label> <input type="checkbox" ng-disabled="true"
						ng-model="all" id="all">
					<!-- ng-change="checkedAll(formData.allMid)" -->
				</div>
					<!-- <div class="col-md-1">
						<label class="control-label" style="margin-top: 50px;"></label>
						<button type="submit" style="background-color: #325772;"
							class="btn btn-outline-secondary" data-dismiss="modal">Start</button>
					</div> -->
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				
				<div class="col-md-6">
					<label for="midYearSubject" class="control-label">Subject</label>
					<input type="text" id="midYearSubject"
							placeholder="Subject" class="form-control" ng-model="formData.midYearSubject"
							>
				</div>
				<div class="col-md-1" >
						<label class="control-label" style="margin-top: 50px;"></label>
						<button type="submit"
							class="btn btn-outline-secondary" data-dismiss="modal">Start</button>
					</div>
				<!-- <div class="col-md-3">
					<label for="departmentIdMid" class="control-label">Department</label>
					<select data-placeholder="Select a Department"
						ng-disabled="isMidCheckedAll" style="width: 100%;" tabindex="-1"
						aria-hidden="true" id="departmentIdMid" style="width: 100%;"
						multiple>
						<option value="">Please select</option>
					</select>
					</div>
				<div class="col-md-3">
					<label for="designationIdMid" class="control-label">Designation</label>
					<select data-placeholder="Select a Designation"
						ng-disabled="isMidCheckedAll" style="width: 100%;" tabindex="-1"
						aria-hidden="true" id="designationIdMid" style="width: 100%;"
						multiple>
						<option value="">Please select</option>
					</select>
				</div>
				<div class="col-md-5">
					<label for="nameMid" class="control-label">Search by
						Individual Employee</label>
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Enter Employee Code" id="nameMid" name="nameMid" ng-disabled="isMidCheckedAll"
							ng-model="formData.nameMid">
						<div class="input-group-btn">

							<button class="btn btn-outline-secondary" type="submit" ng-disabled="isMidCheckedAll">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</div> -->
			</div>
			
			<div class="row">
			<div class="col-md-12">
					<div><label for="content" class="control-label">Content</label></div>
					<div><textarea id="editorMid" name="editorMid" rows="10" cols="80">                         
                    </textarea></div>
				</div>
			</div>
		</form>
		<div id="gridMaster" style="margin-top:15px;" ui-grid="gridOptionsMid" class="gridStyle" ng-show="showAppraisedEmployeeMid"
				ui-grid-auto-resize  ui-grid-exporter></div>
		
		</uib-tab> <uib-tab index="2" heading="Final Year Assessment" select="getActiveTabId(2)">
		<form class="form-horizontal" role="form" name="finalForm"
			ng-submit="start()">
			<div class="row" style="margin-top: 15px;">

				<div class="section-tag">
					<div class="col-md-2">
						<label for="start" class="control-label">Appraisal Year</label> <select
							ng-model="formData.appraisalYear"
							ng-options="c as c.name  for c in appraisalYearList track by c.id" disabled="true"
							class="form-control input-sm">
							<option value="">Select Year </option>
						</select>
					</div>
					<div class="col-md-3" style="color: red;">
						<label for="finalYearligibility" class="control-label" style="text-align: left;">Final Year Appraisal
							Eligibility Criteria</label><input type="date" id="finalYearligibility"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.finalYearligibility" required>
					</div> 
					<div class="col-md-2">
						<label for="start" class="control-label">Submission Start</label><input
							type="date" id="start" placeholder="Date of Joining"
							class="form-control" ng-model="formData.startDateFinal" required>
					</div>
					<div class="col-md-2">
						<label for="expired" class="control-label">Submission
							Expired</label><input type="date" id="expired"
							placeholder="Date of Joining" class="form-control"
							ng-model="formData.endDateFinal" required>
					</div>
					<div class="col-md-2">
					<label for="all" class="control-label"
						style="margin-right: 10px;">ALL</label> <input type="checkbox" ng-disabled="true"
						ng-model="all" id="all">
					<!-- ng-change="checkedAll(formData.allFinal)" -->
				</div>
					<!-- <div class="col-md-1">
						<label class="control-label" style="margin-top: 50px;"></label>
						<button type="submit" style="background-color: #325772;"
							class="btn btn-outline-secondary" data-dismiss="modal">Start</button>
					</div> -->
				</div>
			</div>
			<div class="row" style="margin-top: 15px;">
				
				<div class="col-md-6">
					<label for="finalYearSubject" class="control-label">Subject</label>
					<input type="text" id="finalYearSubject"
							placeholder="Subject" class="form-control" ng-model="formData.finalYearSubject"
							>
				</div>
				<div class="col-md-1">
						<label class="control-label" style="margin-top: 50px;"></label>
						<button type="submit" 
							class="btn btn-outline-secondary" data-dismiss="modal">Start</button>
					</div>
				<!-- <div class="col-md-3">
					<label for="departmentIdFinal" class="control-label">Department</label>
					<select data-placeholder="Select a Department"
						ng-disabled="isFinalCheckedAll" style="width: 100%;" tabindex="-1"
						aria-hidden="true" id="departmentIdFinal" style="width: 100%;"
						multiple>
						<option value="">Please select</option>
					</select>
					</div>
				<div class="col-md-3">
					<label for="designationIdFinal" class="control-label">Designation</label>
					<select data-placeholder="Select a Designation"
						ng-disabled="isFinalCheckedAll" style="width: 100%;" tabindex="-1"
						aria-hidden="true" id="designationIdFinal" style="width: 100%;"
						multiple>
						<option value="">Please select</option>
					</select>
				</div>
				<div class="col-md-5">
					<label for="nameFinal" class="control-label">Search by
						Individual Employee</label>
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Enter Employee Code" id="nameFinal" name="nameFinal" ng-disabled="isFinalCheckedAll"
							ng-model="formData.nameFinal">
						<div class="input-group-btn">
							<button class="btn btn-outline-secondary" type="submit" ng-disabled="isFinalCheckedAll">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</div> -->
			</div>
			<div class="row">
			<div class="col-md-12">
					<div><label for="content" class="control-label">Content</label></div>
					<div><textarea id="editorFinal" name="editorFinal" rows="10" cols="80">
                                            
                    </textarea></div>
				</div>
			</div>
			
		</form>
		
		<div id="gridMaster" style="margin-top:15px;" ui-grid="gridOptionsFinal" class="gridStyle"  ng-show="showAppraisedEmployeeFinal"
				ui-grid-auto-resize  ui-grid-exporter></div>
		</uib-tab></uib-tabset>
		
	</div>
</div>
<style>
a.cke_path_item, span.cke_path_empty {
    display: none !important;
    }
#cke_33
{
   display: none !important;
}
#cke_40
{
   display: none !important;
}
#cke_29
{
   display: none !important;
}
#cke_54
{
   display: none !important;
}

#cke_80
{
   display: none !important;
}
#cke_84
{
   display: none !important;
}
#cke_91
{
   display: none !important;
}
#cke_105
{
   display: none !important;
}


#cke_131
{
   display: none !important;
}
#cke_135
{
   display: none !important;
}
#cke_142
{
   display: none !important;
}
#cke_156
{
   display: none !important;
}

</style>