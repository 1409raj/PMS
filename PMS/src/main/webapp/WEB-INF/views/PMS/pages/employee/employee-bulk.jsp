<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span>Employee Bulk</span>
</div>
<div class="row">
	<div class="col-md-6 col-sm-12 col-xs-12">
		<div class="panel panel-info">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<form class="form-horizontal" role="form" name="employeeBulk"
					ng-submit="save()">
					<div class="col-md-12" id="employeeBulkAlert">{{message}}</div>
					<div class="col-md-12">
						<label for="usr">Choose File:</label> <input type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
							id="file1" file-model="file1" required>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit" class="btn btn-sm btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
						<a ng-href="resources/common/documents/employeeBulkFormat.csv" style="color:red;" download="employeeBulkFormat.csv">Format</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

