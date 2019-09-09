<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.input-group {
	width: 100%;
}

textarea {
	resize: none;
}

tfoot{
    display: none;
}
/* .dataTables_filter{
    display: none !important;
} */
table, th, td {
    border: 0.2px solid lightslategray !important;
    text-align: center;
}
button.dt-button{
background-color: #315772  !important;
color:white;
background-image: linear-gradient(to bottom, #315772 0%, #e9e9e9 100%)  !important;
}
/* .dataTables_paginate{
display:none !important;
} */
/* .dataTables_length{
display:none !important;
} */
</style>
<div class="section-tag">
	<div>
		<span>OverAll Team Rating</span>
	</div>
</div>
<div class="col-md-12" style="padding: 0px;">
	</div>
	<div class="row" ng-init="getEmployeeKraDetails()">
		<div class="col-md-12" style="overflow: auto;margin-top: 15px;">
		
	
		
		<table border="1" style="font-size: 15px;" id="tblEmployeeOverAllDetails">
				<thead style="background-color: #315772; color: #ffffff;color:white;">
					<tr>
						<th style="max-width: 35px;"><spring:message code="th.kra.sr.no" /></th>
						<th style="max-width: 55px;">EMP-CODE</th>
						<th>EMPLOYEE NAME</th>
						<th >PENDING</th>
						<th >STATUS</th>
						<th >SUB-STATUS</th>
						<th >LOCATION</th>
						<th >MOBILE</th>
						<th >EMAIL</th>
						<th >DESIGNATION</th>
						<th >DEPARTMENT</th>
						<th >DATE OF JOINING</th>
						<th style="max-width: 55px;">KRA</th>
						<th style="max-width: 55px;">KRA 75%</th>
						<th >BEHAVIOURAL COMPETENCE</th>
						<th >BEHAVIOURAL COMPETENCE 15%</th>
						<th >EXTRA ORDINARY INITIATIVES</th>
						<th >EXTRA ORDINARY INITIATIVES 10%</th>
						<th style="max-width: 85px;">FINAL RATING</th>
						<th >MID YEAR ACKNOWLEDGE</th>
						<th >FINAL YEAR ACKNOWLEDGE</th>
						<th >Session</th>
					</tr>
				</thead>
				<tfoot>
		            <tr>
		                <th style="max-width: 35px;"></th>
						<th style="max-width: 55px;"></th>
						<th></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th></th>
						<th></th>
						<th ></th>
						<th style="max-width: 55px;" ></th>
						<th style="max-width: 55px;"></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th style="max-width: 85px;"></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th ></th>
						<th ></th>
		            </tr>
		        </tfoot>
				<tbody>
				<tr ng-repeat="choice in teamOverAllData track by $index">
						<td>{{$index+1}}</td>
						<td>{{choice.empCode}}</td>
						<td style="text-align: left;">{{choice.empName}}</td>
						<td style="text-align: left;">{{choice.pendingWithManager}}</td>
						<td style="text-align: left;">{{choice.stage | commatodash}}</td>
						<td style="text-align: left;">{{choice.subStage | commatodash}}</td>
						<td style="text-align: left;">{{choice.location | commatodash}}</td>
						<td style="text-align: left;">{{choice.mobile}}</td>
						<td style="text-align: left;">{{choice.email}}</td>
						<td style="text-align: left;">{{choice.designationName | commatodash}}</td>
						<td style="text-align: left;">{{choice.departmentName | commatodash}}</td>
						<td style="text-align: left;">{{choice.dateOfJoining | date : "dd.MM.y"}}</td>
						<td><span ng-if="choice.kra != '0'">{{choice.kra | number: 2}}</span></td>
						<td><span ng-if="choice.kraCalculation != '0'">{{choice.kraCalculation | number: 2}}</span></td>
						<td><span ng-if="choice.behaviouralCompetenceDetails != '0'">{{choice.behaviouralCompetenceDetails | number: 2}}</span></td>
						<td><span ng-if="choice.behaviouralCompetenceDetailsCalculation != '0'">{{choice.behaviouralCompetenceDetailsCalculation | number: 2}}</span></td>
						<td ><span ng-if="choice.extraOrdinary != '0'">{{choice.extraOrdinary | number: 2}}</span></td>
						<td><span ng-if="choice.extraOrdinaryCalculation != '0'">{{choice.extraOrdinaryCalculation | number: 2}}</span></td>
						<td><span ng-if="choice.finalRating != '0'" >{{choice.finalRating | number: 2}}</span></td>	
						<td><span ng-if="choice.employeeMidYearApproval == '1'" style="color:green;">Acknowledged</span></td>
						<td><span ng-if="choice.employeeApproval == '1'" style="color:green;">Acknowledged</span></td>
						<td style="text-align: left;">{{choice.sessionYear}}</td>
						</tr> 
				</tbody>
			</table>
		</div>
	</div>