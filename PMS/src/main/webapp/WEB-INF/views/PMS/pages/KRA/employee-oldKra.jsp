<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.input-group {
	width: 100%;
}

/* td {
	word-break: break-all;
} */
textarea {
	resize: none;
	font-size: 14px !important;
}

.upload-btn-wrapper {
	position: relative;
	overflow: hidden;
	display: inline-block;
}

/* table, th, td {
	width: max-content !important;
} */
table, th, td {
	border: 1px solid lightslategray !important;
	text-align: center;
	border-collapse: separate !important; /* This line */
}

.btn_file {
	border: 2px solid gray;
	color: gray;
	background-color: white;
	border-radius: 8px;
	font-size: 15px;
	font-weight: bold;
}

.upload-btn-wrapper input[type=file] {
	font-size: 100px;
	position: absolute;
	left: 0;
	top: 0;
	opacity: 0;
}

input[type=date].form-control, input[type=time].form-control, input[type=datetime-local].form-control,
	input[type=month].form-control {
	width: 115px !important;
}
.modal-lg {
    width: 95% !important;
}
</style>
<div class="section-tag">
	<div>
		<span><spring:message code="heading.kra" /></span>
	</div>
</div>
<div class="col-md-12" style="padding: 0px;">
	<div class="sub_text">
		This section will include objectives set for different parameters as
		well as the job description of the employee. Reporting manager should
		decide the weightage for each section Viz. Financial Obj., Business
		Obj., Operational Obj. as per the Job Requirement and deliverables of
		the Individual and the same needs to be agreed by the assessee. Please
		note that organizational commitment is capped at 5% weightage.<span
			style="color: red;">Note - Weightage should be in absolute
			number.</span>
	</div>
</div>
 <div class="row" ng-init="getEmployeeOldKraDetails()">
 <div class="col-md-12" style="text-align: right; font-weight: 600;">
		<span
			style="color: #315772; font-weight: 600; text-decoration: underline;cursor: pointer;" ng-click="gotoNewKRA()">Go To
			New KRS'S</span>
	</div>
	<div class="col-md-12"
		style="overflow: auto; margin-top: 15px; height: 420px;">
		<%-- <table border="1">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
					<th rowspan="2"></th>
					<th rowspan="2"><spring:message code="th.kra.sr.no" /></th>
					<th rowspan="2" style="width: 500px;"><spring:message
							code="th.kra.smart.goal" /></th>
					<th rowspan="2" style="width: 1200px;"><spring:message
							code="th.kra.targrt" /></th>
					<th rowspan="2">ACHIEVEMENT<br>DATE
					</th>
					<th rowspan="2"><spring:message code="th.weightage" /><br>A<br>(%)
					</th>
					<th colspan="4"><spring:message code="th.mid.year.review" /></th>

					<th colspan="4"><spring:message code="th.final.year.review" /></th>

					<th rowspan="2">ATTACHMENTS</th>
					<th rowspan="2">MID YEAR RATING <br> A*B
					</th>
					<th rowspan="2">YEAR END RATING <br> A*C
					</th>
					<th rowspan="2"><spring:message code="th.final.score" /><br>30%
						of A*B and 70% of A*C</th>
					<!-- <th rowspan="2">Average</th> -->

				</tr>
				<tr>
					<th style="width: 400px;"><spring:message
							code="th.achievement" /></th>
					<th><spring:message code="th.self.rating" /></th>
					<th><spring:message code="th.appraisar.rating" /><br>B</th>
					<th style="width: 400px;">MID YEAR <br> ASSESSOR REMARKS
					</th>
					<th style="width: 400px;"><spring:message
							code="th.achievement" /></th>
					<th><spring:message code="th.self.rating" /></th>
					<th><spring:message code="th.appraisar.rating" /><br>C</th>
					<th style="width: 400px;">YEAR END <br> ASSESSOR REMARKS
					</th>
				</tr>
			</thead> --%>
			<table border="1" style="border-collapse: collapse;width: 2000px; overflow: scroll;">
			<thead style="background-color: #315772; color: #ffffff;">
				<tr>
				<th rowspan="2"></th>
					<th rowspan="2" style="width:2%;"><spring:message code="th.kra.sr.no" /></th>
					<th rowspan="2" style="width:10%"><spring:message code="th.kra.smart.goal" /></th>
					<th rowspan="2" style="width:7%;"><spring:message code="th.kra.targrt" /></th>
					<th rowspan="2"  style="width:">ACHIEVEMENT<br>DATE</th>
					<th rowspan="2" style="width:"><spring:message code="th.weightage" /><br>A <br>(%)
					</th>
					<th colspan="4" style="width:5%;"><spring:message code="th.mid.year.review" /></th>
					<th colspan="4"><spring:message code="th.final.year.review" /></th>
					<th rowspan="2" style="width:5%;">ATTACHMENT</th>
					<th rowspan="2" style="width:5%;">MID YEAR<br> RATING <br> A*B</th>
					<th rowspan="2"  style="width:5%;">Year END <br>RATING <br> A*C
					</th>
					<th rowspan="2" ><spring:message
							code="th.final.score" /><br>30% of A*B <br>and 70% of A*C</th>
					<!-- <th rowspan="2">Average</th> -->
					<%-- <th rowspan="2"><spring:message code="th.remrks" /></th> --%>
					
					
				</tr>
				<tr>
					<th style="width:15%;"><spring:message code="th.achievement" /></th>
					<th style="width:7%;"><spring:message code="th.self.rating" /></th>
					<th style="width:5%;"><spring:message code="th.appraisar.rating" /><br>	B</th>
					<th style="width: 10%;">MID YEAR <br> ASSESSSMENT REMARKS</th>
					<th style="width:10%;"><spring:message code="th.achievement" /></th>
					<th style="width:5%;"><spring:message code="th.self.rating" /></th>
					<th style="width:2%;"><spring:message code="th.appraisar.rating" /><br>
					C</th>
					<th style="width: 12%;">YEAR END <br>ASSESSMENT REMARKS</th>
				</tr>
			</thead>
			<tbody>
				<tr class="tr-color">
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionA" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<!-- <td></td> -->
				</tr>
				<tr ng-repeat="choice in sectionAList track by $index">
					<td ng-if="$index =='0'"><i class="fa fa-plus btn plus-button"
						style="border: 2px solid lightgray;"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled"
						ng-click="addNewChoice('sectionA')"></i> <span
						ng-if="$index > '0'" class="input-group-addon btn minus-button"
						ng-click="removeNewChoice($index,'sectionA')"><i
							class="fa fa-minus"></i></span></td>
					<td ng-if="$index > '0'"><span
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled"
						class="input-group-addon btn minus-button"
						ng-click="removeNewChoice($index,'sectionA')"><i
							class="fa fa-minus"></i></span></td>
					<td>{{$index+1}}</td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.smartGoal" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.target" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
					<td><input type="date" class="form-control input-sm" disabled
						ng-model="choice.achievementDate" style="height: 86px;"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></td>
					<td><input type="text" class="form-control input-sm"
						ng-change="countWeightage()" disabled
						style="text-align: center; height: 86px;"
						ng-model="choice.weightage"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"
						numbers-only maxlength="2"></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.midYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
					<td><select class="form-control input-sm" disabled
						style="height: 86px;" ng-model="choice.midYearSelfRating"
						ng-options="item for item in selectsRating"
						ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></select></td>
					<td>{{choice.midYearAppraisarRating}}</td>
					<td  class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.midYearAssessmentRemarks"
							ng-disabled="choice.midYearAssessmentRemarks ==null"></textarea>
					<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.finalYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
					<td><select class="form-control input-sm" disabled
						ng-model="choice.finalYearSelfRating" style="height: 86px;"
						ng-options="item for item in selectsRating"
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></select></td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.remarks"
							ng-disabled="choice.remarks ==null"></textarea>
					<span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
					<td>
						<div class="upload-btn-wrapper">
							<button class="btn_file" ng-if="choice.fileDummyName == null">Doc</button>
							<input type="file" file-model="{{'choice.fileName'}}" disabled
								ng-if="choice.fileDummyName == null"
								accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,
								text/plain, application/pdf, image/*"
								id="{{ 'kraFileDocumentDownloadSectionA' + $index }}"> <a
								ng-href="{{choice.fileName}}" style="color: red;"
								id="{{ 'kraDocumentDownloadSectionA' + $index }}"
								ng-if="choice.fileDummyName != null">Download</a>
						</div> <!-- <input type="file" file-model="{{'choice.fileName'}}"  id="{{ 'kraFileDocumentDownloadSectionA' + $index }}">
					<a ng-href="{{choice.fileName}}" style="color:red;" id="{{ 'kraDocumentDownloadSectionA' + $index }}" ng-if="choice.fileDummyName != null">Download</a>
					 -->

					</td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span></td>

				</tr>
				<tr class="tr-color">
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionB" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr data-ng-repeat="choice in sectionBList track by $index">
					<td ng-if="$index =='0'"><i class="fa fa-plus btn plus-button"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled"
						style="border: 2px solid lightgray;"
						ng-click="addNewChoice('sectionB')"></i></td>
					<td ng-if="$index > '0'"><span
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled"
						class="input-group-addon btn minus-button"
						ng-click="removeNewChoice($index,'sectionB')"><i
							class="fa fa-minus"></i></span></td>
					<td>{{$index+1}}</td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.smartGoal" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.target" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
					<td><input type="date" class="form-control input-sm" disabled
						ng-model="choice.achievementDate" style="height: 86px;"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></td>
					<td><input type="text" class="form-control input-sm"
						ng-change="countWeightage()" disabled
						style="text-align: center; height: 86px;"
						ng-model="choice.weightage"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"
						numbers-only maxlength="2"></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.midYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.midYearSelfRating" style="height: 86px;"
						ng-options="item for item in selectsRating" disabled
						ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></select></td>
					<td>{{choice.midYearAppraisarRating}}</td>
					<td class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.midYearAssessmentRemarks"
							ng-disabled="choice.midYearAssessmentRemarks ==null"></textarea>
					<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.finalYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.finalYearSelfRating" disabled
						ng-options="item for item in selectsRating" style="height: 86px;"
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></select></td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.remarks"
							ng-disabled="choice.remarks ==null"></textarea>
					<span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
					<td>
						<div class="upload-btn-wrapper">
							<button class="btn_file" ng-if="choice.fileDummyName == null">Doc</button>
							<input type="file" file-model="{{'choice.fileName'}}" disabled
								accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,
text/plain, application/pdf, image/*"
								id="{{ 'kraFileDocumentDownloadSectionB' + $index }}"> <a
								ng-href="{{choice.fileName}}" style="color: red;"
								id="{{ 'kraDocumentDownloadSectionB' + $index }}"
								ng-if="choice.fileDummyName != null">Download</a>
						</div> <!-- <input type="file" file-model="{{'choice.fileName'}}"  id="{{ 'kraFileDocumentDownloadSectionB' + $index }}">
					<a ng-href="{{choice.fileName}}" style="color:red;" id="{{ 'kraDocumentDownloadSectionB' + $index }}" ng-if="choice.fileDummyName != null">Download</a> -->


					</td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span></td>
					</tr>
				<tr class="tr-color">
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionC" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

					<!-- <td></td> -->
				</tr>
				<tr data-ng-repeat="choice in sectionCList track by $index">
					<td ng-if="$index =='0'"><i class="fa fa-plus btn plus-button"
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled"
						style="border: 2px solid lightgray;"
						ng-click="addNewChoice('sectionC')"></i></td>
					<td ng-if="$index > '0'"><span
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled "
						class="input-group-addon btn minus-button"
						ng-click="removeNewChoice($index,'sectionC')"><i
							class="fa fa-minus"></i></span></td>
					<td>{{$index+1}}</td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.smartGoal" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.target" disabled
							ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
					<td><input type="date" class="form-control input-sm"
						ng-model="choice.achievementDate" style="height: 86px;" disabled
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></td>
					<td><input type="text" class="form-control input-sm"
						ng-change="countWeightage()"
						style="text-align: center; height: 86px;"
						ng-model="choice.weightage" disabled
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"
						numbers-only maxlength="2"></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.midYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.midYearSelfRating" style="height: 86px;"
						ng-options="item for item in selectsRating" disabled
						ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null "></select></td>
					<td>{{choice.midYearAppraisarRating}}</td>
					<td class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.midYearAssessmentRemarks"
							ng-disabled="choice.midYearAssessmentRemarks ==null"></textarea>
					<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.finalYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></textarea>
							<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.finalYearSelfRating" disabled
						ng-options="item for item in selectsRating" style="height: 86px;"
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0 || choice.weightage === null"></select></td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.remarks"
							ng-disabled="choice.remarks ==null"></textarea>
					<span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
					<td>
						<div class="upload-btn-wrapper">
							<button class="btn_file" ng-if="choice.fileDummyName == null">Doc</button>
							<input type="file" file-model="{{'choice.fileName'}}" disabled
								accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,
										text/plain, application/pdf, image/*"
								id="{{ 'kraFileDocumentDownloadSectionC' + $index }}"> <a
								ng-href="{{choice.fileName}}" style="color: red;"
								id="{{ 'kraDocumentDownloadSectionC' + $index }}"
								ng-if="choice.fileDummyName != null">Download</a>
						</div>
					</td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span></td>
				</tr>
				<tr class="tr-color">
					<td></td>
					<td></td>
					<td style="text-align: left;"><spring:message
							code="td.sectionD" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

					<!-- <td></td> -->
				</tr>
				<tr data-ng-repeat="choice in sectionDList track by $index">
					<td></td>
					<td>{{$index+1}}</td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.smartGoal" disabled
							disabled></textarea><span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.target" disabled
							disabled></textarea><span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
					<td><input type="date" class="form-control input-sm"
						ng-model="choice.achievementDate" style="height: 86px;" disabled
						ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></td>
					<td><input type="text" class="form-control input-sm"
						ng-change="countWeightage()" disabled
						style="text-align: center; height: 86px;"
						ng-model="choice.weightage" disabled numbers-only maxlength="2">
						<!-- ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0" -->
					</td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.midYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.midYear == null ||  loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.midYearSelfRating" style="height: 86px;"
						ng-options="item for item in selectsRating" disabled
						ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></select></td>
					<td>{{choice.midYearAppraisarRating}}</td>
					<td class="tooltip1">					
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.midYearAssessmentRemarks"
							ng-disabled="choice.midYearAssessmentRemarks ==null"></textarea>
					<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
					<td class="tooltip1"><textarea rows="4" cols="50" class="form-control input-sm"
							ng-model="choice.finalYearAchievement" disabled
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></textarea>
							<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
					<td><select class="form-control input-sm"
						ng-model="choice.finalYearSelfRating" style="height: 86px;"
						ng-options="item for item in selectsRating" disabled
						ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.employeeIsvisible == 0"></select></td>
					<td>{{choice.finalYearAppraisarRating}}</td>
					<td  class="tooltip1">
					<textarea rows="4" cols="50"
							class="form-control input-sm"
							ng-model="choice.remarks"
							ng-disabled="choice.remarks ==null"></textarea>
					<span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
					<td>
						<div class="upload-btn-wrapper">
							<button class="btn_file" ng-if="choice.fileDummyName == null">Doc</button>
							<input type="file" file-model="{{'choice.fileName'}}" disabled
								accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint,
text/plain, application/pdf, image/*"
								id="{{ 'kraFileDocumentDownloadSectionD' + $index }}"> <a
								ng-href="{{choice.fileName}}" style="color: red;"
								id="{{ 'kraDocumentDownloadSectionD' + $index }}"
								ng-if="choice.fileDummyName != null">Download</a>

						</div>
					</td>
					<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
							) * (choice.weightage ))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
							) * (choice.weightage))/100 | number: 2 }}</span></td>
					<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((((choice.midYearAppraisarRating
							* choice.weightage )/100) *30/100) +
							(((choice.finalYearAppraisarRating * choice.weightage)/100) *
							70/100)) | number: 2 }}</span></td>
				</tr>
				<tr style="height: 25px;">
					<td colspan="5"></td>
					<td style="font-weight: bold;">{{WeightageCount}}</td>
					<td colspan="11"></td>
					<td><strong ng-if="kraToalCalculation > 0">{{kraToalCalculation
							| number: 2 }}</strong></td>

				</tr>
			</tbody>
		</table>
	</div>
</div>