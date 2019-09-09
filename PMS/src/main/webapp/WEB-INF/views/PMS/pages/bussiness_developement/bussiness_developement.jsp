<!-- 


<div ng-controller="BussinessDevelopementController">
   <div class="section-tag"><span>Section-1:B</span></div>
	<center>
	<ul class="pagination">
			<li ><a href="#/financial_objective">Financial
					Objective</a></li>
			<li class="active"><a href="#/bussiness_developement">Bussiness
					Developement</a></li>
			<li><a href="#/operational_objective"> Operatinal Objectives</a></li>
			<li><a href="#/organizational_commitment"> Organizational
					Commitment</a></li>

			<li><a href="#/final_evaluation"> Final Evaluation</a></li>
		</ul>
	</center>
	<form class="form-horizontal" style="font-size:10px;" role="form">
   <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Employee Name</label>
                    <div class="col-sm-3">
                       <input type="text" id="firstName" placeholder="Employee Name" class="form-control" autofocus>                    
                    </div>
                     <label for="firstName" class="col-sm-2 control-label">Department</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Department" class="form-control" autofocus>                       
                    </div>              
                    </div>   
                    <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Employee Code</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Employee Code" class="form-control" autofocus>                       
                    </div>
                     <label for="firstName" class="col-sm-2 control-label">Date of Joining</label>
                    <div class="col-sm-3">
                      <input type="text" id="firstName" placeholder="Date of Joining" class="form-control" autofocus>   
                    </div>                   
                    </div>
                    <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Designation</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Designation" class="form-control" autofocus>
                    </div>
                     <label for="firstName" class="col-sm-2 control-label">Qualification</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Qualification" class="form-control" autofocus>
                     </div>
                     </div>
                    <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Location</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Location" class="form-control" autofocus>
                    </div>
                     <label for="firstName" class="col-sm-2 control-label">First Level Superior</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="First Level Superior" class="form-control" autofocus>
                    </div>
                    </div>
                    <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Assessment for the Period</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Assessment for the Period" class="form-control" autofocus>
                    </div>
                     <label for="firstName" class="col-sm-2 control-label">Second level superior</label>
                    <div class="col-sm-3">
                        <input type="text" id="firstName" placeholder="Second level superior" class="form-control" autofocus>
                    </div>
                    </div>
            </form>
              <div class="row">
            <div class="col-md-12">
           <div class="col-md-3">
           <span class="span-label">Employee Name : </span><span>Ankit Rai</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Employee Code : </span><span>EMP1</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Designation : </span><span>Software Engineer</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Department : </span><span>Computer Science</span>
           </div>
           </div>
           <div class="col-md-12">
           <div class="col-md-3">
           <span class="span-label">Date of Joining : </span><span>08-02-2018</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Qualification : </span><span>B.tech</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">First Level Superior : </span><span>Ankit Rai</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Second Level Superior : </span><span>Ankit Rai</span>
           </div>
            </div>
            <div class="col-md-12">
           <div class="col-md-3">
           <span class="span-label">Location : </span><span>Delhi</span>
           </div>
           <div class="col-md-3">
           <span class="span-label">Assessment for the period: </span><span>1 year</span>
           </div>
            </div>
             </div>
	<div class="form-group" style="margin-top: 15px;">
		<i class="fa fa-plus btn" style="float:right;border: 2px solid lightgray;"ng-click="addNewChoice()"></i>
		<table border="1">
			<thead>
				<tr>
					<th rowspan="2">S.No.</th>
					<th rowspan="2" style="width: 20%;">Smart Goal</th>
					<th rowspan="2"style="width: 20%;">Target</th>
					<th rowspan="2"style="width: 5%;">Achievement Date</th>
					<th rowspan="2">Weightage</th>
					<th colspan="2">Mid Year Review</th>
					<th colspan="2">Final Year Review</th>
					<th rowspan="2">30% of AR-MID and 70% Of AR-Final</th>
					<th rowspan="2">Final Score</th>
				</tr>
				<tr>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
					<th>Self Rating</th>
					<th>Appraisar Rating</th>
				</tr>
			</thead>
			<tbody>
				<tr data-ng-repeat="choice in choices">
					<td>{{$index + 1}}</td>
					<td><input type="text" class="form-control input-sm"
						ng-modal="{{choice.name}}" name="" ></td>
					<td><input type="text" class="form-control input-sm"
						></td>
					<td><input type="date" class="form-control input-sm"
						></td>
					<td><select class="form-control input-sm">
							<option>1</option>
							<option>2</option>

							<option>3</option>

							<option>4</option>

							<option>5</option>

					</select></td>


					<td><select class="form-control input-sm">
							<option>1</option>
							<option>2</option>

							<option>3</option>

							<option>4</option>

							<option>5</option>

					</select></td>
					<td><select class="form-control input-sm">
							<option>1</option>
							<option>2</option>

							<option>3</option>

							<option>4</option>

							<option>5</option>

					</select></td>
					<td><select class="form-control input-sm">
							<option>1</option>
							<option>2</option>

							<option>3</option>

							<option>4</option>

							<option>5</option>

					</select></td>
					<td><select class="form-control input-sm">
							<option>1</option>
							<option>2</option>

							<option>3</option>

							<option>4</option>

							<option>5</option>

					</select></td>
					<td><input type="text" class="form-control input-sm"
						></td>


					<td>
						<div class="input-group">
							<input id="email" type="text" class="form-control input-sm"
								name="email"> <span
								class="input-group-addon btn" ng-click="removeNewChoice()"><i
								class="fa fa-minus"></i></span>
						</div>

					</td>
					

				</tr>


			</tbody>
		</table>
		<hr>
		<div class="form-group">
			<div class="col-sm-4">
				<a href="#/operational_objective">Next</a>
				<button type="button" class="btn btn-outline-secondary">Save</button>
			</div>
		</div>
	</div>
</div> -->