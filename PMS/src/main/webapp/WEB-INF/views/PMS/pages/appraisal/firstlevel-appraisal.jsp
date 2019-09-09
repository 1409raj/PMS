<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
h4 {
	font-size: 18px;
	background-color: #2e4053;
	color: white;
	text-align: center;
}

td {
	height: 25px !important;
	/* word-break: break-all; */
}

textarea {
	resize: none;
	font-size: 14px !important;
}

.alert-successs {
	margin-bottom: 0px !important;
}

input[type=date].form-control, input[type=time].form-control, input[type=datetime-local].form-control,
	input[type=month].form-control {
	width: 115px !important;
}

.modal-lg {
	width: 95% !important;
}
</style>
<div class="row">
	<div class="col-md-12">
		<div class="section-tag">
			<span><spring:message code="menu.view.appraisal" /></span>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12" style="margin-top: 15px;">
		<uib-tabset active="activeJustified" justified="true"> <uib-tab
			index="0" heading="KRA's" select="getActiveTabId(0)">
		<div class="row">
			<!-- <div class="col-md-12">
				<button type="button" data-toggle="modal" data-target="#oldKRAModal"
					ng-click="getOldKRAData()"
					style="float: right; margin-top: 5px; margin-bottom: 5px; background-color: white; border: none; color: red; text-decoration: underline;">GO
					to Old KRA'S</button>
			</div> -->
			<div class="col-md-12" style="overflow: auto; height: 415px;"
				id="employeeFirtsLevelKRATable">
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
							<td></td>
						</tr>
						<tr ng-repeat="choice in sectionAList track by $index">
							<!-- loggedUserAppraisalDetails.finalYear == null || -->
							<td ng-if="$index =='0'"><i
								class="fa fa-plus btn plus-button"
								style="border: 2px solid lightgray;"
								ng-disabled="loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								ng-click="addNewChoice('sectionA')"></i> <span
								ng-if="$index > '0'" class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionA')"><i
									class="fa fa-minus"></i></span></td>
							<td ng-if="$index > '0'"><span
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionA')"><i
									class="fa fa-minus"></i></span></td>
							<td>{{$index+1}}</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.smartGoal"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
									</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.target"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td></td>
							<td><input type="date" class="form-control input-sm"
								ng-model="choice.achievementDate" style="height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.weightage" ng-change="countWeightage()"
								style="text-align: center; height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "
								numbers-only maxlength="2"></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAchievement"
									ng-disabled=" loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span>
									</td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearSelfRating" style="height: 86px;"
								ng-options="item for item in selectsRating"
								ng-disabled=" loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-model="choice.midYearAppraisarRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAssessmentRemarks"
									ng-disabled="loggedUserAppraisalDetails.midYear == null ||  loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.finalYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearSelfRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-model="choice.finalYearAppraisarRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.remarks"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>
							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->

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
							<td></td>
						</tr>
						<tr data-ng-repeat="choice in sectionBList track by $index">
							<!-- loggedUserAppraisalDetails.finalYear == null || -->
							<td ng-if="$index =='0'"><i
								class="fa fa-plus btn plus-button"
								style="border: 2px solid lightgray;"
								ng-disabled="loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								ng-click="addNewChoice('sectionB')"></i> <span
								ng-if="$index > '0'" class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionB')"><i
									class="fa fa-minus"></i></span></td>
							<td ng-if="$index > '0'"><span
								ng-disabled="loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionB')"><i
									class="fa fa-minus"></i></span></td>
							<td>{{$index+1}}</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.smartGoal"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.smartGoal != null">{{choice.smartGoal}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.target"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
							<td><input type="date" class="form-control input-sm"
								style="height: 86px;" ng-model="choice.achievementDate"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.weightage" ng-change="countWeightage()"
								style="text-align: center; height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "
								numbers-only maxlength="2"></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span>
									</td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearSelfRating" style="height: 86px;"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearAppraisarRating" style="height: 86px;"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAssessmentRemarks"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span ng-if="choice.midYearAssessmentRemarks != null"
						class="tooltiptext1">{{choice.midYearAssessmentRemarks}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.finalYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearSelfRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearAppraisarRating" style="height: 86px;"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.remarks"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span></td>

							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->

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
							<td></td>
						</tr>
						<tr data-ng-repeat="choice in sectionCList track by $index">
							<!-- loggedUserAppraisalDetails.initializationYear == null || -->
							<td ng-if="$index =='0'"><i
								class="fa fa-plus btn plus-button"
								style="border: 2px solid lightgray;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								ng-click="addNewChoice('sectionC')"></i> <span
								ng-if="$index > '0'" class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionC')"><i
									class="fa fa-minus"></i></span></td>
							<td ng-if="$index > '0'"><span
								ng-disabled="loggedUserAppraisalDetails.initializationYear == 0 || kraButtonDisabled "
								class="input-group-addon btn minus-button"
								ng-click="removeNewChoice($index,'sectionC')"><i
									class="fa fa-minus"></i></span></td>
							<td>{{$index+1}}</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.smartGoal"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.smartGoal != null">{{choice.smartGoal}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.target"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></textarea>
									<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
							<td><input type="date" class="form-control input-sm"
								ng-model="choice.achievementDate" style="height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.weightage" ng-change="countWeightage()"
								style="text-align: center; height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "
								numbers-only maxlength="2"></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span>
									</td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearSelfRating" style="height: 86px;"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearAppraisarRating" style="height: 86px;"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAssessmentRemarks"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.finalYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearSelfRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearAppraisarRating" style="height: 86px;"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></select></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.remarks"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>



							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->

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
							<td></td>

						</tr>
						<tr data-ng-repeat="choice in sectionDList track by $index">
							<td></td>
							<td>{{$index+1}}</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.smartGoal"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea>
									<span class="tooltiptext1" ng-if="choice.smartGoal != null">{{choice.smartGoal}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.target"
									ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea>
									<span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
							<td><input type="date" class="form-control input-sm"
								ng-model="choice.achievementDate" style="height: 86px;"
								ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></td>
							<td><input type="text" class="form-control input-sm"
								ng-model="choice.weightage" ng-change="countWeightage()"
								disabled style="text-align: center; height: 86px;" numbers-only
								maxlength="2"></td>
							<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea>
									<span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
							<td><select class="form-control input-sm"
								ng-model="choice.midYearSelfRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select></td>
							<td><select class="form-control input-sm"
								style="height: 86px;" ng-model="choice.midYearAppraisarRating"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select>
							</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.midYearAssessmentRemarks"
									ng-disabled="loggedUserAppraisalDetails.midYear == null || loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm"
									ng-model="choice.finalYearAchievement"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea>
									<span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearSelfRating"
								ng-options="item for item in selectsRating"
								style="height: 86px;"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select></td>
							<td><select class="form-control input-sm"
								ng-model="choice.finalYearAppraisarRating" style="height: 86px;"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
								ng-options="item for item in selectsRating"
								ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select>
							</td>
							<td class="tooltip1"><textarea rows="4" cols="50"
									class="form-control input-sm" ng-model="choice.remarks"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null ||  loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea>
									<span
						class="tooltiptext1" ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<!-- <td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.weightage
									) + (choice.finalYearAppraisarRating))/100 | number:
									2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.weightage
									* ((choice.midYearAppraisarRating * 30/100) +
									(choice.finalYearAppraisarRating *70/100)))/100 | number: 2}}</span></td> -->
							<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>
							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->

						</tr>

						<!-- <tr style="height: 25px;">
							<td colspan="4"></td>
							<td style="font-weight: bold;">{{WeightageCount}}</td>
							<td colspan="14"></td>
						</tr> -->
						<tr style="height: 25px;">
							<td colspan="5"></td>
							<td style="font-weight: bold;">{{WeightageCount}}</td>
							<td colspan="11"></td>
							<td><strong ng-if="kraToalCalculation > 0">{{kraToalCalculation
									| number: 2 }}</strong></td>
							<td></td>

						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12" style="margin-top: 15px;">
				<button type="button" ng-click="saveKRAModule('SAVE')"
					ng-hide="kraButtonSendToManager"
					ng-disabled="kraButtonDisabled || loggedUserAppraisalDetails == null"
					class="btn btn-outline-secondary">Save</button>
				<button type="button" ng-click="saveKRAModule('SAVE')"
					ng-show="kraButtonSendToManager" ng-disabled="kraButtonDisabled "
					class="btn btn-outline-secondary">Save</button>
				<button type="button" ng-click="submitKRAModule('SUBMIT')"
					ng-hide="kraButtonSendToManager"
					ng-disabled="kraButtonDisabled || loggedUserAppraisalDetails == null"
					class="btn btn-outline-secondary">Submit</button>
				<button type="button" ng-show="kraButtonSendToManager"
					ng-click="submitNewKRAModule('SUBMIT')"
					ng-disabled="kraButtonDisabled" class="btn btn-outline-secondary">Send
					to Manager</button>
				<!-- ng-disabled="kraButtonSendToManager" -->
			</div>

		</div>
		</uib-tab> <uib-tab index="1" heading="EXTRAORDINARY INITIATIVES"
			select="getActiveTabId(1)">
		<div class="row">
			<!-- 	<div class="col-md-12" id="extraOrdinaryAlert">{{message}}</div> -->
			<div class="col-md-12">
				<form class="form-horizontal" role="form">
					<table class="table" border="1">
						<thead style="background-color: #315772; color: #ffffff;">
							<tr>
								<th rowspan="2"><spring:message
										code="th.trainingNeeds.sr.no" /></th>
								<th rowspan="2"><spring:message code="th.contribution" /></th>
								<th rowspan="2"><spring:message
										code="th.contribution.details" /></th>
								<th rowspan="2" style="width: 7%;"><spring:message
										code="th.weightage" /><br> %</th>
								<th colspan="2"><spring:message code="th.final.year.review" /></th>
								<th rowspan="2" style="width: 7%;"><spring:message
										code="th.trainingNeeds.finalscore" /></th>
								<!-- <th rowspan="2" ng-repeat="dyna in dynamicExtraOrdinaryDetails">{{dyna.labelName}}</th> -->
								<!-- <th rowspan="2">Weighted Rating</th> -->
								<th rowspan="2"><spring:message code="th.remrks" /></th>
							</tr>
							<tr>
								<th><spring:message code="th.self.rating" /></th>
								<th><spring:message code="th.appraisar.rating" /></th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="choice in extraOrdinaryDetails"
								ng-init="outerIndex=$index">
								<td>{{$index+1}}</td>
								<td><textarea rows="4" cols="50"
										class="form-control input-sm" ng-model="choice.contributions"
										ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"></textarea></td>
								<td><textarea rows="4" cols="50"
										class="form-control input-sm"
										ng-model="choice.contributionDetails"
										ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"></textarea></td>
								<td><input type="text" class="form-control input-sm"
									style="text-align: center; height: 86px;"
									ng-change="countExtraOrdinaryWeightage()"
									ng-model="choice.weightage"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"
									only-digits></td>
								<td align="center"><select class="form-control input-sm"
									ng-model="choice.finalYearSelfRating" style="height: 86px;"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"></select></td>
								<td align="center"><select class="form-control input-sm"
									style="height: 86px;"
									ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
									ng-model="choice.finalYearAppraisarRating"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"></select></td>
								<td align="center">{{(choice.weightage *
									choice.finalYearAppraisarRating)*10/100}}</td>
								<!-- Dynamic Column populate -->
								<!-- <td ng-repeat="dynamicChoice in choice.child" ng-init="innerIndex=$index">
								<input type="text" class="form-control input-sm" 
								ng-model="dynamicChoice.textData"  ></td> -->
								<!-- ng-disabled="loggedUserAppraisalDetails.finalYear == null" -->
								<!-- <td>{{choice.weightage * choice.finalScore}}</td> -->
								<td><textarea rows="4" cols="50"
										class="form-control input-sm" ng-model="choice.remarks"
										name=""
										ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled"></textarea></td>
							</tr>
							<!-- <tr style="height: 25px;">
								<td colspan="3"></td>
								<td style="font-weight: bold;">{{extraOrdinaryWeightageCount}}</td>
								<td colspan="5"></td>

							</tr> -->
							<tr style="height: 25px;">
								<td colspan="3"></td>
								<td style="font-weight: bold;">{{extraOrdinaryWeightageCount}}</td>
								<td colspan="2"></td>
								<td><strong ng-if="ExtraOrdinaryTotalCalculation > 0">{{ExtraOrdinaryTotalCalculation *10
										| number: 2 }}</strong></td>
								<td colspan="3"></td>

							</tr>
						</tbody>
					</table>
					<div style="margin-top: 15px">
						<button type="button" class="btn btn-outline-secondary"
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled || loggedUserAppraisalDetails == null"
							ng-click="saveExtraOrdinaryModule('SAVE')">Save</button>
						<button type="button"
							ng-click="submitExtraOrdinaryModule('SUBMIT')"
							ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.finalYear == 0 || extraOrdinaryButtonDisabled || loggedUserAppraisalDetails == null"
							class="btn btn-outline-secondary">Submit</button>
					</div>
				</form>
			</div>
		</div>

		</uib-tab> <uib-tab index="2" heading="BEHAVIOURAL COMPETENCE"
			select="getActiveTabId(2)">

		<div class="row">
			<!-- <div class="col-md-12" id="behavioralComptenceAlert">{{message}}</div> -->
			<div class="col-md-12">
				<form class="form-horizontal" style="font-size: 12px;" role="form">

					<table>
						<thead style="background-color: #315772; color: #ffffff;">
							<tr>
								<th rowspan="2" width="45%;"><spring:message
										code="th.behavioural.competence" /></th>
								<th rowspan="2" width="6%;"><spring:message
										code="th.weightage" /><br>A <br> %</th>
								<th colspan="2" width="6%;"><spring:message
										code="th.mid.year.review" /></th>
								<th colspan="2" width="6%;"><spring:message
										code="th.final.year.review" /></th>
								<th rowspan="2" width="6%;">Mid Term Rating <br> A*B
								</th>
								<th rowspan="2" width="6%;">Year End Rating <br> A*C
								</th>
								<th rowspan="2" width="6%;"><spring:message
										code="th.final.score" /><br>30% of A*B and 70% of A*C</th>
							</tr>

							<tr>
								<th><spring:message code="th.self.rating" /></th>
								<th><spring:message code="th.appraisar.rating" /><br>
									<spring:message code="th.ar.mid" /></th>
								<th><spring:message code="th.self.rating" /></th>
								<th><spring:message code="th.appraisar.rating" /><br>
									<spring:message code="th.ar.final" /></th>
							</tr>
						</thead>
						<tbody ng-repeat="x in formDataBC">
							<tr>
								<td style="text-align: left;"><label><b>{{$index+1}}.&nbsp;{{x.name}}</b></label></td>
								<td align="center">{{x.weightage}}</td>

								<td valign="bottom" align="center"><select
									class="form-control input-sm" ng-model="x.midYearSelfRating"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select></td>
								<td valign="bottom" align="center"><select
									class="form-control input-sm"
									ng-model="x.midYearAssessorRating"
									ng-style="x.midYearHighlights === 0 && {'color':'black'}  || x.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 "></select>
								</td>
								<td valign="bottom" align="center"><select
									class="form-control input-sm" ng-model="x.finalYearSelfRating"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select></td>
								<td valign="bottom" align="center"><select
									class="form-control input-sm"
									ng-model="x.finalYearAssessorRating"
									ng-style="x.finalYearHighlights === 0 && {'color':'black'}  || x.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
									ng-options="item for item in selectsRating"
									ng-disabled="loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></select>
								</td>
								<td><span ng-if="x.midYearAssessorRating!=null">{{((x.midYearAssessorRating
										* x.weightage)/100) | number: 2 }}</span></td>
								<td><span ng-if="x.finalYearAssessorRating!=null">{{((x.weightage
										* x.finalYearAssessorRating)/100) | number: 2}}</span>
								<td><span ng-if="x.finalYearAssessorRating!=null && x.midYearAssessorRating!=null">{{((((x.midYearAssessorRating
										* x.weightage )/100) *30/100) + (((x.finalYearAssessorRating *
										x.weightage)/100) * 70/100)) | number: 2 }}</span>
								<span ng-if="x.finalYearAssessorRating!=null && x.midYearAssessorRating ==null">{{((x.weightage
										* x.finalYearAssessorRating)/100) | number: 2}}</span></td>
							</tr>
							<tr>
								<td colspan="9" v-align="middle" style="text-align: left;">
									<p style="margin: 5px;">{{x.description}}</p>
								</td>
							</tr>
							<tr>
								<td colspan="9"
									style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;"><textarea
										ng-model="x.comments"
										class="form-control input-sm ng-pristine ng-valid ng-touched"
										style="" placeholder="Comments" rows="4"
										ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0"></textarea></td>
							</tr>
							<tr>
								<td colspan="9"
									style="border-top: 1px solid #ffffff; border-left: 1px solid #ffffff; border-right: 1px solid #ffffff; height: 25px;"></td>
							</tr>
						</tbody>
						<tr
							style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;">
							<td style="text-align: left; font-size: 14px; font-weight: 600;">TOTAL
								WEIGHTAGE</td>
							<td>100%</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><strong ng-if="BehavioralComptenceTotalCalculation > 0">{{BehavioralComptenceTotalCalculation
									| number: 2 }}</strong></td>
						</tr>
					</table>
					<br>
					<!-- loggedUserAppraisalDetails.midYear == 0 || -->
					<button type="button"
						ng-click="saveBehaviouralCompetenceModule('SAVE')"
						ng-disabled=" behaviouralCompetenceButtonDisabled || loggedUserAppraisalDetails == null"
						class="btn btn-outline-secondary">Save</button>
					<button type="button"
						ng-click="submitBehaviouralCompetenceModule('SUBMIT')"
						ng-disabled=" behaviouralCompetenceButtonDisabled || loggedUserAppraisalDetails == null"
						class="btn btn-outline-secondary">Submit</button>
				</form>
			</div>
		</div>



		</uib-tab> <uib-tab index="3" heading="CAREER ASPIRATION"
			select="getActiveTabId(3)">
		<div class="row" style="margin-top: 15px;">
			<form class="form-horizontal" role="form">
				<div class="col-md-4">
					<div>
						<!-- ng-disabled="loggedUserAppraisalDetails.firstLevelIsvisible == 0 || employeeCareerAspirationDetails.isValidatedByFirstLevel == 1" -->
						<textarea disabled
							ng-model="employeeCareerAspirationDetails.initializationComments"
							rows="10" style="width: 90%;"
							placeholder="Career aspirations of employee (to be filled in by employee but to be supported by the Superior in context to the Organization and Department.">
					</textarea>
					</div>
					<div>
						<label class="control-label">Appraiser Goal Review :</label>
					</div>
					<div>
						<textarea
							ng-model="employeeCareerAspirationDetails.initializationManagerReview"
							rows="10" style="width: 90%;"
							ng-disabled="employeeCareerAspirationDetails.isValidatedByFirstLevel == 1">
					</textarea>
					</div>
					<div style="margin-top: 15px;">
						<!-- ng-disabled="employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel === 1" -->
						<button type="button" class="btn btn-outline-secondary"
							ng-disabled="isCareerAspirationsSubmitted || loggedUserAppraisalDetails == null"
							ng-click="saveCareerAspirationsModule('SAVE')">Save</button>
						<button type="button"
							ng-disabled="isCareerAspirationsSubmitted || loggedUserAppraisalDetails == null"
							ng-click="submitCareerAspirationsModule('SUBMIT')"
							class="btn btn-outline-secondary">Submit</button>
					</div>
				</div>
				<div class="col-md-4">
					<div>
						<!-- ng-disabled="employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel === 1" -->
						<textarea disabled
							ng-model="employeeCareerAspirationDetails.midYearComments"
							ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear == 1 || employeeCareerAspirationDetails.midYearComments !=null"
							rows="10" style="width: 90%;" placeholder="">
					</textarea>
					</div>
					<div
						ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear == 1 || employeeCareerAspirationDetails.midYearComments !=null">
						<label class="control-label">Appraiser Mid Review :</label>
					</div>
					<div>
						<textarea
							ng-model="employeeCareerAspirationDetails.midYearCommentsManagerReview"
							ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear == 1 ||  employeeCareerAspirationDetails.midYearComments !=null"
							rows="10" style="width: 90%;"
							ng-disabled="employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel === 1 || loggedUserAppraisalDetails.finalYear == 1"
							placeholder="">
					</textarea>
					</div>
				</div>
				<div class="col-md-4">
					<div>
						<!-- ng-disabled="employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel === 1" -->
						<textarea disabled
							ng-model="employeeCareerAspirationDetails.yearEndComments"
							ng-if="loggedUserAppraisalDetails.finalYear == 1" rows="10"
							style="width: 90%;" placeholder="">
					</textarea>
					</div>
					<div ng-if="loggedUserAppraisalDetails.finalYear == 1">
						<label class="control-label">Appraiser Year End Review :</label>
					</div>
					<div>
						<textarea
							ng-model="employeeCareerAspirationDetails.yearEndCommentsManagerReview"
							ng-if="loggedUserAppraisalDetails.finalYear == 1" rows="10"
							style="width: 90%;"
							ng-disabled="employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel === 1"
							placeholder="">
					</textarea>
					</div>
				</div>
			</form>
		</div>
		</uib-tab> <uib-tab index="4" heading="TRAINING NEEDS"
			select="getActiveTabId(4)">

		<div class="row">
			<div class="col-md-12">
				<!-- <div class="col-md-12" id="trainingNeedsAlert">{{message}}</div> -->
				<form class="form-horizontal" role="form">
					<table border="1" width="100%">
						<thead style="background-color: #315772; color: #ffffff;">
							<tr style="height: 30px;">
								<th><spring:message code="th.trainingNeeds.sr.no" /></th>
								<th width="30%;"><spring:message
										code="th.trainingNeeds.course" /></th>
								<th width="30%;"><spring:message
										code="th.trainingNeeds.topic.choose" /></th>
								<th width="10%;"><spring:message
										code="th.trainingNeeds.proposed.training" /></th>
								<th width="5%;"><spring:message
										code="th.trainingNeeds.approve.reject" /></th>
								<th width="25%;"><spring:message code="th.remrks" /></th>
								<!-- <th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th> -->
								<th></th>
								<th></th>
								<!-- <th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th> -->
								<!-- <th></th>
								<th></th> -->
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="choice in trainingNeedsDetails">
								<th>{{$index+1}}</th>
								<td>
									<!-- ng-disabled=" trainingNeedsButtonDisabled || choice.isValidatedByFirstLevel ==1" -->
									<textarea id="note"  rows="4" cols="50" disabled
										class="form-control input-sm" ng-model="choice.trainingTopic"></textarea>
								</td>
								<td>
									<!-- ng-disabled=" trainingNeedsButtonDisabled || choice.isValidatedByFirstLevel ==1" -->
									<textarea  id="note"  rows="4" cols="50" disabled
										class="form-control input-sm"
										ng-model="choice.trainingReasons"></textarea>
								</td>
								<td>
									<!-- ng-disabled=" trainingNeedsButtonDisabled || choice.isValidatedByFirstLevel ==1" -->
									<textarea rows="4" cols="50" disabled
										class="form-control input-sm" ng-model="choice.manHours"></textarea>
								</td>
								<td><select class="form-control input-sm"
									style="height: 86px;"
									ng-disabled=" trainingNeedsButtonDisabled || choice.isValidatedByFirstLevel ==1"
									ng-model="choice.approvedReject"
									ng-options="item for item in approveRejectList"></select></td>
								<td><textarea id="note" rows="4" cols="50"
										class="form-control input-sm"
										ng-disabled=" trainingNeedsButtonDisabled || choice.isValidatedByFirstLevel ==1"
										ng-model="choice.remarks"></textarea></td>
							</tr>
						</tbody>
					</table>
					<div style="margin-top: 15px;">

						<button type="button" class="btn btn-outline-secondary"
							ng-disabled="trainingNeedsButtonDisabled || loggedUserAppraisalDetails == null"
							ng-click="saveTrainingNeedsModule('SAVE')">Save</button>
						<button type="button"
							ng-click="submitTrainingNeedsModule('SUBMIT')"
							ng-disabled="trainingNeedsButtonDisabled || loggedUserAppraisalDetails == null"
							class="btn btn-outline-secondary">Submit</button>
					</div>
				</form>
			</div>
		</div>

		</uib-tab> <uib-tab index="5" heading="FINAL SUBMISSION"
			select="getActiveTabId(5)"> <!-- <div class="col-md-12" style="text-align: right; color: blue;">
			<button type="button" ng-click="printFirstLevel()"
				class="btn btn-outline-secondary">
				<span class="glyphicon glyphicon-download-alt"></span> Print
			</button>

		</div> -->
		<div style="margin-top: 15px;" id="viewAppraisalFirstLevel">

			<table width="100%" style="border: none;">
				<tr>
					<td style="border: none; text-align: left;"><label
						for="empCode" class="control-label"><spring:message
								code="label.employee.code" /> :&nbsp;</label> <label class="labelText">{{subEmployeeDetails.empCode}}</label></td>
					<td style="border: none; text-align: left;"><label for="name"
						class="control-label"><spring:message
								code="label.employee.name" /> :&nbsp;</label> <label class="labelText">{{subEmployeeDetails.empName}}</label></td>
					<td style="border: none; text-align: left;"><label for="email"
						class="control-label"><spring:message
								code="label.employee.email" /></label> :&nbsp;<label class="labelText">{{subEmployeeDetails.email}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="mobile" class="control-label"><spring:message
								code="label.employee.mobile" /></label> :&nbsp;<label class="labelText">{{subEmployeeDetails.mobile}}</label></td>

				</tr>
				<tr>

					<td style="border: none; text-align: left;"><label
						for="designation" class="control-label"><spring:message
								code="label.employee.designation" /> :&nbsp;</label> <label
						class="labelText">{{subEmployeeDetails.designationName}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="department" class="control-label"><spring:message
								code="label.employee.department" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.departmentName}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="qualification" class="control-label"><spring:message
								code="label.employee.qualification" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.qualificationName}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="dateOfJoining" class="control-label"><spring:message
								code="label.employee.doj" /> :&nbsp;</label><label class="labelText">{{subEmployeeDetails.dateOfJoining
							| date : "dd.MM.y" }}</label></td>

				</tr>
				<tr>

					<td style="border: none; text-align: left;"><label
						for="firstLevelSuperiorEmpId" class="control-label"><spring:message
								code="label.employee.firstLevelSuperiorID" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.firstLevelSuperiorName}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="secondLevelSuperiorEmpId" class="control-label"><spring:message
								code="label.employee.secondLevelSuperiorID" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.secondLevelSuperiorName}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="location" class="control-label"><spring:message
								code="label.employee.location" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.location}}</label></td>
					<td style="border: none; text-align: left;"><label
						for="location" class="control-label"><spring:message
								code="label.employee.company" /> :&nbsp;</label><label
						class="labelText">{{subEmployeeDetails.company}}</label></td>

				</tr>
				<tr>
					<td style="border: none; text-align: left;"><label
						for="mobile" class="control-label"><spring:message
								code="label.employee.dob" /></label> :&nbsp;<label class="labelText">{{subEmployeeDetails.dateOfBirth
							| date : "dd.MM.y"}}</label></td>

				</tr>
			</table>


			<div class="form-group table-responsive" style="overflow: auto;">
				<div class="section-tag">
					<span><spring:message code="heading.kra" /></span>
				</div>
				<table border="1"
					style="border-collapse: collapse; width: 2000px; overflow: scroll;">
						<thead style="background-color: #315772; color: #ffffff;">
				<tr>
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
					<th style="width:2%;"><spring:message code="th.appraisar.rating" /><br>C</th>
					<th style="width: 12%;">YEAR END <br>ASSESSMENT REMARKS</th>
				</tr>
			</thead>
					<tbody>
						<tr style="background-color: #315772; color: white; height: 25px;">
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
						</tr>
						<tr ng-repeat="choice in sectionAList track by $index">
							<td align="center">{{$index+1}}</td>
							<td class="tableText" valign="top">{{choice.smartGoal}}</td>
							<td class="tableText" valign="top">{{choice.target}}</td>
							<td align="center">{{choice.achievementDate | date :
								"dd.MM.y" }}</td>
							<td align="center">{{choice.weightage}}</td>
							<td valign="top" class="tableText">{{choice.midYearAchievement}}</td>
							<td align="center">{{choice.midYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.midYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.midYearAssessmentRemarks}}</td>
							<td valign="top" class="tableText">{{choice.finalYearAchievement}}</td>
							<td align="center">{{choice.finalYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.finalYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.remarks}}</td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.weightage
									) * (choice.finalYearAppraisarRating))/100 | number:
									2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.weightage
									* ((choice.midYearAppraisarRating * 30/100) +
									(choice.finalYearAppraisarRating *70/100)))/100}}</span></td> -->
							<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>

							<!-- ng-disabled="loggedUserAppraisalDetails.initializationYear == null || loggedUserAppraisalDetails.initializationYear == 0 || buttonDisabled" -->

							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->


						</tr>
						<tr style="background-color: #315772; color: white; height: 25px;">
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
							<td align="center">{{$index+1}}</td>
							<td class="tableText" valign="top">{{choice.smartGoal}}</td>
							<td class="tableText" valign="top">{{choice.target}}</td>
							<td align="center">{{choice.achievementDate | date :
								"dd.MM.y"}}</td>
							<td align="center">{{choice.weightage}}</td>
							<td valign="top" class="tableText">{{choice.midYearAchievement}}</td>
							<td align="center">{{choice.midYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.midYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.midYearAssessmentRemarks}}</td>
							<td valign="top" class="tableText">{{choice.finalYearAchievement}}</td>
							<td align="center">{{choice.finalYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.finalYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.remarks}}</td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.weightage
									) * (choice.finalYearAppraisarRating))/100 | number:
									2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.weightage
									* ((choice.midYearAppraisarRating * 30/100) +
									(choice.finalYearAppraisarRating *70/100)))/100 | number: 2 }}</span></td> -->
							<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>


							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->

						</tr>
						<tr style="background-color: #315772; color: white; height: 25px;">
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
						</tr>
						<tr data-ng-repeat="choice in sectionCList track by $index">
							<td align="center">{{$index+1}}</td>
							<td class="tableText" valign="top">{{choice.smartGoal}}</td>
							<td class="tableText" valign="top">{{choice.target}}</td>
							<td align="center">{{choice.achievementDate | date :
								"dd.MM.y"}}</td>
							<td align="center">{{choice.weightage}}</td>
							<td valign="top" class="tableText">{{choice.midYearAchievement}}</td>
							<td align="center">{{choice.midYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.midYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.midYearAssessmentRemarks}}</td>
							<td valign="top" class="tableText">{{choice.finalYearAchievement}}</td>
							<td align="center">{{choice.finalYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.finalYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.remarks}}</td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.weightage
									) * (choice.finalYearAppraisarRating))/100 | number:
									2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.weightage
									* ((choice.midYearAppraisarRating * 30/100) +
									(choice.finalYearAppraisarRating *70/100)))/100 | number: 2 }}</span></td> -->
							<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>



							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->


						</tr>
						<tr style="background-color: #315772; color: white; height: 25px;">
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
						</tr>
						<tr data-ng-repeat="choice in sectionDList track by $index">
							<td align="center">{{$index+1}}</td>
							<td class="tableText" valign="top">{{choice.smartGoal}}</td>
							<td class="tableText" valign="top">{{choice.target}}</td>
							<td align="center">{{choice.achievementDate | date :
								"dd.MM.y"}}</td>
							<td align="center">{{choice.weightage}}</td>
							<td valign="top" class="tableText">{{choice.midYearAchievement}}</td>
							<td align="center">{{choice.midYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.midYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.midYearAssessmentRemarks}}</td>
							<td valign="top" class="tableText">{{choice.finalYearAchievement}}</td>
							<td align="center">{{choice.finalYearSelfRating}}</td>
							<td align="center"
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.finalYearAppraisarRating}}</td>
							<td valign="top" class="tableText">{{choice.remarks}}</td>
							<td><a ng-href={{choice.fileName}} style="color: red;"
								ng-if="choice.fileName != null">Download</a></td>
							<!-- <td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.weightage
									) * (choice.finalYearAppraisarRating))/100 | number:
									2 }}</span></td>
							<td align="center"><span ng-if="choice.finalYearAppraisarRating!=null">{{(choice.weightage
									* ((choice.midYearAppraisarRating * 30/100) +
									(choice.finalYearAppraisarRating *70/100)))/100 | number: 2 }}</span></td> -->

							<td><span ng-if="choice.midYearAppraisarRating!=null">{{((choice.midYearAppraisarRating
									) * (choice.weightage ))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null">{{((choice.finalYearAppraisarRating
									) * (choice.weightage))/100 | number: 2 }}</span></td>
							<td><span ng-if="choice.finalYearAppraisarRating!=null && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
									* choice.weightage )/100) *30/100) +
									(((choice.finalYearAppraisarRating * choice.weightage)/100) *
									70/100)) | number: 2 }}</span>
							<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
							</td>

							<!-- <td><span ng-if="choice.finalYearAppraisarRating != null">{{(choice.weightage
							* ((choice.midYearAppraisarRating * 30/100) +
							(choice.finalYearAppraisarRating *70/100))) / choice.weightage | number: 2}}</span></td> -->


						</tr>

						<tr style="height: 25px;">
							<td colspan="4"></td>
							<td style="font-weight: bold;" align="center">{{WeightageCount}}</td>
							<td colspan="11"></td>
							<td><strong ng-if="kraToalCalculation > 0">
									{{kraToalCalculation | number: 2 }}</strong></td>
						</tr>
					</tbody>
				</table>

			</div>

			<div class="form-group">
				<div class="section-tag">
					<span>EXTRAORDINARY INITIATIVES</span>
				</div>
				<table border="1" width="100%" style="border-collapse: collapse;">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr>
							<th rowspan="2"><spring:message
									code="th.trainingNeeds.sr.no" /></th>
							<th rowspan="2"><spring:message code="th.contribution" /></th>
							<th rowspan="2"><spring:message
									code="th.contribution.details" /></th>
							<th rowspan="2" style="width: 7%;"><spring:message
									code="th.weightage" /><br>%</th>
							<th colspan="2"><spring:message code="th.final.year.review" /></th>
							<th rowspan="2"><spring:message code="th.remrks" /></th>
							<th rowspan="2" style="width: 5%;"><spring:message
									code="th.trainingNeeds.finalscore" /></th>
							<!-- <th rowspan="2" ng-repeat="dyna in dynamicExtraOrdinaryDetails">{{dyna.labelName}}</th> -->
							<!-- <th rowspan="2">Weighted Rating</th> -->

							<!-- <th  rowspan="2"></th> -->
						</tr>
						<tr>
							<th><spring:message code="th.self.rating" /></th>
							<th><spring:message code="th.appraisar.rating" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="choice in extraOrdinaryDetails"
							ng-init="outerIndex=$index">
							<td align="center">{{$index+1}}</td>
							<td style="text-align: left;    word-break: break-all;">{{choice.contributions}}</td>
							<td style="text-align: left;    word-break: break-all;">{{choice.contributionDetails}}</td>
							<td>{{choice.weightage}}</td>
							<td>{{choice.finalYearSelfRating}}</td>
							<td
								ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{choice.finalYearAppraisarRating}}</td>
							<td style="text-align: left;    word-break: break-all;">{{choice.remarks}}</td>
							<td>{{(choice.weightage *
								choice.finalYearAppraisarRating)*10/100}}</td>
							<!-- <td>{{choice.weightage * choice.finalScore}}</td> -->
							<!-- Dynamic Column populate -->
							<!-- <td ng-repeat="dynamicChoice in choice.child" ng-init="innerIndex=$index">
								<input type="text" class="form-control input-sm" 
								ng-model="dynamicChoice.textData"  ></td> -->
							<!-- ng-disabled="loggedUserKraDetails.finalYear == null" -->

						</tr>
						<tr style="height: 25px;">
							<td colspan="3"></td>
							<td style="font-weight: bold;">{{extraOrdinaryWeightageCount}}</td>
							<td colspan="3"></td>
							<td><strong ng-if="ExtraOrdinaryTotalCalculation > 0">{{ExtraOrdinaryTotalCalculation*10
									| number: 2 }}</strong></td>
						</tr>
					</tbody>
				</table>
			</div>








			<div class="form-group">
				<div class="section-tag">
					<b><span>BEHAVIOURAL COMPETENCE</span></b>
				</div>


				<table border="1" style="border-collapse: collapse;">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr>
							<th rowspan="2" width="45%;"><spring:message
									code="th.behavioural.competence" /></th>
							<th rowspan="2" width="6%;"><spring:message
									code="th.weightage" /><br>A <br> %</th>
							<th colspan="2" width="6%;"><spring:message
									code="th.mid.year.review" /></th>
							<th colspan="2" width="6%;"><spring:message
									code="th.final.year.review" /></th>
							<th rowspan="2" width="6%;">Mid Term Rating <br> A*B
							</th>
							<th rowspan="2" width="6%;">Year End Rating <br> A*C
							</th>
							<th rowspan="2" width="6%;"><spring:message
									code="th.final.score" /><br>30% of A*B and 70% of A*C</th>
						</tr>

						<tr>
							<th><spring:message code="th.self.rating" /></th>
							<th><spring:message code="th.appraisar.rating" /><br>
								<spring:message code="th.ar.mid" /></th>
							<th><spring:message code="th.self.rating" /></th>
							<th><spring:message code="th.appraisar.rating" /><br>
								<spring:message code="th.ar.final" /></th>
						</tr>
					</thead>
					<tbody ng-repeat="x in formDataBC">
						<tr>
							<td style="text-align: left;"><label><b>{{$index+1}}.&nbsp;{{x.name}}</b></label></td>
							<td align="center">{{x.weightage}}</td>

							<td align="center">{{x.midYearSelfRating}}</td>
							<td align="center"
								ng-style="x.midYearHighlights === 0 && {'color':'black'}  || x.midYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{x.midYearAssessorRating}}

							
							<td align="center">{{x.finalYearSelfRating}}</td>
							<td align="center"
								ng-style="x.finalYearHighlights === 0 && {'color':'black'}  || x.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}">{{x.finalYearAssessorRating}}</td>
							<td><span ng-if="x.midYearAssessorRating!=null">{{((x.midYearAssessorRating
									* x.weightage)/100) | number: 2 }}</span></td>
							<td><span ng-if="x.finalYearAssessorRating!=null">{{((x.weightage
									* x.finalYearAssessorRating)/100) | number: 2}}</span>
							<td><span ng-if="x.finalYearAssessorRating!=null && x.midYearAssessorRating!=null">{{((((x.midYearAssessorRating
										* x.weightage )/100) *30/100) + (((x.finalYearAssessorRating *
										x.weightage)/100) * 70/100)) | number: 2 }}</span>
								<span ng-if="x.finalYearAssessorRating!=null && x.midYearAssessorRating ==null">{{((x.weightage
										* x.finalYearAssessorRating)/100) | number: 2}}</span></td>
						</tr>
						<tr>
							<td colspan="9" v-align="middle" style="text-align: left;">

								<p style="margin: 5px;">{{x.description}}</p>
							</td>
						</tr>
						<tr>
							<td colspan="9"
								style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black; text-align: left;">{{x.comments}}</td>
						</tr>
						<tr>
							<td colspan="9"
								style="border-top: 1px solid #ffffff; border-left: 1px solid #ffffff; border-right: 1px solid #ffffff; height: 25px;"></td>
						</tr>
					</tbody>
					<tr
						style="webkit-box-shadow: 0 8px 6px -6px black; -moz-box-shadow: 0 8px 6px -6px black; box-shadow: 0 7px 6px -6px black;">
						<td style="text-align: left; font-size: 14px; font-weight: 600;">TOTAL
							WEIGHTAGE</td>
						<td>100%</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><strong ng-if="BehavioralComptenceTotalCalculation > 0">{{BehavioralComptenceTotalCalculation
								| number: 2 }}</strong></td>
					</tr>


				</table>

			</div>
			<div class="form-group">
				<div class="section-tag">
					<b><spring:message code="heading.careerAspirations" /></b>
				</div>
				<table border="1" style="width: 100%; border-collapse: collapse;">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr style="height: 25px;">
							<th style="width: 5%;">S.No.</th>
							<th>COMMENTS OF ASSESSEE</th>
							<th>APPRAISER REVIEW</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="center">1</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.initializationComments}}</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.initializationManagerReview}}</td>
						</tr>
						<tr
							ng-if="loggedUserAppraisalDetails.midYear ==1 || loggedUserAppraisalDetails.finalYear ==1">
							<td align="center">2</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.midYearComments}}</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.midYearCommentsManagerReview}}</td>
						</tr>
						<tr ng-if="loggedUserAppraisalDetails.finalYear ==1">
							<td align="center">3</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.yearEndComments}}</td>
							<td style="text-align: left;word-break: break-all;">{{employeeCareerAspirationDetails.yearEndCommentsManagerReview}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="form-group">
				<div class="section-tag">
					<b><spring:message code="heading.trainingNeeds" /></b>
				</div>
				<table border="1" style="width: 100%; border-collapse: collapse;">
					<thead style="background-color: #315772; color: #ffffff;">
						<tr style="height: 25px;">
							<th><spring:message code="th.trainingNeeds.sr.no" /></th>
							<th width="30%;"><spring:message
									code="th.trainingNeeds.course" /></th>
							<th width="30%;"><spring:message
									code="th.trainingNeeds.topic.choose" /></th>
							<th width="10%;"><spring:message
									code="th.trainingNeeds.proposed.training" /></th>
							<th width="5%;"><spring:message
									code="th.trainingNeeds.approve.reject" /></th>
							<th width="25%;"><spring:message code="th.remrks" /></th>

							<!-- <th ng-repeat="dyna in dynamicTrainingNeedsDetails">{{dyna.labelName}}</th> -->
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="choice in trainingNeedsDetails">
							<th>{{$index+1}}</th>
							<td style="text-align: left;word-break: break-all;">{{choice.trainingTopic}}</td>
							<td style="text-align: left;word-break: break-all;">{{choice.trainingReasons}}</td>
							<td>{{choice.manHours}}</td>
							<td>{{choice.approvedReject}}</td>
							<td style="text-align: left;word-break: break-all;">{{choice.remarks}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="form-group" style="margin-top: 15px;"
			ng-if="loggedUserAppraisalDetails.initializationYear == 1 ">
			<input type="checkbox" id="firstManagerGoalApprovalId"
				ng-disabled="managerAcknowledgement.isFirstManagerGoalApproval == 1"
				ng-model="managerAcknowledgement.firstManagerGoalApproval">
			<label for="firstManagerGoalApprovalId" class="control-label">
				I have discussed and reviewed goals with respective individual and
				had one-on-one discussion and made him/her understand KRA's relevant
				to department and organization.</label>

		</div>
		<div class="form-group" style="margin-top: 15px;"
			ng-if="loggedUserAppraisalDetails.midYear ==1">
			<input type="checkbox" id="firstManagerMidYearApprovalId"
				ng-disabled="managerAcknowledgement.isFirstManagerMidYearApproval == 1"
				ng-model="managerAcknowledgement.firstManagerMidYearApproval">
			<label for="firstManagerMidYearApprovalId" class="control-label">
				I have a candid conversation with respective individual and reviewed
				goals, aspirations and development areas. : </label>

		</div>
		<div class="form-group" style="margin-top: 15px;"
			ng-if="loggedUserAppraisalDetails.finalYear ==1">
			<input type="checkbox" id="firstManagerYearEndAssessmentApprovalId"
				ng-disabled="managerAcknowledgement.isFirstManagerYearEndAssessmentApproval == 1"
				ng-model="managerAcknowledgement.firstManagerYearEndAssessmentApproval">
			<label for="firstManagerYearEndAssessmentApprovalId"
				class="control-label"> I have a candid conversation with
				respective individual and reviewed goals, aspirations and
				development areas. : </label>

		</div>
		<div class="form-group" style="margin-top: 15px;">
			<!-- loggedUserAppraisalDetails.initializationYear == 0 || -->
			<button type="button" ng-click="submit('APPROVED')"
				ng-disabled=" loggedUserAppraisalDetails.finalYear == null ||
			 loggedUserAppraisalDetails.firstLevelIsvisible == 0"
				class="btn btn-outline-secondary">Approve</button>
			<button type="button" ng-click="submit('REJECTED')"
				ng-disabled="loggedUserAppraisalDetails.finalYear == null || loggedUserAppraisalDetails.firstLevelIsvisible == 0"
				class="btn btn-outline-secondary">Reject</button>
		</div>
		<!-- <div class="form-group" style="margin-top: 15px;">
			ng-disabled="managerAcknowledgement.normalRejectionId == 1"
			<input type="radio"
				ng-model="managerAcknowledgement.rejectionId" value=1>Normal
			Rejection <br> <input type="radio"
				ng-model="managerAcknowledgement.rejectionId" value=2>Rejection
			for new KRA'S
			<input type="checkbox" id="normalRejectionIdId"
				ng-model="managerAcknowledgement.normalRejectionId">
			<label for="normalRejectionId"
				class="control-label"></label><br>
				ng-disabled="managerAcknowledgement.newKRARejection == 1"
			<input type="checkbox" id="newKRARejectionId"
				ng-model="managerAcknowledgement.newKRARejection">
			<label for="newKRARejectionId"
				class="control-label"> Rejection for new KRA'S.</label>
		</div>
		<div class="form-group" style="margin-top: 15px;">
			
		</div> --> </uib-tab> </uib-tabset>
	</div>
</div>
<div id="oldKRAModal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">
		<!--   Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Employee Old KRA'S</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12" style="overflow: auto; height: 430px;"
						id="employeeFirtsLevelKRATable">
						<table border="1">
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
									<th rowspan="2"><spring:message code="th.weightage" /><br>A<br>(%)</th>
									<th colspan="4"><spring:message code="th.mid.year.review" /></th>
									<th colspan="4"><spring:message
											code="th.final.year.review" /></th>
									<th rowspan="2">ATTACHMENTS</th>
									<th rowspan="2">MID YEAR <br>RATING<br> A*B
									</th>
									<th rowspan="2">YEAR END <br>RATING<br> A*C
									</th>
									<th rowspan="2"><spring:message code="th.final.score" /><br>30%
										of A*B and 70% of A*C</th>
									<!-- <th rowspan="2">Average</th> -->
									<th rowspan="2"></th>

								</tr>
								<tr>
									<th style="width: 400px;"><spring:message
											code="th.achievement" /></th>
									<th><spring:message code="th.self.rating" /></th>
									<th><spring:message code="th.appraisar.rating" /><br>
										B</th>
									<th style="width: 400px;">MID YEAR <br> ASSESSSMENT
										REMARKS
									</th>
									<th style="width: 400px;"><spring:message
											code="th.achievement" /></th>
									<th><spring:message code="th.self.rating" /></th>
									<th><spring:message code="th.appraisar.rating" /><br>
										C</th>
									<th style="width: 400px;">YEAR END <br> ASSESSMENT
										REMARKS
									</th>
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
									<td></td>
								</tr>
								<tr ng-repeat="choice in sectionAListOld track by $index">
									<td ng-if="$index =='0'"><i
										class="fa fa-plus btn plus-button"
										style="border: 2px solid lightgray;" disabled></i>
										<span ng-if="$index > '0'"
										class="input-group-addon btn minus-button"><i
											class="fa fa-minus"></i></span></td>
									<td ng-if="$index > '0'"><span disabled
										class="input-group-addon btn minus-button"><i
											class="fa fa-minus"></i></span></td>

									<td>{{$index+1}}</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.smartGoal"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.target"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="date" class="form-control input-sm"
										ng-model="choice.achievementDate" style="height: 86px;"
										disabled></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="text" class="form-control input-sm"
										ng-model="choice.weightage" ng-change="countWeightage()"
										style="text-align: center; height: 86px;" disabled
										numbers-only maxlength="2"></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearSelfRating" style="height: 86px;"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-model="choice.midYearAppraisarRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAssessmentRemarks" disabled></textarea><span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.finalYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearSelfRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-model="choice.finalYearAppraisarRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.remarks"
											disabled></textarea><span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
									<td><a ng-href={{choice.fileName}} style="color: red;"
										ng-if="choice.fileName != null">Download</a></td>
									<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
											) * (choice.weightage ))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
											) * (choice.weightage))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null  && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
											* choice.weightage )/100) *30/100) +
											(((choice.finalYearAppraisarRating * choice.weightage)/100) *
											70/100)) | number: 2 }}</span>
									<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
									</td>
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
									<td></td>
								</tr>
								<tr data-ng-repeat="choice in sectionBListOld track by $index">
									<td ng-if="$index =='0'"><i
										class="fa fa-plus btn plus-button"
										style="border: 2px solid lightgray;" disabled
										ng-click="addNewChoice('sectionB')"></i> <span
										ng-if="$index > '0'"
										class="input-group-addon btn minus-button"><i
											class="fa fa-minus"></i></span></td>
									<td ng-if="$index > '0'"><span
										class="input-group-addon btn minus-button" disabled><i
											class="fa fa-minus"></i></span></td>
									<td>{{$index+1}}</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.smartGoal"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.target"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="date" class="form-control input-sm"
										style="height: 86px;" ng-model="choice.achievementDate"
										disabled></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="text" class="form-control input-sm"
										ng-model="choice.weightage" ng-change="countWeightage()"
										style="text-align: center; height: 86px;" disabled
										numbers-only maxlength="2"></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearSelfRating" style="height: 86px;"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearAppraisarRating" style="height: 86px;"
										ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAssessmentRemarks" disabled></textarea><span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.finalYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearSelfRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearAppraisarRating"
										style="height: 86px;"
										ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.remarks"
											disabled></textarea><span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
									<td><a ng-href={{choice.fileName}} style="color: red;"
										ng-if="choice.fileName != null">Download</a></td>
									<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
											) * (choice.weightage ))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
											) * (choice.weightage))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null  && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
											* choice.weightage )/100) *30/100) +
											(((choice.finalYearAppraisarRating * choice.weightage)/100) *
											70/100)) | number: 2 }}</span>
									<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
									</td>
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
									<td></td>
								</tr>
								<tr data-ng-repeat="choice in sectionCListOld track by $index">
									<td ng-if="$index =='0'"><i
										class="fa fa-plus btn plus-button"
										style="border: 2px solid lightgray;" disabled></i>
										<span ng-if="$index > '0'"
										class="input-group-addon btn minus-button"><i
											class="fa fa-minus"></i></span></td>
									<td ng-if="$index > '0'"><span disabled
										class="input-group-addon btn minus-button"><i
											class="fa fa-minus"></i></span></td>
									<td>{{$index+1}}</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.smartGoal"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.target"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="date" class="form-control input-sm"
										ng-model="choice.achievementDate" style="height: 86px;"
										disabled></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><input type="text" class="form-control input-sm"
										ng-model="choice.weightage" ng-change="countWeightage()"
										style="text-align: center; height: 86px;" disabled
										numbers-only maxlength="2"></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearSelfRating" style="height: 86px;"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearAppraisarRating" style="height: 86px;"
										ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAssessmentRemarks" disabled></textarea><span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.finalYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearSelfRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearAppraisarRating"
										style="height: 86px;" disabled
										ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating"></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.remarks"
											disabled></textarea><span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
									<td><a ng-href={{choice.fileName}} style="color: red;"
										ng-if="choice.fileName != null">Download</a></td>
									<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
											) * (choice.weightage ))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
											) * (choice.weightage))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null  && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
											* choice.weightage )/100) *30/100) +
											(((choice.finalYearAppraisarRating * choice.weightage)/100) *
											70/100)) | number: 2 }}</span>
									<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
									</td>
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
									<td></td>

								</tr>
								<tr data-ng-repeat="choice in sectionDListOld track by $index">
									<td></td>
									<td>{{$index+1}}</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.smartGoal"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.smartGoal != null" >{{choice.smartGoal}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.target"
											disabled></textarea><span class="tooltiptext1" ng-if="choice.target != null">{{choice.target}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td><input type="date" class="form-control input-sm"
										ng-model="choice.achievementDate" style="height: 86px;"
										disabled></td>
									<td><input type="text" class="form-control input-sm"
										disabled ng-model="choice.weightage"
										ng-change="countWeightage()" disabled
										style="text-align: center; height: 86px;" numbers-only
										maxlength="2"></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.initializationYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" disabled
											ng-model="choice.midYearAchievement"></textarea><span class="tooltiptext1" ng-if="choice.midYearAchievement != null">{{choice.midYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td><select class="form-control input-sm"
										ng-model="choice.midYearSelfRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td><select class="form-control input-sm"
										style="height: 86px;" ng-model="choice.midYearAppraisarRating"
										ng-style="choice.midYearHighlights === 0 && {'color':'black'}  || choice.midYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating" disabled></select>
									</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.midYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0 || choice.weightage === null" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.midYearAssessmentRemarks" disabled></textarea><span
						class="tooltiptext1" ng-if="choice.midYearAssessmentRemarks != null">{{choice.midYearAssessmentRemarks}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm"
											ng-model="choice.finalYearAchievement" disabled></textarea><span class="tooltiptext1" ng-if="choice.finalYearAchievement != null">{{choice.finalYearAchievement}}</span></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearSelfRating"
										ng-options="item for item in selectsRating"
										style="height: 86px;" disabled></select></td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td><select class="form-control input-sm"
										ng-model="choice.finalYearAppraisarRating"
										style="height: 86px;"
										ng-style="choice.finalYearHighlights === 0 && {'color':'black'}  || choice.finalYearHighlights === 1 && {'color':'green','font-weight': '900'}"
										ng-options="item for item in selectsRating" disabled></select>
									</td>
									<!-- ng-disabled=" loggedUserAppraisalDetails.finalYear == 0 || loggedUserAppraisalDetails.firstLevelIsvisible == 0" -->
									<td class="tooltip1"><textarea rows="4" cols="50"
											class="form-control input-sm" ng-model="choice.remarks"
											disabled></textarea><span class="tooltiptext1"  ng-if="choice.remarks != null">{{choice.remarks}}</span></td>
									<td><a ng-href={{choice.fileName}} style="color: red;"
										ng-if="choice.fileName != null">Download</a></td>
									<td><span ng-if="choice.midYearAppraisarRating !=null">{{((choice.midYearAppraisarRating
											) * (choice.weightage ))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null">{{((choice.finalYearAppraisarRating
											) * (choice.weightage))/100 | number: 2 }}</span></td>
									<td><span ng-if="choice.finalYearAppraisarRating !=null  && choice.midYearAppraisarRating !=null">{{((((choice.midYearAppraisarRating
											* choice.weightage )/100) *30/100) +
											(((choice.finalYearAppraisarRating * choice.weightage)/100) *
											70/100)) | number: 2 }}</span>
									<span ng-if="choice.finalYearAppraisarRating !=null && choice.midYearAppraisarRating ==null">{{((choice.finalYearAppraisarRating) * (choice.weightage))/100 | number: 2 }}</span>
									</td>
								</tr>
								<tr style="height: 25px;">
									<td colspan="5"></td>
									<td style="font-weight: bold;">{{WeightageCount}}</td>
									<td colspan="11"></td>
									<td><strong ng-if="kraToalCalculation > 0">{{kraToalCalculation
											| number: 2 }}</strong></td>
									<td></td>

								</tr>
							</tbody>
						</table>

					</div>

					<!-- <div class="col-md-12" style="margin-top: 15px;">
				<button type="button" ng-click="saveKRAModule('SAVE')"
					ng-disabled="kraButtonDisabled" class="btn btn-outline-secondary">Save</button>
				<button type="button" ng-click="submitKRAModule('SUBMIT')"
					ng-disabled="kraButtonDisabled" class="btn btn-outline-secondary">Submit</button>
			</div>
 -->
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-secondary"
					data-dismiss="modal" style="float: right;">Close</button>
			</div>
		</div>

	</div>
</div>