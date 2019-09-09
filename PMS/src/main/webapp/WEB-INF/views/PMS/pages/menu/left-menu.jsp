 <ul class="sidebar-menu tree" ng:controller="NavigationController"  data-widget="tree">
        <li ng-repeat="item in items" ng-if="item.chidItems.length == null" ng-class="{'active': item.id == states.activeItem}" ng-click="states.activeItem=item.id">
          <a  href={{item.link}}>
           <!--  <i class="{{item.icon}}"></i>  -->{{item.title}}
            <span class="pull-right-container" ng-if="item.chidItems.length> '0'">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
        </li>
         <li class="treeview" ng-repeat="item in items" ng-if="item.chidItems.length > '0'" ng-class="{'active': item.id == states.activeItem}"  ng-click="states.activeItem=item.id">
          <a  href={{item.link}}>
            <!-- <i class="{{item.icon}}"></i>  -->{{item.title}}
            <span class="pull-right-container" ng-if="item.chidItems.length> '0'">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu"  style="display: none;">
            <li class="active" ng-repeat="category in item.chidItems">
            <a href={{category.link}}><i class={{category.icon}}></i>{{category.title}}</a></li>
          </ul>
        </li>
        </ul> 
        