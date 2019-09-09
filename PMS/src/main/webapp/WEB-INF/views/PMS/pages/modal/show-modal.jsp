<title>{{modalOptions.headerText}}</title>
<div class="modal-header">
  <h4>{{modalOptions.headerText}}</h4>
</div>
<div class="modal-body">
  <p>{{modalOptions.bodyText}}</p>
</div>
<div class="modal-footer">
  <button class="btn btn-primary col-md-3" style="align: center; float: center;" 
          data-ng-click="modalOptions.ok();">{{modalOptions.actionButtonText}}</button>
  <button type="button" class="btn btn-info col-md-3" style="align: center; float: center;" ng-show="modalOptions.modalType == 'Confirm'"
          data-ng-click="modalOptions.close()">{{modalOptions.closeButtonText}}</button>
</div>
