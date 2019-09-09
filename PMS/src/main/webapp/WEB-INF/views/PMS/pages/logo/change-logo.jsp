<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="section-tag">
	<span>Change Logo</span>
</div>
<div class="row">
	<div class="col-md-6 col-sm-12 col-xs-12">
		<div class="panel panel-info">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<form class="form-horizontal" role="form" name="changeLogo"
					ng-submit="save()">
					<div class="col-md-12" id="logoAlert">{{message}}</div>
					<div class="col-md-12">
						<label for="usr">Choose File:</label> <input type="file" accept="image/*" 
							id="file1" file-model="file" required>
					</div>
					<div class="col-md-12" style="margin-top: 15px;">
						<button type="submit" class="btn btn-sm btn-outline-secondary">
							<spring:message code="button.save" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
