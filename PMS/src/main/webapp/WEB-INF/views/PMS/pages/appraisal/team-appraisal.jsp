<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <style>
.btn-primary {
    border-radius: 50%;
}
.btn-primary:hover {
    color: #fff;
    background-color: #88c566;
    border-color: #88c566;
}
.ui-grid-coluiGrid-008 {
    width: 135px;
    text-align: center !important;
}
 </style>

<div class="section-tag"><span><spring:message code="heading.team-appraisal.list" /></span></div> 
<!------------------------------- for grid  --------------------------------->
<div class="row">
	<div class="col-md-3">
		<label for="levelId" class="control-label">Employee Type</label> <select
			ng-model="levelType" id="levelId" required
			ng-options="dp as dp  for dp in employeeLevel"
			ng-change="getOnLoadData()" class="form-control">
			<option value="">Please select</option>
		</select>
	</div>
</div>
<div id="grid1" ui-grid="gridOptions" class="gridStyle" ui-grid-auto-resize style="margin-top:15px;"></div>
