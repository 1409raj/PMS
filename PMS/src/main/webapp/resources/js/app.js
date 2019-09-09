// Function called when user refreshes or closes browser window
window.onbeforeunload = function(e) {
	var currentUrl = window.location.hash.toString();
	if ((currentUrl.indexOf("unit") == 2)
			|| (currentUrl.indexOf("contact") == 2)
			|| (currentUrl.indexOf("client") == 2) || (currentUrl.length < 3)) {
	} else {
		return document.getElementById("alertRefresh").value;
	}
};

var loggedUser = {
	userName : document.getElementById("globalUserName").value,
	roleName : document.getElementById("globalUserRoleName").value,
	userRole : {}
};

var app = angular.module('app', [ 'ngAnimate', 'ngTouch', 'ui.grid',
		'ui.grid.resizeColumns', 'ui.grid.exporter', 'ui.grid.autoResize',
		'ui.grid.selection', 'ui.grid.edit', 'ngRoute', 'chart.js',
		'ui.bootstrap', 'ngTagsInput' ]);
// app.run(function($rootScope, $templateCache) {
// $rootScope.$on('$viewContentLoaded', function() {
// $templateCache.removeAll();
// });
// });
// Required to update title on browser according to selected page
app.run([ '$rootScope', function($rootScope) {
	$rootScope.$on('$routeChangeSuccess', function(event, current, previous) {
		// $rootScope.title = current.$$route.title;
	});
} ]);

// Service used to share data across controllers
app.service('sharedProperties', function() {
	var hashtable = {};
	return {
		setValue : function(key, value) {
			hashtable[key] = value;
		},
		getValue : function(key) {
			return hashtable[key];
		}
	}
});


app.filter('commatodash', function () {
	  return function (input) {
	      return input.replace(/,/g, ' /');
	  };
	});

app.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;
			element.bind('change', function() {
				scope.$apply(function() {
					if (element[0].files[0].size > 100000) {
						alert("Required file size is 100kb");
						element[0].files[0] = null;
						if (angular.equals(element[0].files[0].id), "file1") {
							document.getElementById("file1").value = '';
						}

					} else {
						modelSetter(scope, element[0].files[0]);
					}
				});
			});
		}
	};
} ]);


app.directive('capitalizeFirst', function(uppercaseFilter, $parse) {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, modelCtrl) {
			var capitalize = function(inputValue) {
				var capitalized = inputValue.charAt(0).toUpperCase()
						+ inputValue.substring(1);
				if (capitalized !== inputValue) {
					modelCtrl.$setViewValue(capitalized);
					modelCtrl.$render();
				}
				return capitalized;
			}
			var model = $parse(attrs.ngModel);
			modelCtrl.$parsers.push(capitalize);
			capitalize(model(scope));
		}
	};
});
app.directive('onlyDigits', function() {
	return {
		require : 'ngModel',
		restrict : 'A',
		link : function(scope, element, attr, ctrl) {
			function inputValue(val) {
				if (val) {
					var digits = val.replace(/[^0-9.]/g, '');

					if (digits.split('.').length > 2) {
						digits = digits.substring(0, digits.length - 1);
					}

					if (digits !== val) {
						ctrl.$setViewValue(digits);
						ctrl.$render();
					}
					return parseFloat(digits);
				}
				return undefined;
			}
			ctrl.$parsers.push(inputValue);
		}
	};
});

app.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});
app.config(function($httpProvider, $provide, $windowProvider) {
	// Added to handle redirecting to a proper login page after session
	// timeout
	$provide.factory('myHttpInterceptor', function($q, $location) {
		return {
			'response' : function(response) {
				if (response.status === 200) {
				}
				// handle error code less than 300
				if (response.status === 302) {
				}
				// handle success response here
				return response;
			},
			'responseError' : function(rejection) {
				// session expired code - 401, 500
				// unauthorized access code - 403
				if (rejection.status === 401 || rejection.status === 500
						|| rejection.status === 403) {
					var alertMessage = document
							.getElementById("alertSessionExpired").value;
					if (rejection.status === 403) {
						alertMessage = document
								.getElementById("alertAccessDenied").value;
					}
					var $window = $windowProvider.$get();
					var host = location.host;
					var loginPage = $location.protocol() + '://' + host
							+ '/PMS/login';
					logDebug('rejection.status >> ' + rejection.status
							+ ', host >> ' + host + ', loginPage >> '
							+ loginPage);
					alert(alertMessage);
					$window.location.href = loginPage;
				}
				return $q.reject(rejection);
			}
		};
	});

	$httpProvider.interceptors.push('myHttpInterceptor');

	// Added to handle datetime format and timezone while exporting grid
	// data to csv
	$provide.decorator('uiGridExporterService',
			function($delegate, $filter) {
				$delegate.formatFieldAsCsv = function(field) {
					if (field.value == null) {
						return '';
					}
					if (typeof (field.value) === 'number') {
						return field.value;
					}
					if (typeof (field.value) === 'boolean') {
						return (field.value ? 'TRUE' : 'FALSE');
					}
					if (typeof (field.value) === 'string') {
						return '"' + field.value + '"';
					}
					if (field.value instanceof Date
							|| typeof (field.value) === 'date') {
						return getFormattedDate(field.value, 'long');
					}

					return JSON.stringify(field.value);
				};

				return $delegate;
			});
});

function actionPath(path) {
	window.location = path;
}

function getAngularDate(date) {
	if (date != null && !angular.isDate(date)) {
		date = new Date(date);
	}
	return date;
}

function getFormattedDate(date, datetype) {
	date = getAngularDate(date);
	if (datetype == 'short') {
		var datestring = date.toLocaleDateString();
		var dateFormat = document.getElementById("dateShort").value;
		if (dateFormat == 'MM/dd/yyyy') {
			datestring = ("0" + (date.getMonth() + 1)).slice(-2) + "/"
					+ ("0" + date.getDate()).slice(-2) + "/"
					+ date.getFullYear();
		} else if (dateFormat == 'dd/MM/yyyy') {
			datestring = ("0" + date.getDate()).slice(-2) + "/"
					+ ("0" + (date.getMonth() + 1)).slice(-2) + "/"
					+ date.getFullYear();
		}
		logDebug('getFormattedDate >> ' + date + ' >> ' + datestring);
		return datestring;
	}
	if (datetype == 'long') {
		var datestring = date.toLocaleString();
		var dateFormat = document.getElementById("dateLong").value;
		var mmdd = 'MM/dd/yyyy';
		var ddmm = 'dd/MM/yyyy';
		if (dateFormat.slice(0, mmdd.length) == mmdd) {
			datestring = ("0" + (date.getMonth() + 1)).slice(-2) + "/"
					+ ("0" + date.getDate()).slice(-2) + "/"
					+ date.getFullYear();
		} else if (dateFormat.slice(0, ddmm.length) == ddmm) {
			datestring = ("0" + date.getDate()).slice(-2) + "/"
					+ ("0" + (date.getMonth() + 1)).slice(-2) + "/"
					+ date.getFullYear();
		}
		var hhmm = 'HH:mm';
		var hhmmss = 'HH:mm:ss';
		var hhmma = 'HH:mm a';
		if (stringEndsWith(dateFormat, hhmm)) {
			datestring = datestring + ' ' + ("0" + (date.getHours())).slice(-2)
					+ ':' + ("0" + (date.getMinutes())).slice(-2);
		} else if (stringEndsWith(dateFormat, hhmmss)) {
			datestring = datestring + ' ' + ("0" + (date.getHours())).slice(-2)
					+ ':' + ("0" + (date.getMinutes())).slice(-2) + ':'
					+ ("0" + (date.getSeconds())).slice(-2);
		} else if (stringEndsWith(dateFormat, hhmma)) {
			var hours = date.getHours();
			var ampm = hours >= 12 ? 'PM' : 'AM';
			hours = hours % 12;
			hours = hours ? hours : 12; // the hour '0' should be '12'
			datestring = datestring + ' ' + ("0" + (hours)).slice(-2) + ':'
					+ ("0" + (date.getMinutes())).slice(-2) + ' ' + ampm;
		}
		logDebug('getFormattedDate >> ' + date + ' >> ' + datestring);
		return datestring;
	}
	return date;
}

function getToday() {
	var today = new Date();
	today.setHours(0, 0, 0, 0);
	return today;
}

function getEndOfCentury() {
	return new Date(2099, 11, 31, 0, 0, 0, 0);
}

function stringEndsWith(filename, ext) {
	var str = angular.lowercase(filename);
	var substr = angular.lowercase(ext);
	var matches = str.match(substr + "$") == substr;
	logDebug('[' + filename + '] ends with [' + ext + '] >> ' + matches);
	return matches;
}

function logDebug(message) {
	console.log(message);
}

app.filter('ceil', function() {
	return function(input) {
		return Math.ceil(input);
	};
});

// configure our routes
app.config(function($routeProvider) {
	$routeProvider
	// route for the home page
	.when('/', {
		templateUrl : 'index',
		controller : 'IndexController',
	// title : document.getElementById("menuDashboard").value
	}).when('/change-logo', {
		templateUrl : 'change-logo',
		controller : 'ChangeLogoController',
	// title : document.getElementById("menuDashboard").value
	}).when('/change_password', {
		templateUrl : 'change_password',
		controller : 'ChangePasswordController',
	// title : document.getElementById("menuDashboard").value
	}).when('/master-page', {
		templateUrl : 'master-page',
		controller : 'MasterPageController',
	// title : document.getElementById("menuDashboard").value
	// }).when('/manager_behavioral_comptence', {
	// templateUrl : 'manager_behavioral_comptence',
	// controller : 'ManagerBehavioralComptenceController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-promotion', {
		templateUrl : 'employee-promotion',
		controller : 'EmployeePromotionController'
	}).when('/hrEmployeePromotion', {
		templateUrl : 'hrEmployeePromotion',
		controller : 'HREmployeePromotionController'
	}).when('/ceoEmployeePromotion', {
		templateUrl : 'ceoEmployeePromotion',
		controller : 'CEOEmployeePromotionController'
	}).when('/department-list', {
		templateUrl : 'department-list',
		controller : 'DepartmentListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/designation-list', {
		templateUrl : 'designation-list',
		controller : 'DesignationListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/extra_ordinary-list', {
		templateUrl : 'extra_ordinary-list',
		controller : 'ExtraOrdinaryListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/organization_roles-list', {
		templateUrl : 'organization_roles-list',
		controller : 'organizationRolesListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/application_roles-list', {
		templateUrl : 'application_roles-list',
		controller : 'applicationRolesListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/training_needs-list', {
		templateUrl : 'training_needs-list',
		controller : 'TrainingNeedsListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-list', {
		templateUrl : 'employee-list',
		controller : 'EmployeeListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-form-configuration', {
		templateUrl : 'employee-form-configuration',
		controller : 'EmployeeFormConfigurationController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-bulk', {
		templateUrl : 'employee-bulk',
		controller : 'EmployeeBulkController',
	// title : document.getElementById("menuDashboard").value
	}).when('/dashboard', {
		templateUrl : 'dashboard',
		controller : 'DashboardController',
		title : document.getElementById("menuDashboard").value
	}).when('/hrDashboard', {
		templateUrl : 'hrDashboard',
		controller : 'HRDashboardController',
		title : document.getElementById("menuDashboard").value
	}).when('/appraisal-cycle', {
		templateUrl : 'appraisal-cycle',
		controller : 'AppraisalCycleController',
	// title : document.getElementById("menuDashboard").value
	}).when('/firstlevel-appraisal', {
		templateUrl : 'firstlevel-appraisal',
		controller : 'FirstLevelAppraisalController',
	// title : document.getElementById("menuDashboard").value
	}).when('/secondlevel-appraisal', {
		templateUrl : 'secondlevel-appraisal',
		controller : 'SecondLevelAppraisalController',
	// title : document.getElementById("menuDashboard").value
	}).when('/appraisal_years-list', {
		templateUrl : 'appraisal_years-list',
		controller : 'AppraisalYearListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/over_all_rating', {
		templateUrl : 'over_all_rating',
		controller : 'OverAllRatingController',
	// title : document.getElementById("menuDashboard").value over_all_rating
	// team_rating
	}).when('/team_over_all_rating', {
		templateUrl : 'team_over_all_rating',
		controller : 'TeamOverAllRatingController',
	// title : document.getElementById("menuDashboard").value over_all_rating
	}).when('/team_rating', {
		templateUrl : 'team_rating',
		controller : 'TeamRatingController',
	// title : document.getElementById("menuDashboard").value over_all_rating
	}).when('/view-appraisal', {
		templateUrl : 'view-appraisal',
		controller : 'ViewAppraisalController',
	// title : document.getElementById("menuDashboard").value
	// }).when('/view-subEmployee-appraisal', {
	// templateUrl : 'view-subEmployee-appraisal',
	// controller : 'ViewSubEmployeeAppraisalController',
	// title : document.getElementById("menuDashboard").value
	}).when('/add-employee', {
		templateUrl : 'add-employee',
		controller : 'AddEmployeeController',
	// title : document.getElementById("menuDashboard").value
	}).when('/modify-employee', {
		templateUrl : 'modify-employee',
		controller : 'ModifyEmployeeController',
	// title : document.getElementById("menuDashboard").value
	}).when('/view-employee', {
		templateUrl : 'view-employee',
		controller : 'ViewEmployeeController',
	// title : document.getElementById("menuDashboard").value
	}).when('/team-appraisal', {
		templateUrl : 'team-appraisal',
		controller : 'TeamAppraisalController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-kra', {
		templateUrl : 'employee-kra',
		controller : 'EmployeeKRAController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-newKra', {
		templateUrl : 'employee-newKra',
		controller : 'EmployeeNewKRAController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-oldKra', {
		templateUrl : 'employee-oldKra',
		controller : 'EmployeeOldKRAController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-kra-list', {
		templateUrl : 'employee-kra-list',
		controller : 'EmployeeKRAListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/extra_ordinary', {
		templateUrl : 'extra_ordinary',
		controller : 'ExtraOrdinaryController',
	// title : document.getElementById("menuDashboard").value
	}).when('/employee-appraisal-list', {
		templateUrl : 'employee-appraisal-list',
		controller : 'EmployeeAppraisalListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/extra_ordinary1', {
		templateUrl : 'extra_ordinary1',
		controller : 'ExtraOrdinaryController',
	// title : document.getElementById("menuDashboard").value
	}).when('/behavioral_comptence-list', {
		templateUrl : 'behavioral_comptence-list',
		controller : 'BehavioralComptenceListController',
	// title : document.getElementById("menuDashboard").value
	}).when('/behavioral_comptence', {
		templateUrl : 'behavioral_comptence',
		controller : 'BehavioralComptenceController',
	// title : document.getElementById("menuDashboard").value
	}).when('/career_aspiration', {
		templateUrl : 'career_aspiration',
		controller : 'CareerAspirationController',
	// title : document.getElementById("menuDashboard").value
	}).when('/training_needs', {
		templateUrl : 'training_needs',
		controller : 'TrainingNeedsController',
	// title : document.getElementById("menuDashboard").value
	}).when('/final_review', {
		templateUrl : 'final_review',
		controller : 'FinalReviewController',
	// title : document.getElementById("menuDashboard").value
	})
});

// app
// .controller(
// 'AcknowledgementController',
// function($window, $scope, $timeout, $http, sharedProperties) {
// logDebug('Enter AcknowledgementController');
// logDebug('Browser language >> ' + window.navigator.language);
// document.getElementById("appraisalYearID").style.visibility = "hidden";
// $scope.appraisalYearId = JSON.parse(localStorage
// .getItem("appraisalYearId"));
//		
// $scope.submit = function()
// {
// var input = {
// "empCode" : document
// .getElementById("globalUserName").value,
// "appraisalYearId" : $scope.appraisalYearId.id
// };
// var res = $http.post(
// '/PMS/employeeAcknowledgement',input);
// res.success(function(data, status) {
// if (data.integer == 1) {
// document
// .getElementById("acknowledgementAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("acknowledgementAlert").className = "";
// $scope.message = "";
// }, 5000);
// } else {
// document
// .getElementById("acknowledgementAlert").className = "alert-dangers";
// }
// });
//			
// }
// });


app
.controller(
		'EmployeeNewKRAController',
		function($window, $scope, $timeout, $http, sharedProperties) {
			logDebug('Enter EmployeeNewKRAController');
			logDebug('Browser language >> ' + window.navigator.language);
			$scope.loggedUserAppraisalDetails = [];
			document.getElementById("appraisalYearID").style.visibility = "visible";
			document.getElementById("main_body").className = "loading-pane";
			$scope.appraisalYearId = JSON.parse(localStorage
					.getItem("appraisalYearId"));
			$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
			$scope.selectsRating = [ 1, 2, 3, 4, 5 ];
			$scope.WeightageCount = 5;
			$scope.fileList = [];
			
			$scope.sectionAList = [ {
				'sectionName' : 'Section A',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'kraType':null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionBList = [ {
				'sectionName' : 'Section B',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'kraType':null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionCList = [ {
				'sectionName' : 'Section C',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'kraType':null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionDList = [
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalOne").value,
						'target' : document.getElementById("targetOne").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageOne").value,
						'kraType':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					},
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalTwo").value,
						'target' : document.getElementById("targetTwo").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageTwo").value,
						'kraType':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					},
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalThree").value,
						'target' : document
								.getElementById("targetThree").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageThree").value,
						'kraType':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					} ];

			$scope.formData = {
				"sectionAList" : $scope.sectionAList,
				"sectionBList" : $scope.sectionBList,
				"sectionCList" : $scope.sectionCList,
				"sectionDList" : $scope.sectionDList
			};
			
			$scope.gotoOldKRA=function()
			{
				$window.location.href = "#/employee-oldKra";
			}

			$scope.copyOldKRAtoNewKRA = function()
			{
				var input = {
						"empCode" : document
								.getElementById("globalUserName").value,
						"appraisalYearId" : $scope.appraisalYearId.id
					};
					var res = $http.post('/PMS/copyOldKRAtoNewKRA', input);
					res.success(function(data, status) {
						if (data.integer == 1) {
							swal(data.string);
							$scope.loggedUserKRADetails = data.object;
							var p = 0;
							var q = 0;
							var r = 0;
							var s = 0;
							$scope.kraDetailsId = [];
							$scope.kraToalCalculation = 0;
							$scope.WeightageCount = 0;
							for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
								if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
									$scope.buttonDisabled = true;
									$scope.WeightageCount = 0;
									break;
								}
							}
							for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
								$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
								if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
									$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
									if($scope.loggedUserKRADetails[i].weightage !=null)
										{
									$scope.WeightageCount = $scope.WeightageCount
									+ parseInt($scope.loggedUserKRADetails[i].weightage);
										}
									if ($scope.loggedUserKRADetails[i].achievementDate != null) {
										$scope.sectionAList[p].achievementDate = new Date(
												$scope.loggedUserKRADetails[i].achievementDate);
									}
									if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
											$scope.sectionAList[p].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
											(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
											70/100)));
										}
									
									if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
											$scope.sectionAList[p].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (
											(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
										}
									if ($scope.sectionAList[p].fileName !=null)
									{
									$scope.sectionAList[p].fileDummyName = $scope.sectionAList[p].fileName;
									}
									p++;
								} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
									$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
									if ($scope.loggedUserKRADetails[i].achievementDate != null) {
										$scope.sectionBList[q].achievementDate = new Date(
												$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
									}
									if($scope.loggedUserKRADetails[i].weightage !=null)
									{
										$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.loggedUserKRADetails[i].weightage);	
									}
									if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
											$scope.sectionBList[q].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
											(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
											70/100)));
										}
									if($scope.sectionBList[q].midYearAppraisarRating == null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
											$scope.sectionBList[q].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (
											(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
										}
									if ($scope.sectionBList[q].fileName !=null)
									{
									$scope.sectionBList[q].fileDummyName = $scope.sectionBList[q].fileName;
									}
									q++;
								} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
									$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
									if ($scope.loggedUserKRADetails[i].achievementDate != null) {
										$scope.sectionCList[r].achievementDate = new Date(
												$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
									}
									if($scope.loggedUserKRADetails[i].weightage !=null)
									{
										$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.loggedUserKRADetails[i].weightage);
									}
									if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
											$scope.sectionCList[r].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
											(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
											70/100)));
										}
									if($scope.sectionCList[r].midYearAppraisarRating == null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
											$scope.sectionCList[r].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (
											(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
										}
									if ($scope.sectionCList[r].fileName !=null)
									{
										$scope.sectionCList[r].fileDummyName = $scope.sectionCList[r].fileName;
									}
									r++;
								} else {
									$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
									if ($scope.loggedUserKRADetails[i].achievementDate != null) {
										$scope.sectionDList[s].achievementDate = new Date(
												$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
									}
									if($scope.loggedUserKRADetails[i].weightage !=null)
									{
										$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.loggedUserKRADetails[i].weightage);
									}
									if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
											$scope.sectionDList[s].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
											(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
											70/100)));
										}
									if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
											$scope.sectionDList[s].weightage !=null)
										{
									$scope.kraToalCalculation = $scope.kraToalCalculation + (
											(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
										}
									if ($scope.sectionDList[s].fileName !=null)
									{
										$scope.sectionDList[s].fileDummyName = $scope.sectionDList[s].fileName;
									}
									s++;
								}
							}
							document.getElementById("main_body").className = "";
							
							for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
								if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
									$scope.buttonDisabled = true;
									break;
								}

							}

						}
					});
			}
			
			$scope.countWeightage = function() {
				$scope.WeightageCount = 0;
				for (var i = 0; i < $scope.sectionAList.length; i++) {
					if ($scope.sectionAList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionAList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionBList.length; i++) {
					if ($scope.sectionBList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionBList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionCList.length; i++) {
					if ($scope.sectionCList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionCList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionDList.length; i++) {
					if ($scope.sectionDList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionDList[i].weightage);
				}
			}

			$scope.getEmployeeKraDetails = function() {
				$scope.kraDetailsId = [];
				$scope.WeightageCount = 0;
// $scope.WeightageCountCheck = 0;
				var input = {
					"empCode" : document
							.getElementById("globalUserName").value,
					"type" : "EmployeeNewKra",
					"appraisalYearId" : $scope.appraisalYearId.id
				};
				var res = $http.post('/PMS/getDetails', input);
				res
						.success(function(data, status) {
							$scope.result = data.object[0];
							$scope.loggedUserKRADetails = data.object[1].object;
							
							if ($scope.result.object === null) {
								$scope.loggedUserAppraisalDetails
										.push({
											"initializationYear" : null,
											"midYear" : null,
											"finalYear" : null
										});
							$scope.WeightageCount = 5;
							document.getElementById("main_body").className = "";
							} else {
								var p = 0;
								var q = 0;
								var r = 0;
								var s = 0;
								$scope.kraDetailsId = [];
								$scope.kraToalCalculation = 0;
								$scope.loggedUserAppraisalDetails = $scope.result.object;
								if($scope.loggedUserKRADetails.length == 0)
									{
									$scope.WeightageCount = 5;
									}
								for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
									if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
										$scope.buttonDisabled = true;
										$scope.WeightageCount = 0;
										break;
									}
								}
// if($scope.loggedUserKRADetails.length == 0)
// {
// $scope.buttonDisabled = false;
// }
								for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
									$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// console.log("kraFileDocumentDownload"+i);
// document.getElementById("kraFileDocumentDownload"+i).style.display =
// 'visible';
// document.getElementById("kraDocumentDownload"+i).style.display = 'none';
									if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
										$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
										if($scope.loggedUserKRADetails[i].weightage !=null)
											{
										$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionAList[p].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
// if ($scope.WeightageCountCheck == 1) {
// $scope.WeightageCount = $scope.WeightageCount - 5;
// $scope.WeightageCountCheck = 0;
// }
										}
										if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
												$scope.sectionAList[p].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
												(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
												70/100)));
											}
										if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
												$scope.sectionAList[p].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
											}
										if ($scope.sectionAList[p].fileName !=null)
										{
										$scope.sectionAList[p].fileDummyName = $scope.sectionAList[p].fileName;
// document.getElementById("kraFileDocumentDownloadSectionA"+p).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionA"+p).style.display =
// 'visible';
										}
										p++;
									} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
										$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionBList[q].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);	
										}
										if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
												$scope.sectionBList[q].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
												(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
												70/100)));
											}
										if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
												$scope.sectionBList[q].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
											}
										if ($scope.sectionBList[q].fileName !=null)
										{
										$scope.sectionBList[q].fileDummyName = $scope.sectionBList[q].fileName;
// document.getElementById("kraFileDocumentDownloadSectionB"+q).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionB"+q).style.display =
// 'visible';
										}
										q++;
									} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
										$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionCList[r].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);
										}
										if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
												$scope.sectionCList[r].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
												(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
												70/100)));
											}
										if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
												$scope.sectionCList[r].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
											}
										if ($scope.sectionCList[r].fileName !=null)
										{
											$scope.sectionCList[r].fileDummyName = $scope.sectionCList[r].fileName;
// document.getElementById("kraFileDocumentDownloadSectionC"+r).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionC"+r).style.display =
// 'visible';
										}
										r++;
									} else {
										$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionDList[s].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);
										}
										if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
												$scope.sectionDList[s].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
												(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
												70/100)));
											}
										if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
												$scope.sectionDList[s].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
											}
										if ($scope.sectionDList[s].fileName !=null)
										{
											$scope.sectionDList[s].fileDummyName = $scope.sectionDList[s].fileName;
// document.getElementById("kraFileDocumentDownloadSectionD"+s).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionD"+s).style.display =
// 'visible';
										}
										s++;
									}
								}
								document.getElementById("main_body").className = "";
							}

						});
			}
			$scope.addNewChoice = function(data,index) {
				if (data === "sectionA") {
					$scope.sectionAList.push({
						'sectionName' : 'Section A',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					});
// $scope.idCreation(data,$scope.sectionAList.length-1);
				}  if (data === "sectionB") {
					$scope.sectionBList.push({
						'sectionName' : 'Section B',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					});
// $scope.idCreation(data,$scope.sectionBList.length-1);
				}  if (data === "sectionC") {
					$scope.sectionCList.push({
						'sectionName' : 'Section C',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					});
// $scope.idCreation(data,$scope.sectionCList.length-1);
				} 
				
			};

// $scope.idCreation = function(data,index)
// {
// if (data === "sectionA") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionA"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionA"+index).style.display
// = 'none';
// }
// if (data === "sectionB") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionB"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionB"+index).style.display
// = 'none';
// }
// if (data === "sectionC") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionC"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionC"+index).style.display
// = 'none';
// }
// }
			
			$scope.removeNewChoice = function(index, data) {
				if (data === "sectionA") {
					$scope.sectionAList.splice(index, 1);
					;
				}  if (data === "sectionB") {
					$scope.sectionBList.splice(index, 1);
					;
				}  if (data === "sectionC") {
					$scope.sectionCList.splice(index, 1);
					;
				}
			};

			$scope.save = function(type) {
				document.getElementById("main_body").className = "loading-pane";
				var formdata = new FormData();
				$scope.fileList = [];
				for (var i = 0; i < $scope.sectionAList.length; i++) {
					if($scope.sectionAList[i].fileDummyName == null && $scope.sectionAList[i].fileName != null)
						{
						$scope.fileList.push($scope.sectionAList[i].fileName);
						$scope.sectionAList[i].fileName = $scope.sectionAList[i].fileName.name;
						}
				}
				for (var i = 0; i < $scope.sectionBList.length; i++) {
					if($scope.sectionBList[i].fileDummyName == null && $scope.sectionBList[i].fileName != null)
						{
						$scope.fileList.push($scope.sectionBList[i].fileName);
						$scope.sectionBList[i].fileName = $scope.sectionBList[i].fileName.name;
						}
				}
				for (var i = 0; i < $scope.sectionCList.length; i++) {
					if($scope.sectionCList[i].fileDummyName == null && $scope.sectionCList[i].fileName != null)
						{
						$scope.fileList.push($scope.sectionCList[i].fileName);
						$scope.sectionCList[i].fileName = $scope.sectionCList[i].fileName.name;
						}
				}
				for (var i = 0; i < $scope.sectionDList.length; i++) {
					if($scope.sectionDList[i].fileDummyName == null && $scope.sectionDList[i].fileName != null)
						{
						$scope.fileList.push($scope.sectionDList[i].fileName);
						$scope.sectionDList[i].fileName = $scope.sectionDList[i].fileName.name;
						}
				}
				formdata.append("kraSections", JSON.stringify($scope.formData));
				formdata.append("type", type);
				formdata.append("list",$scope.kraDetailsId);
				for(var i = 0;i<$scope.fileList.length;i++)
				{
					if( $scope.fileList[i].size != undefined)
						{
				        formdata.append("fileList", $scope.fileList[i]);
						}
				}
				$http.post("/PMS/add-employee-new-kra-details", formdata, {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(
								function(data, status, headers) {
									if (status == 200) {
										swal(data.string);
										var p = 0;
										var q = 0;
										var r = 0;
										var s = 0;
										$scope.kraDetailsId = [];
										$scope.kraToalCalculation = 0;
										$scope.loggedUserKRADetails = data.object;
										if($scope.loggedUserKRADetails.length == 0)
											{
											$scope.WeightageCount = 5;
											}
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
												$scope.WeightageCount = 0;
												break;
											}
										}
// if($scope.loggedUserKRADetails.length == 0)
// {
// $scope.buttonDisabled = false;
// }
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// console.log("kraFileDocumentDownload"+i);
// document.getElementById("kraFileDocumentDownload"+i).style.display =
// 'visible';
// document.getElementById("kraDocumentDownload"+i).style.display = 'none';
											if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
												$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
												if($scope.loggedUserKRADetails[i].weightage !=null)
													{
												$scope.WeightageCount = $scope.WeightageCount
												+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionAList[p].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
// if ($scope.WeightageCountCheck == 1) {
// $scope.WeightageCount = $scope.WeightageCount - 5;
// $scope.WeightageCountCheck = 0;
// }
												}
												if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
														$scope.sectionAList[p].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
														70/100)));
													}
												if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
														$scope.sectionAList[p].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
													}
												if ($scope.sectionAList[p].fileName !=null)
												{
												$scope.sectionAList[p].fileDummyName = $scope.sectionAList[p].fileName;
// document.getElementById("kraFileDocumentDownloadSectionA"+p).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionA"+p).style.display =
// 'visible';
												}
												p++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
												$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionBList[q].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);	
												}
												if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
														$scope.sectionBList[q].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
														70/100)));
													}
												if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
														$scope.sectionBList[q].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
													}
												if ($scope.sectionBList[q].fileName !=null)
												{
												$scope.sectionBList[q].fileDummyName = $scope.sectionBList[q].fileName;
// document.getElementById("kraFileDocumentDownloadSectionB"+q).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionB"+q).style.display =
// 'visible';
												}
												q++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
												$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionCList[r].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
												if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
														$scope.sectionCList[r].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
														70/100)));
													}
												if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
														$scope.sectionCList[r].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
													}
												if ($scope.sectionCList[r].fileName !=null)
												{
													$scope.sectionCList[r].fileDummyName = $scope.sectionCList[r].fileName;
// document.getElementById("kraFileDocumentDownloadSectionC"+r).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionC"+r).style.display =
// 'visible';
												}
												r++;
											} else {
												$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionDList[s].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
												if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
														$scope.sectionDList[s].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
														70/100)));
													}
												if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
														$scope.sectionDList[s].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
													}
												if ($scope.sectionDList[s].fileName !=null)
												{
													$scope.sectionDList[s].fileDummyName = $scope.sectionDList[s].fileName;
// document.getElementById("kraFileDocumentDownloadSectionD"+s).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionD"+s).style.display =
// 'visible';
												}
												s++;
											}
										}
										
										document.getElementById("main_body").className = "";
										
										
										
										
										
										
										
										
										
										
// for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
// if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
// $scope.buttonDisabled = true;
// break;
// }
// }
									}
				});
// var input = {
// "kraDetails" : $scope.formData,
// "list" : $scope.kraDetailsId,
// "type" : type,
// };
// var res = $http.post('/PMS/add-employee-kra-details',
// input);
// res
// .success(function(data, status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// swal($scope.result.string);
// $scope.getEmployeeKraDetails();
// }
//
// });
			}
			$scope.submit = function(type) {
				swal({
					title : "Are you sure?",
					icon : "warning",
					buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
					dangerMode : true,
				})
						.then(
								function(isConfirm) {
									if (isConfirm) {
										$scope.checkValidation = true;
										if ($scope.checkValidation) {
											$scope.totalWeightage = 0;
											$scope.sectionDWeightage = 0;
											if ($scope.loggedUserAppraisalDetails[0].midYear === null) {
												for (var i = 0; i < $scope.sectionAList.length; i++) {
													if ($scope.sectionAList[i].smartGoal !== null
															&& $scope.sectionAList[i].smartGoal != "") {
														if ($scope.sectionAList[i].target === null
																|| $scope.sectionAList[i].achievementDate === null
																|| $scope.sectionAList[i].weightage === null
																|| $scope.sectionAList[i].target === ""
																|| $scope.sectionAList[i].achievementDate === ""
																|| $scope.sectionAList[i].weightage === "") {
															swal("Please fill all the field in Section A.");
															$scope.checkValidation = false;
															break;
														}
													}

												}
											}
// if ($scope.loggedUserAppraisalDetails.midYear > 0) {
// for (var i = 0; i < $scope.sectionAList.length; i++) {
// if ($scope.sectionAList[i].smartGoal !== null
// && $scope.sectionAList[i].smartGoal != "") {
// if ($scope.sectionAList[i].target === null
// || $scope.sectionAList[i].achievementDate === null
// || $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].target === ""
// || $scope.sectionAList[i].achievementDate === ""
// || $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// ) {
// swal("Please fill all the field in Section A for mid Term");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
											if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
												for (var i = 0; i < $scope.sectionAList.length; i++) {
													if ($scope.sectionAList[i].smartGoal !== null
															&& $scope.sectionAList[i].smartGoal != "") {
														if ($scope.sectionAList[i].target === null
																|| $scope.sectionAList[i].achievementDate === null
																|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAssessmentRemarks === null
																|| $scope.sectionAList[i].finalYearAchievement === null
																|| $scope.sectionAList[i].finalYearSelfRating === null
																|| $scope.sectionAList[i].target === ""
																|| $scope.sectionAList[i].achievementDate === ""
																|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAssessmentRemarks === ""
																|| $scope.sectionAList[i].finalYearAchievement === ""
																|| $scope.sectionAList[i].finalYearSelfRating === ""
																) {

															swal("Please fill all the field in Section A for Final Term");
															$scope.checkValidation = false;
															break;
														}
													}
												}
											}
										}
										if ($scope.checkValidation) {
											if ($scope.loggedUserAppraisalDetails[0].midYear === null) {
												for (var i = 0; i < $scope.sectionBList.length; i++) {
													if ($scope.sectionBList[i].smartGoal !== null
															&& $scope.sectionBList[i].smartGoal != "") {
														if ($scope.sectionBList[i].target === null
																|| $scope.sectionBList[i].achievementDate === null
																|| $scope.sectionBList[i].weightage === null
																|| $scope.sectionBList[i].target === ""
																|| $scope.sectionBList[i].achievementDate === ""
																|| $scope.sectionBList[i].weightage === "") {
															swal("Please fill all the field in Section B");
															$scope.checkValidation = false;
															break;
														}
													}

												}
											}
// if ($scope.loggedUserAppraisalDetails.midYear > 0) {
// for (var i = 0; i < $scope.sectionBList.length; i++) {
// if ($scope.sectionBList[i].smartGoal !== null
// && $scope.sectionBList[i].smartGoal != "") {
// if ($scope.sectionBList[i].target === null
// || $scope.sectionBList[i].achievementDate === null
// || $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].target === ""
// || $scope.sectionBList[i].achievementDate === ""
// || $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// ) {
// swal("Please fill all the field in Section B for mid Term");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
											if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
												for (var i = 0; i < $scope.sectionBList.length; i++) {
													if ($scope.sectionBList[i].smartGoal !== null
															&& $scope.sectionBList[i].smartGoal != "") {
														if ($scope.sectionBList[i].target === null
																|| $scope.sectionBList[i].achievementDate === null
																|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAssessmentRemarks === null
																|| $scope.sectionBList[i].finalYearAchievement === null
																|| $scope.sectionBList[i].finalYearSelfRating === null
																|| $scope.sectionBList[i].target === ""
																|| $scope.sectionBList[i].achievementDate === ""
																|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAssessmentRemarks === ""
																|| $scope.sectionBList[i].finalYearAchievement === ""
																|| $scope.sectionBList[i].finalYearSelfRating === ""
																) {
															swal("Please fill all the field in Section B for Final Term");
															$scope.checkValidation = false;
															break;
														}
													}

												}
											}
										}
										if ($scope.checkValidation) {
											if ($scope.loggedUserAppraisalDetails[0].midYear === null) {
												for (var i = 0; i < $scope.sectionCList.length; i++) {
													if ($scope.sectionCList[i].smartGoal !== null
															&& $scope.sectionCList[i].smartGoal != "") {
														if ($scope.sectionCList[i].target === null
																|| $scope.sectionCList[i].achievementDate === null
																|| $scope.sectionCList[i].weightage === null
																|| $scope.sectionCList[i].target === ""
																|| $scope.sectionCList[i].achievementDate === ""
																|| $scope.sectionCList[i].weightage === "") {
															swal("Please fill all the field in Section C");
															$scope.checkValidation = false;
															break;
														}
													}

												}
											}
// if ($scope.loggedUserAppraisalDetails.midYear > 0) {
// for (var i = 0; i < $scope.sectionCList.length; i++) {
// if ($scope.sectionCList[i].smartGoal !== null
// && $scope.sectionCList[i].smartGoal != "") {
// if ($scope.sectionCList[i].target === null
// || $scope.sectionCList[i].achievementDate === null
// || $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].target === ""
// || $scope.sectionCList[i].achievementDate === ""
// || $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// ) {
// swal("Please fill all the field in Section C for mid Term");
// $scope.checkValidation = false;
// break;
// }
// }
//
// }
// }
											if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
												for (var i = 0; i < $scope.sectionCList.length; i++) {
													if ($scope.sectionCList[i].smartGoal !== null
															&& $scope.sectionCList[i].smartGoal != "") {
														if ($scope.sectionCList[i].target === null
																|| $scope.sectionCList[i].achievementDate === null
																|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAssessmentRemarks === null
																|| $scope.sectionCList[i].finalYearAchievement === null
																|| $scope.sectionCList[i].finalYearSelfRating === null
																|| $scope.sectionCList[i].target === ""
																|| $scope.sectionCList[i].achievementDate === ""
																|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAssessmentRemarks === ""
																|| $scope.sectionCList[i].finalYearAchievement === ""
																|| $scope.sectionCList[i].finalYearSelfRating === "") {
															swal("Please fill all the field in Section C for Final Term");
															$scope.checkValidation = false;
															break;
														}
													}

												}
											}
										}
										if ($scope.checkValidation) {
											if ($scope.loggedUserAppraisalDetails[0].midYear === null) {
												for (var i = 0; i < $scope.sectionDList.length; i++) {
													if ($scope.sectionDList[i].smartGoal !== null
															&& $scope.sectionDList[i].smartGoal != "") {
														if ($scope.sectionDList[i].target === null
																|| $scope.sectionDList[i].achievementDate === null
																|| $scope.sectionDList[i].weightage === null
																|| $scope.sectionDList[i].target === ""
																|| $scope.sectionDList[i].achievementDate === ""
																|| $scope.sectionDList[i].weightage === "") {
															swal("Please fill all the field in Section D");
															$scope.checkValidation = false;
															break;
														}
													}
												}
											}
// if ($scope.loggedUserAppraisalDetails.midYear > 0) {
// for (var i = 0; i < $scope.sectionDList.length; i++) {
// if ($scope.sectionDList[i].smartGoal !== null
// && $scope.sectionDList[i].smartGoal != "") {
// if ($scope.sectionDList[i].target === null
// || $scope.sectionDList[i].achievementDate === null
// || $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].target === ""
// || $scope.sectionDList[i].achievementDate === ""
// || $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// ) {
// swal("Please fill all the field in Section D for mid Term");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
											if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
												for (var i = 0; i < $scope.sectionDList.length; i++) {
													if ($scope.sectionDList[i].smartGoal !== null
															&& $scope.sectionDList[i].smartGoal != "") {
														if ($scope.sectionDList[i].target === null
																|| $scope.sectionDList[i].achievementDate === null
																|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAssessmentRemarks === null
																|| $scope.sectionDList[i].finalYearAchievement === null
																|| $scope.sectionDList[i].finalYearSelfRating === null
																|| $scope.sectionDList[i].target === ""
																|| $scope.sectionDList[i].achievementDate === ""
																|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAssessmentRemarks === ""
																|| $scope.sectionDList[i].finalYearAchievement === ""
																|| $scope.sectionDList[i].finalYearSelfRating === "") {
															swal("Please fill all the field in Section D for Final Term");
															$scope.checkValidation = false;
															break;
														}
													}
												}
											}
										}
										if ($scope.checkValidation) {
											if ($scope.WeightageCount > 100
													|| $scope.WeightageCount < 100) {
												swal("Required weightage count is 100%");
											}
											if ($scope.WeightageCount == 100) {
												$scope.save(type);
												
											}
										}
									}
								});
			}

		
		});


app
.controller(
		'EmployeeOldKRAController',
		function($window, $scope, $timeout, $http, sharedProperties) {
			logDebug('Enter EmployeeOldKRAController');
			logDebug('Browser language >> ' + window.navigator.language);
			$scope.loggedUserAppraisalDetails = [];
			document.getElementById("appraisalYearID").style.visibility = "visible";
			document.getElementById("main_body").className = "loading-pane";
			$scope.appraisalYearId = JSON.parse(localStorage
					.getItem("appraisalYearId"));
			$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
			$scope.selectsRating = [ 1, 2, 3, 4, 5 ];
			$scope.WeightageCount = 5;
			$scope.fileList = [];
			
			$scope.sectionAList = [ {
				'sectionName' : 'Section A',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionBList = [ {
				'sectionName' : 'Section B',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionCList = [ {
				'sectionName' : 'Section C',
				'smartGoal' : null,
				'target' : null,
				"fileName" :null,
				"fileDummyName":null,
				'achievementDate' : null,
				'weightage' : null,
				'midYearAchievement' : null,
				'midYearSelfRating' : null,
				'midYearAppraisarRating' : null,
				'midYearAssessmentRemarks' : null,
				'finalYearAchievement' : null,
				'finalYearSelfRating' : null,
				'finalYearAppraisarRating' : null,
				'remarks' : null
			} ];
			$scope.sectionDList = [
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalOne").value,
						'target' : document.getElementById("targetOne").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageOne").value,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					},
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalTwo").value,
						'target' : document.getElementById("targetTwo").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageTwo").value,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					},
					{
						'sectionName' : 'Section D',
						'smartGoal' : document
								.getElementById("smartGoalThree").value,
						'target' : document
								.getElementById("targetThree").value,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : document
								.getElementById("weightageThree").value,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					} ];

			$scope.formData = {
				"sectionAList" : $scope.sectionAList,
				"sectionBList" : $scope.sectionBList,
				"sectionCList" : $scope.sectionCList,
				"sectionDList" : $scope.sectionDList
			};
			
			$scope.gotoNewKRA=function()
			{
				$window.location.href = "#/employee-kra";
			}

			$scope.countWeightage = function() {
				$scope.WeightageCount = 0;
				for (var i = 0; i < $scope.sectionAList.length; i++) {
					if ($scope.sectionAList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionAList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionBList.length; i++) {
					if ($scope.sectionBList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionBList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionCList.length; i++) {
					if ($scope.sectionCList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionCList[i].weightage);
				}
				for (var i = 0; i < $scope.sectionDList.length; i++) {
					if ($scope.sectionDList[i].weightage != null)
						$scope.WeightageCount = $scope.WeightageCount
								+ parseInt($scope.sectionDList[i].weightage);
				}
			}

			$scope.getEmployeeOldKraDetails = function() {
				$scope.kraDetailsId = [];
				$scope.WeightageCount = 0;
// $scope.WeightageCountCheck = 0;
				var input = {
					"empCode" : document
							.getElementById("globalUserName").value,
					"type" : "EmployeeOldKra",
					"appraisalYearId" : $scope.appraisalYearId.id
				};
				var res = $http.post('/PMS/getDetails', input);
				res
						.success(function(data, status) {
							$scope.result = data.object[0];
							$scope.loggedUserKRADetails = data.object[1].object;
							if ($scope.result.length == 0) {
								$scope.loggedUserAppraisalDetails
										.push({
											"initializationYear" : null,
											"midYear" : null,
											"finalYear" : null
										});
							$scope.WeightageCount = 5;
							document.getElementById("main_body").className = "";
							} else {
								var p = 0;
								var q = 0;
								var r = 0;
								var s = 0;
								$scope.kraDetailsId = [];
								$scope.kraToalCalculation = 0;
								$scope.loggedUserAppraisalDetails = $scope.result.object;
// if($scope.loggedUserAppraisalDetails != null)
// {
// if ($scope.loggedUserAppraisalDetails.kraForwardCheck == null) {
// $('#kraDataForwardCurrentYear')
// .modal({
// backdrop : 'static',
// keyboard : false
// });
// }
// }
								if($scope.loggedUserKRADetails.length == 0)
									{
									$scope.WeightageCount = 5;
									}
								for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
									if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
										$scope.buttonDisabled = true;
										$scope.WeightageCount = 0;
										break;
									}

								}
// if($scope.loggedUserKRADetails.length == 0)
// {
// // document.getElementById("kraFileDocumentDownloadSectionA0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionA0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionB0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionB0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionC0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionC0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionD0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionD0").style.display =
// 'none';
// }
								for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
									$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// console.log("kraFileDocumentDownload"+i);
// document.getElementById("kraFileDocumentDownload"+i).style.display =
// 'visible';
// document.getElementById("kraDocumentDownload"+i).style.display = 'none';
									if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
										$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
										if($scope.loggedUserKRADetails[i].weightage !=null)
											{
										$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionAList[p].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
// if ($scope.WeightageCountCheck == 1) {
// $scope.WeightageCount = $scope.WeightageCount - 5;
// $scope.WeightageCountCheck = 0;
// }
										}
										if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
												$scope.sectionAList[p].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
												(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
												70/100)));
											}
										if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
												$scope.sectionAList[p].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
											}
										if ($scope.sectionAList[p].fileName !=null)
										{
										$scope.sectionAList[p].fileDummyName = $scope.sectionAList[p].fileName;
// document.getElementById("kraFileDocumentDownloadSectionA"+p).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionA"+p).style.display =
// 'visible';
										}
										p++;
									} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
										$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionBList[q].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);	
										}
										if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
												$scope.sectionBList[q].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
												(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
												70/100)));
											}
										if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
												$scope.sectionBList[q].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
											}
										if ($scope.sectionBList[q].fileName !=null)
										{
										$scope.sectionBList[q].fileDummyName = $scope.sectionBList[q].fileName;
// document.getElementById("kraFileDocumentDownloadSectionB"+q).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionB"+q).style.display =
// 'visible';
										}
										q++;
									} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
										$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionCList[r].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);
										}
										if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
												$scope.sectionCList[r].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
												(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
												70/100)));
											}
										if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
												$scope.sectionCList[r].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
											}
										if ($scope.sectionCList[r].fileName !=null)
										{
											$scope.sectionCList[r].fileDummyName = $scope.sectionCList[r].fileName;
// document.getElementById("kraFileDocumentDownloadSectionC"+r).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionC"+r).style.display =
// 'visible';
										}
										r++;
									} else {
										$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
										if ($scope.loggedUserKRADetails[i].achievementDate != null) {
											$scope.sectionDList[s].achievementDate = new Date(
													$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
										}
										if($scope.loggedUserKRADetails[i].weightage !=null)
										{
											$scope.WeightageCount = $scope.WeightageCount
											+ parseInt($scope.loggedUserKRADetails[i].weightage);
										}
										if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
												$scope.sectionDList[s].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
												(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
												70/100)));
											}
										if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
												$scope.sectionDList[s].weightage !=null)
											{
										$scope.kraToalCalculation = $scope.kraToalCalculation + (
												(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
											}
										if ($scope.sectionDList[s].fileName !=null)
										{
											$scope.sectionDList[s].fileDummyName = $scope.sectionDList[s].fileName;
// document.getElementById("kraFileDocumentDownloadSectionD"+s).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionD"+s).style.display =
// 'visible';
										}
										s++;
									}
								}
								document.getElementById("main_body").className = "";
							}

						});
			}
			
			
			});


app
.controller(
		'TeamRatingController',
		function($window, $scope, $timeout, $http, sharedProperties) {
			logDebug('Enter TeamRatingController');
			logDebug('Browser language >> ' + window.navigator.language);
			document.getElementById("main_body").className = "loading-pane";
			document.getElementById("appraisalYearID").style.visibility = "hidden";
			$scope.appraisalYearId = JSON.parse(localStorage
					.getItem("appraisalYearId"));
			var input = {
					"appraisalYearId" : $scope.appraisalYearId.id,
					"empCode" : document
							.getElementById("globalUserName").value,
				};
				var res = $http.post('/PMS/teamRating',
						input);
				res.success(function(data, status) {
							if(data.integer == 1)
								{
								$scope.teamOverAllData = data.object;
								for(var i =0;i<$scope.teamOverAllData.length;i++)
									{
									if($scope.teamOverAllData[i].initializationYear == 1 && ($scope.teamOverAllData[i].employeeIsvisible == 1 || $scope.teamOverAllData[i].employeeIsvisible == 0)	
											&& $scope.teamOverAllData[i].firstLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 0)
										{
										$scope.teamOverAllData[i].stage = "In Planning";
										$scope.teamOverAllData[i].subStage = "Goal Setting";
										}
									if(($scope.teamOverAllData[i].initializationYear == 1 && $scope.teamOverAllData[i].employeeIsvisible == 0	 && $scope.teamOverAllData[i].acknowledgementCheck == 1
											&& ($scope.teamOverAllData[i].firstLevelIsvisible == 1 || $scope.teamOverAllData[i].firstLevelIsvisible == 0 || $scope.teamOverAllData[i].secondLevelIsvisible == 1) && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 0))
										{
										$scope.teamOverAllData[i].stage = "In Planning";
										$scope.teamOverAllData[i].subStage = "Goal Approval";
										}
									if($scope.teamOverAllData[i].initializationYear == 1 && $scope.teamOverAllData[i].employeeIsvisible == 0	
											&& $scope.teamOverAllData[i].firstLevelIsvisible == 0 &&  $scope.teamOverAllData[i].secondLevelIsvisible == 0  && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 1)
										{
										$scope.teamOverAllData[i].stage  = "In Review";
										$scope.teamOverAllData[i].subStage = "Mid Year Review";
										}
									if($scope.teamOverAllData[i].midYear == 1 && ($scope.teamOverAllData[i].employeeIsvisible == 1 ||  $scope.teamOverAllData[i].firstLevelIsvisible == 1 || $scope.teamOverAllData[i].secondLevelIsvisible == 1) && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 0)
										{
										$scope.teamOverAllData[i].stage  = "In Review";
										$scope.teamOverAllData[i].subStage = "Mid Year Review";
										}
									if($scope.teamOverAllData[i].midYear == 1 && $scope.teamOverAllData[i].employeeIsvisible == 0 &&  $scope.teamOverAllData[i].firstLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 1)
									{
										$scope.teamOverAllData[i].stage  = "In Process";
									$scope.teamOverAllData[i].subStage = "Year End Assessment";
									}
									if($scope.teamOverAllData[i].finalYear == 1 && ($scope.teamOverAllData[i].employeeIsvisible == 1 ||  $scope.teamOverAllData[i].firstLevelIsvisible == 1 || $scope.teamOverAllData[i].secondLevelIsvisible == 1) && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 0)
									{
										$scope.teamOverAllData[i].stage  = "In Process";
									$scope.teamOverAllData[i].subStage = "Year End Assessment";
									}
									if($scope.teamOverAllData[i].finalYear == 1 && $scope.teamOverAllData[i].employeeIsvisible == 0 &&  $scope.teamOverAllData[i].firstLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisible == 0 && $scope.teamOverAllData[i].secondLevelIsvisibleCheck == 1)
									{
									$scope.teamOverAllData[i].stage  = "In Process";
									$scope.teamOverAllData[i].subStage = "Assessment approval";
									}
		}
								$(document).ready(function() {									
								// Setup - add a text input to each footer cell
							    $('#tblEmployeeOverAllDetails tfoot th').each( function () {
							        var title = $(this).text();
							        if(this.cellIndex == 0 )
							        	{
							        	$(this).html( '<input type="text" style="max-width: 35px;" placeholder="'+title+'" />' );
							        	}
							        else if(this.cellIndex == 1 || this.cellIndex == 9 || this.cellIndex == 10)
						        	{
						        	$(this).html( '<input type="text" style="max-width: 55px;" placeholder="'+title+'" />' );
						        	}
							        else if(this.cellIndex == 15)
						        	{
						        	$(this).html( '<input type="text" style="max-width: 85px;" placeholder="'+title+'" />' );
						        	}
							        else{
							        	$(this).html( '<input type="text"  placeholder="'+title+'" />' );
							        }
							    } );
							 
							    // DataTable
							    var table = $('#tblEmployeeOverAllDetails').DataTable({
							        dom: 'Bfrtip',
							        scrollX: true,
							    	scrollY: '300px',
							    	scrollCollapse: true,
							    	fixedColumns:{
							    		leftColumns:3
							    	},
							        
							        buttons: [
							            'csv', 'excel'
							        ]
							    } );
							 
							    // Apply the search
							    table.columns().every( function () {
							        var that = this;
							 
							        $( 'input', this.footer() ).on( 'keyup change', function () {
							            if ( that.search() !== this.value ) {
							                that
							                    .search( this.value )
							                    .draw();
							            }
							        } );
							    } );
								});
								document.getElementById("main_body").className = "";
		}
		});
				
				
				
				$scope.employeefilter=function(item)
				{
					return item.status === '0';
				}
			
		});


app
		.controller(
				'OverAllRatingController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter OverAllRatingController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.appraisalYearId = JSON.parse(localStorage.getItem("appraisalYearId"));
					$scope.overAllEmployeeRatingData;
					$scope.employeeMidYearApprovalFun = function()
					{
						var input = {
								"appraisalYearId" : $scope.appraisalYearId.id,
								"empCode" : document
										.getElementById("globalUserName").value,
								"id" : $scope.loggedUserAppraisalDetails.id,
								"type" :"MID YEAR"
							};
							var res = $http.post('/PMS/employeeApprovalProcess',
									input);
							res.success(function(data, status) {
										$scope.employeeApprovalResult = data;
										swal(data.string);
										if($scope.employeeApprovalResult.integer == 1)
											{
											$scope.loggedUserAppraisalDetails = data.object;
											if($scope.loggedUserAppraisalDetails.employeeMidYearApproval == 1)
												{
												$scope.isEmployeeMidYearApproval = 1;
												$scope.employeeMidYearApproval = true;
												$scope.isEmployeeMidYearApprovalSubmit = true;
												}
											}
									});
					}
					$scope.employeeApprovalFun = function()
					{
						var input = {
								"appraisalYearId" : $scope.appraisalYearId.id,
								"empCode" : document
										.getElementById("globalUserName").value,
								"id" : $scope.loggedUserAppraisalDetails.id,
								"type" :"FINAL YEAR",
								"kraTotalCalculation":document.getElementById("kraTotalCalculation").value,
								"extraOrdinaryTotalCalculation":document.getElementById("extraOrdinaryTotalCalculation").value,
								"behaviouralCompetenceTotalCalculation":document.getElementById("behaviouralCompetenceTotalCalculation").value	
							};
							var res = $http.post('/PMS/employeeApprovalProcess',
									input);
							res.success(function(data, status) {
										$scope.employeeApprovalResult = data;
										swal(data.string);
										if($scope.employeeApprovalResult.integer == 1)
											{
											$scope.loggedUserAppraisalDetails = data.object;
											if($scope.loggedUserAppraisalDetails.employeeApproval == 1)
												{
												$scope.isEmployeeApproval = 1;
												$scope.employeeApproval = true;
												}
											
											}
									});
						
						
					}
					
					
					
					$scope.getEmployeeOverAllRatingData = function() {
						
						var input = {
								"appraisalYearId" : $scope.appraisalYearId.id,
								"empCode" : document
										.getElementById("globalUserName").value
							};
							var res = $http.post('/PMS/getDetailsOverAllRating',
									input);
							res
									.success(function(data, status) {
										$scope.loggedUserAppraisalDetails = data.object;
										if($scope.loggedUserAppraisalDetails !=null)
											{
										if($scope.loggedUserAppraisalDetails.employeeApproval == 1)
										{
										$scope.isEmployeeApproval = 1;
										$scope.employeeApproval = true;
										}
										if($scope.loggedUserAppraisalDetails.employeeMidYearApproval == 1)
										{
										$scope.isEmployeeMidYearApprovalSubmit = true;
										$scope.employeeMidYearApproval = true;
										}
									}
						var input = {
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : document
									.getElementById("globalUserName").value
						};
						var res = $http.post('/PMS/getEmployeeOverAllRating',
								input);
						res
								.success(function(data, status) {
									$scope.employeeOverAllRatingData = data.object;
									// $scope.kraSectionACalculationAverageCount
									// = 0 ;
									$scope.kraSectionACalculationAverage = 0;
									$scope.kraSectionBCalculationAverage = 0;
									$scope.kraSectionCCalculationAverage = 0;
									$scope.kraSectionDCalculationAverage = 0;
									$scope.sectionAWeightage = 0;
									$scope.sectionBWeightage = 0;
									$scope.sectionCWeightage = 0;
									$scope.sectionDWeightage = 0;
									$scope.employeeOverAllKRARatingData = [ {
										"sectionName" : null,
										"sectionAWeightage" : 0,
										"kraSectionACalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionBWeightage" : 0,
										"kraSectionBCalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionCWeightage" : 0,
										"kraSectionCCalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionDWeightage" : 0,
										"kraSectionDCalculationAverage" : 0
									} ];
									$scope.employeeExtraOrdinaryWeightage = 0;
									$scope.employeeExtraOrdinaryTotalWeightageRating = 0;
									$scope.employeeOverAllExtraOrdinaryRatingData = [];
									$scope.BCWeightage = 0;
									$scope.BCCalculationAverage = 0;
									$scope.employeeOverAllKRARatingDataTotalWeightage = 0;
									$scope.employeeOverAllBCRatingData = [ {
										"BCWeightage" : 0,
										"BCCalculationAverage" : 0
									} ];
									if ($scope.employeeOverAllRatingData[0].length > 0) {
										
										for (var i = 0; i < $scope.employeeOverAllRatingData[0].length; i++) {
											if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section A" && $scope.employeeOverAllRatingData[0][i].weightage !=null) {
												$scope.sectionAWeightage = $scope.sectionAWeightage
														+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
													{
												$scope.kraSectionACalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
														+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
													}
												else
													{
													$scope.kraSectionACalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);
											    	}
												$scope.kraSectionACalculationAB = $scope.kraSectionACalculationB
														* parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												$scope.kraSectionACalculationAverage = $scope.kraSectionACalculationAverage
														+ $scope.kraSectionACalculationAB;
												$scope.employeeOverAllKRARatingData[0].sectionName = "Section A";
												$scope.employeeOverAllKRARatingData[0].sectionAWeightage = $scope.sectionAWeightage;
												$scope.employeeOverAllKRARatingData[0].kraSectionACalculationAverage = $scope.kraSectionACalculationAverage;
											}
											if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section B" && $scope.employeeOverAllRatingData[0][i].weightage !=null) {
												$scope.sectionBWeightage = $scope.sectionBWeightage
														+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
												{
												$scope.kraSectionBCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
														+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
												}
												else
												{
												$scope.kraSectionBCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);
												}
												$scope.kraSectionBCalculationAB = $scope.kraSectionBCalculationB
														* parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												$scope.kraSectionBCalculationAverage = $scope.kraSectionBCalculationAverage
														+ $scope.kraSectionBCalculationAB;
												$scope.employeeOverAllKRARatingData[1].sectionName = "Section B";
												$scope.employeeOverAllKRARatingData[1].sectionBWeightage = $scope.sectionBWeightage;
												$scope.employeeOverAllKRARatingData[1].kraSectionBCalculationAverage = $scope.kraSectionBCalculationAverage;
											}
											if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section C" && $scope.employeeOverAllRatingData[0][i].weightage !=null) {
												$scope.sectionCWeightage = $scope.sectionCWeightage
														+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
												{
												$scope.kraSectionCCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
														+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
												}
												else{
													$scope.kraSectionCCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);	
												}
												$scope.kraSectionCCalculationAB = $scope.kraSectionCCalculationB
														* parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												$scope.kraSectionCCalculationAverage = $scope.kraSectionCCalculationAverage
														+ $scope.kraSectionCCalculationAB;
												$scope.employeeOverAllKRARatingData[2].sectionName = "Section C";
												$scope.employeeOverAllKRARatingData[2].sectionCWeightage = $scope.sectionCWeightage;
												$scope.employeeOverAllKRARatingData[2].kraSectionCCalculationAverage = $scope.kraSectionCCalculationAverage;
											}
											if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section D" && $scope.employeeOverAllRatingData[0][i].weightage !=null) {
												$scope.sectionDWeightage = $scope.sectionDWeightage
														+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
												{
												$scope.kraSectionDCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
														+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
												}else{
													$scope.kraSectionDCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);	
												}
												$scope.kraSectionDCalculationAB = $scope.kraSectionDCalculationB
														* parseInt($scope.employeeOverAllRatingData[0][i].weightage);
												$scope.kraSectionDCalculationAverage = $scope.kraSectionDCalculationAverage
														+ $scope.kraSectionDCalculationAB;
												$scope.employeeOverAllKRARatingData[3].sectionName = "Section D";
												$scope.employeeOverAllKRARatingData[3].sectionDWeightage = $scope.sectionDWeightage;
												$scope.employeeOverAllKRARatingData[3].kraSectionDCalculationAverage = $scope.kraSectionDCalculationAverage;
											}
										}
									}
									

// if ($scope.employeeOverAllKRARatingData[0].length > 0) {
									for (var i = 0; i < $scope.employeeOverAllKRARatingData.length; i++) {
// && $scope.employeeOverAllRatingData[0][i].weightage != null
										if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section A"
												) {
											$scope.employeeOverAllKRARatingData[i].averageRatingSectionA = $scope.employeeOverAllKRARatingData[i].kraSectionACalculationAverage
													/ $scope.employeeOverAllKRARatingData[i].sectionAWeightage;
											$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingData[i].sectionAWeightage;
										}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
										if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section B"
												) {
											$scope.employeeOverAllKRARatingData[i].averageRatingSectionB = $scope.employeeOverAllKRARatingData[i].kraSectionBCalculationAverage
													/ $scope.employeeOverAllKRARatingData[i].sectionBWeightage;
											$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
													+ $scope.employeeOverAllKRARatingData[i].sectionBWeightage;
										}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
										if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section C"
												) {
											$scope.employeeOverAllKRARatingData[i].averageRatingSectionC = $scope.employeeOverAllKRARatingData[i].kraSectionCCalculationAverage
													/ $scope.employeeOverAllKRARatingData[i].sectionCWeightage;
											$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
													+ $scope.employeeOverAllKRARatingData[i].sectionCWeightage;
										}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
										if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section D"
												) {
											$scope.employeeOverAllKRARatingData[i].averageRatingSectionD = $scope.employeeOverAllKRARatingData[i].kraSectionDCalculationAverage
													/ $scope.employeeOverAllKRARatingData[i].sectionDWeightage;
											$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
													+ $scope.employeeOverAllKRARatingData[i].sectionDWeightage;
										}
									}
// }
								$scope.averageKraRating = 0;
								for (var i = 0; i < $scope.employeeOverAllKRARatingData.length; i++) {
									if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionA != null) {
										$scope.averageKraRating = $scope.averageKraRating
												+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionA * $scope.employeeOverAllKRARatingData[i].sectionAWeightage);
									}
									if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionB != null) {
										$scope.averageKraRating = $scope.averageKraRating
												+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionB * $scope.employeeOverAllKRARatingData[i].sectionBWeightage);
									}
									if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionC != null) {
										$scope.averageKraRating = $scope.averageKraRating
												+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionC * $scope.employeeOverAllKRARatingData[i].sectionCWeightage);
									}
									if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionD != null) {
										$scope.averageKraRating = $scope.averageKraRating
												+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionD * $scope.employeeOverAllKRARatingData[i].sectionDWeightage);
									}
								}

									if ($scope.employeeOverAllRatingData[1].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllRatingData[1].length; i++) {
											$scope.employeeOverAllExtraOrdinaryRatingData
													.push({
														"weightedRating" : 0,
														"totalWeighted" : 0,
														"totalWeightageRating" : 0
													});
											$scope.employeeOverAllExtraOrdinaryRatingData[i].weightedRating = (parseInt($scope.employeeOverAllRatingData[1][i].finalYearAppraisarRating)
													* $scope.employeeOverAllRatingData[1][i].weightage)*10/100;
											$scope.employeeExtraOrdinaryWeightage = $scope.employeeExtraOrdinaryWeightage
													+ parseInt($scope.employeeOverAllRatingData[1][i].weightage);
											$scope.employeeOverAllExtraOrdinaryRatingData[0].totalWeighted = $scope.employeeExtraOrdinaryWeightage;
											$scope.employeeExtraOrdinaryTotalWeightageRating = $scope.employeeExtraOrdinaryTotalWeightageRating
													+ $scope.employeeOverAllExtraOrdinaryRatingData[i].weightedRating;
											$scope.employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating = $scope.employeeExtraOrdinaryTotalWeightageRating;
										}
									}
									if ($scope.employeeOverAllRatingData[2].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllRatingData[2].length; i++) {
											$scope.BCWeightage = $scope.BCWeightage
													+ parseInt($scope.employeeOverAllRatingData[2][i].weightage);
											if($scope.employeeOverAllRatingData[2][i].midYearAssessorRating !=null)
											{
											$scope.BCCalculationB = ($scope.employeeOverAllRatingData[2][i].midYearAssessorRating * 30 / 100)
													+ ($scope.employeeOverAllRatingData[2][i].finalYearAssessorRating * 70 / 100);
											}else{
												$scope.BCCalculationB = ($scope.employeeOverAllRatingData[2][i].finalYearAssessorRating);
											}
											$scope.BCCalculationAB = $scope.BCCalculationB
													* parseInt($scope.employeeOverAllRatingData[2][i].weightage);
											$scope.BCCalculationAverage = $scope.BCCalculationAverage
													+ $scope.BCCalculationAB;
											$scope.employeeOverAllBCRatingData[0].BCWeightage = $scope.BCWeightage;
											$scope.employeeOverAllBCRatingData[0].BCCalculationAverage = $scope.BCCalculationAverage;
										}
									}
								});
									});
					}

				});

app
		.controller(
				'TeamOverAllRatingController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter TeamOverAllRatingController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.employeeLevel = [ "FIRST LEVEL EMPLOYEE",
							"SECOND LEVEL EMPLOYEE" ];
					$scope.getEmployeeLists = function() {
						var input = {
							"type" : $scope.levelType,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : document
									.getElementById("globalUserName").value
						};
						var res = $http.post(
								'/PMS/get-overAllRating-subEmployeeList',
								input);
						res.success(function(data, status) {
							$scope.subEmployeeList = data.object;
							$scope.isEmployeeTeamApproval = 0;
							$scope.employeeTeamApproval = false;
						});
					}
					$scope.overAllEmployeeRatingData;
					$scope.getEmployeeOverAllRatingData = function() {
						
						if($scope.subEmployeeCode !=undefined)
							{
						var input = {
								"appraisalYearId" : $scope.appraisalYearId.id,
								"empCode" : $scope.subEmployeeCode.empCode
							};
							var res = $http.post('/PMS/getDetailsOverAllRating',
									input);
							res
									.success(function(data, status) {
										$scope.loggedUserAppraisalDetails = data.object;
										if($scope.loggedUserAppraisalDetails.employeeApproval == 1)
										{
										$scope.isEmployeeTeamApproval = 1;
										$scope.employeeTeamApproval = true;
										}
						
						var input = {
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : $scope.subEmployeeCode.empCode
						};
						var res = $http.post('/PMS/getEmployeeOverAllRating',
								input);
						res
								.success(function(data, status) {
									$scope.employeeOverAllRatingData = data.object;
									$scope.kraSectionACalculationAverage = 0;
									$scope.kraSectionBCalculationAverage = 0;
									$scope.kraSectionCCalculationAverage = 0;
									$scope.kraSectionDCalculationAverage = 0;
									$scope.sectionAWeightage = 0;
									$scope.sectionBWeightage = 0;
									$scope.sectionCWeightage = 0;
									$scope.sectionDWeightage = 0;
									$scope.employeeOverAllKRARatingData = [ {
										"sectionName" : null,
										"sectionAWeightage" : 0,
										"kraSectionACalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionBWeightage" : 0,
										"kraSectionBCalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionCWeightage" : 0,
										"kraSectionCCalculationAverage" : 0
									}, {
										"sectionName" : null,
										"sectionDWeightage" : 0,
										"kraSectionDCalculationAverage" : 0
									} ];
									$scope.employeeExtraOrdinaryWeightage = 0;
									$scope.employeeExtraOrdinaryTotalWeightageRating = 0;
									$scope.employeeOverAllExtraOrdinaryRatingData = [];
									$scope.BCWeightage = 0;
									$scope.BCCalculationAverage = 0;
									$scope.employeeExtraOrdinaryTotalWeightageRating = 0;
									$scope.employeeOverAllKRARatingDataTotalWeightage = 0;
									$scope.employeeOverAllBCRatingData = [ {
										"BCWeightage" : 0,
										"BCCalculationAverage" : 0
									} ];
									if ($scope.employeeOverAllRatingData[0].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllRatingData[0].length; i++) {
											if ($scope.employeeOverAllRatingData[0][i].weightage != null) {
												if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section A" ) {
													$scope.sectionAWeightage = $scope.sectionAWeightage
															+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
													if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
													{
													$scope.kraSectionACalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
															+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
													}else{
														$scope.kraSectionACalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);
													}
													$scope.kraSectionACalculationAB = ($scope.kraSectionACalculationB
															* parseInt($scope.employeeOverAllRatingData[0][i].weightage));
													$scope.kraSectionACalculationAverage = $scope.kraSectionACalculationAverage
															+ $scope.kraSectionACalculationAB;
													$scope.employeeOverAllKRARatingData[0].sectionName = "Section A";
													$scope.employeeOverAllKRARatingData[0].sectionAWeightage = $scope.sectionAWeightage;
													$scope.employeeOverAllKRARatingData[0].kraSectionACalculationAverage = $scope.kraSectionACalculationAverage;
												}
												if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section B" ) {
													$scope.sectionBWeightage = $scope.sectionBWeightage
															+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
													if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
													{
													$scope.kraSectionBCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
															+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
													}else{
														$scope.kraSectionBCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);	
													}
													$scope.kraSectionBCalculationAB = ($scope.kraSectionBCalculationB
															* parseInt($scope.employeeOverAllRatingData[0][i].weightage));
													$scope.kraSectionBCalculationAverage = $scope.kraSectionBCalculationAverage
															+ $scope.kraSectionBCalculationAB;
													$scope.employeeOverAllKRARatingData[1].sectionName = "Section B";
													$scope.employeeOverAllKRARatingData[1].sectionBWeightage = $scope.sectionBWeightage;
													$scope.employeeOverAllKRARatingData[1].kraSectionBCalculationAverage = $scope.kraSectionBCalculationAverage;
												}
												if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section C" ) {
													$scope.sectionCWeightage = $scope.sectionCWeightage
															+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
													if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
													{
													$scope.kraSectionCCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
															+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
													}else{
														$scope.kraSectionCCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);
													}
													$scope.kraSectionCCalculationAB = ($scope.kraSectionCCalculationB
															* parseInt($scope.employeeOverAllRatingData[0][i].weightage));
													$scope.kraSectionCCalculationAverage = $scope.kraSectionCCalculationAverage
															+ $scope.kraSectionCCalculationAB;
													$scope.employeeOverAllKRARatingData[2].sectionName = "Section C";
													$scope.employeeOverAllKRARatingData[2].sectionCWeightage = $scope.sectionCWeightage;
													$scope.employeeOverAllKRARatingData[2].kraSectionCCalculationAverage = $scope.kraSectionCCalculationAverage;
												}
												if ($scope.employeeOverAllRatingData[0][i].sectionName === "Section D" ) {
													$scope.sectionDWeightage = $scope.sectionDWeightage
															+ parseInt($scope.employeeOverAllRatingData[0][i].weightage);
													if($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating !=null)
													{
													$scope.kraSectionDCalculationB = ($scope.employeeOverAllRatingData[0][i].midYearAppraisarRating * 30 / 100)
															+ ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating * 70 / 100);
													}else{
														$scope.kraSectionDCalculationB = ($scope.employeeOverAllRatingData[0][i].finalYearAppraisarRating);
													}
													$scope.kraSectionDCalculationAB = ($scope.kraSectionDCalculationB
															* parseInt($scope.employeeOverAllRatingData[0][i].weightage));
													$scope.kraSectionDCalculationAverage = $scope.kraSectionDCalculationAverage
															+ $scope.kraSectionDCalculationAB;
													$scope.employeeOverAllKRARatingData[3].sectionName = "Section D";
													$scope.employeeOverAllKRARatingData[3].sectionDWeightage = $scope.sectionDWeightage;
													$scope.employeeOverAllKRARatingData[3].kraSectionDCalculationAverage = $scope.kraSectionDCalculationAverage;
												}
											}
										}
									}
// if ($scope.employeeOverAllRatingData[0].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllKRARatingData.length; i++) {
// && $scope.employeeOverAllRatingData[0][i].weightage != null
											if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section A"
													) {
												$scope.employeeOverAllKRARatingData[i].averageRatingSectionA = $scope.employeeOverAllKRARatingData[i].kraSectionACalculationAverage
														/ $scope.employeeOverAllKRARatingData[i].sectionAWeightage;
												$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingData[i].sectionAWeightage;
											}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
											if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section B"
													) {
												$scope.employeeOverAllKRARatingData[i].averageRatingSectionB = $scope.employeeOverAllKRARatingData[i].kraSectionBCalculationAverage
														/ $scope.employeeOverAllKRARatingData[i].sectionBWeightage;
												$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
														+ $scope.employeeOverAllKRARatingData[i].sectionBWeightage;
											}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
											if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section C"
													) {
												$scope.employeeOverAllKRARatingData[i].averageRatingSectionC = $scope.employeeOverAllKRARatingData[i].kraSectionCCalculationAverage
														/ $scope.employeeOverAllKRARatingData[i].sectionCWeightage;
												$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
														+ $scope.employeeOverAllKRARatingData[i].sectionCWeightage;
											}
// && $scope.employeeOverAllRatingData[0][i].weightage != null
											if ($scope.employeeOverAllKRARatingData[i].sectionName === "Section D"
													) {
												$scope.employeeOverAllKRARatingData[i].averageRatingSectionD = $scope.employeeOverAllKRARatingData[i].kraSectionDCalculationAverage
														/ $scope.employeeOverAllKRARatingData[i].sectionDWeightage;
												$scope.employeeOverAllKRARatingDataTotalWeightage = $scope.employeeOverAllKRARatingDataTotalWeightage
														+ $scope.employeeOverAllKRARatingData[i].sectionDWeightage;
											}
										}
// }
									$scope.averageKraRating = 0;
									for (var i = 0; i < $scope.employeeOverAllKRARatingData.length; i++) {
										if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionA != null) {
											$scope.averageKraRating = $scope.averageKraRating
													+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionA * $scope.employeeOverAllKRARatingData[i].sectionAWeightage);
										}
										if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionB != null) {
											$scope.averageKraRating = $scope.averageKraRating
													+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionB * $scope.employeeOverAllKRARatingData[i].sectionBWeightage);
										}
										if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionC != null) {
											$scope.averageKraRating = $scope.averageKraRating
													+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionC * $scope.employeeOverAllKRARatingData[i].sectionCWeightage);
										}
										if ($scope.employeeOverAllKRARatingData[i].averageRatingSectionD != null) {
											$scope.averageKraRating = $scope.averageKraRating
													+ ($scope.employeeOverAllKRARatingData[i].averageRatingSectionD * $scope.employeeOverAllKRARatingData[i].sectionDWeightage);
										}
									}

									if ($scope.employeeOverAllRatingData[1].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllRatingData[1].length; i++) {
											$scope.employeeOverAllExtraOrdinaryRatingData
													.push({
														"weightedRating" : 0,
														"totalWeighted" : 0,
														"totalWeightageRating" : 0
													});
											$scope.employeeOverAllExtraOrdinaryRatingData[i].weightedRating = (parseInt($scope.employeeOverAllRatingData[1][i].finalYearAppraisarRating)
													* $scope.employeeOverAllRatingData[1][i].weightage)*10/100;
											$scope.employeeExtraOrdinaryWeightage = $scope.employeeExtraOrdinaryWeightage
													+ parseInt($scope.employeeOverAllRatingData[1][i].weightage);
											$scope.employeeOverAllExtraOrdinaryRatingData[0].totalWeighted = $scope.employeeExtraOrdinaryWeightage;
											$scope.employeeExtraOrdinaryTotalWeightageRating = $scope.employeeExtraOrdinaryTotalWeightageRating
													+ $scope.employeeOverAllExtraOrdinaryRatingData[i].weightedRating;
											$scope.employeeOverAllExtraOrdinaryRatingData[0].totalWeightageRating = $scope.employeeExtraOrdinaryTotalWeightageRating;
											
										}
									}
									if ($scope.employeeOverAllRatingData[2].length > 0) {
										for (var i = 0; i < $scope.employeeOverAllRatingData[2].length; i++) {
											$scope.BCWeightage = $scope.BCWeightage
													+ parseInt($scope.employeeOverAllRatingData[2][i].weightage);
											if($scope.employeeOverAllRatingData[2][i].midYearAssessorRating !=null){
											$scope.BCCalculationB = ($scope.employeeOverAllRatingData[2][i].midYearAssessorRating * 30 / 100)
													+ ($scope.employeeOverAllRatingData[2][i].finalYearAssessorRating * 70 / 100);
											}else{
												$scope.BCCalculationB = ($scope.employeeOverAllRatingData[2][i].finalYearAssessorRating);
											}
											$scope.BCCalculationAB = $scope.BCCalculationB
													* parseInt($scope.employeeOverAllRatingData[2][i].weightage);
											$scope.BCCalculationAverage = $scope.BCCalculationAverage
													+ $scope.BCCalculationAB;
											$scope.employeeOverAllBCRatingData[0].BCWeightage = $scope.BCWeightage;
											$scope.employeeOverAllBCRatingData[0].BCCalculationAverage = $scope.BCCalculationAverage;
										}
									}
								});
									});
					}
					}
				});

app
		.controller(
				'EmployeePromotionController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter EmployeePromotionController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.employeeLevel = [ "FIRST LEVEL EMPLOYEE",
							"SECOND LEVEL EMPLOYEE" ];

					$scope.getEmployeeLists = function() {
						$scope.subEmployeeCode = undefined;
						$scope.subEmployeeDetails = null;
						$scope.employeePromotionDetails = [];
						$scope.subEmployeeList = [];
						$scope.formClear();
						var input = {
							"type" : $scope.levelType,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : document
									.getElementById("globalUserName").value
						};
						var res = $http.post('/PMS/get-all-subEmployeeList',
								input);
						res.success(function(data, status) {
							$scope.subEmployeeList = data.object;

						});

					}
					$scope.currentDate = new Date();
					$scope.getEmployeeBasicDetails = function() {
						var input = {
							"empCode" : $scope.subEmployeeCode.empCode,
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/getEmployeePromotionDetails', input);
						res
								.success(function(data, status) {
									$scope.subEmployeeDetails = data.object[0];
									$scope.employeePromotionDetails = data.object[1];
									if ($scope.employeePromotionDetails.length > 0) {
										$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
										$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
										$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
										$scope.expectations = $scope.employeePromotionDetails[0].expectations;
										$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
										$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
										$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
										$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
										$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
										$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
										$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
										$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
										$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
										$scope.approvedByComments = $scope.employeePromotionDetails[0].approvedByComments;
									} else {
										$scope.formClear();
									}
								});
					}
					$scope.formClear = function() {
						$scope.employeePromotionId = null;
						$scope.recommendedDesignation = null;
						$scope.specificAchievements = null;
						$scope.expectations = null;
						$scope.promotionImpact = null;
						$scope.jobResponsibility = null;
						$scope.departmentLevel = null;
						$scope.organisationLevel = null;
						$scope.additionalTraining = null;
						$scope.nextFiveYears = null;
						$scope.firstLevelSuperiorComments = null;
						$scope.secondLevelSuperiorComments = null;
					}
					$scope.savePromotionModule = function() {
						var input = {
							"id" : $scope.employeePromotionId,
							"type" : $scope.levelType,
							"empCode" : $scope.subEmployeeCode.empCode,
							"recommendedDesignation" : $scope.recommendedDesignation,
							"specificAchievements" : $scope.specificAchievements,
							"expectations" : $scope.expectations,
							"promotionImpact" : $scope.promotionImpact,
							"jobResponsibility" : $scope.jobResponsibility,
							"departmentLevel" : $scope.departmentLevel,
							"organisationLevel" : $scope.organisationLevel,
							"additionalTraining" : $scope.additionalTraining,
							"nextFiveYears" : $scope.nextFiveYears,
							"firstLevelSuperiorComments" : $scope.firstLevelSuperiorComments,
							"secondLevelSuperiorComments" : $scope.secondLevelSuperiorComments,
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/saveEmployeePromotiondetails', input);
						res
								.success(function(data, status) {
									
									if (data.integer == 1) {
										swal(data.string);
										$scope.employeePromotionDetails = data.object.object[1];
										if ($scope.employeePromotionDetails.length > 0) {
											$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
											$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
											$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
											$scope.expectations = $scope.employeePromotionDetails[0].expectations;
											$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
											$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
											$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
											$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
											$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
											$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
											$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
											$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
											$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
											$scope.approvedByComments = $scope.employeePromotionDetails[0].approvedByComments;
										}

										$timeout(
												function() {
													$window.location.reload();
												}, 5000);
									} 
								});
					}
					$scope.save = function() {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if ($scope.levelType == $scope.employeeLevel[0]) {
													if ($scope.subEmployeeCode == undefined
															|| $scope.recommendedDesignation == null
															|| $scope.specificAchievements == null
															|| $scope.expectations == null
															|| $scope.promotionImpact == null
															|| $scope.jobResponsibility == null
															|| $scope.departmentLevel == null
															|| $scope.organisationLevel == null
															|| $scope.additionalTraining == null
															|| $scope.nextFiveYears == null
															|| $scope.firstLevelSuperiorComments == null
															|| $scope.recommendedDesignation == ""
															|| $scope.specificAchievements == ""
															|| $scope.expectations == ""
															|| $scope.promotionImpact == ""
															|| $scope.jobResponsibility == ""
															|| $scope.departmentLevel == ""
															|| $scope.organisationLevel == ""
															|| $scope.additionalTraining == ""
															|| $scope.nextFiveYears == ""
															|| $scope.firstLevelSuperiorComments == "") {
														swal("Please enter the following fields");
													} else {
														$scope
																.savePromotionModule();
													}
												}
												if ($scope.levelType == $scope.employeeLevel[1]) {
													if ($scope.subEmployeeCode == undefined
															|| $scope.recommendedDesignation == null
															|| $scope.specificAchievements == null
															|| $scope.expectations == null
															|| $scope.promotionImpact == null
															|| $scope.jobResponsibility == null
															|| $scope.departmentLevel == null
															|| $scope.organisationLevel == null
															|| $scope.additionalTraining == null
															|| $scope.nextFiveYears == null
															|| $scope.firstLevelSuperiorComments == null
															|| $scope.secondLevelSuperiorComments == null
															|| $scope.recommendedDesignation == ""
															|| $scope.specificAchievements == ""
															|| $scope.expectations == ""
															|| $scope.promotionImpact == ""
															|| $scope.jobResponsibility == ""
															|| $scope.departmentLevel == ""
															|| $scope.organisationLevel == ""
															|| $scope.additionalTraining == ""
															|| $scope.nextFiveYears == ""
															|| $scope.firstLevelSuperiorComments == ""
															|| $scope.secondLevelSuperiorComments == "") {
														swal("Please enter the following fields");
													} else {
														$scope
																.savePromotionModule();
													}
												}
											}
										});
					}
					$scope.rejectEmployeePromotion = function() {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if ($scope.levelType == $scope.employeeLevel[1]) {
													if ($scope.subEmployeeCode == undefined
															|| $scope.recommendedDesignation == null
															|| $scope.specificAchievements == null
															|| $scope.expectations == null
															|| $scope.promotionImpact == null
															|| $scope.jobResponsibility == null
															|| $scope.departmentLevel == null
															|| $scope.organisationLevel == null
															|| $scope.additionalTraining == null
															|| $scope.nextFiveYears == null
															|| $scope.firstLevelSuperiorComments == null
															|| $scope.recommendedDesignation == ""
															|| $scope.specificAchievements == ""
															|| $scope.expectations == ""
															|| $scope.promotionImpact == ""
															|| $scope.jobResponsibility == ""
															|| $scope.departmentLevel == ""
															|| $scope.organisationLevel == ""
															|| $scope.additionalTraining == ""
															|| $scope.nextFiveYears == ""
															|| $scope.firstLevelSuperiorComments == "") {
														swal("Please enter the following fields");
													} else {
														$scope
																.rejectPromotionModule();
													}
												}
											}
										});
					}
					$scope.rejectPromotionModule = function() {
						var input = {
							"id" : $scope.employeePromotionId,
							"type" : $scope.levelType
						};
						var res = $http.post(
								'/PMS/rejectEmployeePromotiondetails', input);
						res
								.success(function(data, status) {
									swal(data.string);
									if (data.integer == 1) {
										$timeout(
												function() {
													$window.location.reload();
												}, 5000);
									}
								});
					}

				});
app
		.controller(
				'HREmployeePromotionController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter HeadEmployeePromotionController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));

					$scope.getEmployeeLists = function() {
						$scope.subEmployeeCode = undefined;
						$scope.subEmployeeList = [];
						var input = {
							"type" : "ADMIN",
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : document
									.getElementById("globalUserName").value
						};
						var res = $http.post('/PMS/get-all-subEmployeeList',
								input);
						res.success(function(data, status) {
							$scope.subEmployeeList = data.object;

						});

					}
					$scope.currentDate = new Date();
					$scope.getEmployeeBasicDetails = function() {
						var input = {
							"empCode" : $scope.subEmployeeCode.empCode,
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/getEmployeePromotionDetails', input);
						res
								.success(function(data, status) {
									$scope.subEmployeeDetails = data.object[0];
									$scope.employeePromotionDetails = data.object[1];
									if ($scope.employeePromotionDetails.length > 0) {
										$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
										$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
										$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
										$scope.expectations = $scope.employeePromotionDetails[0].expectations;
										$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
										$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
										$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
										$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
										$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
										$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
										$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
										$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
										$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
										$scope.approvedByComments = $scope.employeePromotionDetails[0].approvedByComments;
									}
								});
					}
					$scope.save = function() {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if ($scope.subEmployeeCode == undefined
														|| $scope.recommendedDesignation == null
														|| $scope.specificAchievements == null
														|| $scope.expectations == null
														|| $scope.promotionImpact == null
														|| $scope.jobResponsibility == null
														|| $scope.departmentLevel == null
														|| $scope.organisationLevel == null
														|| $scope.additionalTraining == null
														|| $scope.nextFiveYears == null
														|| $scope.firstLevelSuperiorComments == null
														|| $scope.secondLevelSuperiorComments == null
														|| $scope.hrComments == null
														|| $scope.recommendedDesignation == ""
														|| $scope.specificAchievements == ""
														|| $scope.expectations == ""
														|| $scope.promotionImpact == ""
														|| $scope.jobResponsibility == ""
														|| $scope.departmentLevel == ""
														|| $scope.organisationLevel == ""
														|| $scope.additionalTraining == ""
														|| $scope.nextFiveYears == ""
														|| $scope.firstLevelSuperiorComments == ""
														|| $scope.secondLevelSuperiorComments == ""
														|| $scope.hrComments == "") 
												     {
													
														swal("Please enter the following fields");
												} else {
													$scope.saveEmployeeHRPromotionModule();
												}
											}
										});
					}
					$scope.saveEmployeeHRPromotionModule = function() {
						var input = {
							"id" : $scope.employeePromotionId,
							"type" : "HEAD HR",
							"empCode" : $scope.subEmployeeCode.empCode,
							"recommendedDesignation" : $scope.recommendedDesignation,
							"specificAchievements" : $scope.specificAchievements,
							"expectations" : $scope.expectations,
							"promotionImpact" : $scope.promotionImpact,
							"jobResponsibility" : $scope.jobResponsibility,
							"departmentLevel" : $scope.departmentLevel,
							"organisationLevel" : $scope.organisationLevel,
							"additionalTraining" : $scope.additionalTraining,
							"nextFiveYears" : $scope.nextFiveYears,
							"firstLevelSuperiorComments" : $scope.firstLevelSuperiorComments,
							"secondLevelSuperiorComments" : $scope.secondLevelSuperiorComments,
							"hrComments" : $scope.hrComments,
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/saveEmployeePromotiondetails', input);
						res
								.success(function(data, status) {
									if (data.integer == 1) {
										swal(data.string);
										$scope.employeePromotionDetails = data.object.object[1];
										if ($scope.employeePromotionDetails.length > 0) {
											$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
											$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
											$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
											$scope.expectations = $scope.employeePromotionDetails[0].expectations;
											$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
											$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
											$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
											$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
											$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
											$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
											$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
											$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
											$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
										}
										$timeout(
												function() {
													$window.location.reload();
												}, 5000);
									}
								});

					}
					$scope.rejectEmployeeHRPromotionModule = function() {
						var input = {
							"id" : $scope.employeePromotionId,
							"type" : "ADMIN"
						};
						var res = $http.post(
								'/PMS/rejectEmployeePromotiondetails', input);
						res
								.success(function(data, status) {
									if (data.integer == 1) {
										swal(data.string);
										$timeout(
												function() {
													$window.location.reload();
												}, 5000);
									}
								});
					}
					$scope.rejectEmployeePromotion = function() {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if ($scope.subEmployeeCode == undefined
														|| $scope.recommendedDesignation == null
														|| $scope.specificAchievements == null
														|| $scope.expectations == null
														|| $scope.promotionImpact == null
														|| $scope.jobResponsibility == null
														|| $scope.departmentLevel == null
														|| $scope.organisationLevel == null
														|| $scope.additionalTraining == null
														|| $scope.nextFiveYears == null
														|| $scope.firstLevelSuperiorComments == null
														|| $scope.secondLevelSuperiorComments == null
														|| $scope.recommendedDesignation == ""
														|| $scope.specificAchievements == ""
														|| $scope.expectations == ""
														|| $scope.promotionImpact == ""
														|| $scope.jobResponsibility == ""
														|| $scope.departmentLevel == ""
														|| $scope.organisationLevel == ""
														|| $scope.additionalTraining == ""
														|| $scope.nextFiveYears == ""
														|| $scope.firstLevelSuperiorComments == ""
														|| $scope.secondLevelSuperiorComments == "") {
													swal("Please enter the following fields");
												} else {
													$scope
															.rejectEmployeeHRPromotionModule();
												}

											}
										});
					}
				});

app
.controller(
		'CEOEmployeePromotionController',
		function($window, $scope, $timeout, $http, sharedProperties) {
			logDebug('Enter CEOEmployeePromotionController');
			logDebug('Browser language >> ' + window.navigator.language);
			document.getElementById("appraisalYearID").style.visibility = "hidden";
			$scope.appraisalYearId = JSON.parse(localStorage
					.getItem("appraisalYearId"));

			$scope.getEmployeeLists = function() {
				$scope.subEmployeeCode = undefined;
				$scope.subEmployeeList = [];
				var input = {
					"type" : "ADMIN",
					"appraisalYearId" : $scope.appraisalYearId.id,
					"empCode" : document
							.getElementById("globalUserName").value
				};
				var res = $http.post('/PMS/get-all-subEmployeeList',
						input);
				res.success(function(data, status) {
					$scope.subEmployeeList = data.object;

				});

			}
			$scope.currentDate = new Date();
			$scope.getEmployeeBasicDetails = function() {
				var input = {
					"empCode" : $scope.subEmployeeCode.empCode,
					"appraisalYearId" : $scope.appraisalYearId.id
				};
				var res = $http.post(
						'/PMS/getEmployeePromotionDetails', input);
				res
						.success(function(data, status) {
							$scope.subEmployeeDetails = data.object[0];
							$scope.employeePromotionDetails = data.object[1];
							if ($scope.employeePromotionDetails.length > 0) {
								$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
								$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
								$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
								$scope.expectations = $scope.employeePromotionDetails[0].expectations;
								$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
								$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
								$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
								$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
								$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
								$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
								$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
								$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
								$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
								$scope.approvedByComments = $scope.employeePromotionDetails[0].approvedByComments;
							}
						});
			}
			$scope.save = function() {
				swal({
					title : "Are you sure?",
					icon : "warning",
					buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
					dangerMode : true,
				})
						.then(
								function(isConfirm) {
									if (isConfirm) {
										if ($scope.subEmployeeCode == undefined
												|| $scope.recommendedDesignation == null
												|| $scope.specificAchievements == null
												|| $scope.expectations == null
												|| $scope.promotionImpact == null
												|| $scope.jobResponsibility == null
												|| $scope.departmentLevel == null
												|| $scope.organisationLevel == null
												|| $scope.additionalTraining == null
												|| $scope.nextFiveYears == null
												|| $scope.firstLevelSuperiorComments == null
												|| $scope.secondLevelSuperiorComments == null
												|| $scope.hrComments == null
												|| $scope.approvedByComments == null
												|| $scope.recommendedDesignation == ""
												|| $scope.specificAchievements == ""
												|| $scope.expectations == ""
												|| $scope.promotionImpact == ""
												|| $scope.jobResponsibility == ""
												|| $scope.departmentLevel == ""
												|| $scope.organisationLevel == ""
												|| $scope.additionalTraining == ""
												|| $scope.nextFiveYears == ""
												|| $scope.firstLevelSuperiorComments == ""
												|| $scope.secondLevelSuperiorComments == ""
												|| $scope.hrComments == ""
												||	$scope.approvedByComments == "" ) 
										     {
												swal("Please enter the following fields");
											
										} else {
											$scope.saveEmployeeCEOPromotionModule();
										}
									}
								});
			}
			$scope.saveEmployeeCEOPromotionModule = function() {
				var input = {
					"id" : $scope.employeePromotionId,
					"type" : "CEO",
					"empCode" : $scope.subEmployeeCode.empCode,
					"recommendedDesignation" : $scope.recommendedDesignation,
					"specificAchievements" : $scope.specificAchievements,
					"expectations" : $scope.expectations,
					"promotionImpact" : $scope.promotionImpact,
					"jobResponsibility" : $scope.jobResponsibility,
					"departmentLevel" : $scope.departmentLevel,
					"organisationLevel" : $scope.organisationLevel,
					"additionalTraining" : $scope.additionalTraining,
					"nextFiveYears" : $scope.nextFiveYears,
					"firstLevelSuperiorComments" : $scope.firstLevelSuperiorComments,
					"secondLevelSuperiorComments" : $scope.secondLevelSuperiorComments,
					"hrComments" : $scope.hrComments,
					"approvedByComments" : $scope.approvedByComments,
					"appraisalYearId" : $scope.appraisalYearId.id
				};
				var res = $http.post(
						'/PMS/saveEmployeePromotiondetails', input);
				res
						.success(function(data, status) {
							if (data.integer == 1) {
								swal(data.string);
								$scope.employeePromotionDetails = data.object.object[1];
								if ($scope.employeePromotionDetails.length > 0) {
									$scope.employeePromotionId = $scope.employeePromotionDetails[0].id;
									$scope.recommendedDesignation = $scope.employeePromotionDetails[0].recommendedDesignation;
									$scope.specificAchievements = $scope.employeePromotionDetails[0].specificAchievements;
									$scope.expectations = $scope.employeePromotionDetails[0].expectations;
									$scope.promotionImpact = $scope.employeePromotionDetails[0].promotionImpact;
									$scope.jobResponsibility = $scope.employeePromotionDetails[0].jobResponsibility;
									$scope.departmentLevel = $scope.employeePromotionDetails[0].departmentLevel;
									$scope.organisationLevel = $scope.employeePromotionDetails[0].organisationLevel;
									$scope.additionalTraining = $scope.employeePromotionDetails[0].additionalTraining;
									$scope.nextFiveYears = $scope.employeePromotionDetails[0].nextFiveYears;
									$scope.firstLevelSuperiorComments = $scope.employeePromotionDetails[0].firstLevelSuperiorComments;
									$scope.secondLevelSuperiorComments = $scope.employeePromotionDetails[0].secondLevelSuperiorComments;
									$scope.hrComments = $scope.employeePromotionDetails[0].hrComments;
									$scope.approvedByComments = $scope.employeePromotionDetails[0].approvedByComments;
								}
								$timeout(
										function() {
											$window.location.reload();
										}, 5000);
							}
						});

			}
			$scope.rejectEmployeeHRPromotionModule = function() {
				var input = {
					"id" : $scope.employeePromotionId,
					"type" : "ADMIN"
				};
				var res = $http.post(
						'/PMS/rejectEmployeePromotiondetails', input);
				res
						.success(function(data, status) {
							if (data.integer == 1) {
								swal(data.string);
								$timeout(
										function() {
											$window.location.reload();
										}, 5000);
							}
						});
			}
			$scope.rejectEmployeePromotion = function() {
				swal({
					title : "Are you sure?",
					icon : "warning",
					buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
					dangerMode : true,
				})
						.then(
								function(isConfirm) {
									if (isConfirm) {
										if ($scope.subEmployeeCode == undefined
												|| $scope.recommendedDesignation == null
												|| $scope.specificAchievements == null
												|| $scope.expectations == null
												|| $scope.promotionImpact == null
												|| $scope.jobResponsibility == null
												|| $scope.departmentLevel == null
												|| $scope.organisationLevel == null
												|| $scope.additionalTraining == null
												|| $scope.nextFiveYears == null
												|| $scope.firstLevelSuperiorComments == null
												|| $scope.secondLevelSuperiorComments == null
												|| $scope.recommendedDesignation == ""
												|| $scope.specificAchievements == ""
												|| $scope.expectations == ""
												|| $scope.promotionImpact == ""
												|| $scope.jobResponsibility == ""
												|| $scope.departmentLevel == ""
												|| $scope.organisationLevel == ""
												|| $scope.additionalTraining == ""
												|| $scope.nextFiveYears == ""
												|| $scope.firstLevelSuperiorComments == ""
												|| $scope.secondLevelSuperiorComments == "") {
											swal("Please enter the following fields");
										} else {
											$scope
													.rejectEmployeeHRPromotionModule();
										}

									}
								});
			}
		});
app
		.controller(
				'ChangeLogoController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter ChangeLogoController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";

					$scope.save = function() {
						var formdata = new FormData();
						formdata.append("file", $scope.file);
						$http
								.post("/PMS/changeLogo", formdata, {
									transformRequest : angular.identity,
									headers : {
										'Content-Type' : undefined
									}
								})
								.success(
										function(data) {
											if (data.integer == 1) {
												swal(data.string);
											}
										});
					}
				});
app
		.controller(
				'FirstLevelAppraisalController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter AppraisalController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					if (sharedProperties.getValue("employeeDetails") === undefined) {
						$window.location.href = "#/team-appraisal";
					}
					
					$scope.WeightageCount = 5;
					$scope.extraOrdinaryWeightageCount = 0;
					$scope.kraToalCalculation = 0;
					$scope.BehavioralComptenceTotalCalculation = 0;
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.loggedUserAppraisalDetails = [];
					
					$scope.employeeCareerAspirationDetails = [];
					$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
					$scope.selectsRating = [ 0,1, 2, 3, 4, 5 ];
					$scope.approveRejectList = [ "Approve", "Reject" ];
					$scope.sectionAList = [];
					$scope.sectionBList = [];
					$scope.sectionCList = [];
					$scope.sectionDList = [];
					$scope.sectionAListOld = [];
					$scope.sectionBListOld = [];
					$scope.sectionCListOld = [];
					$scope.sectionDListOld = [];
					
					$scope.formDataKRA = {
						"sectionAList" : $scope.sectionAList,
						"sectionBList" : $scope.sectionBList,
						"sectionCList" : $scope.sectionCList,
						"sectionDList" : $scope.sectionDList
					};
					
					
					$scope.setActiveTab = function(index){
					    $scope.activeTab = index;
					  }
					$scope.managerAcknowledgement = [{
					"firstManagerGoalApproval" :null,
					"isFirstManagerGoalApproval": null,
					"firstManagerMidYearApproval" :null,
					"isFirstManagerMidYearApproval":null,
					"firstManagerYearEndAssessmentApproval" :null,
					"isFirstManagerYearEndAssessmentApproval":null,
					}];
					
					
					$scope.extraOrdinaryDetails = [ {
						'contributions' : null,
						'contributionDetails' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						// 'child' : $scope.dynamicExtraOrdinaryDetails,
						'remarks' : null
					} ];
					$scope.trainingNeedsDetails = [ {
						'trainingTopic' : null,
						'trainingReasons' : null,
						'manHours' : null
					// 'child' : $scope.dynamicTrainingNeedsDetails,
					} ];
					$scope.getActiveTabId = function(data) {
						switch (data) {
						case 0:
							$scope.getEmployeeKraDetails();
							break;
						case 1:
							$scope.getExtraOrdinaryDetails();
							break;
						case 2:
							$scope.getBehavioralComptenceDetails();
							break;
						case 3:
							$scope.getCareerAspirationDetails();
							break;
						case 4:
							$scope.getTrainingNeedsDetails();
							break;
						case 5:
							$scope.finalSubmissionData();
							$scope.getExtraOrdinaryDetails();
							$scope.getCareerAspirationDetails();
							$scope.getTrainingNeedsDetails();
							$scope.getBehavioralComptenceDetails();
							break;
						}
					}
					
					$scope.addNewChoice = function(data) {
						if (data === "sectionA") {
							$scope.sectionAList.push({
								'sectionName' : 'Section A',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						}  if (data === "sectionB") {
							$scope.sectionBList.push({
								'sectionName' : 'Section B',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						}  if (data === "sectionC") {
							$scope.sectionCList.push({
								'sectionName' : 'Section C',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						} 
					};

					$scope.removeNewChoice = function(index, data) {
						
						if (data === "sectionA") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionAList[index].weightage);
							$scope.sectionAList.splice(index, 1);
							
						}  if (data === "sectionB") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionBList[index].weightage);
							$scope.sectionBList.splice(index, 1);
							
						}  if (data === "sectionC") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionCList[index].weightage);
							$scope.sectionCList.splice(index, 1);
							
						}
					};
// $scope.printFirstLevel = function() {
// // window.print();
// var mywindow = window.open();
// mywindow.document
// .write('<html><head><title>HERO FUTURE ENERGIES</title>');
// mywindow.document
// .write('<!DOCTYPE html><html><head><meta http-equiv="Content-Type"
// content="text/html; charset=UTF-8"><title>Print</title>');
// // mywindow.document.write('<link rel=\"stylesheet\"
// //
// href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css"
// // type=\"text/css\" media=\"print\">');
// // mywindow.document.write('<link rel=\"stylesheet\"
// // href="resources/common/css/index.css?v=0.13"
// // type=\"text/css\" media=\"print\"/>');
// // mywindow.document.write('<link
// // rel=\"stylesheet\"href="resources/common/css/default.css?v=0.2"
// // type=\"text/css\" media=\"print\" />');
// //
//
// mywindow.document
// .write('</head><body><img id="headerLogoImg"
// src="resources/common/images/logo.jpg" class="hidden-xs" style="float:
// inherit;">');
// // mywindow.document.write('<h1>' + document.title +
// // '</h1>');
// mywindow.document
// .write(document
// .getElementById("viewAppraisalFirstLevel").innerHTML);
// mywindow.document.write('</body></html>');
//
// mywindow.document.close(); // necessary for IE >= 10
// mywindow.focus(); // necessary for IE >= 10*/
//
// mywindow.print();
// $timeout(function() {
// mywindow.close();
// }, 5000);
//
// return true;
// }
					$scope.countWeightage = function() {
						$scope.WeightageCount = 0;
						for (var i = 0; i < $scope.sectionAList.length; i++) {
							if ($scope.sectionAList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionAList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionBList.length; i++) {
							if ($scope.sectionBList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionBList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionCList.length; i++) {
							if ($scope.sectionCList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionCList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionDList.length; i++) {
							if ($scope.sectionDList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionDList[i].weightage);
						}
					}
					$scope.countExtraOrdinaryWeightage = function() {
						$scope.extraOrdinaryWeightageCount = 0;
						for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
							if ($scope.extraOrdinaryDetails[i].weightage != null)
								$scope.extraOrdinaryWeightageCount = $scope.extraOrdinaryWeightageCount
										+ parseInt($scope.extraOrdinaryDetails[i].weightage);
						}
					}
					$scope.finalSubmissionData = function() {
						input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/get-employee-appraisal-details', input);
						res.success(function(data, status) {
							$scope.subEmployeeDetails = data.object[0];
						});
					}

					$scope.getExtraOrdinaryDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "ExtraOrdinary",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									if ($scope.result.object == null) {
										$scope.loggedUserAppraisalDetails = [];
										$scope.loggedUserAppraisalDetails
												.push({
													"initializationYear" : null,
													"midYear" : null,
													"finalYear" : null
												});
									} else {
										$scope.loggedUserAppraisalDetails = $scope.result.object;
										if($scope.loggedUserAppraisalDetails.firstManagerGoalApproval == 1)
										{
										$scope.managerAcknowledgement.firstManagerGoalApproval = true;
										$scope.managerAcknowledgement.isFirstManagerGoalApproval = $scope.loggedUserAppraisalDetails.firstManagerGoalApproval;
										}
									if($scope.loggedUserAppraisalDetails.firstManagerMidYearApproval == 1)
									{
										$scope.managerAcknowledgement.firstManagerMidYearApproval = true;
										$scope.managerAcknowledgement.isFirstManagerMidYearApproval = $scope.loggedUserAppraisalDetails.firstManagerMidYearApproval;
									}
									if($scope.loggedUserAppraisalDetails.firstManagerYearEndAssessmentApproval == 1)
									{
										$scope.managerAcknowledgement.firstManagerYearEndAssessmentApproval = true;
										$scope.managerAcknowledgement.isFirstManagerYearEndAssessmentApproval = $scope.loggedUserAppraisalDetails.firstManagerYearEndAssessmentApproval;
									}
									}
// if($scope.loggedUserAppraisalDetails.firstManagerNormalKRARejection == 1 )
// {
// $scope.managerAcknowledgement.rejectionId = "1";
// }
// if($scope.loggedUserAppraisalDetails.firstManagerNewKRARejection == 1 )
// {
// $scope.managerAcknowledgement.rejectionId = "2";
// }
									if (data.object[1].length > 0) {
										$scope.extraOrdinaryDetails = data.object[1];
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											if ($scope.extraOrdinaryDetails[i].isValidatedByFirstLevel == 1) {
												$scope.extraOrdinaryButtonDisabled = true;
												break;
											}

										}
										$scope.extraOrdinaryWeightageCount = 0;
										$scope.ExtraOrdinaryTotalCalculation = 0;
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											$scope.extraOrdinaryWeightageCount = $scope.extraOrdinaryWeightageCount
													+ parseInt($scope.extraOrdinaryDetails[i].weightage);
											if ($scope.extraOrdinaryDetails[i].weightage != null && $scope.extraOrdinaryDetails[i].finalYearAppraisarRating !=null) {
												$scope.ExtraOrdinaryTotalCalculation = $scope.ExtraOrdinaryTotalCalculation +
												(($scope.extraOrdinaryDetails[i].weightage * $scope.extraOrdinaryDetails[i].finalYearAppraisarRating)/100);
											}
										}
									}
								});
					}
					$scope.getOldKRAData = function()
					{

						$scope.WeightageCountOld = 5;
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "EmployeeOldKra",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.resultOld = data.object[0];
									$scope.loggedUserKRADetailsOld = data.object[1].object;
									var p = 0;
									var q = 0;
									var r = 0;
									var s = 0;
									$scope.WeightageCountOld = 0;
									$scope.kraToalCalculationOld = 0;
									$scope.loggedUserAppraisalDetails = $scope.resultOld.object;
									
									for (var i = 0; i < $scope.loggedUserKRADetailsOld.length; i++) {
// $scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// $scope.currentEmpCode = $scope.loggedUserKRADetails[i].empCode;
										if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section A") {
											$scope.sectionAListOld[p] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionAListOld[p].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionAListOld[p].midYearAppraisarRating !=null && $scope.sectionAListOld[p].finalYearAppraisarRating !=null && $scope.sectionAListOld[p].weightage !=null )
												{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionAListOld[p].midYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100) *30/100) +
														(((($scope.sectionAListOld[p].finalYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100) *
														70/100)));
												}
											if($scope.sectionAListOld[p].midYearAppraisarRating ==null && $scope.sectionAListOld[p].finalYearAppraisarRating !=null && $scope.sectionAListOld[p].weightage !=null )
											{
											$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
													(((($scope.sectionAListOld[p].finalYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100))));
											}
											p++;
										} else if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section B") {
											$scope.sectionBListOld[q] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionBListOld[q].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionBListOld[q].midYearAppraisarRating !=null && $scope.sectionBListOld[q].finalYearAppraisarRating !=null && $scope.sectionBListOld[q].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionBListOld[q].midYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100) *30/100) +
														(((($scope.sectionBListOld[q].finalYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100) *
														70/100)));
											}
											if($scope.sectionBListOld[q].midYearAppraisarRating ==null && $scope.sectionBListOld[q].finalYearAppraisarRating !=null && $scope.sectionBListOld[q].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionBListOld[q].finalYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100))));
											}
											q++;
										} else if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section C") {
											$scope.sectionCListOld[r] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionCListOld[r].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionCListOld[r].midYearAppraisarRating !=null && $scope.sectionCListOld[r].finalYearAppraisarRating !=null && $scope.sectionCListOld[r].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionCListOld[r].midYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100) *30/100) +
														(((($scope.sectionCListOld[r].finalYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100) *
														70/100)));
											}
											if($scope.sectionCListOld[r].midYearAppraisarRating ==null && $scope.sectionCListOld[r].finalYearAppraisarRating !=null && $scope.sectionCListOld[r].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionCListOld[r].finalYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100))));
											}
											r++;
										} else {
											$scope.sectionDListOld[s] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionDListOld[s].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionDListOld[s].midYearAppraisarRating !=null && $scope.sectionDListOld[s].finalYearAppraisarRating !=null && $scope.sectionDListOld[s].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionDListOld[s].midYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100) *30/100) +
														(((($scope.sectionDListOld[s].finalYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100) *
														70/100)));
											}
											if($scope.sectionDListOld[s].midYearAppraisarRating ==null && $scope.sectionDListOld[s].finalYearAppraisarRating !=null && $scope.sectionDListOld[s].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionDListOld[s].finalYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100))));
											}
											s++;
										}

									}
								});
					
					}
					$scope.getEmployeeKraDetails = function() {
						$scope.WeightageCount = 5;
						$scope.kraDetailsId = [];
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "EmployeeKra",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserKRADetails = data.object[1].object;
									var p = 0;
									var q = 0;
									var r = 0;
									var s = 0;
									$scope.WeightageCount = 0;
									$scope.kraToalCalculation = 0;
									$scope.kraButtonSendToManager = false;
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										if ($scope.loggedUserKRADetails[i].isValidatedByFirstLevel == 1) {
											$scope.kraButtonDisabled = true;
											$scope.WeightageCount = 0;
											break;
										}
									}
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										if ($scope.loggedUserKRADetails[i].kraType == "NEW") {
											$scope.kraButtonSendToManager = true;
											break;
										}
									}
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
										$scope.currentEmpCode = $scope.loggedUserKRADetails[i].empCode;
										if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
											$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionAList[p].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null )
												{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
														70/100)));
												}
											if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null )
											{
											$scope.kraToalCalculation = $scope.kraToalCalculation + (
													(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
											}
											p++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
											$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionBList[q].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
														70/100)));
											}
											if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].finalYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
											}
											q++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
											$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionCList[r].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
														70/100)));
											}
											if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].finalYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
											}
											r++;
										} else {
											$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionDList[s].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
														70/100)));
											}
											if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
											}
											
											s++;
										}

									}
								});
					}
					$scope.getBehavioralComptenceDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "BehavioralComptence"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.formDataBC = data.object[1];
									$scope.formMainData = data.object[2];
									$scope.BehavioralComptenceTotalCalculation = 0;
									// if ($scope.result.object == null) {
									// $scope.loggedUserAppraisalDetails
									// .push({
									// "initializationYear" : null,
									// "midYear" : null,
									// "finalYear" : null
									// });
									// } else {
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									// }
									if($scope.loggedUserAppraisalDetails != null)
										{
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.behaviouralCompetenceButtonDisabled = true;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 1
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.behaviouralCompetenceButtonDisabled = false;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
										$scope.behaviouralCompetenceButtonDisabled = false;
									}
										}
									for (var i = 0; i < $scope.formMainData.length; i++) {
										if ($scope.formMainData[i].isValidatedByFirstLevel == 1) {
											$scope.behaviouralCompetenceButtonDisabled = true;
											break;
										}
									}
									for (var i = 0; i < $scope.formMainData.length; i++) {
										$scope.formDataBC[i].behaviouralCompetenceDetailsId = $scope.formMainData[i].id;
										$scope.formDataBC[i].isValidatedByFirstLevel = $scope.formMainData[i].isValidatedByFirstLevel;
										$scope.formDataBC[i].comments = $scope.formMainData[i].comments;
										$scope.formDataBC[i].midYearSelfRating = $scope.formMainData[i].midYearSelfRating;
										$scope.formDataBC[i].midYearAssessorRating = $scope.formMainData[i].midYearAssessorRating;
										$scope.formDataBC[i].finalYearSelfRating = $scope.formMainData[i].finalYearSelfRating;
										$scope.formDataBC[i].finalYearAssessorRating = $scope.formMainData[i].finalYearAssessorRating;
										$scope.formDataBC[i].midYearHighlights = $scope.formMainData[i].midYearHighlights;
										$scope.formDataBC[i].finalYearHighlights = $scope.formMainData[i].finalYearHighlights;
										
										if($scope.formDataBC[i].midYearAssessorRating ==null && $scope.formDataBC[i].weightage !=null && $scope.formDataBC[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formDataBC[i].finalYearAssessorRating * $scope.formDataBC[i].weightage)/100)));
										}	
										if($scope.formDataBC[i].midYearAssessorRating !=null && $scope.formDataBC[i].weightage !=null && $scope.formDataBC[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formDataBC[i].midYearAssessorRating
												* $scope.formDataBC[i].weightage )/100) *30/100) +
												((($scope.formDataBC[i].finalYearAssessorRating * $scope.formDataBC[i].weightage)/100) *
												70/100));
										}	
									}
								});
					}
					$scope.getTrainingNeedsDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "TrainingNeeds"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object;
									if ($scope.result[0].length > 0) {
										$scope.trainingNeedsDetails = $scope.result[0];
									}
									for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
										if ($scope.trainingNeedsDetails[i].isValidatedByFirstLevel == 1) {
											$scope.trainingNeedsButtonDisabled = true;
										} else {
											$scope.trainingNeedsButtonDisabled = false;
										}

									}
								});
					}
					$scope.getCareerAspirationDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "CareerAspiration"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.employeeCareerAspirationDetails = data.object[0][0];
// if($scope.employeeCareerAspirationDetails !=undefined &&
// $scope.loggedUserAppraisalDetails.initializationYear == 1)
// {
// // && $scope.loggedUserAppraisalDetails.initializationYear == 1
// if ($scope.employeeCareerAspirationDetails.isValidatedByFirstLevel == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// // && $scope.loggedUserAppraisalDetails.midYear == 1
// if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel ==
// 1 && $scope.loggedUserAppraisalDetails.midYear == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// // && $scope.loggedUserAppraisalDetails.finalYear == 1
// if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel ==
// 1 && $scope.loggedUserAppraisalDetails.finalYear == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// }
									
									
									if ($scope.employeeCareerAspirationDetails != undefined && $scope.loggedUserAppraisalDetails != null) {
										if ($scope.employeeCareerAspirationDetails.isValidatedByFirstLevel == 1
												&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel == 1
												&& $scope.loggedUserAppraisalDetails.midYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel == 1
												&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										}
									
									
								});
					}
					$scope.saveBehaviouralCompetenceModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : type,
							"behaviouralCompetence" : $scope.formDataBC,
							"name" : "FIRST LEVEL MANAGER"
						};
						var res = $http
								.post(
										'/PMS/add-manager-behavioral-comptence-details',
										input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal( $scope.result.string);
										$scope.getBehavioralComptenceDetails();
										document.getElementById("main_body").className = "";
									}

								});
					}
					$scope.saveTrainingNeedsModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : type,
							"name" : "FIRST LEVEL MANAGER",
							"trainingNeedsDetails" : $scope.trainingNeedsDetails
						};
						var res = $http.post(
								'/PMS/add-manager-trainingNeeds-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal( $scope.result.string);
										$scope.trainingNeedsDetails = $scope.result.object;
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											if ($scope.trainingNeedsDetails[i].isValidatedByFirstLevel == 1) {
												$scope.trainingNeedsButtonDisabled = true;
												break;
											}
										}
										document.getElementById("main_body").className = "";
									}
								});
					}
					$scope.saveCareerAspirationsModule = function(type) {
						if (($scope.employeeCareerAspirationDetails === undefined
								|| $scope.employeeCareerAspirationDetails.initializationComments === null
								|| $scope.employeeCareerAspirationDetails.initializationManagerReview === null
								|| $scope.employeeCareerAspirationDetails.initializationManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
							swal("Please enter career aspirations");
						} else if (($scope.employeeCareerAspirationDetails.midYearComments === null 
								|| $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview === null
								|| $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.midYear == 1) {
							swal("Please enter career aspirations for mid term");
						}else if (($scope.employeeCareerAspirationDetails.yearEndComments === null 
								|| $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview === null
								|| $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
							swal("Please enter career aspirations for final term");
						} else {
							document.getElementById("main_body").className = "loading-pane";
							var input = {
								"empCode" : sharedProperties
										.getValue("employeeDetails"),
								"id" : $scope.employeeCareerAspirationDetails.id,
								"appraisalYearId" : $scope.appraisalYearId.id,
								"type" : type,
								"initializationComments" : $scope.employeeCareerAspirationDetails.initializationComments,
								"initializationManagerReview" : $scope.employeeCareerAspirationDetails.initializationManagerReview,
								"midYearComments" : $scope.employeeCareerAspirationDetails.midYearComments,
								"midYearCommentsManagerReview" : $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview,
								"yearEndComments" : $scope.employeeCareerAspirationDetails.yearEndComments,
								"yearEndCommentsManagerReview" : $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview,
								"name" : "FIRST LEVEL MANAGER"
							};
							var res = $http
									.post(
											'/PMS/add-manager-career_aspirations-details',
											input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											$scope.employeeCareerAspirationDetails = $scope.result.object[0];
											if ($scope.employeeCareerAspirationDetails.isValidatedByFirstLevel == 1
													&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel == 1
													&& $scope.loggedUserAppraisalDetails.midYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel == 1
													&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											swal( $scope.result.string);
											document.getElementById("main_body").className = "";
										}

									});
						}
					}

					$scope.submitCareerAspirationsModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if (($scope.employeeCareerAspirationDetails === undefined
														|| $scope.employeeCareerAspirationDetails.initializationComments === ""
														|| $scope.employeeCareerAspirationDetails.initializationManagerReview === "")
														&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
													swal("Please enter career aspirations");
												} else if (($scope.employeeCareerAspirationDetails.midYearComments === ""
													       || $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview === "")
														&& $scope.loggedUserAppraisalDetails.midYear == 1) {
													swal("Please enter career aspirations for mid term");
												} else if (($scope.employeeCareerAspirationDetails.yearEndComments === ""
												       || $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview === "")
														&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
													swal("Please enter career aspirations for mid term");
												} else {
													$scope.saveCareerAspirationsModule(type);
												}
											}
										});
					}

					$scope.saveKRAModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						if($scope.formDataKRA.sectionAList[0].kraType === "NEW")
							{
						$scope.input = {
							"kraNewDetails" : $scope.formDataKRA,
							"type" : type,
							"list" : $scope.kraDetailsId,
							"empCode":$scope.currentEmpCode,
							"name" : "FIRST LEVEL MANAGER"
						};
					   }
						if($scope.formDataKRA.sectionAList[0].kraType === null)
						{
							$scope.input = {
									"kraDetails" : $scope.formDataKRA,
									"type" : type,
									"list" : $scope.kraDetailsId,
									"empCode":$scope.currentEmpCode,
									"name" : "FIRST LEVEL MANAGER"
								};
						}	
						var res = $http.post('/PMS/add-manager-kra-details',
								$scope.input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal( $scope.result.string);
										$scope.getEmployeeKraDetails();
										document.getElementById("main_body").className = "";
									}
								});
					}
					$scope.saveExtraOrdinaryModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"type" : type,
							"extraOrdinaryDetails" : $scope.extraOrdinaryDetails,
							"name" : "FIRST LEVEL MANAGER"
						};
						var res = $http.post(
								'/PMS/add-manager-extraOrdinary-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										swal( $scope.result.string);
										$scope.getExtraOrdinaryDetails();
										document.getElementById("main_body").className = "";
									}
								});
					}

					$scope.submitNewKRAModule = function(type)
					{

						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionAList.length; i++) {
// if ($scope.sectionAList[i].smartGoal != null
// && $scope.sectionAList[i].smartGoal != "") {
// if ($scope.sectionAList[i].target === null
// || $scope.sectionAList[i].achievementDate === null
// || $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].target === ""
// || $scope.sectionAList[i].achievementDate === ""
// || $scope.sectionAList[i].weightage === "") {
// swal("Please fill all the field in Section A");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
													if ($scope.loggedUserAppraisalDetails === null) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		) {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		) {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === "") {
																	swal("Please fill all the field in Section A for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
// if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionBList.length; i++) {
// if ($scope.sectionBList[i].smartGoal != null
// && $scope.sectionBList[i].smartGoal != "") {
// if ($scope.sectionBList[i].target === null
// || $scope.sectionBList[i].achievementDate === null
// || $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].target === ""
// || $scope.sectionBList[i].achievementDate === ""
// || $scope.sectionBList[i].weightage === "") {
// swal("Please fill all the field in Section B");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
// }
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section B");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section B");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].finalYearAchievement === null
																	|| $scope.sectionBList[i].finalYearSelfRating === null
																	|| $scope.sectionBList[i].finalYearAppraisarRating === null
																	|| $scope.sectionBList[i].remarks === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																	|| $scope.sectionBList[i].finalYearAchievement === ""
																	|| $scope.sectionBList[i].finalYearSelfRating === ""
																	|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionBList[i].remarks === "") {
																swal("Please fill all the field in Section B for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionCList.length; i++) {
// if ($scope.sectionCList[i].smartGoal != null
// && $scope.sectionCList[i].smartGoal != "") {
// if ($scope.sectionCList[i].target === null
// || $scope.sectionCList[i].achievementDate === null
// || $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].target === ""
// || $scope.sectionCList[i].achievementDate === ""
// || $scope.sectionCList[i].weightage === "") {
// swal("Please fill all the field in Section C");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section C");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section C");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].finalYearAchievement === null
																	|| $scope.sectionCList[i].finalYearSelfRating === null
																	|| $scope.sectionCList[i].finalYearAppraisarRating === null
																	|| $scope.sectionCList[i].remarks === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																	|| $scope.sectionCList[i].finalYearAchievement === ""
																	|| $scope.sectionCList[i].finalYearSelfRating === ""
																	|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionCList[i].remarks === "") {
																swal("Please fill all the field in Section C for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionDList.length; i++) {
// if ($scope.sectionDList[i].smartGoal != null
// && $scope.sectionDList[i].smartGoal != "") {
// if ($scope.sectionDList[i].target === null
// || $scope.sectionDList[i].achievementDate === null
// || $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].target === ""
// || $scope.sectionDList[i].achievementDate === ""
// || $scope.sectionDList[i].weightage === "") {
// swal("Please fill all the field in Section D");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
												
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section D for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section D for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].finalYearAchievement === null
																	|| $scope.sectionDList[i].finalYearSelfRating === null
																	|| $scope.sectionDList[i].finalYearAppraisarRating === null
																	|| $scope.sectionDList[i].remarks === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																	|| $scope.sectionDList[i].finalYearAchievement === ""
																	|| $scope.sectionDList[i].finalYearSelfRating === ""
																	|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionDList[i].remarks === "") {
																swal("Please fill all the field in Section D for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
												if ($scope.WeightageCount > 100
														|| $scope.WeightageCount < 100) {
													swal("Required weightage count is 100%");
												}
												if ($scope.WeightageCount == 100) {
													$scope.saveKRAModule(type);
												}
											}

										});
					
					}
					$scope.submitKRAModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												$scope.totalWeightage = 0;
												$scope.sectionDWeightage = 0;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === "") {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].midYearAppraisarRating === ""
																			|| $scope.sectionAList[i].midYearAssessmentRemarks === "") {
																	swal("Please fill all the field in Section A for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
// || $scope.sectionAList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
// || $scope.sectionAList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === "") {
																	swal("Please fill all the field in Section A for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === "") {
																	swal("Please fill all the field in Section B");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].midYearAchievement === null
																		|| $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].midYearAppraisarRating === null
																		|| $scope.sectionBList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].midYearAchievement === ""
																		|| $scope.sectionBList[i].midYearSelfRating === ""
																		|| $scope.sectionBList[i].midYearAppraisarRating === ""
																			|| $scope.sectionBList[i].midYearAssessmentRemarks === "") {
																	swal("Please fill all the field in Section B for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
// || $scope.sectionBList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionBList[i].finalYearAchievement === null
																		|| $scope.sectionBList[i].finalYearSelfRating === null
																		|| $scope.sectionBList[i].finalYearAppraisarRating === null
																		|| $scope.sectionBList[i].remarks === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
// || $scope.sectionBList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionBList[i].finalYearAchievement === ""
																		|| $scope.sectionBList[i].finalYearSelfRating === ""
																		|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionBList[i].remarks === "") {
																	swal("Please fill all the field in Section B for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === "") {
																	swal("Please fill all the field in Section C");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].midYearAchievement === null
																		|| $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].midYearAppraisarRating === null
																		|| $scope.sectionCList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].midYearAchievement === ""
																		|| $scope.sectionCList[i].midYearSelfRating === ""
																		|| $scope.sectionCList[i].midYearAppraisarRating === ""
																			|| $scope.sectionCList[i].midYearAssessmentRemarks === "") {
																	swal("Please fill all the field in Section C for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
// || $scope.sectionCList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionCList[i].finalYearAchievement === null
																		|| $scope.sectionCList[i].finalYearSelfRating === null
																		|| $scope.sectionCList[i].finalYearAppraisarRating === null
																		|| $scope.sectionCList[i].remarks === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
// || $scope.sectionCList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionCList[i].finalYearAchievement === ""
																		|| $scope.sectionCList[i].finalYearSelfRating === ""
																		|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionCList[i].remarks === "") {
																	swal("Please fill all the field in Section C for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === "") {
																	swal("Please fill all the field in Section D");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].midYearAchievement === null
																		|| $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].midYearAppraisarRating === null
																		|| $scope.sectionDList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].midYearAchievement === ""
																		|| $scope.sectionDList[i].midYearSelfRating === ""
																		|| $scope.sectionDList[i].midYearAppraisarRating === ""
																			|| $scope.sectionDList[i].midYearAssessmentRemarks === "") {
																	swal("Please fill all the field in Section D for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
// || $scope.sectionDList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionDList[i].finalYearAchievement === null
																		|| $scope.sectionDList[i].finalYearSelfRating === null
																		|| $scope.sectionDList[i].finalYearAppraisarRating === null
																		|| $scope.sectionDList[i].remarks === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
// || $scope.sectionDList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionDList[i].finalYearAchievement === ""
																		|| $scope.sectionDList[i].finalYearSelfRating === ""
																		|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionDList[i].remarks === "") {
																	swal("Please fill all the field in Section D for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.WeightageCount > 100
															|| $scope.WeightageCount < 100) {
														swal("Required weightage count is 100%");
													}
													if ($scope.WeightageCount == 100) {
														$scope
																.saveKRAModule(type);
													}
												}
											}
										});
					}
					$scope.submitExtraOrdinaryModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
															if ($scope.extraOrdinaryDetails[i].contributions === null
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																	|| $scope.extraOrdinaryDetails[i].weightage === null
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === null
																	|| $scope.extraOrdinaryDetails[i].remarks === null
																	|| $scope.extraOrdinaryDetails[i].contributions === ""
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																	|| $scope.extraOrdinaryDetails[i].weightage === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === ""
																	|| $scope.extraOrdinaryDetails[i].remarks === "") {
																swal("Please fill all the field");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.extraOrdinaryWeightageCount > 10
															|| $scope.extraOrdinaryWeightageCount < 10) {
														swal("Required weightage count is 10%");
													}
													if ($scope.extraOrdinaryWeightageCount == 10) {
														$scope
																.saveExtraOrdinaryModule(type);
													}
												}

											}
										});
					}

					$scope.submitBehaviouralCompetenceModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ($scope.formDataBC[i].comments === null
																	|| $scope.formDataBC[i].midYearSelfRating === null
																	|| $scope.formDataBC[i].midYearAssessorRating === null
																	|| $scope.formDataBC[i].comments === ""
																	|| $scope.formDataBC[i].midYearSelfRating === ""
																	|| $scope.formDataBC[i].midYearAssessorRating === "") {
																swal("Please fill all the field in mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ( 
// $scope.formDataBC[i].comments === null
// || $scope.formDataBC[i].midYearSelfRating === null
// || $scope.formDataBC[i].midYearAssessorRating === null
																	 $scope.formDataBC[i].finalYearSelfRating === null
																	|| $scope.formDataBC[i].finalYearAssessorRating === null
// || $scope.formDataBC[i].comments === ""
// || $scope.formDataBC[i].midYearSelfRating === ""
// || $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].finalYearSelfRating === ""
																	|| $scope.formDataBC[i].finalYearAssessorRating === "") {
																swal("Please fill all the field in final term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													$scope
															.saveBehaviouralCompetenceModule(type);
												}
											}
										});

					}
					$scope.submitTrainingNeedsModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
														if ($scope.trainingNeedsDetails[i].trainingTopic === null
																|| $scope.trainingNeedsDetails[i].trainingReasons === null
																|| $scope.trainingNeedsDetails[i].manHours === null
																|| $scope.trainingNeedsDetails[i].approvedReject === null
																|| $scope.trainingNeedsDetails[i].remarks === null
																|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																|| $scope.trainingNeedsDetails[i].manHours === ""
																|| $scope.trainingNeedsDetails[i].approvedReject === ""
																|| $scope.trainingNeedsDetails[i].remarks === "") {
															swal("Please fill all the field");
															$scope.checkValidation = false;
															break;
														}
													}
												}
												if ($scope.checkValidation) {
													$scope
															.saveTrainingNeedsModule(type);
												}
											}
										});
					}
					$scope.submit = function(buttonType) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												
												if ($scope.checkValidation) {
													
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0 && buttonType ==="APPROVED") {
														if($scope.managerAcknowledgement.firstManagerGoalApproval != true)
															{
															swal("Please select the acknowledgement field");
															$scope.checkValidation = false;
															}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0 && buttonType ==="APPROVED") {
														if($scope.managerAcknowledgement.firstManagerMidYearApproval != true)
														{
														swal("Please select the acknowledgement field");
														$scope.checkValidation = false;
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0 && buttonType ==="APPROVED") {
														if($scope.managerAcknowledgement.firstManagerYearEndAssessmentApproval != true)
														{
														swal("Please select the acknowledgement field");
														$scope.checkValidation = false;
														}
													}
												}
												
												
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === ""
																		|| $scope.sectionAList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].midYearAchievement === null
																		|| $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].midYearAppraisarRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].midYearAchievement === ""
																		|| $scope.sectionBList[i].midYearSelfRating === ""
																		|| $scope.sectionBList[i].midYearAppraisarRating === ""
																		|| $scope.sectionBList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																		|| $scope.sectionBList[i].finalYearAchievement === null
																		|| $scope.sectionBList[i].finalYearSelfRating === null
																		|| $scope.sectionBList[i].finalYearAppraisarRating === null
																		|| $scope.sectionBList[i].remarks === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		|| $scope.sectionBList[i].finalYearAchievement === ""
																		|| $scope.sectionBList[i].finalYearSelfRating === ""
																		|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionBList[i].remarks === ""
																		|| $scope.sectionBList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].midYearAchievement === null
																		|| $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].midYearAppraisarRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].midYearAchievement === ""
																		|| $scope.sectionCList[i].midYearSelfRating === ""
																		|| $scope.sectionCList[i].midYearAppraisarRating === ""
																		|| $scope.sectionCList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																		|| $scope.sectionCList[i].finalYearAchievement === null
																		|| $scope.sectionCList[i].finalYearSelfRating === null
																		|| $scope.sectionCList[i].finalYearAppraisarRating === null
																		|| $scope.sectionCList[i].remarks === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		|| $scope.sectionCList[i].finalYearAchievement === ""
																		|| $scope.sectionCList[i].finalYearSelfRating === ""
																		|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionCList[i].remarks === ""
																		|| $scope.sectionCList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].smartGoal === ""
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].midYearAchievement === null
																		|| $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].midYearAppraisarRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].midYearAchievement === ""
																		|| $scope.sectionDList[i].midYearSelfRating === ""
																		|| $scope.sectionDList[i].midYearAppraisarRating === ""
																		|| $scope.sectionDList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																		|| $scope.sectionDList[i].finalYearAchievement === null
																		|| $scope.sectionDList[i].finalYearSelfRating === null
																		|| $scope.sectionDList[i].finalYearAppraisarRating === null
																		|| $scope.sectionDList[i].remarks === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		|| $scope.sectionDList[i].finalYearAchievement === ""
																		|| $scope.sectionDList[i].finalYearSelfRating === ""
																		|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionDList[i].remarks === ""
																		|| $scope.sectionDList[i].isValidatedByFirstLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0
															|| $scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
															if ($scope.trainingNeedsDetails[i].trainingTopic === null
																	|| $scope.trainingNeedsDetails[i].trainingReasons === null
																	|| $scope.trainingNeedsDetails[i].manHours === null
																	|| $scope.trainingNeedsDetails[i].approvedReject === null
																	|| $scope.trainingNeedsDetails[i].remarks === null
																	|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																	|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																	|| $scope.trainingNeedsDetails[i].manHours === ""
																	|| $scope.trainingNeedsDetails[i].approvedReject === ""
																	|| $scope.trainingNeedsDetails[i].remarks === ""
																	|| $scope.trainingNeedsDetails[i].isValidatedByFirstLevel === 0) {
																swal("All fields in training needs to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
															if ($scope.extraOrdinaryDetails[i].contributions === null
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === null
																	|| $scope.extraOrdinaryDetails[i].finalScore === null
																	|| $scope.extraOrdinaryDetails[i].remarks === null
																	|| $scope.extraOrdinaryDetails[i].contributions === ""
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalScore === ""
																	|| $scope.extraOrdinaryDetails[i].remarks === ""
																	|| $scope.extraOrdinaryDetails[i].isValidatedByFirstLevel === 0) {
																swal("All fields in extra ordinary to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												

												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear == 1) {
														if ($scope.employeeCareerAspirationDetails === undefined
																|| $scope.employeeCareerAspirationDetails.initializationComments === ""
																|| $scope.employeeCareerAspirationDetails.isValidatedByFirstLevel == 0) {
															swal("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear == 1) {
														if ($scope.employeeCareerAspirationDetails.midYearComments === ""
																|| $scope.employeeCareerAspirationDetails.midYearComments === null
																|| $scope.employeeCareerAspirationDetails.midYearCommentsStatusFirstLevel == 0) {
															alert("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear == 1) {
														if ($scope.employeeCareerAspirationDetails.yearEndComments === ""
																|| $scope.employeeCareerAspirationDetails.yearEndComments === null
																|| $scope.employeeCareerAspirationDetails.yearEndCommentsStatusFirstLevel == 0) {
															swal("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
												}

												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ($scope.formDataBC[i].comments === null
																	|| $scope.formDataBC[i].midYearSelfRating === null
																	|| $scope.formDataBC[i].midYearAssessorRating === null
																	|| $scope.formDataBC[i].comments === ""
																	|| $scope.formDataBC[i].midYearSelfRating === ""
																	|| $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].isValidatedByFirstLevel == 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if (  
// $scope.formDataBC[i].comments === null
// || $scope.formDataBC[i].midYearSelfRating === null
// || $scope.formDataBC[i].midYearAssessorRating === null
																	$scope.formDataBC[i].finalYearSelfRating === null
																	|| $scope.formDataBC[i].finalYearAssessorRating === null
// || $scope.formDataBC[i].comments === ""
// || $scope.formDataBC[i].midYearSelfRating === ""
// || $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].finalYearSelfRating === ""
																	|| $scope.formDataBC[i].finalYearAssessorRating === ""
																	|| $scope.formDataBC[i].isValidatedByFirstLevel == 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													document.getElementById("main_body").className = "loading-pane";
													
													
													var input = {
														"empCode" : sharedProperties
																.getValue("employeeDetails"),
														"name" : "FIRST LEVEL MANAGER",
														"type" : buttonType,
														"appraisalYearId" : $scope.appraisalYearId.id,
														
													};
													var res = $http
															.post(
																	'/PMS/sendToManager',
																	input);
													res
															.success(function(
																	data,
																	status) {
																$scope.result = data;
																$scope.loggedUserAppraisalDetails = $scope.result.object;
																$scope.message = $scope.result.string;
																if ($scope.result.integer == 1) {
																	swal( $scope.result.string);
																	
																	if($scope.loggedUserAppraisalDetails.firstManagerGoalApproval == 1)
																		{
																		$scope.managerAcknowledgement.firstManagerGoalApproval = true;
																		$scope.managerAcknowledgement.isFirstManagerGoalApproval = $scope.loggedUserAppraisalDetails.firstManagerGoalApproval;
																		}
																	if($scope.loggedUserAppraisalDetails.firstManagerMidYearApproval == 1)
																	{
																		$scope.managerAcknowledgement.firstManagerMidYearApproval = true;
																		$scope.managerAcknowledgement.isFirstManagerMidYearApproval = $scope.loggedUserAppraisalDetails.firstManagerMidYearApproval;
																	}
																	if($scope.loggedUserAppraisalDetails.firstManagerYearEndAssessmentApproval == 1)
																	{
																		$scope.managerAcknowledgement.firstManagerYearEndAssessmentApproval = true;
																		$scope.managerAcknowledgement.isFirstManagerYearEndAssessmentApproval = $scope.loggedUserAppraisalDetails.firstManagerYearEndAssessmentApproval;
																	}
// if($scope.loggedUserAppraisalDetails.firstManagerNormalKRARejection == 1 )
// {
// $scope.managerAcknowledgement.rejectionId = "1";
// }
// if($scope.loggedUserAppraisalDetails.firstManagerNewKRARejection == 1 )
// {
// $scope.managerAcknowledgement.rejectionId = "2";
// }
																	document.getElementById("main_body").className = "";
																}
															});
												}
											}
										});
					}
				});

app
		.controller(
				'SecondLevelAppraisalController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter AppraisalController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					if (sharedProperties.getValue("employeeDetails") === undefined) {
						$window.location.href = "#/team-appraisal";
					}
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.loggedUserAppraisalDetails = [];
					$scope.employeeCareerAspirationDetails = [];
					$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
					$scope.selectsRating = [0,1, 2, 3, 4, 5 ];
					$scope.approveRejectList = [ "Approve", "Reject" ];
					$scope.sectionAList = [];
					$scope.sectionBList = [];
					$scope.sectionCList = [];
					$scope.sectionDList = [];
					$scope.sectionAListOld = [];
					$scope.sectionBListOld = [];
					$scope.sectionCListOld = [];
					$scope.sectionDListOld = [];
					$scope.WeightageCount = 5;
					$scope.extraOrdinaryWeightageCount = 0;
					$scope.BehavioralComptenceTotalCalculation = 0;
					$scope.formDataKRA = {
						"sectionAList" : $scope.sectionAList,
						"sectionBList" : $scope.sectionBList,
						"sectionCList" : $scope.sectionCList,
						"sectionDList" : $scope.sectionDList
					};

					$scope.extraOrdinaryDetails = [ {
						'contributions' : null,
						'contributionDetails' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'finalScore' : null,
						// 'child' : $scope.dynamicExtraOrdinaryDetails,
						'remarks' : null
					} ];
					$scope.trainingNeedsDetails = [ {
						'trainingTopic' : null,
						'trainingReasons' : null,
						'manHours' : null
					// 'child' : $scope.dynamicTrainingNeedsDetails,
					} ];
					$scope.getActiveTabId = function(data) {
						switch (data) {
						case 0:
							$scope.getEmployeeKraDetails();
							break;
						case 1:
							$scope.getExtraOrdinaryDetails();
							break;
						case 2:
							$scope.getBehavioralComptenceDetails();
							break;
						case 3:
							$scope.getCareerAspirationDetails();
							break;
						case 4:
							$scope.getTrainingNeedsDetails();
							break;
						case 5:
							$scope.finalSubmissionData();
							$scope.getExtraOrdinaryDetails();
							$scope.getCareerAspirationDetails();
							$scope.getTrainingNeedsDetails();
							$scope.getBehavioralComptenceDetails();
							break;
						}
					}

					$scope.printSecondLevel = function() {
						var mywindow = window.open();
						mywindow.document
								.write('<html><head><title>HERO FUTURE ENERGIES</title>');
						mywindow.document
								.write('<!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Print</title>');
						// mywindow.document.write('<link rel=\"stylesheet\"
						// href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css"
						// type=\"text/css\" media=\"print\">');
						// mywindow.document.write('<link rel=\"stylesheet\"
						// href="resources/common/css/index.css?v=0.13"
						// type=\"text/css\" media=\"print\"/>');
						// mywindow.document.write('<link
						// rel=\"stylesheet\"href="resources/common/css/default.css?v=0.2"
						// type=\"text/css\" media=\"print\" />');
						//						

						mywindow.document
								.write('</head><body><img id="headerLogoImg" src="resources/common/images/logo.jpg" class="hidden-xs" style="float: inherit;">');
						// mywindow.document.write('<h1>' + document.title +
						// '</h1>');
						mywindow.document
								.write(document
										.getElementById("viewAppraisalSecondLevel").innerHTML);
						mywindow.document.write('</body></html>');

						mywindow.document.close(); // necessary for IE >= 10
						mywindow.focus(); // necessary for IE >= 10*/

						mywindow.print();
						$timeout(function() {
							mywindow.close();
						}, 5000);

						return true;
					}
					$scope.addNewChoice = function(data) {
						if (data === "sectionA") {
							$scope.sectionAList.push({
								'sectionName' : 'Section A',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						}  if (data === "sectionB") {
							$scope.sectionBList.push({
								'sectionName' : 'Section B',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						}  if (data === "sectionC") {
							$scope.sectionCList.push({
								'sectionName' : 'Section C',
								'smartGoal' : null,
								'target' : null,
								'achievementDate' : null,
								'weightage' : 0,
								'kraType' : null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
						} 
					};

					$scope.getOldKRAData = function()
					{

						$scope.WeightageCountOld = 5;
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "EmployeeOldKra",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.resultOld = data.object[0];
									$scope.loggedUserKRADetailsOld = data.object[1].object;
									var p = 0;
									var q = 0;
									var r = 0;
									var s = 0;
									$scope.WeightageCountOld = 0;
									$scope.kraToalCalculationOld = 0;
									$scope.loggedUserAppraisalDetails = $scope.resultOld.object;
									
									for (var i = 0; i < $scope.loggedUserKRADetailsOld.length; i++) {
// $scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// $scope.currentEmpCode = $scope.loggedUserKRADetails[i].empCode;
										if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section A") {
											$scope.sectionAListOld[p] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionAListOld[p].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionAListOld[p].midYearAppraisarRating !=null && $scope.sectionAListOld[p].finalYearAppraisarRating !=null && $scope.sectionAListOld[p].weightage !=null )
												{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionAListOld[p].midYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100) *30/100) +
														(((($scope.sectionAListOld[p].finalYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100) *
														70/100)));
												}
											if($scope.sectionAListOld[p].midYearAppraisarRating ==null && $scope.sectionAListOld[p].finalYearAppraisarRating !=null && $scope.sectionAListOld[p].weightage !=null )
											{
											$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
													(((($scope.sectionAListOld[p].finalYearAppraisarRating * $scope.sectionAListOld[p].weightage)/100))));
											}
											p++;
										} else if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section B") {
											$scope.sectionBListOld[q] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionBListOld[q].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionBListOld[q].midYearAppraisarRating !=null && $scope.sectionBListOld[q].finalYearAppraisarRating !=null && $scope.sectionBListOld[q].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionBListOld[q].midYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100) *30/100) +
														(((($scope.sectionBListOld[q].finalYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100) *
														70/100)));
											}
											if($scope.sectionBListOld[q].midYearAppraisarRating ==null && $scope.sectionBListOld[q].finalYearAppraisarRating !=null && $scope.sectionBListOld[q].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionBListOld[q].finalYearAppraisarRating * $scope.sectionBListOld[q].weightage)/100))));
											}
											q++;
										} else if ($scope.loggedUserKRADetailsOld[i].sectionName == "Section C") {
											$scope.sectionCListOld[r] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionCListOld[r].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionCListOld[r].midYearAppraisarRating !=null && $scope.sectionCListOld[r].finalYearAppraisarRating !=null && $scope.sectionCListOld[r].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionCListOld[r].midYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100) *30/100) +
														(((($scope.sectionCListOld[r].finalYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100) *
														70/100)));
											}
											if($scope.sectionCListOld[r].midYearAppraisarRating ==null && $scope.sectionCListOld[r].finalYearAppraisarRating !=null && $scope.sectionCListOld[r].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionCListOld[r].finalYearAppraisarRating * $scope.sectionCListOld[r].weightage)/100))));
											}
											r++;
										} else {
											$scope.sectionDListOld[s] = $scope.loggedUserKRADetailsOld[i];
											if ($scope.loggedUserKRADetailsOld[i].achievementDate != null) {
												$scope.sectionDListOld[s].achievementDate = new Date(
														$scope.loggedUserKRADetailsOld[i].achievementDate);
												$scope.WeightageCountOld = $scope.WeightageCountOld
														+ parseInt($scope.loggedUserKRADetailsOld[i].weightage);
											}
											if($scope.sectionDListOld[s].midYearAppraisarRating !=null && $scope.sectionDListOld[s].finalYearAppraisarRating !=null && $scope.sectionDListOld[s].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (((($scope.sectionDListOld[s].midYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100) *30/100) +
														(((($scope.sectionDListOld[s].finalYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100) *
														70/100)));
											}
											if($scope.sectionDListOld[s].midYearAppraisarRating ==null && $scope.sectionDListOld[s].finalYearAppraisarRating !=null && $scope.sectionDListOld[s].weightage !=null )
											{
												$scope.kraToalCalculationOld = $scope.kraToalCalculationOld + (
														(((($scope.sectionDListOld[s].finalYearAppraisarRating * $scope.sectionDListOld[s].weightage)/100))));
											}
											s++;
										}

									}
								});
					
					}
					$scope.removeNewChoice = function(index, data) {
						if (data === "sectionA") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionAList[index].weightage);
							$scope.sectionAList.splice(index, 1);
							
						}  if (data === "sectionB") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionBList[index].weightage);
							$scope.sectionBList.splice(index, 1);
							
						}  if (data === "sectionC") {
							$scope.WeightageCount = $scope.WeightageCount - parseInt($scope.sectionCList[index].weightage);
							$scope.sectionCList.splice(index, 1);
							
						}
					};
					$scope.countWeightage = function() {
						$scope.WeightageCount = 0;
						for (var i = 0; i < $scope.sectionAList.length; i++) {
							if ($scope.sectionAList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionAList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionBList.length; i++) {
							if ($scope.sectionBList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionBList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionCList.length; i++) {
							if ($scope.sectionCList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionCList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionDList.length; i++) {
							if ($scope.sectionDList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionDList[i].weightage);
						}
					}
					$scope.countExtraOrdinaryWeightage = function() {
						$scope.extraOrdinaryWeightageCount = 0;
						for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
							if ($scope.extraOrdinaryDetails[i].weightage != null)
								$scope.extraOrdinaryWeightageCount = $scope.extraOrdinaryWeightageCount
										+ parseInt($scope.extraOrdinaryDetails[i].weightage);
						}
					}
					$scope.finalSubmissionData = function() {
						input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/get-employee-appraisal-details', input);
						res.success(function(data, status) {
							$scope.subEmployeeDetails = data.object[0];
						});
					}

					$scope.getExtraOrdinaryDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "ExtraOrdinary",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									if ($scope.result.object == null) {
										$scope.loggedUserAppraisalDetails  = [];
										$scope.loggedUserAppraisalDetails
												.push({
													"initializationYear" : null,
													"midYear" : null,
													"finalYear" : null
												});
									} else {
										$scope.loggedUserAppraisalDetails = $scope.result.object;
									}

									if (data.object[1].length > 0) {
										$scope.extraOrdinaryDetails = data.object[1];
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											if ($scope.extraOrdinaryDetails[i].isValidatedBySecondLevel == 1) {
												$scope.extraOrdinaryButtonDisabled = true;
												break;
											}

										}
										$scope.extraOrdinaryWeightageCount = 0;
										$scope.ExtraOrdinaryTotalCalculation = 0;
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											$scope.extraOrdinaryWeightageCount = $scope.extraOrdinaryWeightageCount
													+ parseInt($scope.extraOrdinaryDetails[i].weightage);
											if ($scope.extraOrdinaryDetails[i].weightage != null && $scope.extraOrdinaryDetails[i].finalYearAppraisarRating !=null) {
												$scope.ExtraOrdinaryTotalCalculation = $scope.ExtraOrdinaryTotalCalculation +
												(($scope.extraOrdinaryDetails[i].weightage * $scope.extraOrdinaryDetails[i].finalYearAppraisarRating)/100);
											}
										}
									}
								});
					}
					$scope.getEmployeeKraDetails = function() {
						$scope.kraDetailsId = [];
						$scope.WeightageCount = 5;
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : "EmployeeKra",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserKRADetails = data.object[1].object;
									var p = 0;
									var q = 0;
									var r = 0;
									var s = 0;
									$scope.WeightageCount = 0;
									$scope.kraToalCalculation = 0;
									$scope.kraDetailsId = [];
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										if ($scope.loggedUserKRADetails[i].isValidatedBySecondLevel == 1) {
											$scope.kraButtonDisabled = true;
											$scope.WeightageCount = 0;
											break;
										}
									}
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										if ($scope.loggedUserKRADetails[i].kraType == "NEW") {
											$scope.kraButtonSendToManager = true;
											break;
										}
									}
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
										$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
										$scope.currentEmpCode = $scope.loggedUserKRADetails[i].empCode;
										if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
											$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionAList[p].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null )
											{
											$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
													(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
													70/100)));
											}
											if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].finalYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null )
											{
											$scope.kraToalCalculation = $scope.kraToalCalculation + (
													(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
											}
											p++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
											$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionBList[q].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
														70/100)));
											}
											if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].finalYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
											}
											q++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
											$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionCList[r].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
														70/100)));
											}
											if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].finalYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
											}
											r++;
										} else {
											$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
											if ($scope.loggedUserKRADetails[i].achievementDate != null) {
												$scope.sectionDList[s].achievementDate = new Date(
														$scope.loggedUserKRADetails[i].achievementDate);
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
														70/100)));
											}
											if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].finalYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null )
											{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
											}
											s++;
										}

									}
								});
					}
					$scope.getBehavioralComptenceDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "BehavioralComptence"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.formDataBC = data.object[1];
									$scope.formMainData = data.object[2];
									$scope.BehavioralComptenceTotalCalculation = 0;
									// if ($scope.result.object == null) {
									// $scope.loggedUserAppraisalDetails
									// .push({
									// "initializationYear" : null,
									// "midYear" : null,
									// "finalYear" : null
									// });
									// } else {
									//										
									// }
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									if($scope.loggedUserAppraisalDetails !=null)
										{
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.behaviouralCompetenceButtonDisabled = true;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 1
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.behaviouralCompetenceButtonDisabled = false;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
										$scope.behaviouralCompetenceButtonDisabled = false;
									}
										}
									for (var i = 0; i < $scope.formMainData.length; i++) {
										if ($scope.formMainData[i].isValidatedBySecondLevel == 1) {
											$scope.behaviouralCompetenceButtonDisabled = true;
											break;
										}
									}
									for (var i = 0; i < $scope.formMainData.length; i++) {
										$scope.formDataBC[i].behaviouralCompetenceDetailsId = $scope.formMainData[i].id;
										$scope.formDataBC[i].comments = $scope.formMainData[i].comments;
										$scope.formDataBC[i].isValidatedBySecondLevel = $scope.formMainData[i].isValidatedBySecondLevel;
										$scope.formDataBC[i].midYearSelfRating = $scope.formMainData[i].midYearSelfRating;
										$scope.formDataBC[i].midYearAssessorRating = $scope.formMainData[i].midYearAssessorRating;
										$scope.formDataBC[i].finalYearSelfRating = $scope.formMainData[i].finalYearSelfRating;
										$scope.formDataBC[i].finalYearAssessorRating = $scope.formMainData[i].finalYearAssessorRating;
										$scope.formDataBC[i].midYearHighlights = $scope.formMainData[i].midYearHighlights;
										$scope.formDataBC[i].finalYearHighlights = $scope.formMainData[i].finalYearHighlights;
										
										if($scope.formDataBC[i].midYearAssessorRating !=null && $scope.formDataBC[i].weightage !=null && $scope.formDataBC[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formDataBC[i].midYearAssessorRating
												* $scope.formDataBC[i].weightage )/100) *30/100) +
												((($scope.formDataBC[i].finalYearAssessorRating * $scope.formDataBC[i].weightage)/100) *
												70/100));
										}
										if($scope.formDataBC[i].midYearAssessorRating ==null && $scope.formDataBC[i].weightage !=null && $scope.formDataBC[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formDataBC[i].finalYearAssessorRating * $scope.formDataBC[i].weightage)/100)));
										}
									}
								});
					}
					$scope.getTrainingNeedsDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "TrainingNeeds"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									if ($scope.result.length > 0) {
										$scope.trainingNeedsDetails = $scope.result;
									}
									for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
										if ($scope.trainingNeedsDetails[i].isValidatedBySecondLevel == 1) {
											$scope.trainingNeedsButtonDisabled = true;
										} else {
											$scope.trainingNeedsButtonDisabled = false;
										}
									}
									// $scope.dynamicTrainingNeedsDetails =
									// data.object[1];
									// $scope.tishu = angular
									// .copy($scope.dynamicTrainingNeedsDetails);
								});
					}
					$scope.getCareerAspirationDetails = function() {
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "CareerAspiration"
						};
						var res = $http
								.post('/PMS/get-manager-Details', input);
						res
								.success(function(data, status) {
									$scope.employeeCareerAspirationDetails = data.object[0][0];
// if($scope.employeeCareerAspirationDetails !=undefined &&
// $scope.loggedUserAppraisalDetails.initializationYear == 1)
// {
// /*&& $scope.loggedUserAppraisalDetails.initializationYear == 1*/
// if ($scope.employeeCareerAspirationDetails.isValidatedBySecondLevel == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// /*&& $scope.loggedUserAppraisalDetails.midYear == 1*/
// if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusSecondLevel
// == 1 && $scope.loggedUserAppraisalDetails.midYear == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// /*&& $scope.loggedUserAppraisalDetails.finalYear == 1*/
// if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusSecondLevel
// == 1 && $scope.loggedUserAppraisalDetails.finalYear == 1
// ) {
// $scope.isCareerAspirationsSubmitted = true;
// }
// }
									
									if ($scope.employeeCareerAspirationDetails != undefined && $scope.loggedUserAppraisalDetails != null) {
										if ($scope.employeeCareerAspirationDetails.isValidatedBySecondLevel == 1
												&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusSecondLevel == 1
												&& $scope.loggedUserAppraisalDetails.midYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusSecondLevel == 1
												&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
											$scope.isCareerAspirationsSubmitted = true;
										}
										}
								});
					}
					$scope.saveBehaviouralCompetenceModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"type" : type,
							"behaviouralCompetence" : $scope.formDataBC,
							"name" : "SECOND LEVEL MANAGER"
						};
						var res = $http
								.post(
										'/PMS/add-manager-behavioral-comptence-details',
										input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.getBehavioralComptenceDetails();
										document.getElementById("main_body").className = "";
									}
								});
					}
					$scope.saveTrainingNeedsModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : sharedProperties
									.getValue("employeeDetails"),
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : type,
							"name" : "SECOND LEVEL MANAGER",
							"trainingNeedsDetails" : $scope.trainingNeedsDetails
						};
						var res = $http.post(
								'/PMS/add-manager-trainingNeeds-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.trainingNeedsDetails = $scope.result.object;
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											if ($scope.trainingNeedsDetails[i].isValidatedBySecondLevel == 1) {
												$scope.trainingNeedsButtonDisabled = true;
											} else {
												$scope.trainingNeedsButtonDisabled = false;
											}
										}
										document.getElementById("main_body").className = "";
									}
								});
					}
					$scope.saveCareerAspirationsModule = function(type) {
						if (($scope.employeeCareerAspirationDetails === undefined
								|| $scope.employeeCareerAspirationDetails.initializationManagerReview === null
								|| $scope.employeeCareerAspirationDetails.initializationComments === null
								|| $scope.employeeCareerAspirationDetails.initializationManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
							swal("Please enter career aspirations");
						} else if (($scope.employeeCareerAspirationDetails.midYearComments === null ||
								$scope.employeeCareerAspirationDetails.midYearCommentsManagerReview === null
								|| $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.midYear == 1) {
							swal("Please enter career aspirations for mid term");
						}  else if (($scope.employeeCareerAspirationDetails.yearEndComments === null ||
								$scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview === null
								|| $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview == "")
								&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
							swal("Please enter career aspirations for final term");
						} else {
							document.getElementById("main_body").className = "loading-pane";
							var input = {
								"empCode" : sharedProperties
										.getValue("employeeDetails"),
								"initializationComments" : $scope.employeeCareerAspirationDetails.initializationComments,
								"initializationManagerReview" : $scope.employeeCareerAspirationDetails.initializationManagerReview,
								"midYearComments" : $scope.employeeCareerAspirationDetails.midYearComments,
								"midYearCommentsManagerReview" : $scope.employeeCareerAspirationDetails.midYearCommentsManagerReview,
								"yearEndComments" : $scope.employeeCareerAspirationDetails.yearEndComments,
								"yearEndCommentsManagerReview" : $scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview,
								"id" : $scope.employeeCareerAspirationDetails.id,
								"appraisalYearId" : $scope.appraisalYearId.id,
								"type" : type,
								"name" : "SECOND LEVEL MANAGER"
							};
							var res = $http
									.post(
											'/PMS/add-manager-career_aspirations-details',
											input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											$scope.employeeCareerAspirationDetails = $scope.result.object[0];
											if ($scope.employeeCareerAspirationDetails.isValidatedBySecondLevel == 1
													&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.midYearCommentsStatusSecondLevel == 1
													&& $scope.loggedUserAppraisalDetails.midYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatusSecondLevel == 1
													&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											swal($scope.result.string);
											document.getElementById("main_body").className = "";
										}

									});
						}
					}
					$scope.saveKRAModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
// var input = {
// "kraDetails" : $scope.formDataKRA,
// "type" : type,
// "list" : $scope.kraDetailsId,
// "empCode":$scope.currentEmpCode,
// "name" : "SECOND LEVEL MANAGER"
// };
						if($scope.formDataKRA.sectionAList[0].kraType === "NEW")
						{
						$scope.input = {
							"kraNewDetails" : $scope.formDataKRA,
							"type" : type,
							"list" : $scope.kraDetailsId,
							"empCode":$scope.currentEmpCode,
							"name" : "SECOND LEVEL MANAGER"
						};
					   }
						if($scope.formDataKRA.sectionAList[0].kraType === null)
						{
							$scope.input = {
								"kraDetails" : $scope.formDataKRA,
								"type" : type,
								"list" : $scope.kraDetailsId,
								"empCode":$scope.currentEmpCode,
								"name" : "SECOND LEVEL MANAGER"
							};
					   }	
						var res = $http.post('/PMS/add-manager-kra-details',
								$scope.input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.getEmployeeKraDetails();
										document.getElementById("main_body").className = "";
									}
								});
					}
					$scope.saveExtraOrdinaryModule = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"type" : type,
							"extraOrdinaryDetails" : $scope.extraOrdinaryDetails,
							"name" : "SECOND LEVEL MANAGER"
						};
						var res = $http.post(
								'/PMS/add-manager-extraOrdinary-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.getExtraOrdinaryDetails();
										document.getElementById("main_body").className = "";
									}
								});
					}
					
					
					
					$scope.submitKRAModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === "") {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].midYearAppraisarRating === "") {
																	swal("Please fill all the field in Section A for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === "") {
																	swal("Please fill all the field in Section A for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === "") {
																	swal("Please fill all the field in Section B");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
																	|| $scope.sectionBList[i].midYearAchievement === null
																	|| $scope.sectionBList[i].midYearSelfRating === null
																	|| $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
																	|| $scope.sectionBList[i].midYearAchievement === ""
																	|| $scope.sectionBList[i].midYearSelfRating === ""
																	|| $scope.sectionBList[i].midYearAppraisarRating === "") {
																swal("Please fill all the field in Section B for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].finalYearAchievement === null
																	|| $scope.sectionBList[i].finalYearSelfRating === null
																	|| $scope.sectionBList[i].finalYearAppraisarRating === null
																	|| $scope.sectionBList[i].remarks === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																	|| $scope.sectionBList[i].finalYearAchievement === ""
																	|| $scope.sectionBList[i].finalYearSelfRating === ""
																	|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionBList[i].remarks === "") {
																swal("Please fill all the field in Section B for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
												if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === "") {
																swal("Please fill all the field in Section C");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
																	|| $scope.sectionCList[i].midYearAchievement === null
																	|| $scope.sectionCList[i].midYearSelfRating === null
																	|| $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
																	|| $scope.sectionCList[i].midYearAchievement === ""
																	|| $scope.sectionCList[i].midYearSelfRating === ""
																	|| $scope.sectionCList[i].midYearAppraisarRating === "") {
																swal("Please fill all the field in Section C for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].finalYearAchievement === null
																	|| $scope.sectionCList[i].finalYearSelfRating === null
																	|| $scope.sectionCList[i].finalYearAppraisarRating === null
																	|| $scope.sectionCList[i].remarks === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																	|| $scope.sectionCList[i].finalYearAchievement === ""
																	|| $scope.sectionCList[i].finalYearSelfRating === ""
																	|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionCList[i].remarks === "") {
																swal("Please fill all the field in Section C for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
												if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === "") {
																swal("Please fill all the field in Section D");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
																	|| $scope.sectionDList[i].midYearAchievement === null
																	|| $scope.sectionDList[i].midYearSelfRating === null
																	|| $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
																	|| $scope.sectionDList[i].midYearAchievement === ""
																	|| $scope.sectionDList[i].midYearSelfRating === ""
																	|| $scope.sectionDList[i].midYearAppraisarRating === "") {
																swal("Please fill all the field in Section D for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].finalYearAchievement === null
																	|| $scope.sectionDList[i].finalYearSelfRating === null
																	|| $scope.sectionDList[i].finalYearAppraisarRating === null
																	|| $scope.sectionDList[i].remarks === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																	|| $scope.sectionDList[i].finalYearAchievement === ""
																	|| $scope.sectionDList[i].finalYearSelfRating === ""
																	|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionDList[i].remarks === "") {
																swal("Please fill all the field in Section D for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
												if ($scope.WeightageCount > 100
														|| $scope.WeightageCount < 100) {
													swal("Required weightage count is 100%");
												}
												if ($scope.WeightageCount == 100) {
													$scope.saveKRAModule(type);
												}
											}

										});
					}
					
					$scope.submitNewKRAModule = function(type)
					{

						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionAList.length; i++) {
// if ($scope.sectionAList[i].smartGoal != null
// && $scope.sectionAList[i].smartGoal != "") {
// if ($scope.sectionAList[i].target === null
// || $scope.sectionAList[i].achievementDate === null
// || $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].target === ""
// || $scope.sectionAList[i].achievementDate === ""
// || $scope.sectionAList[i].weightage === "") {
// swal("Please fill all the field in Section A");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
													if ($scope.loggedUserAppraisalDetails === null) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		) {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		) {
																	swal("Please fill all the field in Section A");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === "") {
																	swal("Please fill all the field in Section A for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
// if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionBList.length; i++) {
// if ($scope.sectionBList[i].smartGoal != null
// && $scope.sectionBList[i].smartGoal != "") {
// if ($scope.sectionBList[i].target === null
// || $scope.sectionBList[i].achievementDate === null
// || $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].target === ""
// || $scope.sectionBList[i].achievementDate === ""
// || $scope.sectionBList[i].weightage === "") {
// swal("Please fill all the field in Section B");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
// }
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section B");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section B");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																	|| $scope.sectionBList[i].finalYearAchievement === null
																	|| $scope.sectionBList[i].finalYearSelfRating === null
																	|| $scope.sectionBList[i].finalYearAppraisarRating === null
																	|| $scope.sectionBList[i].remarks === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																	|| $scope.sectionBList[i].finalYearAchievement === ""
																	|| $scope.sectionBList[i].finalYearSelfRating === ""
																	|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionBList[i].remarks === "") {
																swal("Please fill all the field in Section B for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionCList.length; i++) {
// if ($scope.sectionCList[i].smartGoal != null
// && $scope.sectionCList[i].smartGoal != "") {
// if ($scope.sectionCList[i].target === null
// || $scope.sectionCList[i].achievementDate === null
// || $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].target === ""
// || $scope.sectionCList[i].achievementDate === ""
// || $scope.sectionCList[i].weightage === "") {
// swal("Please fill all the field in Section C");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section C");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section C");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																	|| $scope.sectionCList[i].finalYearAchievement === null
																	|| $scope.sectionCList[i].finalYearSelfRating === null
																	|| $scope.sectionCList[i].finalYearAppraisarRating === null
																	|| $scope.sectionCList[i].remarks === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																	|| $scope.sectionCList[i].finalYearAchievement === ""
																	|| $scope.sectionCList[i].finalYearSelfRating === ""
																	|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionCList[i].remarks === "") {
																swal("Please fill all the field in Section C for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
// if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
// for (var i = 0; i < $scope.sectionDList.length; i++) {
// if ($scope.sectionDList[i].smartGoal != null
// && $scope.sectionDList[i].smartGoal != "") {
// if ($scope.sectionDList[i].target === null
// || $scope.sectionDList[i].achievementDate === null
// || $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].target === ""
// || $scope.sectionDList[i].achievementDate === ""
// || $scope.sectionDList[i].weightage === "") {
// swal("Please fill all the field in Section D");
// $scope.checkValidation = false;
// break;
// }
// }
// }
// }
												
												if ($scope.loggedUserAppraisalDetails === null) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section D for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												
												else if ($scope.loggedUserAppraisalDetails.midYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		) {
																swal("Please fill all the field in Section D for mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												else if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																	|| $scope.sectionDList[i].finalYearAchievement === null
																	|| $scope.sectionDList[i].finalYearSelfRating === null
																	|| $scope.sectionDList[i].finalYearAppraisarRating === null
																	|| $scope.sectionDList[i].remarks === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																	|| $scope.sectionDList[i].finalYearAchievement === ""
																	|| $scope.sectionDList[i].finalYearSelfRating === ""
																	|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																	|| $scope.sectionDList[i].remarks === "") {
																swal("Please fill all the field in Section D for Final Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
											}
											if ($scope.checkValidation) {
												if ($scope.WeightageCount > 100
														|| $scope.WeightageCount < 100) {
													swal("Required weightage count is 100%");
												}
												if ($scope.WeightageCount == 100) {
													$scope.saveKRAModule(type);
												}
											}

										});
					
					}
					$scope.submitExtraOrdinaryModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
															if ($scope.extraOrdinaryDetails[i].contributions === null
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																	|| $scope.extraOrdinaryDetails[i].weightage === null
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === null
																	|| $scope.extraOrdinaryDetails[i].finalScore === null
																	|| $scope.extraOrdinaryDetails[i].remarks === null
																	|| $scope.extraOrdinaryDetails[i].contributions === ""
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																	|| $scope.extraOrdinaryDetails[i].weightage === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalScore === ""
																	|| $scope.extraOrdinaryDetails[i].remarks === "") {
																swal("Please fill all the field");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.extraOrdinaryWeightageCount > 10
															|| $scope.extraOrdinaryWeightageCount < 10) {
														swal("Required weightage count is 10%");
													}
													if ($scope.extraOrdinaryWeightageCount == 10) {
														$scope
																.saveExtraOrdinaryModule(type);
													}
												}
											}
										});
					}

					$scope.submitBehaviouralCompetenceModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ( 
																	$scope.formDataBC[i].comments === null
																	|| $scope.formDataBC[i].midYearSelfRating === null
																	|| $scope.formDataBC[i].midYearAssessorRating === null
																	|| $scope.formDataBC[i].comments === ""
																	|| $scope.formDataBC[i].midYearSelfRating === ""
																	|| $scope.formDataBC[i].midYearAssessorRating === "") {
																swal("Please fill all the field in mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ( 
// $scope.formDataBC[i].comments === null
// || $scope.formDataBC[i].midYearSelfRating === null
// || $scope.formDataBC[i].midYearAssessorRating === null
																	 $scope.formDataBC[i].finalYearSelfRating === null
																	|| $scope.formDataBC[i].finalYearAssessorRating === null
// || $scope.formDataBC[i].comments === ""
// || $scope.formDataBC[i].midYearSelfRating === ""
// || $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].finalYearSelfRating === ""
																	|| $scope.formDataBC[i].finalYearAssessorRating === "") {
																swal("Please fill all the field in final term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													$scope
															.saveBehaviouralCompetenceModule(type);
												}
											}
										});

					}
					$scope.submitTrainingNeedsModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
														if ($scope.trainingNeedsDetails[i].trainingTopic === null
																|| $scope.trainingNeedsDetails[i].trainingReasons === null
																|| $scope.trainingNeedsDetails[i].manHours === null
																|| $scope.trainingNeedsDetails[i].approvedReject === null
																|| $scope.trainingNeedsDetails[i].remarks === null
																|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																|| $scope.trainingNeedsDetails[i].manHours === ""
																|| $scope.trainingNeedsDetails[i].approvedReject === ""
																|| $scope.trainingNeedsDetails[i].remarks === "") {
															swal("Please fill all the field");
															$scope.checkValidation = false;
															break;
														}
													}
												}
												if ($scope.checkValidation) {
													$scope
															.saveTrainingNeedsModule(type);
												}
											}
										});
					}
					$scope.submitCareerAspirationsModule = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if (($scope.employeeCareerAspirationDetails === undefined
													|| $scope.employeeCareerAspirationDetails.initializationComments === "")
														&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
													swal("Please enter career aspirations");
												} else if (($scope.employeeCareerAspirationDetails.midYearComments === "" ||
														$scope.employeeCareerAspirationDetails.midYearCommentsManagerReview === "")
														&& $scope.loggedUserAppraisalDetails.midYear == 1) {
													swal("Please enter career aspirations for mid term");
												}else if (($scope.employeeCareerAspirationDetails.yearEndComments === "" ||
														$scope.employeeCareerAspirationDetails.yearEndCommentsManagerReview === "")
														&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
													swal("Please enter career aspirations for final term");
												} else {
													$scope.saveCareerAspirationsModule(type);
												}
											}
										});
					}
					$scope.submit = function(buttonType) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAppraisarRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAppraisarRating === null
																		|| $scope.sectionAList[i].remarks === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAppraisarRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionAList[i].remarks === ""
																		|| $scope.sectionAList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].smartGoal === null
																		|| $scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].midYearAchievement === null
																		|| $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].midYearAppraisarRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].midYearAchievement === ""
																		|| $scope.sectionBList[i].midYearSelfRating === ""
																		|| $scope.sectionBList[i].midYearAppraisarRating === ""
																		|| $scope.sectionBList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAppraisarRating === null
																		|| $scope.sectionBList[i].finalYearAchievement === null
																		|| $scope.sectionBList[i].finalYearSelfRating === null
																		|| $scope.sectionBList[i].finalYearAppraisarRating === null
																		|| $scope.sectionBList[i].remarks === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAppraisarRating === ""
																		|| $scope.sectionBList[i].finalYearAchievement === ""
																		|| $scope.sectionBList[i].finalYearSelfRating === ""
																		|| $scope.sectionBList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionBList[i].remarks === ""
																		|| $scope.sectionBList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].midYearAchievement === null
																		|| $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].midYearAppraisarRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].midYearAchievement === ""
																		|| $scope.sectionCList[i].midYearSelfRating === ""
																		|| $scope.sectionCList[i].midYearAppraisarRating === ""
																		|| $scope.sectionCList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAppraisarRating === null
																		|| $scope.sectionCList[i].finalYearAchievement === null
																		|| $scope.sectionCList[i].finalYearSelfRating === null
																		|| $scope.sectionCList[i].finalYearAppraisarRating === null
																		|| $scope.sectionCList[i].remarks === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAppraisarRating === ""
																		|| $scope.sectionCList[i].finalYearAchievement === ""
																		|| $scope.sectionCList[i].finalYearSelfRating === ""
																		|| $scope.sectionCList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionCList[i].remarks === ""
																		|| $scope.sectionCList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].midYearAchievement === null
																		|| $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].midYearAppraisarRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].midYearAchievement === ""
																		|| $scope.sectionDList[i].midYearSelfRating === ""
																		|| $scope.sectionDList[i].midYearAppraisarRating === ""
																		|| $scope.sectionDList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAppraisarRating === null
																		|| $scope.sectionDList[i].finalYearAchievement === null
																		|| $scope.sectionDList[i].finalYearSelfRating === null
																		|| $scope.sectionDList[i].finalYearAppraisarRating === null
																		|| $scope.sectionDList[i].remarks === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAppraisarRating === ""
																		|| $scope.sectionDList[i].finalYearAchievement === ""
																		|| $scope.sectionDList[i].finalYearSelfRating === ""
																		|| $scope.sectionDList[i].finalYearAppraisarRating === ""
																		|| $scope.sectionDList[i].remarks === ""
																		|| $scope.sectionDList[i].isValidatedBySecondLevel === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0
															|| $scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
															if ($scope.trainingNeedsDetails[i].trainingTopic === null
																	|| $scope.trainingNeedsDetails[i].trainingReasons === null
																	|| $scope.trainingNeedsDetails[i].manHours === null
																	|| $scope.trainingNeedsDetails[i].approvedReject === null
																	|| $scope.trainingNeedsDetails[i].remarks === null
																	|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																	|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																	|| $scope.trainingNeedsDetails[i].manHours === ""
																	|| $scope.trainingNeedsDetails[i].approvedReject === ""
																	|| $scope.trainingNeedsDetails[i].remarks === ""
																	|| $scope.trainingNeedsDetails[i].isValidatedBySecondLevel === 0) {
																swal("All fields in training needs to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
															if ($scope.extraOrdinaryDetails[i].contributions === null
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === null
																	|| $scope.extraOrdinaryDetails[i].finalScore === null
																	|| $scope.extraOrdinaryDetails[i].remarks === null
																	|| $scope.extraOrdinaryDetails[i].contributions === ""
																	|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalYearAppraisarRating === ""
																	|| $scope.extraOrdinaryDetails[i].finalScore === ""
																	|| $scope.extraOrdinaryDetails[i].remarks === ""
																	|| $scope.extraOrdinaryDetails[i].isValidatedBySecondLevel === 0) {
																swal("All fields in extra Ordinary to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if ($scope.formDataBC[i].comments === null
																	|| $scope.formDataBC[i].midYearSelfRating === null
																	|| $scope.formDataBC[i].midYearAssessorRating === null
																	|| $scope.formDataBC[i].comments === ""
																	|| $scope.formDataBC[i].midYearSelfRating === ""
																	|| $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].isValidatedBySecondLevel === 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}

													if ($scope.checkValidation) {
														if ($scope.loggedUserAppraisalDetails.initializationYear == 1) {
															if ($scope.employeeCareerAspirationDetails === undefined
																	|| $scope.employeeCareerAspirationDetails.initializationComments === ""
																	|| $scope.employeeCareerAspirationDetails.isValidatedBySecondLevel == 0) {
																swal("All fields in career aspirations to be filled.");
																$scope.checkValidation = false;
															}
														}
														if ($scope.loggedUserAppraisalDetails.midYear == 1) {
															if ($scope.employeeCareerAspirationDetails.midYearComments === ""
																	|| $scope.employeeCareerAspirationDetails.midYearComments === null
																	|| $scope.employeeCareerAspirationDetails.midYearCommentsStatusSecondLevel == 0) {
																swal("All fields in career aspirations to be filled.");
																$scope.checkValidation = false;
															}
														}
														if ($scope.loggedUserAppraisalDetails.finalYear == 1) {
															if ($scope.employeeCareerAspirationDetails.yearEndComments === ""
																	|| $scope.employeeCareerAspirationDetails.yearEndComments === null
																	|| $scope.employeeCareerAspirationDetails.yearEndCommentsStatusSecondLevel == 0) {
																swal("All fields in career aspirations to be filled.");
																$scope.checkValidation = false;
															}
														}
													}

													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.formDataBC.length; i++) {
															if (    
// $scope.formDataBC[i].comments === null
// || $scope.formDataBC[i].midYearSelfRating === null
// || $scope.formDataBC[i].midYearAssessorRating === null
																	 $scope.formDataBC[i].finalYearSelfRating === null
																	|| $scope.formDataBC[i].finalYearAssessorRating === null
// || $scope.formDataBC[i].comments === ""
// || $scope.formDataBC[i].midYearSelfRating === ""
// || $scope.formDataBC[i].midYearAssessorRating === ""
																	|| $scope.formDataBC[i].finalYearSelfRating === ""
																	|| $scope.formDataBC[i].finalYearAssessorRating === ""
																	|| $scope.formDataBC[i].isValidatedBySecondLevel === 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													document.getElementById("main_body").className = "loading-pane";
													var input = {
														"empCode" : sharedProperties
																.getValue("employeeDetails"),
														"name" : "SECOND LEVEL MANAGER",
														"type" : buttonType,
														"appraisalYearId" : $scope.appraisalYearId.id,
													};
													var res = $http
															.post(
																	'/PMS/sendToManager',
																	input);
													res
															.success(function(
																	data,
																	status) {
																$scope.result = data;
																$scope.loggedUserAppraisalDetails = $scope.result.object;
																$scope.message = $scope.result.string;
																if ($scope.result.integer == 1) {
																	swal($scope.result.string);
																	document.getElementById("main_body").className = "";
																}
															});
												}
											}
										});
					}
				});

// app
// .controller(
// 'ManagerBehavioralComptenceController',
// function($window, $scope, $timeout, $http, sharedProperties) {
// logDebug('Enter ManagerBehavioralComptenceController');
// logDebug('Browser language >> ' + window.navigator.language);
// document.getElementById("appraisalYearID").style.visibility = "visible";
// $scope.appraisalYearId = JSON.parse(localStorage
// .getItem("appraisalYearId"));
//
// $scope.getBehavioralComptenceDetails = function() {
// var input = {
// "appraisalYearId" : $scope.appraisalYearId.id,
// "type" : "BehavioralComptence"
// };
// var res = $http.post('/PMS/getDetails', input);
// res
// .success(function(data, status) {
// $scope.formData = data.object[1];
// $scope.result = data.object[0];
// if ($scope.result.object == null) {
// $scope.loggedUserKraDetails.push({
// "appraisalYearId" : null,
// "midYear" : null,
// "finalYear" : null
// });
// } else {
// $scope.loggedUserKraDetails = $scope.result.object;
// }
// // var res = $http
// // .post('/PMS/get-behavioral-competence-list');
// // res.success(function(data, status) {
// // $scope.formData = data;
// // });
// });
// }
// $scope.selects = [ "1", "2", "3", "4", "5" ];
//
// $scope.formClear = function() {
//
// }
// $scope.save = function() {
// var input = {
//
// "behaviouralCompetenceDetails" : $scope.formData
// };
// var res = $http
// .post('/PMS/add-behavioral-comptence-details',
// input);
// res
// .success(function(data, status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// $scope.message = $scope.result.string;
// document
// .getElementById("behavioralComptenceAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("behavioralComptenceAlert").className = "";
// $scope.message = "";
// }, 5000);
// $scope.formClear();
// }
//
// });
// }
//
// });

app
		.controller(
				'EmployeeAppraisalListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter EmployeeAppraisalListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.appraisalStage = "Goal Setting";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						enableFiltering : true,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'empName',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'empName',
									width : '15%'
								},
								{
									name : 'Employee Code',
									field : 'empCode',
									width : '10%'
								},
								{
									name : 'Department',
									field : 'departmentName',
									width : '10%'
								},
								{
									name : 'Designation',
									field : 'designationName',
									width : '10%'
								},
								{
									name : 'First Superior Name',
									field : 'firstLevelSuperiorName',
									width : '15%'
								},
								{
									name : 'Second Superior Name',
									field : 'secondLevelSuperiorName',
									width : '15%'
								},
								{
									name : 'Appraisal',
									field : 'appraisalYearDetailsId',
									width : '5%',
									cellTemplate : "<div ng-if=\"row.entity.appraisalYearDetailsStatus == '1'\"><span style='color :green;cursor: pointer;' ng-click='grid.appScope.updateEmployeeAppraisal(row.entity)' >Activate</span></div>"
											+ "<div ng-if=\"row.entity.appraisalYearDetailsStatus == '0' \"><span style='color :red;cursor: pointer;'  ng-click='grid.appScope.updateEmployeeAppraisal(row.entity)' >DeActivate</span></div>"
								},
								{
									name : 'Stage',
									cellTemplate : '<div style ="text-align :center;"> <button class=" btn-primary "ng-click="grid.appScope.employeeStage(row.entity)"><span class="glyphicon glyphicon-eye-open"style="margin: 0px;"></span> </button></div>',
									width : '2%'
								} ]
					};
					$scope.submit = function() {

						var input = {
							"stage" : $scope.appraisalStage,
							"endDate" : $scope.submissionExpired,
							"empCode" : $scope.subEmployeeDetails.empCode,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"id" : $scope.subEmployeeDetails.appraisalYearDetailsId
						};
						var res = $http.post(
								'/PMS/changeEmployeeAppraisalStage', input);
						res.success(function(data, status) {
							$scope.result = data;
							swal($scope.result.string);
							if ($scope.result.integer == 1) {
							
								// $scope.gridOptions.data =
								// $scope.result.object;
								// document
								// .getElementById("employeeAppraisalListAlert").className
								// = "alert-successs";
// $timeout(function() {
// // document
// // .getElementById("employeeAppraisalListAlert").className
// // = "";
// $scope.message = "";
// }, 5000);
							}
						});

					}
					$scope.employeeStage = function(row) {
						$scope.showAppraisalStage = !$scope.showAppraisalStage;
						$scope.subEmployeeDetails = row;
					}
					$scope.updateEmployeeAppraisal = function(data) {
						if (data.appraisalYearDetailsStatus == 1) {
							$scope.title = "Are you sure want to deactivate?";
						} else {
							$scope.title = "Are you sure want to activate?";
						}
						swal({
							title : $scope.title,
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"status" : data.appraisalYearDetailsStatus,
													"id" : data.appraisalYearDetailsId,
													"appraisalYearId" : $scope.appraisalYearId.id
												};
												var res = $http
														.post(
																'/PMS/updateEmployeeAppraisal',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("employeeAppraisalListAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("employeeAppraisalListAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});

					}

					$scope.getDataOnLoad = function() {
						var res = $http.post(
								'/PMS/get-all-employeeAppraisalList',
								$scope.appraisalYearId.id);
						res.success(function(data, status) {
							$scope.gridOptions.data = data.object;
						});
					}

				});

app
		.controller(
				'ChangePasswordController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter ChangePasswordController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";

					$scope.formClear = function() {
						$scope.newPassword = null;
						$scope.currentPassword = null;
						$scope.confirmPassword = null;
					}
					$scope.save = function() {
						if ($scope.newPassword != $scope.confirmPassword) {
							swal("New and Confirm password doesn't matched.");
						} else {
							var input = {
								"password" : $scope.newPassword,
								"currentPassword" : $scope.currentPassword,
							};
							var res = $http.post('/PMS/changeUserPassword',
									input);
							res
									.success(function(data, status) {
										$scope.result = data;
										swal($scope.result.string);
										if ($scope.result.integer == 1) {
											$scope.formClear();
										}
									});
						}
					}
				});

app
		.controller(
				'ModifyEmployeeController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter ModifyEmployeeController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.empId = sharedProperties.getValue("employeeDetails");
					$scope.modifyEmployee = true;
					if ($scope.empId === undefined) {
						$window.location.href = "#/employee-list";
					} else {
						var res = $http.post(
								'/PMS/get_current_employee_details',
								$scope.empId);
						res
								.success(function(data, status) {
									$scope.employeeDetails = data.object;
									$scope.employeeDetailsDocument = $scope.employeeDetails.document;
									var res = $http
											.post(
													'/PMS/get_Current_employee_dynamic_form',
													$scope.appraisalYearId.id);
									res
											.success(function(data, status) {
												$scope.employeeDynamicFormDetails = data;
												for (var i = 0; i < $scope.employeeDynamicFormDetails.length; i++) {
													switch (i) {
													case 0:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field1;
														break;
													case 1:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field2;
														break;
													case 2:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field3;
														break;
													case 3:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field4;
														break;
													case 4:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field5;
														break;
													case 5:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field6;
														break;	
													case 6:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field7;
														break;
													case 7:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field8;
														break;
													case 8:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field9;
														break;
													case 9:
														$scope.employeeDynamicFormDetails[i].data = $scope.employeeDetails.field10;
														break;
													}
												}
												var res = $http
														.post('/PMS/get-all-basic-details');
												res
														.success(function(data,
																status) {
															if($scope.employeeDetails.document != null)
															{
															$scope.fileName = $scope.employeeDetails.document;
															$scope.documentCheck = $scope.fileName
																	.split("/pms/images?image=");
															$scope.documentExist = $scope.documentCheck[1]
																	.split("&");
															if ($scope.documentExist[0] == "null") {
																document
																		.getElementById("documentDownload").style.display = 'none';
															}
															}
															if($scope.employeeDetails.document == null)
																{
																document
																.getElementById("documentDownload").style.display = 'none';
																}
															$scope.departmentList = data.departmentList;
															$scope.designationList = data.designationList;
															// $scope.qualificationsList
															// =
															// data.qualificationList;
															$scope.employeeList = data.employeeList;
															$scope.applicationRolesList = data.applicationRoleList;

															for (var i = 0; i < $scope.departmentList.length; i++) {
																if ($scope.departmentList[i].name == $scope.employeeDetails.departmentName) {
																	$scope.department = $scope.departmentList[i];
																	var res = $http
																			.post(
																					'/PMS/get-all-organization-roles',
																					$scope.department.id);
																	res
																			.success(function(
																					data,
																					status) {
																				$scope.organizationRolesList = data;

																				for (var i = 0; i < $scope.organizationRolesList.length; i++) {
																					if ($scope.organizationRolesList[i].name == $scope.employeeDetails.organizationalRoleName) {
																						$scope.organizationRoleId = $scope.organizationRolesList[i];
																					}
																				}
																			});
																	break;
																}
															}

															for (var i = 0; i < $scope.designationList.length; i++) {
																if ($scope.designationList[i].name == $scope.employeeDetails.designationName) {
																	$scope.designationId = $scope.designationList[i];
																	break;
																}
															}

															for (var i = 0; i < $scope.applicationRolesList.length; i++) {
																if ($scope.applicationRolesList[i].name == $scope.employeeDetails.roleName) {
																	$scope.roleId = $scope.applicationRolesList[i];
																	break;
																}

															}

															$(
																	"#firstLevelSuperiorEmpId")
																	.select2(
																			{
																				data : $scope.employeeList
																			});
															$(
																	"#secondLevelSuperiorEmpId")
																	.select2(
																			{
																				data : $scope.employeeList
																			});
															$(
																	'#firstLevelSuperiorEmpId')
																	.val(
																			$scope.employeeDetails.firstLevelSuperiorEmpId)
																	.trigger(
																			'change');
															$(
																	'#secondLevelSuperiorEmpId')
																	.val(
																			$scope.employeeDetails.secondLevelSuperiorEmpId)
																	.trigger(
																			'change');
															$scope.empCode = $scope.employeeDetails.empCode;
															$scope.qualification = $scope.employeeDetails.qualificationName;
															$scope.name = $scope.employeeDetails.empName;
															$scope.email = $scope.employeeDetails.email;
															$scope.dateOfJoining = new Date(
																	$scope.employeeDetails.dateOfJoining);
															$scope.dateOfBirth = new Date(
																	$scope.employeeDetails.dateOfBirth);
															$scope.jobDescription = $scope.employeeDetails.jobDescription;
															$scope.mobile = $scope.employeeDetails.mobile;
															$scope.company = $scope.employeeDetails.company;
															$scope.employeeType = $scope.employeeDetails.empType;
															$scope.location = $scope.employeeDetails.location;
														});
											});
								});

					}
					$scope.getAllOrganizationRoles = function() {
						var res = $http.post(
								'/PMS/get-all-organization-roles',
								$scope.department.id);
						res.success(function(data, status) {
							$scope.organizationRolesList = data;
						});

					}
				
					$scope.save = function() {
						var firstLevelSuperiorEmpId = ($(
								'#firstLevelSuperiorEmpId').select2('data')[0].text)
								.split("-");
						var secondLevelSuperiorEmpId = ($(
								'#secondLevelSuperiorEmpId').select2('data')[0].text)
								.split("-");
						var formdata = new FormData();
						formdata.append("empId", $scope.empId);
						formdata.append("empName", $scope.name);
						formdata.append("empCode", $scope.empCode);
						formdata.append("email", $scope.email);
						formdata.append("company", $scope.company);
						formdata.append("dateOfJoining", $scope.dateOfJoining);
						formdata.append("dateOfBirth", $scope.dateOfBirth);
						formdata
								.append("jobDescription", $scope.jobDescription);
						formdata.append("departmentId", $scope.department.id);
						formdata.append("organizationRoleId",
								$scope.organizationRoleId.id);
						formdata.append("roleId", $scope.roleId.id);
						formdata.append("designationId",
								$scope.designationId.id);
						formdata.append("qualification",
								$scope.qualification);
						formdata.append("mobile", $scope.mobile);
						formdata.append("location", $scope.location);
						formdata.append("firstLevelSuperiorEmpId",
								firstLevelSuperiorEmpId[0].trim());
						formdata.append("secondLevelSuperiorEmpId",
								secondLevelSuperiorEmpId[0].trim());
						// formdata.append("firstLevelSuperiorName",
						// firstLevelSuperiorEmpId[1].trim());
						// formdata.append("secondLevelSuperiorName",
						// secondLevelSuperiorEmpId[1].trim());
						formdata.append("file1", $scope.file1);
						// formdata.append("appraisalYearId",
						// $scope.employeeDetails.appraisalYearId);

						for (var i = 0; i < $scope.employeeDynamicFormDetails.length; i++) {
							formdata.append("fields",
									$scope.employeeDynamicFormDetails[i].data);
						}
						$http
								.post("/PMS/add-new-employee", formdata, {
									transformRequest : angular.identity,
									headers : {
										'Content-Type' : undefined
									}
								})
								.success(
										function(data) {
											swal(data.string);
											if (data.integer == 1) {
												$timeout(
														function() {
															$window.location.href = "#/employee-list";
														}, 5000);
											}
											});
					}
				});

app.controller('ViewEmployeeController', function($window, $scope, $timeout,
		$http, sharedProperties) {
	logDebug('Enter ViewEmployeeController');
	logDebug('Browser language >> ' + window.navigator.language);
	document.getElementById("appraisalYearID").style.visibility = "hidden";
	$scope.empCode = sharedProperties.getValue("employeeDetails");
	if ($scope.empCode === undefined) {
		$window.location.href = "#/employee-list";
	} else {
		var res = $http.post('/PMS/get_current_employee_details',
				$scope.empCode);
		res.success(function(data, status) {
			$scope.employeeDetails = data.object;
		});
	}
	$scope.back = function() {
		$window.location.href = "#/employee-list";
	}

});

app
		.controller(
				'AppraisalYearListController',
				function($window, $scope, $http, sharedProperties, $timeout) {
					logDebug('Enter AppraisalYearListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '25%'
								},
								{
									name : 'Created On',
									field : 'createdOn',
									type : 'date',
									cellFilter : 'date:"dd-MMM-yy"',
									width : '15%'
								},
								{
									name : 'Created By',
									field : 'createdBy',
									width : '15%'
								},
								{
									name : 'Status',
									field : 'status',
									width : '5%',
									cellTemplate : "<div ng-if=\"row.entity.status == '1'\"><span style='color :green;' >Activate</span></div>"
											+ "<div ng-if=\"row.entity.status == '0'\"><span style='color :red;' >Deleted</span></div>"
											+ "<div ng-if=\"row.entity.status == '2'\"><span style='color :red;cursor: pointer;'  ng-click='grid.appScope.updateApplicationAppraisalYear(row.entity)' >DeActivate</span></div>"
								},
								{
									name : 'Edit',
									field : 'status',
									cellTemplate : "<div style ='text-align : center;' ng-if=\"row.entity.status == '1'\"><button  class='btn-primary' style='background-color:green;' data-toggle='modal' data-target='#appraisalYearUpdate'  ng-click='grid.appScope.getAppraisalYear(row.entity)'  ><span class='fa fa-pencil' style='margin: 0px;'></span> </button></div>"
											+ "<div style ='text-align : center;' ng-if=\"row.entity.status == '0'\"><button  class='btn-danger disabled'  style='cursor: no-drop;'><span class='fa fa-ban' style='margin: 0px;'></span> </button></div>"
											+ "<div style ='text-align : center;' ng-if=\"row.entity.status == '2'\"><button  class='btn-primary' data-toggle='modal' data-target='#appraisalYearUpdate'  ng-click='grid.appScope.getAppraisalYear(row.entity)'  ><span class='fa fa-pencil' style='margin: 0px;'></span> </button></div>",
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : "<div style ='text-align : center;' ng-if=\"row.entity.status == '2'\"><button  class='btn-danger'  ng-click='grid.appScope.deleteAppraisalYear(row.entity)'  ><span class='fa fa-trash' style='margin: 0px;'></span> </button></div>"
											+ "<div style ='text-align : center;' ng-if=\"row.entity.status == '0'\"><button  class='btn-danger disabled'  style='cursor: no-drop;'><span class='fa fa-ban' style='margin: 0px;'></span> </button></div>"
											+ "<div style ='text-align : center;' ng-if=\"row.entity.status == '1'\"><button  class='btn-danger disabled'  style='cursor: no-drop;'><span class='fa fa-ban' style='margin: 0px;'></span> </button></div>",
									width : '5%'
								} ]
					};

					$scope.updateApplicationAppraisalYear = function(data) {
						$scope.yearData = data;
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"list" : $scope.YearsId
												};
												var res = $http
														.post(
																'/PMS/update-application-appraisal-year',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																$scope.YearsId = [];
																for (var i = 0; i < $scope.result.object.length; i++) {
																	$scope.YearsId[i] = $scope.result.object[i].id;
																}
																document
																		.getElementById("appraisalYearListAlert").className = "alert-successs";
																localStorage
																		.setItem(
																				"appraisalYearId",
																				JSON
																						.stringify($scope.yearData));
																$window.location
																		.reload();
																$timeout(
																		function() {
																			document
																					.getElementById("appraisalYearListAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}
					$scope.getAllAppraisalYearList = function() {
						var res = $http.post('/PMS/get-appraisal-years-list');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
							$scope.YearsId = [];
							for (var i = 0; i < data.length; i++) {
								$scope.YearsId[i] = data[i].id;
							}
						});
					}

					$scope.getAppraisalYear = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.yearData = data;
					}

					$scope.save = function() {
						var input = {
							"name" : $scope.name
						};
						var res = $http
								.post('/PMS/save-appraisal-year', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										$scope.YearsId = [];
										for (var i = 0; i < $scope.result.object.length; i++) {
											$scope.YearsId[i] = $scope.result.object[i].id;
										}
										document
												.getElementById("appraisalYearListAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("appraisalYearListAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.name = null;
									}
								});
					}

					$scope.edit = function() {
						$('#appraisalYearUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"name" : $scope.updateName,
						};
						var res = $http
								.post('/PMS/save-appraisal-year', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										$scope.YearsId = [];
										for (var i = 0; i < $scope.result.object.length; i++) {
											$scope.YearsId[i] = $scope.result.object[i].id;
										}
										document
												.getElementById("appraisalYearListAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("appraisalYearListAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
									}
								});
					}

					$scope.deleteAppraisalYear = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http
														.post(
																'/PMS/delete-appraisal-year',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("appraisalYearListAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("appraisalYearListAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}
				});

app
		.controller(
				'EmployeeBulkController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter EmployeeBulkController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.save = function() {
						document.getElementById("main_body").className = "loading-pane";
						var formdata = new FormData();
						formdata.append("file1", $scope.file1);
						$http
								.post("/PMS/add-employee-bulk", formdata, {
									transformRequest : angular.identity,
									headers : {
										'Content-Type' : undefined
									}
								})
								.success(
										function(data, status, headers) {
											if (status == 200) {
												headers = headers();
												if (headers['content-type'] == "application/csv;charset=ISO-8859-1") {

													var filename = headers['content-disposition'];
													var contentType = headers['content-type'];
													var linkElement = document
															.createElement('a');
													try {
														var blob = new Blob(
																[ data ],
																{
																	type : contentType
																});
														var url = window.URL
																.createObjectURL(blob);

														linkElement
																.setAttribute(
																		'href',
																		url);
														linkElement
																.setAttribute(
																		"download",
																		filename);
														// linkElement.download
														// = 'Document.csv';
														var clickEvent = new MouseEvent(
																"click",
																{
																	"view" : window,
																	"bubbles" : true,
																	"cancelable" : false
																});
														linkElement
																.dispatchEvent(clickEvent);
													} catch (ex) {

													}
													// document
													// .getElementById("employeeBulkAlert").className
													// = "alert-dangers";
												} else {
													$scope.message = data;

													document
															.getElementById("employeeBulkAlert").className = "alert-successs";
													$timeout(
															function() {
																document
																		.getElementById("employeeBulkAlert").className = "";
																$scope.message = "";
																$scope.file1 = null;
																document
																		.getElementById("file1").value = '';
															}, 5000);

												}
												document.getElementById("main_body").className = "";
											}
										});
					}
				});
app
		.controller(
				'EmployeeFormConfigurationController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter EmployeeFormConfigurationController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '2%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'labelName',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Status',
									field : 'status',
									width : '5%',
									cellTemplate : "<div ng-if=\"row.entity.status == '1'\"><span style='color :green;' >Activate</span></div>"
											+ "<div ng-if=\"row.entity.status == '0'\"><span style='color :red;cursor: pointer;'  ng-click='grid.appScope.activateColumn(row.entity)' >DeActivate</span></div>"
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#employeeConfigurationUpdate" ng-click="grid.appScope.getColumnData(row.entity)" ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger" ng-click="grid.appScope.deleteColumnData(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};
					$scope.activateColumn = function(data) {
						swal({
							title : "Are you sure to activate ?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"appraisalYearId" : $scope.appraisalYearId.id
												};
												var res = $http
														.post(
																'/PMS/activate_column',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																swal($scope.result.string);
																$scope.gridOptions.data = $scope.result.object;
															}
														});
											}
										})

					}
					$scope.getEmployeeDynamicFormDetails = function() {
						var res = $http
								.post('/PMS/get-employee-dynamic-form-details');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.deleteColumnData = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http.post(
														'/PMS/delete-column',
														input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																swal($scope.result.string);
																$scope.gridOptions.data = $scope.result.object;
																			$scope.updateName = null;
																			$scope.updateDescription = null;
															}
														});
											}
										});
					}
					$scope.getColumnData = function(data) {
						$scope.id = data.id;
						$scope.updateName = data.labelName;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						$('#employeeConfigurationUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
						};
						var res = $http.post('/PMS/save-column', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.gridOptions.data = $scope.result.object;
										swal($scope.result.string);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

				});
app
		.controller(
				'BehavioralComptenceListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter BehavioralComptenceListController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.users = [ {
						"id" : 1,
						"name" : "EMPLOYEE"
					}, {
						"id" : 2,
						"name" : "HOD"
					} ];
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Type',
									field : 'type',
									width : '10%'
								},
								{
									name : 'Name',
									field : 'name',
									width : '20%'
								},
								{
									name : 'Weightage',
									field : 'weightagePercantage',
									width : '10%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '35%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#behavioralComptenceUpdate"  ng-click="grid.appScope.getBehavioralComptence(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteBehavioralComptence(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.carryForwardToNextYear = function() {
						if ($scope.format == 1) {
							var input = {
								"appraisalYearId" : $scope.appraisalYearId.id,
								"type" : $scope.user
							};
							var res = $http.post(
									'/PMS/behavioral_data_carry_forward',
									input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											$scope.gridOptions.data = $scope.result.object;
											$scope.weightageCount = 0;
											for (var i = 0; i < $scope.gridOptions.data.length; i++) {
												$scope.gridOptions.data[i].weightagePercantage = $scope.gridOptions.data[i].weightage
														+ '%';
												$scope.weightageCount = $scope.weightageCount
														+ parseInt($scope.gridOptions.data[i].weightage);
											}
											document
													.getElementById("behavioralComptenceAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("behavioralComptenceAlert").className = "";
														$scope.message = "";
													}, 5000);

										}
									});
						}
					}
					$scope.getAllBehavioralComptenceList = function() {
						var input = {
							"id" : $scope.appraisalYearId.id,
							"type" : $scope.user
						};
						var res = $http.post(
								'/PMS/get-behavioral-competence-list', input);
						res
								.success(function(data, status) {
									$scope.gridOptions.data = data;
									if (data.length == 0) {
										$('#behavioraldataForwardNextYear')
												.modal({
													backdrop : 'static',
													keyboard : false
												});
									} else {
										$scope.weightageCount = 0;
										for (var i = 0; i < data.length; i++) {
											$scope.gridOptions.data[i].weightagePercantage = data[i].weightage
													+ '%';
											$scope.weightageCount = $scope.weightageCount
													+ parseInt(data[i].weightage);
										}
									}
								});
					}

					$scope.getBehavioralComptence = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateWeightage = data.weightage;
						$scope.updateDescription = data.description;
						$scope.checkWeighatge = data.weightage;
					}

					$scope.save = function() {
// console.log(parseInt($scope.weightageCount +
// parseInt($scope.updateWeightage)));
						if (parseInt($scope.weightageCount
								+ parseInt($scope.weightage)) > 100) {
							swal({
								text : "Invalid weightage",
								button : "Ok",
							});
						} else {
							var input = {
								"id" : $scope.id,
								"appraisalYearId" : $scope.appraisalYearId.id,
								"name" : $scope.name,
								"type" : $scope.user,
								"description" : $scope.description,
								"weightage" : $scope.weightage
							};
							var res = $http.post(
									'/PMS/save-behavioral_comptence', input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											$scope.gridOptions.data = $scope.result.object;
											$scope.weightageCount = 0;
											for (var i = 0; i < $scope.result.object.length; i++) {
												$scope.gridOptions.data[i].weightagePercantage = $scope.result.object[i].weightage
														+ '%';
												$scope.weightageCount = $scope.weightageCount
														+ parseInt($scope.result.object[i].weightage);
											}
											document
													.getElementById("behavioralComptenceAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("behavioralComptenceAlert").className = "";
														$scope.message = "";
													}, 5000);
											$scope.name = null;
											$scope.description = null;
											$scope.weightage = null;
										}
									});

						}

					}

					$scope.edit = function() {
						$scope.weightageCount = $scope.weightageCount
								- parseInt($scope.checkWeighatge)
// console.log(parseInt($scope.weightageCount
// + parseInt($scope.updateWeightage)));
						if (parseInt($scope.weightageCount
								+ parseInt($scope.updateWeightage)) > 100) {
							swal({
								text : "Invalid weightage",
								button : "Ok",
							});
							$scope.weightageCount = $scope.weightageCount
									+ parseInt($scope.checkWeighatge)
						} else {
							$('#behavioralComptenceUpdate').modal('hide');
							var input = {
								"id" : $scope.id,
								"appraisalYearId" : $scope.appraisalYearId.id,
								"name" : $scope.updateName,
								"type" : $scope.user,
								"weightage" : $scope.updateWeightage,
								"description" : $scope.updateDescription,
							};
							var res = $http.post(
									'/PMS/save-behavioral_comptence', input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											$scope.message = $scope.result.string;
											$scope.gridOptions.data = $scope.result.object;
											$scope.weightageCount = 0;
											for (var i = 0; i < $scope.result.object.length; i++) {
												$scope.gridOptions.data[i].weightagePercantage = $scope.result.object[i].weightage
														+ '%';
												$scope.weightageCount = $scope.weightageCount
														+ parseInt($scope.result.object[i].weightage);
											}
											document
													.getElementById("behavioralComptenceAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("behavioralComptenceAlert").className = "";
														$scope.message = "";
													}, 5000);
											$scope.updateName = null;
											$scope.updateDescription = null;
											$scope.updateWeightage = null;
											$scope.id = null;
										}
									});
						}
					}

					$scope.deleteBehavioralComptence = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"type" : $scope.user,
													"appraisalYearId" : $scope.appraisalYearId.id
												};
												var res = $http
														.post(
																'/PMS/delete-behavioral_comptence',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																$scope.weightageCount = 0;
																for (var i = 0; i < $scope.result.object.length; i++) {
																	$scope.gridOptions.data[i].weightagePercantage = $scope.result.object[i].weightage
																			+ '%';
																	$scope.weightageCount = $scope.weightageCount
																			+ parseInt($scope.result.object[i].weightage);
																}
																document
																		.getElementById("behavioralComptenceAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("behavioralComptenceAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										})
					}

				});
app
		.controller(
				'DepartmentListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter DepartmentListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '5%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '35%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '40%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#departmentUpdate"  ng-click="grid.appScope.getDepartment(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteDepartment(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '10%'
								} ]
					};

					$scope.getAllDepartmentList = function() {
						var res = $http.post('/PMS/get-all-departments');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getDepartment = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						var input = {
							"name" : $scope.name,
							"description" : $scope.description
						};
						var res = $http.post('/PMS/save-department', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.gridOptions.data = $scope.result.object;
// document
// .getElementById("departmentAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("departmentAlert").className = "";
// $scope.message = "";
// }, 5000);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

					$scope.edit = function() {
						$('#departmentUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
						};
						var res = $http.post('/PMS/save-department', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("departmentAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("departmentAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteDepartment = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http
														.post(
																'/PMS/delete-department',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("departmentAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("departmentAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}

				});
app
		.controller(
				'DesignationListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter DesignationListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#designationUpdate"  ng-click="grid.appScope.getDesignation(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteDesignation(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.getAllDesignationList = function() {
						var res = $http.post('/PMS/get-all-designations');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getDesignation = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						var input = {
							"name" : $scope.name,
							"description" : $scope.description
						};
						var res = $http.post('/PMS/save-designation', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("designationAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("designationAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

					$scope.edit = function() {
						$('#designationUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
						};
						var res = $http.post('/PMS/save-designation', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("designationAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("designationAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteDesignation = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http
														.post(
																'/PMS/delete-designation',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("designationAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("designationAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										})
					}

				});
app
		.controller(
				'ExtraOrdinaryListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter ExtraOrdinaryListController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '5%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'labelName',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Status',
									field : 'status',
									width : '5%',
									cellTemplate : "<div ng-if=\"row.entity.status == '1'\"><span style='color :green;' >Activate</span></div>"
											+ "<div ng-if=\"row.entity.status == '0'\"><span style='color :red;cursor: pointer;'  ng-click='grid.appScope.activateExtraOrdinary(row.entity)' >DeActivate</span></div>"
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#extraOrdinaryUpdate"  ng-click="grid.appScope.getExtraOrdinary(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteExtraOrdinary(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.activateExtraOrdinary = function(data) {
						swal({
							title : "Are you sure want to activate?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"appraisalYearId" : $scope.appraisalYearId.id
												};
												var res = $http
														.post(
																'/PMS/activate_extra_ordinary',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("extraOrdinaryAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("extraOrdinaryAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}
					$scope.getAllExtraOrdinaryList = function() {
						var res = $http.post('/PMS/get_extra_ordinary_list');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getExtraOrdinary = function(data) {
						$scope.id = data.id
						$scope.updateName = data.labelName;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						$('#extraOrdinaryUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
						};
						var res = $http
								.post('/PMS/save_extra_ordinary', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("extraOrdinaryAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("extraOrdinaryAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteExtraOrdinary = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http
														.post(
																'/PMS/delete_extra_ordinary',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("extraOrdinaryAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("extraOrdinaryAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										})
					}

				});

app
		.controller(
				'organizationRolesListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter organizationRolesListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#organizationRolesUpdate"  ng-click="grid.appScope.getOraganizationRole(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteOrganizationRole(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.getAllDepartmentList = function() {
						var res = $http.post('/PMS/get-all-departments');
						res.success(function(data, status) {
							$scope.departmentList = data;
						});
					}

					$scope.getAllOrganizationalRolesList = function() {
						var res = $http.post(
								'/PMS/get-all-organization-roles',
								$scope.department);
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getOraganizationRole = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						var input = {
							"name" : $scope.name,
							"description" : $scope.description,
							"departmentId" : $scope.department
						};
						var res = $http.post('/PMS/save-organization-roles',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("organizationRolesAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("organizationRolesAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

					$scope.edit = function() {
						$('#organizationRolesUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
							"departmentId" : $scope.department
						};
						var res = $http.post('/PMS/save-organization-roles',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("organizationRolesAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("organizationRolesAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteOrganizationRole = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"departmentId" : $scope.department
												};
												var res = $http
														.post(
																'/PMS/delete-organization-roles',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("organizationRolesAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("organizationRolesAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										})
					}
				});
// app
// .controller(
// 'QualificationListController',
// function($window, $scope, $timeout, $http, sharedProperties) {
// logDebug('Enter QualificationListController');
// logDebug('Browser language >> ' + window.navigator.language);
// document.getElementById("appraisalYearID").style.visibility = "hidden";
// $scope.gridOptions = {
// showGridFooter : true,
// multiSelect : false,
// columnDefs : [
// {
// name : 'Sr #',
// field : 'name',
// width : '1%',
// cellTemplate : '<div
// class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
// },
// {
// name : 'Name',
// field : 'name',
// width : '25%'
// },
// {
// name : 'Description',
// field : 'description',
// width : '50%'
// },
// {
// name : 'Edit',
// cellTemplate : '<div style ="text-align : center;"> <button class="
// btn-primary " data-toggle="modal" data-target="#qualificationUpdate"
// ng-click="grid.appScope.getQualification(row.entity)" ><span class="fa
// fa-pencil" style="margin: 0px;"></span> </button></div>',
// width : '2%'
// },
// {
// name : 'Delete',
// cellTemplate : '<div style ="text-align : center;"> <button class="
// btn-danger" ng-click="grid.appScope.deleteQualification(row.entity)"><span
// class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
// width : '5%'
// } ]
// };
//
// $scope.getAllQualificationList = function() {
// var res = $http.post('/PMS/get-all-qualifications');
// res.success(function(data, status) {
// $scope.gridOptions.data = data;
// });
// }
//
// $scope.getQualification = function(data) {
// $scope.id = data.id
// $scope.updateName = data.name;
// $scope.updateDescription = data.description;
// }
//
// $scope.save = function() {
// var input = {
// "name" : $scope.name,
// "description" : $scope.description
// };
// var res = $http.post('/PMS/save-qualification', input);
// res
// .success(function(data, status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// $scope.message = $scope.result.string;
// $scope.gridOptions.data = $scope.result.object;
// document
// .getElementById("qualificationAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("qualificationAlert").className = "";
// $scope.message = "";
// }, 5000);
// $scope.name = null;
// $scope.description = null;
// }
// });
// }
//
// $scope.edit = function() {
// $('#qualificationUpdate').modal('hide');
// var input = {
// "id" : $scope.id,
// "name" : $scope.updateName,
// "description" : $scope.updateDescription,
// };
// var res = $http.post('/PMS/save-qualification', input);
// res
// .success(function(data, status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// $scope.message = $scope.result.string;
// $scope.gridOptions.data = $scope.result.object;
// document
// .getElementById("qualificationAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("qualificationAlert").className = "";
// $scope.message = "";
// }, 5000);
// $scope.updateName = null;
// $scope.updateDescription = null;
// }
// });
// }
//
// $scope.deleteQualification = function(data) {
// swal({
// title : "Are you sure?",
// icon : "warning",
// buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
// dangerMode : true,
// })
// .then(
// function(isConfirm) {
// if (isConfirm) {
// var input = {
// "id" : data.id
// };
// var res = $http
// .post(
// '/PMS/delete-qualification',
// input);
// res
// .success(function(data,
// status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// $scope.message = $scope.result.string;
// $scope.gridOptions.data = $scope.result.object;
// document
// .getElementById("qualificationAlert").className = "alert-successs";
// $timeout(
// function() {
// document
// .getElementById("qualificationAlert").className = "";
// $scope.message = "";
// }, 5000);
// }
// });
// }
// })
// }
//
// });
app
		.controller(
				'applicationRolesListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter applicationRolesListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '1%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#applicationRolesUpdate"  ng-click="grid.appScope.getApplicationRoles(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '2%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteApplicationRoles(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.getAllApplicationRolesList = function() {
						var res = $http
								.post('/PMS/get-all-applicationRoles-list');
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getApplicationRoles = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						var input = {
							"name" : $scope.name,
							"description" : $scope.description
						};
						var res = $http.post('/PMS/save-applicationRoles',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("applicationRolesAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("applicationRolesAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

					$scope.edit = function() {
						$('#applicationRolesUpdate').modal('hide');
						var input = {
							"id" : $scope.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
						};
						var res = $http.post('/PMS/save-applicationRoles',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("applicationRolesAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("applicationRolesAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteApplicationRoles = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id
												};
												var res = $http
														.post(
																'/PMS/delete-applicationRoles',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("applicationRolesAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("applicationRolesAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										})
					}

				});
app
.controller(
		'EmployeeKRAListController',
		function($window, $scope, $timeout, $http, sharedProperties) {
			$scope.type="2";
			logDebug('Enter EmployeeKRAListController');
			logDebug('Browser language >> ' + window.navigator.language);
			$scope.appraisalYearId = JSON.parse(localStorage
					.getItem("appraisalYearId"));
			document.getElementById("appraisalYearID").style.visibility = "visible";
			
			$scope.getAllEmployeeList= function()
			{
				var res = $http.post('/PMS/getAllEmployeeListData');
				res.success(function(data, status) {
					if(data.integer == 1)
					{
						$scope.getAllDepartmentList();
					$("#selectedEmployeeId").select2({
						data : data.object
					});
					}
				});
			}
			$scope.getAllDepartmentList = function() {
				var res = $http.post('/PMS/get-all-departments');
				res.success(function(data, status) {
					$scope.departmentList = data;
				});
			}
			$scope.getAllDepartmentWiseKRAList = function()
			{
				var input = {
						"departmentId" : $scope.department.id,
						"appraisalYearId" : $scope.appraisalYearId.id
					};
					var res = $http.post('/PMS/getAllDepartmentWiseKRAList', input);
					res.success(function(data, status) {
								$scope.result = data.object;
								$scope.teamKRADetails = [];
								for(var y = 0;y<$scope.result[0].object.length;y++)
								{
								$scope.loggedUserKRADetails = $scope.result[0].object[y];
								$scope.viewKRATotalCalculation = 0;
								$scope.sectionAList = [];
								$scope.sectionBList = [];
								$scope.sectionCList = [];
								$scope.sectionDList = [];
								var p = 0;
								var q = 0;
								var r = 0;
								var s = 0;
								$scope.WeightageCount = 0;
// $scope.loggedUserAppraisalDetails = $scope.result.object;
// if ($scope.loggedUserAppraisalDetails != undefined) {
// if ($scope.loggedUserAppraisalDetails.initializationYear != 0
// && $scope.loggedUserAppraisalDetails.midYear == 0
// && $scope.loggedUserAppraisalDetails.finalYear == 0) {
// $scope.yearType = "START";
//
// }
// if ($scope.loggedUserAppraisalDetails.initializationYear == 0
// && $scope.loggedUserAppraisalDetails.midYear != 0
// && $scope.loggedUserAppraisalDetails.finalYear == 0) {
// $scope.yearType = "MID";
// }
// if ($scope.loggedUserAppraisalDetails.initializationYear == 0
// && $scope.loggedUserAppraisalDetails.midYear == 0
// && $scope.loggedUserAppraisalDetails.finalYear != 0) {
// $scope.yearType = "FINAL";
// }
// }
									for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {

										if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
											$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
											if ($scope.sectionAList[p].weightage != null) {
												
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
											if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
												{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
														70/100)));
												}
											if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
											{
											$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
													(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
											}
											p++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
											$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
											if ($scope.sectionBList[q].weightage != null) {
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
											}
											if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
														70/100)));
											}
											if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
											}
											q++;
										} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
											$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
											if ($scope.sectionCList[r].weightage != null) {
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
											if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
														70/100)));
											}
											if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
											}
											r++;
										} else {
											$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
											if ($scope.sectionDList[s].weightage != null) {
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
											if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
														70/100)));
											}
											if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
											{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
											}
											s++;
										}
									
								}
									$scope.teamKRADetails.push({
										"sectionAList" :$scope.sectionAList,
										"sectionBList":$scope.sectionBList,
										"sectionCList":$scope.sectionCList,
										"sectionDList" :$scope.sectionDList,
										"WeightageCount":$scope.WeightageCount,
										"viewKRATotalCalculation": $scope.viewKRATotalCalculation
										});
					}
							});
					
			}
			 $scope.Export = function () {
	                $("#tblEmployeeKRAList").table2excel({
	                    filename: "EmployeeKRAList.xls"
	                });
	            }
			 
			$(document.body).on("change","#selectedEmployeeId",function(){
				$scope.department = null;
				$scope.empCode = this.value;
				 var input = {
						 "empCode" : $scope.empCode,
						 "appraisalYearId" : $scope.appraisalYearId.id
				 };
				 var res = $http.post('/PMS/getBasicEmployeeDetails',
						 input);
					res.success(function(data, status) {
						$scope.employeeDetails = data.object;
// var input = {
// "empCode" : $scope.empCode,
// "type" : "EmployeeKra",
// "appraisalYearId" : $scope.appraisalYearId.id
// };
// var res = $http.post('/PMS/getDetails', input);
							
							var input = {
									"empCode" : $scope.empCode,
									"appraisalYearId" : $scope.appraisalYearId.id
								};
								var res = $http.post('/PMS/getAllDepartmentWiseKRAList', input);
							
							
							res.success(function(data, status) {
								$scope.result = data.object;
								$scope.teamKRADetails = [];
								for(var y = 0;y<$scope.result[0].object.length;y++)
								{
								$scope.loggedUserKRADetails = $scope.result[0].object[y];
								$scope.viewKRATotalCalculation = 0;
										$scope.sectionAList = [];
										$scope.sectionBList = [];
										$scope.sectionCList = [];
										$scope.sectionDList = [];
										$scope.teamKRADetails =[];
										var p = 0;
										var q = 0;
										var r = 0;
										var s = 0;
										$scope.WeightageCount = 0;
// $scope.loggedUserAppraisalDetails = $scope.result.object;
// if ($scope.loggedUserAppraisalDetails != undefined) {
// if ($scope.loggedUserAppraisalDetails.initializationYear != 0
// && $scope.loggedUserAppraisalDetails.midYear == 0
// && $scope.loggedUserAppraisalDetails.finalYear == 0) {
// $scope.yearType = "START";
//
// }
// if ($scope.loggedUserAppraisalDetails.initializationYear == 0
// && $scope.loggedUserAppraisalDetails.midYear != 0
// && $scope.loggedUserAppraisalDetails.finalYear == 0) {
// $scope.yearType = "MID";
// }
// if ($scope.loggedUserAppraisalDetails.initializationYear == 0
// && $scope.loggedUserAppraisalDetails.midYear == 0
// && $scope.loggedUserAppraisalDetails.finalYear != 0) {
// $scope.yearType = "FINAL";
// }
// }
											for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {

												if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
													$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
													if ($scope.sectionAList[p].weightage != null) {
														
														$scope.WeightageCount = $scope.WeightageCount
																+ parseInt($scope.loggedUserKRADetails[i].weightage);
														}
													if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
														{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
																(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
																70/100)));
														}
													if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
													{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
															(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
													}
													p++;
												} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
													$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
													if ($scope.sectionBList[q].weightage != null) {
														$scope.WeightageCount = $scope.WeightageCount
																+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
													if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
																(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
																70/100)));
													}
													if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
																(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
													}
													q++;
												} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
													$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
													if ($scope.sectionCList[r].weightage != null) {
														$scope.WeightageCount = $scope.WeightageCount
																+ parseInt($scope.loggedUserKRADetails[i].weightage);
														}
													if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
																(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
																70/100)));
													}
													if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
																(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
													}
													r++;
												} else {
													$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
													if ($scope.sectionDList[s].weightage != null) {
														$scope.WeightageCount = $scope.WeightageCount
																+ parseInt($scope.loggedUserKRADetails[i].weightage);
														}
													if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
																(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
																70/100)));
													}
													if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
													{
														$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
																(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
													}
													s++;
												}
											
										}
											$scope.teamKRADetails.push({
												"sectionAList" :$scope.sectionAList,
												"sectionBList":$scope.sectionBList,
												"sectionCList":$scope.sectionCList,
												"sectionDList" :$scope.sectionDList,
												"WeightageCount":$scope.WeightageCount,
												"viewKRATotalCalculation": $scope.viewKRATotalCalculation
												});
								}
									});
							
					});
			});
			
			
			
			
			
			
			
			
			
			
			$scope.employeeType=function()
			{
					
				$("#selectedEmployeeId").empty();
//				
				$('#selectedEmployeeId').append($('<option>', {value: '0', text: 'Please select'}));
			
				if($scope.type==0)
					{
					var res = $http.post('/PMS/getAllDeletedEmployeeList');
					res.success(function(data, status) {
						console.log(data);
					
						if(data.integer == 1)
						{
							
							$("#selectedEmployeeId").select2({
							
								data : data.object
								
							});

			
						}
					});
					}
				else if($scope.type==2)
					{
					var res = $http.post('/PMS/getAllEmployeeListData');
					res.success(function(data, status) {
						 
						if(data.integer == 1)
						{
						$("#selectedEmployeeId").select2({
						
							data : data.object
							
							
						});
						
			
						}
					});
				
					}
				else 
					{
					var res = $http.post('/PMS/getAllActiveEmployeeList');
					res.success(function(data, status) {
						
						if(data.integer == 1)
						{
						$("#selectedEmployeeId").select2({
						
							data : data.object
						
							
						});
						
			
						}
					});
				
					
					}
				
			}
			
			
			
			
			
		});
app
		.controller(
				'TrainingNeedsListController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					
					$scope.type="2";
					
					logDebug('Enter TrainingNeedsListController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					document.getElementById("appraisalYearID").style.visibility = "visible";
					
					$scope.getAllEmployeeList= function()
					{
						var res = $http.post('/PMS/getAllEmployeeListData');
						res.success(function(data, status) {
							if(data.integer == 1)
							{
							$("#selectedEmployeeId").select2({
								data : data.object
								
							});
							
				
							}
						});
					}
					

		            $scope.Export = function () {
		                $("#tblTrainingNeeds").table2excel({
		                    filename: "Table.xls"
		                });
		            }
		            
					$scope.allEmployeeTrainingNeeds = function(data)
					{
						$scope.trainingNeedsDetails = [];
						if($scope.all == true)
							{
						var input = {
								"empCode" : data,
								"appraisalYearId" : $scope.appraisalYearId.id,
								"type" : "TrainingNeeds"
							};
							var res = $http.post('/PMS/getDetails', input);
							res.success(function(data, status) {
								$scope.result = data.object;
								if ($scope.result[1].length > 0) {
									$scope.trainingNeedsDetails = $scope.result[1];
								}
							});
							}
					}
					$(document.body).on("change","#selectedEmployeeId",function(){
						$scope.empCode = this.value;
						 var input = {
								 "empCode" : $scope.empCode,
								 "appraisalYearId" : $scope.appraisalYearId.id
						 };
						 var res = $http.post('/PMS/getBasicEmployeeDetails',
								 input);
							res.success(function(data, status) {
								$scope.employeeDetails = data.object;
								$scope.trainingNeedsDetails = [];
								var input = {
										"empCode" : $scope.empCode,
										"appraisalYearId" : $scope.appraisalYearId.id,
										"type" : "TrainingNeeds"
									};
									var res = $http.post('/PMS/getDetails', input);
									res.success(function(data, status) {
										$scope.result = data.object;
										if ($scope.result[1].length > 0) {
											$scope.trainingNeedsDetails = $scope.result[1];
										}
									});
							});
					});
					
					
					
					
		$scope.employeeType=function()
		{
			
		
			$("#selectedEmployeeId").empty();
//			
			$('#selectedEmployeeId').append($('<option>', {value: '0', text: 'Please select'}));
		
			if($scope.type==0)
				{
				var res = $http.post('/PMS/getAllDeletedEmployeeList');
				res.success(function(data, status) {console.log('cgweucgw');
					console.log(data);
					
					if(data.integer == 1)
					{
						
						$("#selectedEmployeeId").select2({
							data : data.object
						});

		
					}
				});
				}
			else if($scope.type==2)
				{
				var res = $http.post('/PMS/getAllEmployeeListData');
				res.success(function(data, status) {
				
					if(data.integer == 1)
					{
					$("#selectedEmployeeId").select2({
						data : data.object
						
					});
					
		
					}
				});
			
				}
			else 
				{

				var res = $http.post('/PMS/getAllActiveEmployeeList');
				res.success(function(data, status) {
					
					if(data.integer == 1)
					{
					$("#selectedEmployeeId").select2({
						data : data.object
						
					});
					
		
					}
				});
			
				
				}
			
		}
					
					
					
					
					
					
					
					
					
					
					
				});

app.controller('EmployeeListController',function($window, $scope, $http, $timeout, sharedProperties) {
					
					$scope.type="1";
					logDebug('Enter EmployeeListController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						enableFiltering : true,
						enableGridMenu : true,
						enableSelectAll : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'employeeList.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
								
						columnDefs : [
// {
// name : 'Sr #',
// field : '<span>{{rowRenderIndex+1}}</span>',
// width : '3%',
// cellTemplate : '<div
// class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
// },
								{
									name : 'Name',
									field : 'empName',
									width : '15%'
								},
								{
									name : 'Employee Code',
									field : 'empCode',
									width : '10%'
								},
								{
									name : 'Department',
									field : 'departmentName',
									width : '15%'
								},
								{
									name : 'Designation',
									field : 'designationName',
									width : '15%'
								},
								{
									name : 'Application Roles',
									field : 'applicationRole',
									width : '10%'
								},
								{
									name : 'Organization Roles',
									field : 'organizationRole',
									width : '15%'
								},
								{
									name : 'First Superior Name',
									field : 'firstLevelSuperiorName',
									width : '15%'
								},
								{
									name : 'Second Superior Name',
									field : 'secondLevelSuperiorName',
									width : '15%'
								},
								{
									name : 'Status',
									field : 'status',
									width : '5%',
									cellTemplate : "<div ng-if=\"row.entity.status == '1'\"><span style='color :green;' >Activate</span></div>"
											+ "<div ng-if=\"row.entity.status == '0'\"><span style='color :red;' >Deleted</span></div>"
								},
								// {
								// name : 'View',
								// cellTemplate : '<div style ="text-align :
								// center;"> <button class=" btn-primary "
								// ng-click="grid.appScope.viewEmployee(row.entity.id)"><span
								// class="glyphicon glyphicon-eye-open"
								// style="margin: 0px;"></span>
								// </button></div>',
								// width : '5%'
								// },
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  ng-click="grid.appScope.modifyEmployee(row.entity.id)" ><span class="glyphicon glyphicon-pencil" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteEmployee(row.entity.id)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.viewEmployee = function(data) {
						sharedProperties.setValue("employeeDetails", data);
						$window.location.href = "#/view-employee";
					}
					$scope.modifyEmployee = function(data) {
						sharedProperties.setValue("employeeDetails", data);
						$window.location.href = "#/modify-employee";
					}
					$scope.deleteEmployee = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data
												};
												var res = $http
														.post(
																'/PMS/delete-employee',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("employeeListAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("employeeListAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}
					$scope.cancel = function() {
						$scope.isFormValid = false;
					}

					$scope.getDataOnLoad = function() {
						var res = $http.post('/PMS/get-all-employeeList');
						res.success(function(data, status) {
							$scope.gridOptions.data = data.object;
						});
					}
					
					
					$scope.employeeType=function()
					{		
						if($scope.type==0)
							{
							
                            var res = $http.post('/PMS/get-all-deleted-employeeList');
							res.success(function(data, status) {
								$scope.gridOptions.data = data.object;
							});
						
							}
						else if($scope.type==2)
							{
							  var res = $http.post('/PMS/get-alldetails-employeeList');
								res.success(function(data, status) {
									$scope.gridOptions.data = data.object;
								});
							}
						else 
							{
                          
							$scope.getDataOnLoad();
						
							}
						
					}
					
					
					
					
					
					
					
					
				});
app
		.controller(
				'MasterPageController',
				function($window, $scope, $timeout, $http, sharedProperties) {
					logDebug('Enter MasterPageController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.roleField = false;
					$scope.name = " ";
					$scope.description = " ";
					$scope.updateName = " ";
					$scope.updateDescription = " ";

					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '5%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Name',
									field : 'name',
									width : '25%'
								},
								{
									name : 'Description',
									field : 'description',
									width : '50%'
								},
								{
									name : 'Edit',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary "  data-toggle="modal" data-target="#masterPageUpdate"  ng-click="grid.appScope.getParameter(row.entity)"  ><span class="fa fa-pencil" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								},
								{
									name : 'Delete',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-danger"  ng-click="grid.appScope.deleteParameter(row.entity)"><span class="fa fa-trash" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								} ]
					};

					$scope.getAllParameters = function() {

						var res = $http.post('/PMS/get-all-parameters');
						res.success(function(data, status) {
							$scope.parametersList = data;
						});
					}

					$scope.getAllParameterData = function() {
						var res = $http.post('/PMS/get-all-parameter-data',
								$scope.parameter.id);
						res
								.success(function(data, status) {
									$scope.result = data.object;
									if ($scope.result.departmentList != undefined) {
										$scope.addRole = true;
										$scope.departmentList = $scope.result.departmentList;
									} else {
										$scope.addRole = false;
										$scope.gridOptions.data = $scope.result;
									}
								});
					}

					$scope.getAllOrganizationalRoles = function() {
						var res = $http.post(
								'/PMS/get-all-organization-roles',
								$scope.department);
						res.success(function(data, status) {
							$scope.gridOptions.data = data;
						});
					}

					$scope.getParameter = function(data) {
						$scope.id = data.id
						$scope.updateName = data.name;
						$scope.updateDescription = data.description;
					}

					$scope.save = function() {
						var input = {
							"typeId" : $scope.parameter.id,
							"name" : $scope.name,
							"description" : $scope.description,
							"departmentId" : $scope.department
						};
						var res = $http.post('/PMS/save-parameter', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("masterPageAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("masterPageAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.name = null;
										$scope.description = null;
									}
								});
					}

					$scope.edit = function() {
						var input = {
							"id" : $scope.id,
							"typeId" : $scope.parameter.id,
							"name" : $scope.updateName,
							"description" : $scope.updateDescription,
							"departmentId" : $scope.department
						};
						var res = $http.post('/PMS/save-parameter', input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										$scope.gridOptions.data = $scope.result.object;
										document
												.getElementById("masterPageAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("masterPageAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.updateName = null;
										$scope.updateDescription = null;
									}
								});
					}

					$scope.deleteParameter = function(data) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												var input = {
													"id" : data.id,
													"typeId" : $scope.parameter.id,
													"departmentId" : $scope.department
												};
												var res = $http
														.post(
																'/PMS/delete-parameter',
																input);
												res
														.success(function(data,
																status) {
															$scope.result = data;
															if ($scope.result.integer == 1) {
																$scope.message = $scope.result.string;
																$scope.gridOptions.data = $scope.result.object;
																document
																		.getElementById("masterPageAlert").className = "alert-successs";
																$timeout(
																		function() {
																			document
																					.getElementById("masterPageAlert").className = "";
																			$scope.message = "";
																		}, 5000);
															}
														});
											}
										});
					}
				});
app
		.controller(
				'DashboardController',
				function($window, $timeout, $scope, $http, sharedProperties) {
					logDebug('Enter DashboardController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "";
					$scope.appraisalStages = [ "In Planning", "In Review",
							"In Process" ];
					$scope.employeeLevel = [ "FIRST LEVEL EMPLOYEE",
												"SECOND LEVEL EMPLOYEE" ];
					var overALL;
					var overALL1;
					Chart.defaults.doughnutLabels = Chart.helpers
							.clone(Chart.defaults.doughnut);

					var helpers = Chart.helpers;
					var defaults = Chart.defaults;

					Chart.controllers.doughnutLabels = Chart.controllers.doughnut
							.extend({
								updateElement : function(arc, index, reset) {
									var _this = this;
									var chart = _this.chart, chartArea = chart.chartArea, opts = chart.options, animationOpts = opts.animation, arcOpts = opts.elements.arc, centerX = (chartArea.left + chartArea.right) / 2, centerY = (chartArea.top + chartArea.bottom) / 2, startAngle = opts.rotation, // non
									endAngle = opts.rotation, dataset = _this
											.getDataset(), circumference = reset
											&& animationOpts.animateRotate ? 0
											: arc.hidden ? 0
													: _this
															.calculateCircumference(dataset.data[index])
															* (opts.circumference / (2.0 * Math.PI)), innerRadius = reset
											&& animationOpts.animateScale ? 0
											: _this.innerRadius, outerRadius = reset
											&& animationOpts.animateScale ? 0
											: _this.outerRadius, custom = arc.custom
											|| {}, valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

									helpers
											.extend(
													arc,
													{
														_datasetIndex : _this.index,
														_index : index,
														_model : {
															x : centerX
																	+ chart.offsetX,
															y : centerY
																	+ chart.offsetY,
															startAngle : startAngle,
															endAngle : endAngle,
															circumference : circumference,
															outerRadius : outerRadius,
															innerRadius : innerRadius,
															label : valueAtIndexOrDefault(
																	dataset.label,
																	index,
																	chart.data.labels[index])
														},

														draw : function() {
															var ctx = this._chart.ctx, vm = this._view, sA = vm.startAngle, eA = vm.endAngle, opts = this._chart.config.options;

															var labelPos = this
																	.tooltipPosition();
															var segmentLabel = vm.circumference
																	/ opts.circumference
																	* 100;

															ctx.beginPath();

															ctx
																	.arc(
																			vm.x,
																			vm.y,
																			vm.outerRadius,
																			sA,
																			eA);
															ctx
																	.arc(
																			vm.x,
																			vm.y,
																			vm.innerRadius,
																			eA,
																			sA,
																			true);

															ctx.closePath();
															ctx.strokeStyle = vm.borderColor;
															ctx.lineWidth = vm.borderWidth;

															ctx.fillStyle = vm.backgroundColor;

															ctx.fill();
															ctx.lineJoin = 'bevel';

															if (vm.borderWidth) {
																ctx.stroke();
															}

															if (vm.circumference > 0.15) {
																ctx.beginPath();
																ctx.font = helpers
																		.fontString(
																				opts.defaultFontSize,
																				opts.defaultFontStyle,
																				opts.defaultFontFamily);
																ctx.fillStyle = "#fff";
																ctx.textBaseline = "top";
																ctx.textAlign = "center";
																ctx
																		.fillText(
																				segmentLabel
																						.toFixed(0)
																						+ "%",
																				labelPos.x,
																				labelPos.y);
															}
														}
													});

									var model = arc._model;
									model.backgroundColor = custom.backgroundColor ? custom.backgroundColor
											: valueAtIndexOrDefault(
													dataset.backgroundColor,
													index,
													arcOpts.backgroundColor);
									model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor
											: valueAtIndexOrDefault(
													dataset.hoverBackgroundColor,
													index,
													arcOpts.hoverBackgroundColor);
									model.borderWidth = custom.borderWidth ? custom.borderWidth
											: valueAtIndexOrDefault(
													dataset.borderWidth, index,
													arcOpts.borderWidth);
									model.borderColor = custom.borderColor ? custom.borderColor
											: valueAtIndexOrDefault(
													dataset.borderColor, index,
													arcOpts.borderColor);
									// Set correct angles if not resetting
									if (!reset || !animationOpts.animateRotate) {
										if (index === 0) {
											model.startAngle = opts.rotation;
										} else {
											model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
										}
										model.endAngle = model.startAngle
												+ model.circumference;
									}
									arc.pivot();
								}
							});
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						enableGridMenu : true,
						enableFiltering : true,
						enableSelectAll : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'myFile.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
						columnDefs : [
								{
									name : 'Sr #',
									field : 'empCode',
									width : '5%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								}, {
									name : 'Emp Code',
									field : 'empCode',
									width : '15%'
								}, {
									name : 'Name',
									field : 'empName',
									width : '25%'
								}, {
									name : 'Department',
									field : 'departmentName',
									width : '10%'
								}, {
									name : 'Designation',
									field : 'designationName',
									width : '10%'
								}, {
									name : 'Stage',
									field : 'stage',
									width : '10%'
								}, {
									name : 'Sub-Stage',
									field : 'subStage',
									width : '10%'
								}]
					};

					$scope.getAppraisalData = function()
					{
						if (overALL) {
							overALL.destroy();
						}
						$scope.gridOptions.data = [];
						$scope.selectedAppraisalStage = null;
					}
					
					$scope.getDashBoardDetails = function() {
						$scope.datasetsData = [];
						$scope.datasetsDataLabel = [];
						document.getElementById("main_body").className = "loading-pane";
						// var res = $http
						// .post('/PMS/get-all-appraisal-year-details');
						// res
						// .success(function(data, status) {
						// $scope.appraisalYearList = data.object[0];
						// $scope.appraisalYear = data.object[1];
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type":$scope.levelType,
							"stage" : $scope.selectedAppraisalStage,
							"appraisalYearId" : $scope.appraisalYear.id,
						};
						var res = $http
								.post('/PMS/getDashBoardDetails', input);
						res
								.success(function(data, status) {
									$scope.overAllData = data.object[0][0];
									$scope.chartLayout = true;
									if ($scope.overAllData != null) {
										if ($scope.selectedAppraisalStage === "In Planning") {
											$scope.datasetsDataLabel
													.push("Goal Setting");
											$scope.datasetsDataLabel
													.push("Goal Approval");
										}
										if ($scope.selectedAppraisalStage === "In Review") {
											$scope.datasetsDataLabel
													.push("Mid Term Review");
										}
										if ($scope.selectedAppraisalStage === "In Process") {
											$scope.datasetsDataLabel
													.push("Year End Assessment");
											$scope.datasetsDataLabel
													.push("Assessment Approval");
										}

										if ($scope.overAllData.goalSettingFirstManager > 0
												|| $scope.overAllData.goalApprovalFirstManager > 0) {
											$scope.datasetsData
													.push($scope.overAllData.goalSettingFirstManager);
											$scope.datasetsData
													.push($scope.overAllData.goalApprovalFirstManager);
										}
										if ($scope.overAllData.goalSettingSecondManager > 0
												|| $scope.overAllData.goalApprovalSecondManager > 0) {
											$scope.datasetsData =[];
											$scope.datasetsData
													.push($scope.overAllData.goalSettingSecondManager);
											$scope.datasetsData
													.push($scope.overAllData.goalApprovalSecondManager);
											$scope.hideCanvas = true;
										}
										if ($scope.overAllData.inReviewFirstManager > 0) {
											$scope.datasetsData
													.push($scope.overAllData.inReviewFirstManager);
										}
										if ($scope.overAllData.inReviewSecondManager > 0) {
											$scope.datasetsData =[];
											$scope.datasetsData
													.push($scope.overAllData.inReviewSecondManager);
											$scope.hideCanvas = true;
										}
										if ($scope.overAllData.yearEndAssessmentFirstManager > 0
												|| $scope.overAllData.assessmentApprovalFirstManager > 0) {

											$scope.datasetsData
													.push($scope.overAllData.yearEndAssessmentFirstManager);
											$scope.datasetsData
													.push($scope.overAllData.assessmentApprovalFirstManager);
										}
										if ($scope.overAllData.yearEndAssessmentSecondManager > 0
												|| $scope.overAllData.assessmentApprovalSecondManager > 0) {
											$scope.datasetsData =[];
											$scope.datasetsData
													.push($scope.overAllData.yearEndAssessmentSecondManager);
											$scope.datasetsData
													.push($scope.overAllData.assessmentApprovalSecondManager);
											$scope.hideCanvas = true;
										}
									}
									var config = {
										type : 'doughnutLabels',
										data : {
											datasets : [ {
												data : $scope.datasetsData,
												backgroundColor : [ "#46BFBD",
														"#FDB45C", "#F7464A" ],
												label : 'Dataset 1'
											} ],
											labels : $scope.datasetsDataLabel
										},
										options : {
											scale : {
												pointLabels : {
													fontSize : 20,

												}
											},
											pointLabelFontSize : 20,
											responsive : true,
											legend : {
												position : 'bottom',
											},

											animation : {
												animateScale : true,
												animateRotate : true
											}
										}
									};

									var canvasOL = document
											.getElementById("firstManagerChart");
									var ctxOL = canvasOL.getContext("2d");
									if (overALL) {
										overALL.destroy();
									}
									overALL = new Chart(ctxOL, config);
									canvasOL.onclick = function(evt) {
										$scope.gridOptions.data = null;
										var activePoints = overALL
												.getElementsAtEvent(evt);
										if (activePoints[0]) {
											var chartData = activePoints[0]['_chart'].config.data;
											var idx = activePoints[0]['_index'];
											var label = chartData.labels[idx];
											$scope.modalHeading = label;
											var input = {
												"empCode" : document
														.getElementById("globalUserName").value,
												"appraisalYearId" : $scope.appraisalYear.id,
												"type" : label,
												"name" : "FIRST LEVEL MANAGER"
											};
											var res = $http
													.post(
															'/PMS/getAppraisalPendingEmployeeDetails',
															input);
											res
													.success(function(data,
															status) {
														if (data.integer == 1) {
															$scope.result = data.object;
															for(var i =0;i<$scope.result.length;i++)
															{
													if($scope.result[i].initializationYear == 1 && ($scope.result[i].employeeIsvisible == 1 || $scope.result[i].employeeIsvisible == 0)	
															&& $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage = "In Planning";
														$scope.result[i].subStage = "Goal Setting";
														}
													if(($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	 && $scope.result[i].acknowledgementCheck == 1
															&& ($scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].firstLevelIsvisible == 0 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0))
														{
														$scope.result[i].stage = "In Planning";
														$scope.result[i].subStage = "Goal Approval";
														}
// if($scope.result[i].initializationYear == 1 &&
// $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Review";
// $scope.result[i].subStage = "Mid Year Review";
// }
													if($scope.result[i].midYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage  = "In Review";
														$scope.result[i].subStage = "Mid Year Review";
														}
// if($scope.result[i].midYear == 1 && $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Process";
// $scope.result[i].subStage = "Year End Assessment";
// }
													if($scope.result[i].finalYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Year End Assessment";
													}
													if($scope.result[i].finalYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Assessment approval";
													}
													}
															$scope.gridOptions.data = $scope.result;
														}
													});
											$('#myModal').modal('show');

										}
									};
									var canvasOL1 = document
											.getElementById("secondManagerChart");
									var ctxOL1 = canvasOL1.getContext("2d");
									if (overALL1) {
										overALL1.destroy();
									}
									overALL1 = new Chart(ctxOL1, config);
									canvasOL1.onclick = function(evt) {
										$scope.gridOptions.data = null;
										var activePoints = overALL1
												.getElementsAtEvent(evt);
										if (activePoints[0]) {
											var chartData = activePoints[0]['_chart'].config.data;
											var idx = activePoints[0]['_index'];
											var label = chartData.labels[idx];
											$scope.modalHeading = label;
											var input = {
												"empCode" : document
														.getElementById("globalUserName").value,
												"appraisalYearId" : $scope.appraisalYear.id,
												"type" : label,
												"name" : "SECOND LEVEL MANAGER",
											};
											var res = $http
													.post(
															'/PMS/getAppraisalPendingEmployeeDetails',
															input);
											res
													.success(function(data,
															status) {
														if (data.integer == 1) {
															$scope.result = data.object;
															for(var i =0;i<$scope.result.length;i++)
															{
														if($scope.result[i].initializationYear == 1 && ($scope.result[i].employeeIsvisible == 1 || $scope.result[i].employeeIsvisible == 0)	
															&& $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage = "In Planning";
														$scope.result[i].subStage = "Goal Setting";
														}
														if($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	 && $scope.result[i].acknowledgementCheck == 1
																&& ($scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].firstLevelIsvisible == 0 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
															$scope.result[i].stage = "In Planning";
														$scope.result[i].subStage = "Goal Approval";
														}
// if($scope.result[i].initializationYear == 1 &&
// $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Review";
// $scope.result[i].subStage = "Mid Year Review";
// }
													if($scope.result[i].midYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage  = "In Review";
														$scope.result[i].subStage = "Mid Year Review";
														}
// if($scope.result[i].midYear == 1 && $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Process";
// $scope.result[i].subStage = "Year End Assessment";
// }
													if($scope.result[i].finalYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Year End Assessment";
													}
													if($scope.result[i].finalYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Assessment approval";
													}
													}
															$scope.gridOptions.data = $scope.result;
														}
													});
											$('#myModal').modal('show');

										}
									};
									document.getElementById("main_body").className = "";
									$scope.chartData = [];
									$scope.chartData = $scope.datasetsData;

									// $scope.subEmployeeAppraisalCountDetails =
									// [];
									// if ($scope.result1 != null) {
									// if ($scope.result1.pendingWithEmployee >
									// 0
									// || $scope.result1.pendingWithFirstManager
									// > 0
									// || $scope.result1.firstManagerClosed > 0)
									// {
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.pendingWithEmployee);
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.pendingWithFirstManager);
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.firstManagerClosed);
									// }
									// if ($scope.result1.pendingWithFirstLevel
									// > 0
									// ||
									// $scope.result1.pendingWithSecondManager >
									// 0
									// || $scope.result1.secondManagerClosed >
									// 0) {
									// $scope.hideCanvas = true;
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.pendingWithFirstLevel);
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.pendingWithSecondManager);
									// $scope.subEmployeeAppraisalCountDetails
									// .push($scope.result1.secondManagerClosed);
									// }
									// }
									// $scope.loggedUserAppraisalDetails =
									// $scope.result.object;
									// if ($scope.loggedUserAppraisalDetails !=
									// null) {
									// if
									// ($scope.loggedUserAppraisalDetails.initializationYear
									// == 1) {
									// $scope.stage = "In Planning";
									// } else if
									// ($scope.loggedUserAppraisalDetails.midYear
									// == 1) {
									// $scope.stage = "In Review";
									// } else {
									// $scope.stage = "In Process";
									// }
									// }
									// var config = {
									// type : 'doughnutLabels',
									// data : {
									// datasets : [ {
									// data :
									// $scope.subEmployeeAppraisalCountDetails,
									// backgroundColor : [
									// "#46BFBD",
									// "#FDB45C",
									// "#F7464A", ],
									// label : 'Dataset 1'
									// } ],
									// labels : [
									// "In Planning",
									// "In Review",
									// "Completed" ]
									// },
									// options : {
									// scale : {
									// pointLabels : {
									// fontSize : 20
									// }
									// },
									// responsive : true,
									// legend : {
									// position : 'bottom',
									// },
									// animation : {
									// animateScale : true,
									// animateRotate : true
									// }
									// }
									// };

									// var canvasOL = document
									// .getElementById("firstManagerChart");
									// var ctxOL = canvasOL
									// .getContext("2d");
									// var overALL = new Chart(ctxOL,
									// config);
									// canvasOL.onclick = function(evt) {
									// var activePoints = overALL
									// .getElementsAtEvent(evt);
									// if (activePoints[0]) {
									// var chartData =
									// activePoints[0]['_chart'].config.data;
									// var idx = activePoints[0]['_index'];
									// var label = chartData.labels[idx];
									// $scope.modalHeading = label;
									// if (label === "Completed") {
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "FIRST LEVEL MANAGER",
									// "type" : "CLOSED WITH MANAGER"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									// $('#myModal')
									// .modal(
									// 'show');
									// } else if (label === "In Review") {
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "FIRST LEVEL MANAGER",
									// "type" : "PENDING WITH MANAGER"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									//
									// $('#myModal')
									// .modal(
									// 'show');
									// } else {
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "FIRST LEVEL MANAGER",
									// "type" : "PENDING WITH EMPLOYEE"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									//
									// $('#myModal')
									// .modal(
									// 'show');
									// }
									// }
									// };
									// var canvasSecond = document
									// .getElementById("secondManagerChart");
									// var ctxSecond = canvasSecond
									// .getContext("2d");
									// var overSecond = new Chart(
									// ctxSecond, config);
									// canvasSecond.onclick = function(
									// evt) {
									// var activePoints = overSecond
									// .getElementsAtEvent(evt);
									// if (activePoints[0]) {
									// var chartData =
									// activePoints[0]['_chart'].config.data;
									// var idx = activePoints[0]['_index'];
									// var label = chartData.labels[idx];
									// $scope.modalHeading = label;
									// if (label === "Completed") {
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "SECOND LEVEL MANAGER",
									// "type" : "CLOSED WITH MANAGER"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									//
									// $('#myModal')
									// .modal(
									// 'show');
									// } else if (label === "In Review") {
									//
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "SECOND LEVEL MANAGER",
									// "type" : "PENDING WITH MANAGER"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									// $('#myModal')
									// .modal(
									// 'show');
									// } else {
									//
									// var input = {
									// "empCode" : document
									// .getElementById("globalUserName").value,
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// "name" : "SECOND LEVEL MANAGER",
									// "type" : "PENDING WITH EMPLOYEE"
									// };
									// var res = $http
									// .post(
									// '/PMS/getAppraisalPendingEmployeeDetails',
									// input);
									// res
									// .success(function(
									// data,
									// status) {
									// $scope.gridOptions.data = data.object;
									// });
									// $('#myModal')
									// .modal(
									// 'show');
									// }
									// }
									// };
									document.getElementById("main_body").className = "";
								});
						// });
					}
				});

app
		.controller(
				'HRDashboardController',
				function($window, $timeout, $scope, $http, sharedProperties) {
					logDebug('Enter HRDashboardController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					document.getElementById("main_body").className = "";
					var ctxB;
					var myBarChart;
					var overALL;

					$scope.appraisalStages = [ "In Planning", "In Review",
							"In Process" ];
					$scope.barChartDataLabels = [];
					$scope.barChartDataGoalSetting = [];
					$scope.barChartDataGoalApproval = [];
					$scope.barChartDataInReview = [];
					$scope.barChartDataYearEndAssessment = [];
					$scope.barChartDataApprraisalApproval = [];
					Chart.defaults.doughnutLabels = Chart.helpers
							.clone(Chart.defaults.doughnut);

					var helpers = Chart.helpers;
					var defaults = Chart.defaults;

					Chart.controllers.doughnutLabels = Chart.controllers.doughnut
							.extend({
								updateElement : function(arc, index, reset) {
									var _this = this;
									var chart = _this.chart, chartArea = chart.chartArea, opts = chart.options, animationOpts = opts.animation, arcOpts = opts.elements.arc, centerX = (chartArea.left + chartArea.right) / 2, centerY = (chartArea.top + chartArea.bottom) / 2, startAngle = opts.rotation, // non
									// reset
									// case
									// handled
									// later
									endAngle = opts.rotation, // non reset
									// case handled
									// later
									dataset = _this.getDataset(), circumference = reset
											&& animationOpts.animateRotate ? 0
											: arc.hidden ? 0
													: _this
															.calculateCircumference(dataset.data[index])
															* (opts.circumference / (2.0 * Math.PI)), innerRadius = reset
											&& animationOpts.animateScale ? 0
											: _this.innerRadius, outerRadius = reset
											&& animationOpts.animateScale ? 0
											: _this.outerRadius, custom = arc.custom
											|| {}, valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

									helpers
											.extend(
													arc,
													{
														// Utility
														_datasetIndex : _this.index,
														_index : index,

														// Desired view
														// properties
														_model : {
															x : centerX
																	+ chart.offsetX,
															y : centerY
																	+ chart.offsetY,
															startAngle : startAngle,
															endAngle : endAngle,
															circumference : circumference,
															outerRadius : outerRadius,
															innerRadius : innerRadius,
															label : valueAtIndexOrDefault(
																	dataset.label,
																	index,
																	chart.data.labels[index])
														},

														draw : function() {
															var ctx = this._chart.ctx, vm = this._view, sA = vm.startAngle, eA = vm.endAngle, opts = this._chart.config.options;

															var labelPos = this
																	.tooltipPosition();
															var segmentLabel = vm.circumference
																	/ opts.circumference
																	* 100;

															ctx.beginPath();

															ctx
																	.arc(
																			vm.x,
																			vm.y,
																			vm.outerRadius,
																			sA,
																			eA);
															ctx
																	.arc(
																			vm.x,
																			vm.y,
																			vm.innerRadius,
																			eA,
																			sA,
																			true);

															ctx.closePath();
															ctx.strokeStyle = vm.borderColor;
															ctx.lineWidth = vm.borderWidth;

															ctx.fillStyle = vm.backgroundColor;

															ctx.fill();
															ctx.lineJoin = 'bevel';

															if (vm.borderWidth) {
																ctx.stroke();
															}

															if (vm.circumference > 0.15) { // Trying
																// to
																// hide
																// label when it
																// doesn't fit
																// in
																// segment
																ctx.beginPath();
																ctx.font = helpers
																		.fontString(
																				opts.defaultFontSize,
																				opts.defaultFontStyle,
																				opts.defaultFontFamily);
																ctx.fillStyle = "#fff";
																ctx.textBaseline = "top";
																ctx.textAlign = "center";

																// Round
																// percentage in
																// a way that it
																// always adds
																// up to 100%
																ctx
																		.fillText(
																				segmentLabel
																						.toFixed(0)
																						+ "%",
																				labelPos.x,
																				labelPos.y);
															}
														}
													});

									var model = arc._model;
									model.backgroundColor = custom.backgroundColor ? custom.backgroundColor
											: valueAtIndexOrDefault(
													dataset.backgroundColor,
													index,
													arcOpts.backgroundColor);
									model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor
											: valueAtIndexOrDefault(
													dataset.hoverBackgroundColor,
													index,
													arcOpts.hoverBackgroundColor);
									model.borderWidth = custom.borderWidth ? custom.borderWidth
											: valueAtIndexOrDefault(
													dataset.borderWidth, index,
													arcOpts.borderWidth);
									model.borderColor = custom.borderColor ? custom.borderColor
											: valueAtIndexOrDefault(
													dataset.borderColor, index,
													arcOpts.borderColor);

									// Set correct angles if not resetting
									if (!reset || !animationOpts.animateRotate) {
										if (index === 0) {
											model.startAngle = opts.rotation;
										} else {
											model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
										}

										model.endAngle = model.startAngle
												+ model.circumference;
									}

									arc.pivot();
								}
							});

					$scope.getDashBoardDetails = function() {
						document.getElementById("main_body").className = "loading-pane";
						$scope.showCharts = true;
						$scope.datasetsData = [];
						$scope.datasetsDataLabel = [];
						$scope.barChartDataLabels = [];
						$scope.barChartDataGoalSetting = [];
						$scope.barChartDataGoalApproval = [];
						$scope.barChartDataInReview = [];
						$scope.barChartDataYearEndAssessment = [];
						$scope.barChartDataApprraisalApproval = [];
						if($scope.selectedAppraisalStage!=undefined)
						{
						var input = {
							"appraisalYearId" : $scope.appraisalYear.id,
							"type" : $scope.selectedAppraisalStage
						};
						var res = $http.post('/PMS/getHRDashBoardDetails',
								input);
						res
								.success(function(data, status) {
									$scope.overAllDepartmentData = data.object[0];
									$scope.departmentWiseBarChatData = data.object[1];

									if ($scope.selectedAppraisalStage === "In Planning") {
										$scope.datasetsDataLabel
												.push("Goal Setting");
										$scope.datasetsDataLabel
												.push("Goal Approval");
										$scope.datasetsData
												.push($scope.overAllDepartmentData.goalSetting)
										$scope.datasetsData
												.push($scope.overAllDepartmentData.goalApproval)
									}
									if ($scope.selectedAppraisalStage === "In Review") {
										$scope.datasetsDataLabel
												.push("Mid Term Review");
										$scope.datasetsData
												.push($scope.overAllDepartmentData.inReview)

									}
									if ($scope.selectedAppraisalStage === "In Process") {
										$scope.datasetsDataLabel
												.push("Year End Assessment");
										$scope.datasetsDataLabel
												.push("Assessment Approval");
										$scope.datasetsData
												.push($scope.overAllDepartmentData.yearEndAssessment)
										$scope.datasetsData
												.push($scope.overAllDepartmentData.assessmentApproval)
									}
									var config = {
										type : 'doughnutLabels',
										data : {
											datasets : [ {
												data : $scope.datasetsData,
												backgroundColor : [ "#46BFBD",
														"#FDB45C", "#F7464A" ],
												label : 'Dataset 1'
											} ],
											labels : $scope.datasetsDataLabel
										},
										options : {
											scale : {
												pointLabels : {
													fontSize : 20,

												}
											},
											pointLabelFontSize : 20,
											responsive : true,
											legend : {
												position : 'bottom',
											},

											animation : {
												animateScale : true,
												animateRotate : true
											}
										}
									};
									var canvasOL = document
											.getElementById("myChart");
									var ctxOL = canvasOL.getContext("2d");
									if (overALL) {
										overALL.destroy();
									}
									overALL = new Chart(ctxOL, config);
									canvasOL.onclick = function(evt) {
										$scope.gridOptions.data = null;
										var activePoints = overALL
												.getElementsAtEvent(evt);
										if (activePoints[0]) {
											var chartData = activePoints[0]['_chart'].config.data;
											var idx = activePoints[0]['_index'];
											var label = chartData.labels[idx];
											$scope.modalHeading = label;
											// if (label === "Goal Setting") {
											var input = {
												"appraisalYearId" : $scope.appraisalYear.id,
												"type" : label
											};
											var res = $http
													.post(
															'/PMS/getALLAppraisalPendingEmployeeDetails',
															input);
											res
													.success(function(data,
															status) {
														if (data.integer == 1) {
															$scope.result = data.object;
															for(var i =0;i<$scope.result.length;i++)
															{
													if($scope.result[i].initializationYear == 1 && ($scope.result[i].employeeIsvisible == 1 || $scope.result[i].employeeIsvisible == 0)	
															&& $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage = "In Planning";
														$scope.result[i].subStage = "Goal Setting";
														}
													if($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	&& $scope.result[i].acknowledgementCheck == 1
															&& ($scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].firstLevelIsvisible == 0 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
													{
														$scope.result[i].stage = "In Planning";
													$scope.result[i].subStage = "Goal Approval";
													}
// if($scope.result[i].initializationYear == 1 &&
// $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Review";
// $scope.result[i].subStage = "Mid Year Review";
// }
													if($scope.result[i].midYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
														{
														$scope.result[i].stage  = "In Review";
														$scope.result[i].subStage = "Mid Year Review";
														}
// if($scope.result[i].midYear == 1 && $scope.result[i].employeeIsvisible == 0
// && $scope.result[i].firstLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisible == 0 &&
// $scope.result[i].secondLevelIsvisibleCheck == 1)
// {
// $scope.result[i].stage = "In Process";
// $scope.result[i].subStage = "Year End Assessment";
// }
													if($scope.result[i].finalYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Year End Assessment";
													}
													if($scope.result[i].finalYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
													{
														$scope.result[i].stage  = "In Process";
													$scope.result[i].subStage = "Assessment approval";
													}
													}
															$scope.gridOptions.data = $scope.result;
														}
													});
											$('#myModal').modal('show');
											// } else if (label === "Goal
											// Approval") {
											// var input = {
											// "appraisalYearId" :
											// $scope.appraisalYear.id,
											// "type" : label
											// };
											// var res = $http
											// .post(
											// '/PMS/getALLAppraisalPendingEmployeeDetails',
											// input);
											// res
											// .success(function(
											// data,
											// status) {
											// if (data.integer == 1) {
											// $scope.gridOptions.data =
											// data.object;
											// }
											// });
											// $('#myModal')
											// .modal(
											// 'show');
											// } else if (label === "Mid Term
											// Review") {
											// var input = {
											// "appraisalYearId" :
											// $scope.appraisalYear.id,
											// "type" : label
											// };
											// var res = $http
											// .post(
											// '/PMS/getALLAppraisalPendingEmployeeDetails',
											// input);
											// res
											// .success(function(
											// data,
											// status) {
											// if (data.integer == 1) {
											// $scope.gridOptions.data =
											// data.object;
											// }
											// });
											// $('#myModal')
											// .modal(
											// 'show');
											// } else if (label === "Year End
											// Assessment") {
											// var input = {
											// "appraisalYearId" :
											// $scope.appraisalYear.id,
											// "type" : label
											// };
											// var res = $http
											// .post(
											// '/PMS/getALLAppraisalPendingEmployeeDetails',
											// input);
											// res
											// .success(function(
											// data,
											// status) {
											// if (data.integer == 1) {
											// $scope.gridOptions.data =
											// data.object;
											// }
											// });
											// $('#myModal')
											// .modal(
											// 'show');
											// } else {
											// var input = {
											// "appraisalYearId" :
											// $scope.appraisalYear.id,
											// "type" : label
											// };
											// var res = $http
											// .post(
											// '/PMS/getALLAppraisalPendingEmployeeDetails',
											// input);
											// res
											// .success(function(
											// data,
											// status) {
											// if (data.integer == 1) {
											// $scope.gridOptions.data =
											// data.object;
											// }
											// });
											// $('#myModal')
											// .modal(
											// 'show');
											// }

										}
									};

									for (var i = 0; i < $scope.departmentWiseBarChatData.length; i++) {
										$scope.barChartDataLabels
												.push($scope.departmentWiseBarChatData[i].name);

										if ($scope.selectedAppraisalStage === "In Planning") {
											$scope.barChartDataGoalSetting
													.push($scope.departmentWiseBarChatData[i].goalSetting);
											$scope.barChartDataGoalApproval
													.push($scope.departmentWiseBarChatData[i].goalApproval);
										}
										if ($scope.selectedAppraisalStage === "In Review") {
											$scope.barChartDataInReview
													.push($scope.departmentWiseBarChatData[i].inReview);

										}
										if ($scope.selectedAppraisalStage === "In Process") {
											$scope.barChartDataYearEndAssessment
													.push($scope.departmentWiseBarChatData[i].yearEndAssessment);
											$scope.barChartDataApprraisalApproval
													.push($scope.departmentWiseBarChatData[i].assessmentApproval);

										}

									}
									var barChartData;
									if ($scope.selectedAppraisalStage === "In Planning") {
										barChartData = {
											labels : $scope.barChartDataLabels,
											datasets : [
													{
														label : "Goal Setting",
														backgroundColor : "#46BFBD",
														data : $scope.barChartDataGoalSetting
													},
													{
														label : "Goal Approaval",
														backgroundColor : "#FDB45C",
														data : $scope.barChartDataGoalApproval
													} ]
										};
									}
									if ($scope.selectedAppraisalStage === "In Review") {
										barChartData = {
											labels : $scope.barChartDataLabels,
											datasets : [ {
												label : "Mid Term Review",
												backgroundColor : "#46BFBD",
												data : $scope.barChartDataInReview
											} ]
										};
									}
									if ($scope.selectedAppraisalStage === "In Process") {
										barChartData = {
											labels : $scope.barChartDataLabels,
											datasets : [
													{
														label : "Year End Assessment",
														backgroundColor : "#46BFBD",
														data : $scope.barChartDataYearEndAssessment
													},
													{
														label : "Assessment Approval",
														backgroundColor : "#FDB45C",
														data : $scope.barChartDataApprraisalApproval
													} ]
										};
									}

									ctxB = document.getElementById("barChart")
											.getContext("2d");
									if (myBarChart) {
										myBarChart.destroy();
									}
									myBarChart = new Chart(ctxB, {
										type : 'bar',

										data : barChartData,
										options : {
											legend : {
												display : true,
												position : 'bottom',
												labels : {
													fontColor : '#2E4053'
												}
											},"hover": {
									        	"animationDuration": 0
									        },
											 "animation": {
										        	"duration": 1,
																"onComplete": function () {
																	var chartInstance = this.chart,
																		ctx = chartInstance.ctx;
																	
																	ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
																	ctx.textAlign = 'center';
																	ctx.textBaseline = 'bottom';

																	this.data.datasets.forEach(function (dataset, i) {
																		var meta = chartInstance.controller.getDatasetMeta(i);
																		meta.data.forEach(function (bar, index) {
																			var data = dataset.data[index];                            
																			ctx.fillText(data, bar._model.x, bar._model.y - 5);
																		});
																	});
																}
										        },
											barValueSpacing : 20,
											tooltips: {
									        	"enabled": false
									         },
											scales : {
												xAxes : [ {
													ticks : {
														autoSkip : false,
														maxRotation : 90,
														minRotation : 90
													}
												} ]
											}
										}
									});
									$scope.showLoader = true;
									document.getElementById("main_body").className = "";

									// var input = {
									// "appraisalYearId" :
									// $scope.appraisalYear.id,
									// };
									// var res =
									// $http.post('/PMS/getAllDepartmentHRDashBoardDetails',
									// input);
									// res
									// .success(function(data, status) {
									// $scope.departmentWiseChartData = data;
									// document.getElementById("main_body").className
									// = "";
									// });

								});
						}
						else
							{
							document.getElementById("main_body").className = "";
							}
					}
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						enableGridMenu : true,
						enableSelectAll : true,
						enableFiltering : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'myFile.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '5%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								}, {
									name : 'Emp Code',
									field : 'empCode',
									width : '15%'
								}, {
									name : 'Name',
									field : 'empName',
									width : '25%'
								}, {
									name : 'Designation',
									field : 'designationName',
									width : '10%'
								}, {
									name : 'Department',
									field : 'departmentName',
									width : '7%'
								}, {
									name : 'Stage',
									field : 'stage',
									width : '10%'
								}, {
									name : 'Sub-Stage',
									field : 'subStage',
									width : '10%'
								}
						// , {
						// name : 'Aging Days',
						// field : 'ageing',
						// width : '7%'
						// }
						]
					};

					$scope.ShowHide = function() {
						$scope.IsVisible = $scope.IsVisible ? false : true;
					}

					$scope.bindCanvas = function(data) {
						if (data.goalApproval != null) {
							var config1 = {
								type : 'doughnutLabels',
								data : {
									datasets : [ {
										data : [ data.goalSetting,
												data.goalApproval ],
										backgroundColor : [ "#46BFBD",
												"#FDB45C" ],
										label : 'Dataset 1'
									} ],
									labels : [ "Goal Setting", "Goal Approval" ]
								},
								options : {
									responsive : true,
									legend : {
										position : 'bottom',
									},

									animation : {
										animateScale : true,
										animateRotate : true
									}
								}
							};
						}
						if (data.inReview != null) {

							var config1 = {
								type : 'doughnutLabels',
								data : {
									datasets : [ {
										data : [ data.inReview ],
										backgroundColor : [ "#46BFBD" ],
										label : 'Dataset 1'
									} ],
									labels : [ "Mid Term Review" ]
								},
								options : {
									responsive : true,
									legend : {
										position : 'bottom',
									},

									animation : {
										animateScale : true,
										animateRotate : true
									}
								}
							};
						}
						if (data.yearEndAssessment != null) {
							var config1 = {
								type : 'doughnutLabels',
								data : {
									datasets : [ {
										data : [ data.yearEndAssessment,
												data.assessmentApproval ],
										backgroundColor : [ "#46BFBD",
												"#FDB45C" ],
										label : 'Dataset 1'
									} ],
									labels : [ "Year End Assessment",
											"Assessment Approval" ]
								},
								options : {
									responsive : true,
									legend : {
										position : 'bottom',
									},

									animation : {
										animateScale : true,
										animateRotate : true
									}
								}
							};

						}
						var canvasIT = document.getElementById("myChart-"
								+ data.id);
						var ctxIT = canvasIT.getContext("2d");
						var it = new Chart(ctxIT, config1);
						canvasIT.onclick = function(evt) {
							$scope.gridOptions.data = null;
							var activePoints = it.getElementsAtEvent(evt);

							var chartData = activePoints[0]['_chart'].config.data;
							var idx = activePoints[0]['_index'];
							var label = chartData.labels[idx];
							$scope.modalHeading = label;
							// if (label === "Goal Setting") {
							var input = {
									"appraisalYearId" : $scope.appraisalYear.id,
									 "type" : label,
									 "id":data.id
							};
							var res = $http
									.post(
											'/PMS/getALLAppraisalPendingEmployeeDetails',
											input);
							res
									.success(function(data,
											status) {
										if (data.integer == 1) {
											$scope.result = data.object;
											for(var i =0;i<$scope.result.length;i++)
											{
									if($scope.result[i].initializationYear == 1 && ($scope.result[i].employeeIsvisible == 1 || $scope.result[i].employeeIsvisible == 0)	
											&& $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 0)
										{
										$scope.result[i].stage = "In Planning";
										$scope.result[i].subStage = "Goal Setting";
										}
									if($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	&& $scope.result[i].acknowledgementCheck == 1
											&& ($scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].firstLevelIsvisible == 0 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
									{
										$scope.result[i].stage = "In Planning";
									$scope.result[i].subStage = "Goal Approval";
									}
									if($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	
											&& $scope.result[i].firstLevelIsvisible == 0 &&  $scope.result[i].secondLevelIsvisible == 0  && $scope.result[i].secondLevelIsvisibleCheck == 1)
										{
										$scope.result[i].stage  = "In Review";
										$scope.result[i].subStage = "Mid Year Review";
										}
									if($scope.result[i].midYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
										{
										$scope.result[i].stage  = "In Review";
										$scope.result[i].subStage = "Mid Year Review";
										}
									if($scope.result[i].midYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
									{
										$scope.result[i].stage  = "In Process";
									$scope.result[i].subStage = "Year End Assessment";
									}
									if($scope.result[i].finalYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
									{
										$scope.result[i].stage  = "In Process";
									$scope.result[i].subStage = "Year End Assessment";
									}
									if($scope.result[i].finalYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
									{
										$scope.result[i].stage  = "In Process";
									$scope.result[i].subStage = "Assessment approval";
									}
									}
											$scope.gridOptions.data = $scope.result;
										}
									});
							$('#myModal').modal('show');
							
						
						};
					}

				});

app
		.controller(
				'AppraisalCycleController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					$scope.formData = {};
					$(function() {
						// Replace the <textarea id="editor1"> with a CKEditor
						// instance, using default configuration.
						CKEDITOR.replace('editor1')
						CKEDITOR.replace('editorMid')
						CKEDITOR.replace('editorFinal')
						// bootstrap WYSIHTML5 - text editor
						$('.textarea').wysihtml5()
					})
					logDebug('Enter AppraisalCycleController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					$scope.formData = {};
					$scope.all = true;
					// $scope.departmentList =[];
					// $scope.designationList=[];
					// $scope.departmentIdList =[];
					// $scope.designationIdList =[];

					// $(function() {
					// // Initialize Select2 Elements
					// $('.select2').select2();
					// $("#departmentIdStart").select2({
					// data : $scope.departmentList
					// });
					// $("#designationIdStart").select2({
					// data : $scope.designationList
					// });
					// $("#departmentIdMid").select2({
					// data : $scope.departmentList
					// });
					// $("#designationIdMid").select2({
					// data : $scope.designationList
					// });
					// $("#departmentIdFinal").select2({
					// data : $scope.departmentList
					// });
					// $("#designationIdFinal").select2({
					// data : $scope.designationList
					// });
					// });
					$scope.getActiveTabId = function(index) {
						$scope.activeTabId = index;
						$scope.showAppraisedEmployee = false;
					};

					/* Set Active Year selected in drop down */
					$scope.onLoad = function() {
						var res = $http
								.post('/PMS/get-all-appraisal-year-details');
						res.success(function(data, status) {
							$scope.appraisalYearList = data.object[0];
							$scope.formData.appraisalYear = data.object[1];
						});

						// var res =
						// $http.post('/PMS/get-appraisal-configurator-details');
						// res.success(function(data, status) {
						// if (data.integer == 1) {
						// $scope.departmentListData = data.object[0];
						// $scope.designationListData = data.object[1];
						// for(i=0;i<$scope.departmentListData.length;i++)
						// {
						// $scope.departmentList.push({
						// "id":$scope.departmentListData[i].id,
						// "text":$scope.departmentListData[i].name
						// });
						// }
						// for(i=0;i<$scope.designationListData.length;i++)
						// {
						// $scope.designationList.push({
						// "id":$scope.designationListData[i].id,
						// "text":$scope.designationListData[i].name
						// });
						// }
						// }
						// });
					}
					// $scope.checkedAll = function(data) {
					// if($scope.activeTabId == 0)
					// {
					// $scope.isStartCheckedAll = data;
					// $('#departmentIdStart').html('').select2({data:
					// $scope.departmentList});
					// $('#designationIdStart').html('').select2({data:
					// $scope.designationList});
					// }
					// else if($scope.activeTabId == 1)
					// {
					// $scope.isMidCheckedAll = data;
					// $('#departmentIdMid').html('').select2({data:
					// $scope.departmentList});
					// $('#designationIdMid').html('').select2({data:
					// $scope.designationList});
					// }
					// else
					// {
					// $scope.isFinalCheckedAll = data;
					// $('#departmentIdFinal').html('').select2({data:
					// $scope.departmentList});
					// $('#designationIdFinal').html('').select2({data:
					// $scope.designationList});
					// }
					//		
					// }
					// $scope.getDepartmentIdList = function(data)
					// {
					// $scope.departmentIdList =[];
					// for(var i= 0;i<data.length;i++)
					// {
					// $scope.departmentIdList[i] = data[i].id;
					// }
					//		
					// }
					// $scope.getDesignationIdList = function(data)
					// {
					// $scope.designationIdList =[];
					// for(var i= 0;i<data.length;i++)
					// {
					// $scope.designationIdList[i] = data[i].id;
					// }
					// }
					$scope.gridOptionsInitial = {
						showGridFooter : true,
						multiSelect : false,
						enableGridMenu : true,
						enableSelectAll : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'myFile.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
						columnDefs : [ {
							name : 'Emp Code',
							field : 'empCode',
							width : '15%'
						}, {
							name : 'Name',
							field : 'empName',
							width : '25%'
						}, {
							name : 'Email',
							field : 'email',
							width : '10%'
						}
						// {
						// name : 'View',
						// cellTemplate : '<div style ="text-align : center;">
						// <button class=" btn-primary "
						// ng-click="grid.appScope.ViewAppraisal(row.entity.empCode)"><span
						// class="glyphicon glyphicon-eye-open" style="margin:
						// 0px;"></span> </button></div>',
						// width : '2%'
						// }
						]
					};
					$scope.gridOptionsMid = {
						showGridFooter : true,
						multiSelect : false,
						enableGridMenu : true,
						enableSelectAll : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'myFile.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
						columnDefs : [ {
							name : 'Emp Code',
							field : 'empCode',
							width : '15%'
						}, {
							name : 'Name',
							field : 'empName',
							width : '25%'
						}, {
							name : 'Email',
							field : 'email',
							width : '10%'
						}
						// {
						// name : 'View',
						// cellTemplate : '<div style ="text-align : center;">
						// <button class=" btn-primary "
						// ng-click="grid.appScope.ViewAppraisal(row.entity.empCode)"><span
						// class="glyphicon glyphicon-eye-open" style="margin:
						// 0px;"></span> </button></div>',
						// width : '2%'
						// }
						]
					};
					$scope.gridOptionsFinal = {
						showGridFooter : true,
						multiSelect : false,
						enableGridMenu : true,
						enableSelectAll : true,
						exporterMenuPdf : false, // ADD THIS
						exporterCsvFilename : 'myFile.csv',
						exporterCsvLinkElement : angular.element(document
								.querySelectorAll(".custom-csv-link-location")),
						columnDefs : [ {
							name : 'Emp Code',
							field : 'empCode',
							width : '15%'
						}, {
							name : 'Name',
							field : 'empName',
							width : '25%'
						}, {
							name : 'Email',
							field : 'email',
							width : '10%'
						}
						// {
						// name : 'View',
						// cellTemplate : '<div style ="text-align : center;">
						// <button class=" btn-primary "
						// ng-click="grid.appScope.ViewAppraisal(row.entity.empCode)"><span
						// class="glyphicon glyphicon-eye-open" style="margin:
						// 0px;"></span> </button></div>',
						// width : '2%'
						// }
						]
					};
					$scope.start = function() {
						// $('#startButton').button('loading');
						if ($scope.activeTabId == 0) {
							// $scope.getDepartmentIdList($('#departmentIdStart').select2('data'));
							// $scope.getDesignationIdList($('#designationIdStart').select2('data'));
							// $scope.all = $scope.formData.allStart;
							// $scope.name = $scope.formData.nameStart;
							$scope.startDate = $scope.formData.startDateStart;
							$scope.endDate = $scope.formData.endDateStart;
							$scope.emailSubject = $scope.formData.initialYearSubject;
							$scope.emailContent = CKEDITOR.instances.editor1
									.getData();

						} else if ($scope.activeTabId == 1) {
							// $scope.getDepartmentIdList($('#departmentIdMid').select2('data'));
							// $scope.getDesignationIdList($('#designationIdMid').select2('data'));
							// $scope.all = $scope.formData.allMid;
							// $scope.name = $scope.formData.nameMid;
							$scope.startDate = $scope.formData.startDateMid;
							$scope.formData.eligibility = $scope.formData.midTermeligibility;
							$scope.endDate = $scope.formData.endDateMid;
							$scope.emailSubject = $scope.formData.midYearSubject;
							$scope.emailContent = CKEDITOR.instances.editorMid
									.getData();

						} else {
							// $scope.getDepartmentIdList($('#departmentIdFinal').select2('data'));
							// $scope.getDesignationIdList($('#designationIdFinal').select2('data'));
							// $scope.all = $scope.formData.allFinal;
							// $scope.name = $scope.formData.nameFinal;
							$scope.startDate = $scope.formData.startDateFinal;
							$scope.endDate = $scope.formData.endDateFinal;
							$scope.formData.eligibility = $scope.formData.finalYearligibility;
							$scope.emailSubject = $scope.formData.finalYearSubject;
							$scope.emailContent = CKEDITOR.instances.editorFinal
									.getData();

						}

						var input = {
							"id" : $scope.activeTabId,
							"appraisalYear" : $scope.formData.appraisalYear.name,
							"appraisalYearId" : $scope.formData.appraisalYear.id,
							"eligibility" : $scope.formData.eligibility,
							"startDate" : $scope.startDate,
							"emailSubject" : $scope.emailSubject,
							"emailContent" : $scope.emailContent,
							"endDate" : $scope.endDate,
						// "departmentList" : $scope.departmentIdList,
						// "designationList" : $scope.designationIdList,
						// "name" : $scope.name,
						};
						var res = $http.post('/PMS/appraisal-cycle-start',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									// $('#startButton').button('reset');
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										if ($scope.activeTabId == 0) {
											$scope.gridOptionsInitial.data = $scope.result.object;
											$scope.showAppraisedEmployeeInitial = true;
										} else if ($scope.activeTabId == 1) {
											$scope.gridOptionsMid.data = $scope.result.object;
											$scope.showAppraisedEmployeeMid = true;
										} else {
											$scope.gridOptionsFinal.data = $scope.result.object;
											$scope.showAppraisedEmployeeFinal = true;
										}
										
										$timeout(
												function() {
													$window.location.reload();
												}, 5000);
									}
								});
					}

					// $scope.kraSubmission = function() {
					// if ($scope.appraisalYear === undefined) {
					// alert("Please select year");
					// } else {
					// var input = {
					// "appraisalYearId" : $scope.appraisalYear.id
					// };
					// var res =
					// $http.post('/PMS/appraisal-year-kra-submission', input);
					// res.success(function(data, status) {
					// $scope.appraisalCycleDetails = data.object;
					// });
					// }
					// }
					// $scope.midYearSubmission = function() {
					// if ($scope.appraisalCycleDetails === undefined
					// || $scope.appraisalCycleDetails == null) {
					// alert("Please First choose KRA Submission");
					// } else {
					// var input = {
					// "appraisalYearId" : $scope.appraisalYear.id
					// };
					// var res =
					// $http.post('/PMS/appraisal-year-mid-year-submission',
					// input);
					// res.success(function(data, status) {
					// $scope.appraisalCycleDetails = data.object;
					// });
					// }
					// }
					//
					// $scope.finalYearSubmission = function() {
					// if ($scope.appraisalCycleDetails === undefined
					// || $scope.appraisalCycleDetails == null) {
					// alert("Please First choose Mid Year Submission");
					// } else {
					// var input = {
					// "appraisalYearId" : $scope.appraisalYear.id
					// };
					// var res =
					// $http.post('/PMS/appraisal-year-final-year-submission',
					// input);
					// res.success(function(data, status) {
					// $scope.appraisalCycleDetails = data.object;
					// });
					// }
					// }

				});
// app
// .controller(
// 'ViewSubEmployeeAppraisalController',
// function($window, $scope, $http, sharedProperties) {
// logDebug('Enter ViewSubEmployeeAppraisalController');
// logDebug('Browser language >> ' + window.navigator.language);
// document.getElementById("appraisalYearID").style.visibility = "visible";
// $scope.appraisalYearId = JSON.parse(localStorage
// .getItem("appraisalYearId"));
// var subEmployeeEmpCode = sharedProperties
// .getValue("subEmployeeEmpCode");
// $scope.getBehavioralComptenceDetails = function() {
// var input = {
// "empCode" : subEmployeeEmpCode,
// "appraisalYearId" : $scope.appraisalYearId.id,
// "type" : "BehavioralComptence"
// };
// var res = $http.post('/PMS/getDetails', input);
// res
// .success(function(data, status) {
// $scope.result = data.object[0];
// $scope.formData = data.object[1];
// $scope.formMainData = data.object[2];
// for (var i = 0; i < $scope.formMainData.length; i++) {
// $scope.formData[i].behaviouralCompetenceDetailsId =
// $scope.formMainData[i].id;
// $scope.formData[i].comments = $scope.formMainData[i].comments;
// $scope.formData[i].midYearSelfRating =
// $scope.formMainData[i].midYearSelfRating;
// $scope.formData[i].midYearAssessorRating =
// $scope.formMainData[i].midYearAssessorRating;
// $scope.formData[i].finalYearSelfRating =
// $scope.formMainData[i].finalYearSelfRating;
// $scope.formData[i].finalYearAssessorRating =
// $scope.formMainData[i].finalYearAssessorRating;
// }
// // var res = $http
// // .post('/PMS/get-behavioral-competence-list');
// // res.success(function(data, status) {
// // $scope.formData = data;
// // });
// });
// }
// $scope.getTrainingNeedsDetails = function() {
//
// var input = {
// "empCode" : subEmployeeEmpCode,
// "appraisalYearId" : $scope.appraisalYearId.id,
// "type" : "TrainingNeeds"
// };
// var res = $http.post('/PMS/getDetails', input);
// res.success(function(data, status) {
// $scope.result = data.object;
// if ($scope.result[0].length > 0) {
// $scope.trainingNeedsDetails = $scope.result[0];
// }
// // $scope.dynamicTrainingNeedsDetails =
// // data.object[1];
// // $scope.tishu = angular
// // .copy($scope.dynamicTrainingNeedsDetails);
//
// });
// }
// $scope.approveRejectList = [ "Approve", "Reject" ];
// $scope.selects = [ 1, 2, 3, 4, 5 ];
// $scope.getBehavioralComptenceDetails();
//
// $scope.getTrainingNeedsDetails();
// $scope.getCareerAspirationDetails = function() {
//
// var input = {
// "empCode" : subEmployeeEmpCode,
// "appraisalYearId" : $scope.appraisalYearId.id,
// "type" : "CareerAspiration"
// };
// var res = $http.post('/PMS/getDetails', input);
// res
// .success(function(data, status) {
// $scope.employeeCareerAspirationDetails = data.object[0][0];
// });
// }
// $scope.getCareerAspirationDetails();
// $scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
// $scope.selectsRating = [ 1, 2, 3, 4, 5 ];
// $scope.sectionAList = [];
// $scope.sectionBList = [];
// $scope.sectionCList = [];
// $scope.sectionDList = [];
//
// $scope.getEmployeeKraDetails = function() {
// var input = {
// "empCode" : subEmployeeEmpCode,
// "type" : "EmployeeKra",
// "appraisalYearId" : $scope.appraisalYearId.id
// };
// var res = $http.post('/PMS/getDetails', input);
// res
// .success(function(data, status) {
// $scope.result = data.object[0];
// $scope.loggedUserKRADetails = data.object[1].object;
// var p = 0;
// var q = 0;
// var r = 0;
// var s = 0;
// for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
//
// if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
// $scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
//
// p++;
// } else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
// $scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
//
// q++;
// } else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
// $scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
//
// r++;
// } else {
// $scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
//
// s++;
// }
// }
// });
// }
// $scope.getEmployeeKraDetails();
//
// var input = {
// "empCode" : subEmployeeEmpCode,
// "appraisalYearId" : $scope.appraisalYearId.id
// };
// var res = $http.post(
// '/PMS/get-employee-appraisal-details', input);
// res.success(function(data, status) {
// $scope.subEmployeeDetails = data.object[0];
// // $scope.subEmployeeKraDetails = data.object[1];
// // $scope.subEmployeeBehaviouralCompetenceDetails =
// // data.object[2];
// // $scope.subEmployeeCareerAspirationsDetails =
// // data.object[3];
// // $scope.subEmployeeTrainingNeedsDetails =
// // data.object[4];
// // $scope.subEmployeeFinalReviewDetails =
// // data.object[5];
// // for (var i = 0; i <
// // $scope.subEmployeeKraDetails.length; i++) {
// // var sum =
// // ($scope.subEmployeeKraDetails[i].midYearAppraisarRating
// // * 30) / 100;
// // sum = sum
// // +
// // ($scope.subEmployeeKraDetails[i].finalYearAppraisarRating
// // * 70)
// // / 100;
// // $scope.subEmployeeKraDetails[i].midFinal = sum
// // .toFixed(2);
// // $scope.subEmployeeKraDetails[i].finalScore = (sum *
// // $scope.subEmployeeKraDetails[i].weightage)
// // .toFixed(2);
// // }
// // for (var i = 0; i <
// // $scope.subEmployeeBehaviouralCompetenceDetails.length;
// // i++) {
// // var sum =
// // ($scope.subEmployeeBehaviouralCompetenceDetails[i].midYearAssessorRating
// // * 30) / 100;
// // sum = sum
// // +
// // ($scope.subEmployeeBehaviouralCompetenceDetails[i].finalYearAssessorRating
// // * 70)
// // / 100;
// // $scope.subEmployeeBehaviouralCompetenceDetails[i].midFinal
// // = sum
// // .toFixed(2);
// // $scope.subEmployeeBehaviouralCompetenceDetails[i].finalScore
// // = (sum *
// // $scope.subEmployeeBehaviouralCompetenceDetails[i].weightage)
// // .toFixed(2);
// // }
//
// });
//
// $scope.print = function() {
// window.print();
// }
// });

app
		.controller(
				'ViewAppraisalController',
				function($window, $scope, $http, sharedProperties, $timeout) {
					logDebug('Enter ViewAppraisalController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.approveRejectList = [ "Approve", "Reject" ];
					$scope.selects = [ 1, 2, 3, 4, 5 ];
					$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
					$scope.selectsRating = [ 1, 2, 3, 4, 5 ];
					$scope.sectionAList = [];
					$scope.sectionBList = [];
					$scope.sectionCList = [];
					$scope.sectionDList = [];
					$scope.viewBehavioralComptenceTotalCalculation = 0;
					$scope.viewExtraOrdinaryWeightageCount = 0;
					$scope.ExtraOrdinaryTotalCalculation = 0;
					$scope.viewKRATotalCalculation = 0;

					$scope.trainingNeedsDetails = [ {
						'trainingTopic' : null,
						'trainingReasons' : null,
						'manHours' : null,
					} ];

					$scope.extraOrdinaryDetails = [ {
						'contributions' : null,
						'contributionDetails' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'finalScore' : null,
						// 'child' : $scope.dynamicExtraOrdinaryDetails,
						'remarks' : null
					} ];
					$scope.getBehavioralComptenceDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "BehavioralComptence"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.formData = data.object[1];
									$scope.formMainData = data.object[2];
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									for (var i = 0; i < $scope.formMainData.length; i++) {
										$scope.formData[i].behaviouralCompetenceDetailsId = $scope.formMainData[i].id;
										$scope.formData[i].comments = $scope.formMainData[i].comments;
										$scope.formData[i].isValidatedByEmployee = $scope.formMainData[i].isValidatedByEmployee;
										$scope.formData[i].midYearSelfRating = $scope.formMainData[i].midYearSelfRating;
										$scope.formData[i].midYearAssessorRating = $scope.formMainData[i].midYearAssessorRating;
										$scope.formData[i].finalYearSelfRating = $scope.formMainData[i].finalYearSelfRating;
										$scope.formData[i].finalYearAssessorRating = $scope.formMainData[i].finalYearAssessorRating;
										
										if($scope.formData[i].midYearAssessorRating == null && $scope.formData[i].weightage !=null && $scope.formData[i].finalYearAssessorRating !=null)
										{
										$scope.viewBehavioralComptenceTotalCalculation = $scope.viewBehavioralComptenceTotalCalculation +
											(((($scope.formData[i].finalYearAssessorRating * $scope.formData[i].weightage)/100)));
										}	
										if($scope.formData[i].midYearAssessorRating !=null && $scope.formData[i].weightage !=null && $scope.formData[i].finalYearAssessorRating !=null)
										{
										$scope.viewBehavioralComptenceTotalCalculation = $scope.viewBehavioralComptenceTotalCalculation +
											(((($scope.formData[i].midYearAssessorRating
												* $scope.formData[i].weightage )/100) *30/100) +
												((($scope.formData[i].finalYearAssessorRating * $scope.formData[i].weightage)/100) *
												70/100));
										}	
									}
								});
					}
					$scope.getTrainingNeedsDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "TrainingNeeds"
						};
						var res = $http.post('/PMS/getDetails', input);
						res.success(function(data, status) {
							$scope.result = data.object;
							if ($scope.result[1].length > 0) {
								$scope.trainingNeedsDetails = $scope.result[1];
							}
						});
					}
					$scope.getCareerAspirationDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "CareerAspiration"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.employeeCareerAspirationDetails = data.object[1][0];
								});
					}

					$scope.getEmployeeKraDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type" : "EmployeeKra",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserKRADetails = data.object[1].object;
									var p = 0;
									var q = 0;
									var r = 0;
									var s = 0;
									$scope.WeightageCount = 0;
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									if ($scope.loggedUserAppraisalDetails != undefined) {
										if ($scope.loggedUserAppraisalDetails.initializationYear != 0
												&& $scope.loggedUserAppraisalDetails.midYear == 0
												&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
											$scope.yearType = "START";

										}
										if ($scope.loggedUserAppraisalDetails.initializationYear == 0
												&& $scope.loggedUserAppraisalDetails.midYear != 0
												&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
											$scope.yearType = "MID";
										}
										if ($scope.loggedUserAppraisalDetails.initializationYear == 0
												&& $scope.loggedUserAppraisalDetails.midYear == 0
												&& $scope.loggedUserAppraisalDetails.finalYear != 0) {
											$scope.yearType = "FINAL";
										}
									}
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {

											if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
												$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
												if ($scope.sectionAList[p].weightage != null) {
													$scope.WeightageCount = $scope.WeightageCount
															+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
												if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
													{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
															(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
															70/100)));
													}
												if($scope.sectionAList[p].midYearAppraisarRating ==null && $scope.sectionAList[p].weightage !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null)
												{
												$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
												}
												p++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
												$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
												if ($scope.sectionBList[q].weightage != null) {
													$scope.WeightageCount = $scope.WeightageCount
															+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
												if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
															(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
															70/100)));
												}
												if($scope.sectionBList[q].midYearAppraisarRating ==null && $scope.sectionBList[q].weightage !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
															(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
												}
												q++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
												$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
												if ($scope.sectionCList[r].weightage != null) {
													$scope.WeightageCount = $scope.WeightageCount
															+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
												if($scope.sectionCList[r].midYearAppraisarRating ==null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
															(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
												}
												if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].weightage !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
															(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
															70/100)));
												}
												r++;
											} else {
												$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
												if ($scope.sectionDList[s].weightage != null) {
													$scope.WeightageCount = $scope.WeightageCount
															+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
												if($scope.sectionDList[s].midYearAppraisarRating ==null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (
															(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
												}
												if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].weightage !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null)
												{
													$scope.viewKRATotalCalculation = $scope.viewKRATotalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
															(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
															70/100)));
												}
												s++;
											}
										
									}
								});
					}
					$scope.getEmployeeBasicDetails = function() {
						input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post(
								'/PMS/get-employee-appraisal-details', input);
						res.success(function(data, status) {
							$scope.subEmployeeDetails = data.object[0];
						});
					}
					$scope.getExtraOrdinaryDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type" : "ExtraOrdinary",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									if (data.object[1].length > 0) {
										$scope.extraOrdinaryDetails = data.object[1];
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											if ($scope.extraOrdinaryDetails[i].weightage != null) {
												$scope.viewExtraOrdinaryWeightageCount = $scope.viewExtraOrdinaryWeightageCount
														+ parseInt($scope.extraOrdinaryDetails[i].weightage);
												
											}
											if ($scope.extraOrdinaryDetails[i].weightage != null && $scope.extraOrdinaryDetails[i].finalYearAppraisarRating !=null) {
												
												$scope.ExtraOrdinaryTotalCalculation = $scope.ExtraOrdinaryTotalCalculation +
												(($scope.extraOrdinaryDetails[i].weightage * $scope.extraOrdinaryDetails[i].finalYearAppraisarRating)/100);
											}
											
										}
									}
									document.getElementById("main_body").className = "";
								});

					}
					$scope.getCareerAspirationDetails();
					$scope.getBehavioralComptenceDetails();
					$scope.getTrainingNeedsDetails();
					$scope.getEmployeeKraDetails();
					$scope.getEmployeeBasicDetails();
					$scope.getExtraOrdinaryDetails();

					$scope.print = function() {
						// window.print();
						var mywindow = window.open();
						mywindow.document
								.write('<html><head><title>HERO FUTURE ENERGIES</title>');
						mywindow.document
								.write('<!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Print</title>');
						// mywindow.document.write('<link rel=\"stylesheet\"
						// href="resources/common/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css"
						// type=\"text/css\" media=\"print\">');
						// mywindow.document.write('<link rel=\"stylesheet\"
						// href="resources/common/css/index.css?v=0.13"
						// type=\"text/css\" media=\"print\"/>');
						// mywindow.document.write('<link
						// rel=\"stylesheet\"href="resources/common/css/default.css?v=0.2"
						// type=\"text/css\" media=\"print\" />');
						//						

						mywindow.document
								.write('</head><body><img id="headerLogoImg" src="resources/common/images/logo.jpg" class="hidden-xs" style="float: inherit;">');
						// mywindow.document.write('<h1>' + document.title +
						// '</h1>');
						mywindow.document.write(document
								.getElementById("viewAppraisal").innerHTML);
						mywindow.document.write('</body></html>');

						mywindow.document.close(); // necessary for IE >= 10
						mywindow.focus(); // necessary for IE >= 10*/

						mywindow.print();
						$timeout(function() {
							mywindow.close();
						}, 5000);

						return true;
					}

					$scope.sendToManager = function() {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.sectionAList.length; i++) {
														if ($scope.sectionAList[i].smartGoal != null
																&& $scope.sectionAList[i].smartGoal != "") {
															if ($scope.sectionAList[i].target === null
																	|| $scope.sectionAList[i].achievementDate === null
																	|| $scope.sectionAList[i].weightage === null
																	|| $scope.sectionAList[i].target === ""
																	|| $scope.sectionAList[i].achievementDate === ""
																	|| $scope.sectionAList[i].weightage === ""
																	|| $scope.sectionAList[i].isValidatedByEmployee === 0) {
																swal("All sections in kra to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
														if ($scope.loggedUserAppraisalDetails.midYear > 0) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
														if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
															if ($scope.sectionAList[i].smartGoal != null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		|| $scope.sectionAList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.sectionAList.length == 0) {
														$scope.checkValidation = false;
														swal("All sections in kra to be filled.");
													}
												}
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.sectionBList.length; i++) {
														if ($scope.sectionBList[i].smartGoal != null
																&& $scope.sectionBList[i].smartGoal != "") {
															if ($scope.sectionBList[i].target === null
																	|| $scope.sectionBList[i].achievementDate === null
																	|| $scope.sectionBList[i].weightage === null
																	|| $scope.sectionBList[i].target === ""
																	|| $scope.sectionBList[i].achievementDate === ""
																	|| $scope.sectionBList[i].weightage === ""
																	|| $scope.sectionBList[i].isValidatedByEmployee === 0) {
																swal("All sections in kra to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
														if ($scope.loggedUserAppraisalDetails.midYear > 0) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].midYearAchievement === null
																		|| $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].midYearAchievement === ""
																		|| $scope.sectionBList[i].midYearSelfRating === ""
																		|| $scope.sectionBList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
														if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
															if ($scope.sectionBList[i].smartGoal != null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].finalYearAchievement === null
																		|| $scope.sectionBList[i].finalYearSelfRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
																		|| $scope.sectionBList[i].finalYearAchievement === ""
																		|| $scope.sectionBList[i].finalYearSelfRating === ""
																		|| $scope.sectionBList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.sectionBList.length == 0) {
														$scope.checkValidation = false;
														swal("All sections in kra to be filled.");
													}
												}
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.sectionCList.length; i++) {
														if ($scope.sectionCList[i].smartGoal != null
																&& $scope.sectionCList[i].smartGoal != "") {
															if ($scope.sectionCList[i].target === null
																	|| $scope.sectionCList[i].achievementDate === null
																	|| $scope.sectionCList[i].weightage === null
																	|| $scope.sectionCList[i].target === ""
																	|| $scope.sectionCList[i].achievementDate === ""
																	|| $scope.sectionCList[i].weightage === ""
																	|| $scope.sectionCList[i].isValidatedByEmployee === 0) {
																swal("All sections in kra to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
														if ($scope.loggedUserAppraisalDetails.midYear > 0) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].midYearAchievement === null
																		|| $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].midYearAchievement === ""
																		|| $scope.sectionCList[i].midYearSelfRating === ""
																		|| $scope.sectionCList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
														if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
															if ($scope.sectionCList[i].smartGoal != null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].finalYearAchievement === null
																		|| $scope.sectionCList[i].finalYearSelfRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
																		|| $scope.sectionCList[i].finalYearAchievement === ""
																		|| $scope.sectionCList[i].finalYearSelfRating === ""
																		|| $scope.sectionCList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.sectionCList.length == 0) {
														$scope.checkValidation = false;
														swal("All sections in kra to be filled.");
													}
												}
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.sectionDList.length; i++) {
														if ($scope.sectionDList[i].smartGoal != null
																&& $scope.sectionDList[i].smartGoal != "") {
															if ($scope.sectionDList[i].target === null
																	|| $scope.sectionDList[i].achievementDate === null
																	|| $scope.sectionDList[i].weightage === null
																	|| $scope.sectionDList[i].target === ""
																	|| $scope.sectionDList[i].achievementDate === ""
																	|| $scope.sectionDList[i].weightage === ""
																	|| $scope.sectionDList[i].isValidatedByEmployee === 0) {
																swal("All sections in kra to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
														if ($scope.loggedUserAppraisalDetails.midYear > 0) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].midYearAchievement === null
																		|| $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].midYearAchievement === ""
																		|| $scope.sectionDList[i].midYearSelfRating === ""
																		|| $scope.sectionDList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
														if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
															if ($scope.sectionDList[i].smartGoal != null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].finalYearAchievement === null
																		|| $scope.sectionDList[i].finalYearSelfRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
																		|| $scope.sectionDList[i].finalYearAchievement === ""
																		|| $scope.sectionDList[i].finalYearSelfRating === ""
																		|| $scope.sectionDList[i].isValidatedByEmployee === 0) {
																	swal("All sections in kra to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.sectionDList.length == 0) {
														$scope.checkValidation = false;
														swal("All sections in kra to be filled.");
													}
												}

												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear == 1) {
														if ($scope.employeeCareerAspirationDetails === undefined
																|| $scope.employeeCareerAspirationDetails.initializationComments === ""
																|| $scope.employeeCareerAspirationDetails.isValidatedByEmployee == 0) {
															swal("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear == 1) {
														if ($scope.employeeCareerAspirationDetails.midYearComments === ""
																|| $scope.employeeCareerAspirationDetails.midYearComments === null
																|| $scope.employeeCareerAspirationDetails.midYearCommentsStatus == 0) {
															swal("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear == 1) {
														if ($scope.employeeCareerAspirationDetails.yearEndComments === ""
																|| $scope.employeeCareerAspirationDetails.yearEndComments === null
																|| $scope.employeeCareerAspirationDetails.yearEndCommentsStatus == 0) {
															swal("All fields in career aspirations to be filled.");
															$scope.checkValidation = false;
														}
													}
												}

												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														if ($scope.extraOrdinaryDetails === undefined) {
															$scope.checkValidation = false;
															swal("All fields in extra ordinary to be filled.");
														} else {
															for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
																if ($scope.extraOrdinaryDetails[i].contributions === null
																		|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																		|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																		|| $scope.extraOrdinaryDetails[i].contributions === ""
																		|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																		|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === ""
																		|| $scope.extraOrdinaryDetails[i].isValidatedByEmployee === 0) {
																	swal("All fields in extra ordinary to be filled.");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.formData.length; i++) {
														if ($scope.loggedUserAppraisalDetails.midYear > 0) {
															if ($scope.formData[i].comments === null
																	|| $scope.formData[i].midYearSelfRating === null
																	|| $scope.formData[i].isValidatedByEmployee == 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}

														if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
															if ($scope.formData[i].finalYearSelfRating === null
																	|| $scope.formData[i].finalYearSelfRating === ""
																	|| $scope.formData[i].isValidatedByEmployee == 0) {
																swal("All fields in behavioral comptence to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0
															|| $scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
															if ($scope.trainingNeedsDetails[i].trainingTopic === null
																	|| $scope.trainingNeedsDetails[i].trainingReasons === null
																	|| $scope.trainingNeedsDetails[i].manHours === null
																	|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																	|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																	|| $scope.trainingNeedsDetails[i].manHours === ""
																	|| $scope.trainingNeedsDetails[i].isValidatedByEmployee === 0) {
																swal("All fields in training needs to be filled.");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													document.getElementById("main_body").className = "loading-pane";
													var input = {
														"empCode" : document
																.getElementById("globalUserName").value,
														"name" : "FIRST LEVEL EMPLOYEE",
														"appraisalYearId" : $scope.appraisalYear.id
													};
													var res = $http
															.post(
																	'/PMS/sendToManager',
																	input);
													res
															.success(function(
																	data,
																	status) {
																$scope.result = data;
																$scope.loggedUserAppraisalDetails = $scope.result.object;
																if ($scope.result.integer == 1) {
																swal($scope.result.string);
																document.getElementById("main_body").className = "";
																}
															});
												}
											}
										});
					}

				});

app.controller(
				'IndexController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter IndexController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					document.getElementById("main_body").className = "loading-pane";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					
					$scope.submit = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYear.id
						};
						var res = $http.post('/PMS/employeeAcknowledgement',
								input);
						res
								.success(function(data, status) {
									swal(data.string);
									if(data.integer == 1)
										{
										$scope.acknowledgement = true;
										$scope.isUserFirstTimeLogin = 1;
										}
								});

					}
					$scope.onLoadIndex = function() {
						var res = $http
								.post('/PMS/get-all-appraisal-year-details');
						res
								.success(function(data, status) {
									$scope.appraisalYearList = data.object[0];
									$scope.appraisalYear = data.object[1];
									var input = {
										"appraisalYearId" : $scope.appraisalYear.id,
										"empCode" : document
												.getElementById("globalUserName").value
									};
									var res = $http
											.post('/PMS/isUserFirstTimeLogin',
													input);
									res
											.success(function(data, status) {
												$scope.result = data.integer;
												$scope.isUserFirstTimeLogin = data.object;
												if ($scope.result === 1) {
													$('#updatePasswordModal')
															.modal(
																	{
																		backdrop : 'static',
																		keyboard : false
																	});
												}
												if ($scope.isUserFirstTimeLogin == 1) {
													$scope.acknowledgement = true;
												}
												// var res = $http
												// .post('/PMS/get-all-appraisal-year-details');
												// res
												// .success(function(data,
												// status) {
												// $scope.appraisalYearList =
												// data.object[0];
												// $scope.appraisalYear =
												// data.object[1];
												$scope.yearName = $scope.appraisalYear.name;
												var input = {
													"empCode" : document
															.getElementById("globalUserName").value,
													"type":"EMPLOYEE",
													"appraisalYearId" : $scope.appraisalYear.id,
												};
												var res = $http
														.post(
																'/PMS/getAppraisalStatus',
																input);
												res
														.success(function(data,
																status) {
															$scope.loggedUserAppraisalDetails = data.object;
															if ($scope.loggedUserAppraisalDetails != null) {
																if ($scope.loggedUserAppraisalDetails.initializationYear == 1) {
																	$scope.stage = "In Planning";
																} else if ($scope.loggedUserAppraisalDetails.midYear == 1) {
																	$scope.stage = "In Review";
																} else {
																	$scope.stage = "In Process";
																}
															}
															document
																	.getElementById("main_body").className = "";
														});
											});
								});
					}
					$scope.formClear = function() {
						$scope.newPassword = null;
						$scope.currentPassword = null;
						$scope.confirmPassword = null;
					}

					$scope.save = function() {
						if ($scope.newPassword != $scope.confirmPassword) {
							swal("New and Confirm password doesn't matched.");
						} else {
							var input = {
								"password" : $scope.newPassword,
								"currentPassword" : $scope.currentPassword
							};
							var res = $http.post('/PMS/changeUserPassword',
									input);
							res
									.success(function(data, status) {
										$scope.result = data;
										$scope.message = $scope.result.string;
										if ($scope.result.integer == 1) {
											document
													.getElementById("indexAlert").className = "alert-successs";
											$timeout(
													function() {
														document
																.getElementById("indexAlert").className = "";
														$scope.message = "";
														$scope.formClear();
														$(
																'#updatePasswordModal')
																.modal('hide');
													}, 5000);

										} else {
											document
													.getElementById("indexAlert").className = "alert-dangers";
										}
									});
						}
					}

				});
app
		.controller(
				'TeamAppraisalController',
				function($window, $scope, $http, sharedProperties) {
					logDebug('Enter TeamAppraisalController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.employeeLevel = [ "FIRST LEVEL EMPLOYEE",
							"SECOND LEVEL EMPLOYEE" ];
					$scope.gridOptions = {
						showGridFooter : true,
						multiSelect : false,
						enableFiltering : true,
						columnDefs : [
								{
									name : 'Sr #',
									field : 'name',
									width : '3%',
									cellTemplate : '<div class="ui-grid-cell-contents">{{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>'
								},
								{
									name : 'Emp Code',
									field : 'empCode',
									width : '15%'
								},
								{
									name : 'Name',
									field : 'empName',
									width : '25%'
								},
								{
									name : 'Designation',
									field : 'designationName',
									width : '10%'
								},
								{
									name : 'Department',
									field : 'departmentName',
									width : '7%'
								}, {
									name : 'Status',
									field : 'stage',
									width : '10%'
								}, {
									name : 'Sub-Status',
									field : 'subStage',
									width : '10%'
								},
								{
									name : 'View form',
									cellTemplate : '<div style ="text-align : center;"> <button  class=" btn-primary " ng-click="grid.appScope.approveEmployeeAppraisal(row.entity.empCode)" ><span class="glyphicon glyphicon-pencil" style="margin: 0px;"></span> </button></div>',
									width : '5%'
								}
						// {
						// name : 'View',
						// cellTemplate : '<div style ="text-align : center;">
						// <button class=" btn-primary "
						// ng-click="grid.appScope.ViewAppraisal(row.entity.empCode)"><span
						// class="glyphicon glyphicon-eye-open" style="margin:
						// 0px;"></span> </button></div>',
						// width : '2%'
						// }
						]
					};

					$scope.getOnLoadData = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : $scope.levelType
						};
						var res = $http.post('/PMS/get-all-team-members',
								input);
						res.success(function(data, status) {
							
							
							
							$scope.result = data.object;
							for(var i =0;i<$scope.result.length;i++)
							{
					if($scope.result[i].initializationYear == 1 && ($scope.result[i].employeeIsvisible == 1 || $scope.result[i].employeeIsvisible == 0)	
							&& $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 0)
						{
						$scope.result[i].stage = "In Planning";
						$scope.result[i].subStage = "Goal Setting";
						}
					if(($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	 && $scope.result[i].acknowledgementCheck == 1
							&& ($scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].firstLevelIsvisible == 0 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0))
						{
						$scope.result[i].stage = "In Planning";
						$scope.result[i].subStage = "Goal Approval";
						}
					if($scope.result[i].initializationYear == 1 && $scope.result[i].employeeIsvisible == 0	
							&& $scope.result[i].firstLevelIsvisible == 0 &&  $scope.result[i].secondLevelIsvisible == 0  && $scope.result[i].secondLevelIsvisibleCheck == 1)
						{
						$scope.result[i].stage  = "In Review";
						$scope.result[i].subStage = "Mid Year Review";
						}
					if($scope.result[i].midYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
						{
						$scope.result[i].stage  = "In Review";
						$scope.result[i].subStage = "Mid Year Review";
						}
					if($scope.result[i].midYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
					{
						$scope.result[i].stage  = "In Process";
					$scope.result[i].subStage = "Year End Assessment";
					}
					if($scope.result[i].finalYear == 1 && ($scope.result[i].employeeIsvisible == 1 ||  $scope.result[i].firstLevelIsvisible == 1 || $scope.result[i].secondLevelIsvisible == 1) && $scope.result[i].secondLevelIsvisibleCheck == 0)
					{
						$scope.result[i].stage  = "In Process";
					$scope.result[i].subStage = "Year End Assessment";
					}
					if($scope.result[i].finalYear == 1 && $scope.result[i].employeeIsvisible == 0 &&  $scope.result[i].firstLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisible == 0 && $scope.result[i].secondLevelIsvisibleCheck == 1)
					{
						$scope.result[i].stage  = "In Process";
					$scope.result[i].subStage = "Assessment approval";
					}
					}
							$scope.gridOptions.data = $scope.result;
							
							
							
							
							
							
							
							
							
							
							
// $scope.gridOptions.data = data.object;
						});
					}
					$scope.approveEmployeeAppraisal = function(data) {
						if ($scope.levelType === $scope.employeeLevel[0]) {
							sharedProperties.setValue("employeeDetails", data);
							$window.location.href = "#/firstlevel-appraisal";
						} else {
							sharedProperties.setValue("employeeDetails", data);
							$window.location.href = "#/secondlevel-appraisal";
						}
					}
					// $scope.ViewAppraisal = function(data) {
					// sharedProperties.setValue("subEmployeeEmpCode", data);
					// $window.location.href = "#/view-subEmployee-appraisal";
					// }

				});

app
		.controller(
				'AddEmployeeController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter AddEmployeeController');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.employeeType = "NON-HOD";
					document.getElementById("appraisalYearID").style.visibility = "hidden";
					document.getElementById("documentDownload").style.display = 'none';

					$scope.getCurrentEmployeeDynamicFormDetails = function() {
						var res = $http.post(
								'/PMS/get_Current_employee_dynamic_form',
								$scope.appraisalYearId.id);
						res.success(function(data, status) {
							$scope.employeeDynamicFormDetails = data;
						});
						var res = $http.post('/PMS/get-all-basic-details');
						res
								.success(function(data, status) {
									$scope.departmentList = data.departmentList;
									$scope.designationList = data.designationList;
									// $scope.qualificationsList =
									// data.qualificationList;
									$scope.employeeList = data.employeeList;
									$scope.applicationRolesList = data.applicationRoleList;

									$("#firstLevelSuperiorEmpId").select2({
										data : $scope.employeeList
									});
									$("#secondLevelSuperiorEmpId").select2({
										data : $scope.employeeList
									});
								});
					}
					$scope.getAllOrganizationRoles = function() {
						var res = $http.post(
								'/PMS/get-all-organization-roles',
								$scope.department.id);
						res.success(function(data, status) {
							$scope.organizationRolesList = data;
						});

					}

					$scope.formClear = function() {
						$scope.name = null;
						$scope.empCode = null;
						$scope.department = null;
						$scope.designationId = null;
						$scope.qualification = null;
						$scope.email = null;
						$scope.mobile = null;
						$scope.company = null;
						$scope.location = null;
						$scope.roleId = null;
						$scope.organizationRoleId = null;
						$scope.jobDescription = null;
						$scope.dateOfJoining = null;
						$scope.dateOfBirth = null;
						$scope.file1 = null;
						document.getElementById("file1").value = '';
						// document.getElementById("select2-firstLevelSuperiorEmpId-container").value
						// = null;
						// document.getElementById("select2-secondLevelSuperiorEmpId-container").value
						// = null;
						for (var i = 0; i < $scope.employeeDynamicFormDetails.length; i++) {
							$scope.employeeDynamicFormDetails[i].data = null;
						}
					}
					$scope.save = function() {
						$('#saveEmployee').button('loading');
						var firstLevelSuperiorEmpId = ($(
								'#firstLevelSuperiorEmpId').select2('data')[0].text)
								.split("-");
						var secondLevelSuperiorEmpId = ($(
								'#secondLevelSuperiorEmpId').select2('data')[0].text)
								.split("-");
						var formdata = new FormData();
						formdata.append("empName", $scope.name);
						formdata.append("empCode", $scope.empCode);
						formdata.append("empType", $scope.employeeType);
						formdata.append("email", $scope.email);
						formdata.append("dateOfJoining", $scope.dateOfJoining);
						formdata.append("dateOfBirth", $scope.dateOfBirth);
						formdata
								.append("jobDescription", $scope.jobDescription);
						formdata.append("departmentId", $scope.department.id);
						formdata.append("organizationRoleId",
								$scope.organizationRoleId.id);
						formdata.append("roleId", $scope.roleId.id);
						formdata.append("designationId",
								$scope.designationId.id);
						formdata.append("qualification", $scope.qualification);
						formdata.append("mobile", $scope.mobile);
						formdata.append("company", $scope.company);
						formdata.append("location", $scope.location);
						formdata.append("firstLevelSuperiorEmpId",
								firstLevelSuperiorEmpId[0].trim());
						formdata.append("secondLevelSuperiorEmpId",
								secondLevelSuperiorEmpId[0].trim());
						formdata.append("file1", $scope.file1);

						for (var i = 0; i < $scope.employeeDynamicFormDetails.length; i++) {
							formdata.append("fields",
									$scope.employeeDynamicFormDetails[i].data);
						}
						$http
								.post("/PMS/add-new-employee", formdata, {
									transformRequest : angular.identity,
									headers : {
										'Content-Type' : undefined
									}
								})
								.success(
										function(data) {
											swal(data.string);
											$('#saveEmployee').button('reset');
											if (data.integer == 1) {
												$timeout(
														function() {
															$window.location
																	.reload();
														}, 5000);
											}
										});
					}

				});

app
		.controller(
				'EmployeeKRAController',
				function($window, $scope, $http, $timeout, sharedProperties) {
// $('#employeeKRATable').height($(window).height()-300);
					logDebug('Enter employee-kra');
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.loggedUserAppraisalDetails = [];
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.selectsWeightage = [ "1", "2", "3", "4", "5" ];
					$scope.selectsRating = [ 1, 2, 3, 4, 5 ];
					$scope.WeightageCount = 5;
					$scope.fileList = [];
					$scope.sectionAList = [ {
						'sectionName' : 'Section A',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'kraType' : null,
						'kraScreen':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					} ];
					$scope.sectionBList = [ {
						'sectionName' : 'Section B',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'kraType' : null,
						'kraScreen':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					} ];
					$scope.sectionCList = [ {
						'sectionName' : 'Section C',
						'smartGoal' : null,
						'target' : null,
						"fileName" :null,
						"fileDummyName":null,
						'achievementDate' : null,
						'weightage' : null,
						'kraType' : null,
						'kraScreen':null,
						'midYearAchievement' : null,
						'midYearSelfRating' : null,
						'midYearAppraisarRating' : null,
						'midYearAssessmentRemarks' : null,
						'finalYearAchievement' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						'remarks' : null
					} ];
					$scope.sectionDList = [
							{
								'sectionName' : 'Section D',
								'smartGoal' : document
										.getElementById("smartGoalOne").value,
								'target' : document.getElementById("targetOne").value,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : document
										.getElementById("weightageOne").value,
								'midYearAchievement' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							},
							{
								'sectionName' : 'Section D',
								'smartGoal' : document
										.getElementById("smartGoalTwo").value,
								'target' : document.getElementById("targetTwo").value,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : document
										.getElementById("weightageTwo").value,
										'kraType' : null,
								'midYearAchievement' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							},
							{
								'sectionName' : 'Section D',
								'smartGoal' : document
										.getElementById("smartGoalThree").value,
								'target' : document
										.getElementById("targetThree").value,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : document
										.getElementById("weightageThree").value,
										'kraType' : null,
								'midYearAchievement' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							} ];

					$scope.formData = {
						"sectionAList" : $scope.sectionAList,
						"sectionBList" : $scope.sectionBList,
						"sectionCList" : $scope.sectionCList,
						"sectionDList" : $scope.sectionDList
					};
					
					$scope.gotoNewKRA=function()
					{
						$window.location.href = "#/employee-newKra";
					}
					
					$scope.carryForwardKRADATAToCurrentYear = function() {
						if($scope.kraDataCofirmation !=undefined)
							{
						var input = {
							"id" : $scope.loggedUserAppraisalDetails.id,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"empCode" : document
									.getElementById("globalUserName").value,
							"typeId" : parseInt($scope.kraDataCofirmation)
						};
						var res = $http.post('/PMS/carryForwardEmployeeKRADATAToCurrentYear',input);
						res.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										swal($scope.message);
										var p = 0;
										var q = 0;
										var r = 0;
										var s = 0;
										$scope.kraDetailsId = [];
										$scope.WeightageCount = 0;
										$scope.kraToalCalculation = 0;
										$scope.loggedUserKRADetails = $scope.result.object.object;
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
												$scope.WeightageCount = 0;
												break;
											}

										}

										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
											if($scope.loggedUserKRADetails[i].weightage !=null)
												{
											$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
											if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
												$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionAList[p].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
													$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
													(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
													70/100)));
												}
												p++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
												$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionBList[q].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
													$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
															(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
															70/100)));
												}
												q++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
												$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionCList[r].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
													$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
															(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
															70/100)));
												}
												r++;
											} else {
												$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionDList[s].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
													$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
															(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
															70/100)));
												}
												s++;
											}
										}
										document.getElementById("main_body").className = "";

									}
								});
					}
						else
							{
							swal("Please fill the required field.");
							}
					}
					$scope.countWeightage = function() {
						$scope.WeightageCount = 0;
						for (var i = 0; i < $scope.sectionAList.length; i++) {
							if ($scope.sectionAList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionAList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionBList.length; i++) {
							if ($scope.sectionBList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionBList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionCList.length; i++) {
							if ($scope.sectionCList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionCList[i].weightage);
						}
						for (var i = 0; i < $scope.sectionDList.length; i++) {
							if ($scope.sectionDList[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.sectionDList[i].weightage);
						}
					}

					$scope.goToOldKRA = function()
					{
						$window.location.href = "#/employee-oldKra";
					}

					
					$scope.getEmployeeKraDetails = function() {
						$scope.kraDetailsId = [];
						$scope.WeightageCount = 0;
// $scope.WeightageCountCheck = 0;
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type" : "EmployeeKra",
							"appraisalYearId" : $scope.appraisalYearId.id,
							"kraScreen":"kraScreen"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserKRADetails = data.object[1].object;
									
									if ($scope.result.length == 0) {
										$scope.loggedUserAppraisalDetails
												.push({
													"initializationYear" : null,
													"midYear" : null,
													"finalYear" : null
												});
									$scope.WeightageCount = 5;
									document.getElementById("main_body").className = "";
									} else {
										var p = 0;
										var q = 0;
										var r = 0;
										var s = 0;
										$scope.kraDetailsId = [];
										$scope.kraToalCalculation = 0;
										$scope.loggedUserAppraisalDetails = $scope.result.object;
// if($scope.loggedUserAppraisalDetails.firstManagerNewKRARejection == 1)
// {
// swal("Please click on the add new KRA's link to fill new KRA's.");
// }
										if($scope.loggedUserAppraisalDetails != null)
											{
										if ($scope.loggedUserAppraisalDetails.kraForwardCheck == null) {
											$('#kraDataForwardCurrentYear')
													.modal({
														backdrop : 'static',
														keyboard : false
													});
										}
											}
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											if ($scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
												$scope.WeightageCount = 0;
												break;
											}

										}
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											if ($scope.loggedUserKRADetails[i].kraType == "NEW" && $scope.loggedUserKRADetails[i].isValidatedByEmployee == 1) {
												$scope.checkKraType = 1;
												break;
											}

										}
// if($scope.loggedUserKRADetails.length == 0)
// {
// // document.getElementById("kraFileDocumentDownloadSectionA0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionA0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionB0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionB0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionC0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionC0").style.display =
// 'none';
// // document.getElementById("kraFileDocumentDownloadSectionD0").style.display
// = 'none';
// document.getElementById("kraDocumentDownloadSectionD0").style.display =
// 'none';
// }
										for (var i = 0; i < $scope.loggedUserKRADetails.length; i++) {
											$scope.kraDetailsId[i] = $scope.loggedUserKRADetails[i].id;
// console.log("kraFileDocumentDownload"+i);
// document.getElementById("kraFileDocumentDownload"+i).style.display =
// 'visible';
// document.getElementById("kraDocumentDownload"+i).style.display = 'none';
											if ($scope.loggedUserKRADetails[i].sectionName == "Section A") {
												$scope.sectionAList[p] = $scope.loggedUserKRADetails[i];
												if($scope.loggedUserKRADetails[i].weightage !=null)
													{
												$scope.WeightageCount = $scope.WeightageCount
												+ parseInt($scope.loggedUserKRADetails[i].weightage);
													}
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionAList[p].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
// if ($scope.WeightageCountCheck == 1) {
// $scope.WeightageCount = $scope.WeightageCount - 5;
// $scope.WeightageCountCheck = 0;
// }
												}
												if($scope.sectionAList[p].midYearAppraisarRating !=null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
														$scope.sectionAList[p].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionAList[p].midYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *30/100) +
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100) *
														70/100)));
													}
												if($scope.sectionAList[p].midYearAppraisarRating == null && $scope.sectionAList[p].finalYearAppraisarRating !=null &&
														$scope.sectionAList[p].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionAList[p].finalYearAppraisarRating * $scope.sectionAList[p].weightage)/100))));
													}
												if ($scope.sectionAList[p].fileName !=null)
												{
												$scope.sectionAList[p].fileDummyName = $scope.sectionAList[p].fileName;
// document.getElementById("kraFileDocumentDownloadSectionA"+p).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionA"+p).style.display =
// 'visible';
												}
												p++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section B") {
												$scope.sectionBList[q] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionBList[q].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);	
												}
												if($scope.sectionBList[q].midYearAppraisarRating !=null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
														$scope.sectionBList[q].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionBList[q].midYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *30/100) +
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100) *
														70/100)));
													}
												if($scope.sectionBList[q].midYearAppraisarRating == null && $scope.sectionBList[q].finalYearAppraisarRating !=null &&
														$scope.sectionBList[q].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionBList[q].finalYearAppraisarRating * $scope.sectionBList[q].weightage)/100))));
													}
												if ($scope.sectionBList[q].fileName !=null)
												{
												$scope.sectionBList[q].fileDummyName = $scope.sectionBList[q].fileName;
// document.getElementById("kraFileDocumentDownloadSectionB"+q).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionB"+q).style.display =
// 'visible';
												}
												q++;
											} else if ($scope.loggedUserKRADetails[i].sectionName == "Section C") {
												$scope.sectionCList[r] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionCList[r].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
												if($scope.sectionCList[r].midYearAppraisarRating == null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
														$scope.sectionCList[r].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100))));
													}
												if($scope.sectionCList[r].midYearAppraisarRating !=null && $scope.sectionCList[r].finalYearAppraisarRating !=null &&
														$scope.sectionCList[r].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionCList[r].midYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *30/100) +
														(((($scope.sectionCList[r].finalYearAppraisarRating * $scope.sectionCList[r].weightage)/100) *
														70/100)));
													}
												if ($scope.sectionCList[r].fileName !=null)
												{
													$scope.sectionCList[r].fileDummyName = $scope.sectionCList[r].fileName;
// document.getElementById("kraFileDocumentDownloadSectionC"+r).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionC"+r).style.display =
// 'visible';
												}
												r++;
											} else {
												$scope.sectionDList[s] = $scope.loggedUserKRADetails[i];
												if ($scope.loggedUserKRADetails[i].achievementDate != null) {
													$scope.sectionDList[s].achievementDate = new Date(
															$scope.loggedUserKRADetails[i].achievementDate);
// $scope.WeightageCountCheck = 1;
												}
												if($scope.loggedUserKRADetails[i].weightage !=null)
												{
													$scope.WeightageCount = $scope.WeightageCount
													+ parseInt($scope.loggedUserKRADetails[i].weightage);
												}
												if($scope.sectionDList[s].midYearAppraisarRating !=null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
														$scope.sectionDList[s].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (((($scope.sectionDList[s].midYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *30/100) +
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100) *
														70/100)));
													}
												if($scope.sectionDList[s].midYearAppraisarRating == null && $scope.sectionDList[s].finalYearAppraisarRating !=null &&
														$scope.sectionDList[s].weightage !=null)
													{
												$scope.kraToalCalculation = $scope.kraToalCalculation + (
														(((($scope.sectionDList[s].finalYearAppraisarRating * $scope.sectionDList[s].weightage)/100))));
													}
												if ($scope.sectionDList[s].fileName !=null)
												{
													$scope.sectionDList[s].fileDummyName = $scope.sectionDList[s].fileName;
// document.getElementById("kraFileDocumentDownloadSectionD"+s).style.display =
// 'none';
// document.getElementById("kraDocumentDownloadSectionD"+s).style.display =
// 'visible';
												}
												s++;
											}
										}
										document.getElementById("main_body").className = "";
									}

								});
					}
					$scope.addNewChoice = function(data,index) {
						if (data === "sectionA") {
							$scope.sectionAList.push({
								'sectionName' : 'Section A',
								'smartGoal' : null,
								'target' : null,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
// $scope.idCreation(data,$scope.sectionAList.length-1);
						}  if (data === "sectionB") {
							$scope.sectionBList.push({
								'sectionName' : 'Section B',
								'smartGoal' : null,
								'target' : null,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
// $scope.idCreation(data,$scope.sectionBList.length-1);
						}  if (data === "sectionC") {
							$scope.sectionCList.push({
								'sectionName' : 'Section C',
								'smartGoal' : null,
								'target' : null,
								"fileName" :null,
								"fileDummyName":null,
								'achievementDate' : null,
								'weightage' : null,
								'kraType' : null,
								'kraScreen':null,
								'midYearAchievement' : null,
								'midYearSelfRating' : null,
								'midYearAppraisarRating' : null,
								'midYearAssessmentRemarks' : null,
								'finalYearAchievement' : null,
								'finalYearSelfRating' : null,
								'finalYearAppraisarRating' : null,
								'remarks' : null
							});
// $scope.idCreation(data,$scope.sectionCList.length-1);
						} 
						
					};

// $scope.idCreation = function(data,index)
// {
// if (data === "sectionA") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionA"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionA"+index).style.display
// = 'none';
// }
// if (data === "sectionB") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionB"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionB"+index).style.display
// = 'none';
// }
// if (data === "sectionC") {
// var g = document.createElement('a');
// g.setAttribute("id", "kraDocumentDownloadSectionC"+index).style.display =
// 'none';
// // document.getElementById("kraDocumentDownloadSectionC"+index).style.display
// = 'none';
// }
// }
					
					$scope.removeNewChoice = function(index, data) {
						if (data === "sectionA") {
							$scope.sectionAList.splice(index, 1);
							;
						}  if (data === "sectionB") {
							$scope.sectionBList.splice(index, 1);
							;
						}  if (data === "sectionC") {
							$scope.sectionCList.splice(index, 1);
							;
						}
					};

					$scope.save = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var formdata = new FormData();
						$scope.fileList = [];
						for (var i = 0; i < $scope.sectionAList.length; i++) {
							if($scope.sectionAList[i].fileDummyName == null && $scope.sectionAList[i].fileName != null)
								{
								$scope.fileList.push($scope.sectionAList[i].fileName);
								$scope.sectionAList[i].fileName = $scope.sectionAList[i].fileName.name;
								}
						}
						for (var i = 0; i < $scope.sectionBList.length; i++) {
							if($scope.sectionBList[i].fileDummyName == null && $scope.sectionBList[i].fileName != null)
								{
								$scope.fileList.push($scope.sectionBList[i].fileName);
								$scope.sectionBList[i].fileName = $scope.sectionBList[i].fileName.name;
								}
						}
						for (var i = 0; i < $scope.sectionCList.length; i++) {
							if($scope.sectionCList[i].fileDummyName == null && $scope.sectionCList[i].fileName != null)
								{
								$scope.fileList.push($scope.sectionCList[i].fileName);
								$scope.sectionCList[i].fileName = $scope.sectionCList[i].fileName.name;
								}
						}
						for (var i = 0; i < $scope.sectionDList.length; i++) {
							if($scope.sectionDList[i].fileDummyName == null && $scope.sectionDList[i].fileName != null)
								{
								$scope.fileList.push($scope.sectionDList[i].fileName);
								$scope.sectionDList[i].fileName = $scope.sectionDList[i].fileName.name;
								}
						}
						if($scope.formData.sectionAList[0].kraType === "NEW")
						{
							formdata.append("kraSections", JSON.stringify($scope.formData));
							formdata.append("type", type);
							formdata.append("list",$scope.kraDetailsId);
							for(var i = 0;i<$scope.fileList.length;i++)
							{
								if( $scope.fileList[i].size != undefined)
									{
							formdata.append("fileList", $scope.fileList[i]);
									}
							}
							$http.post("/PMS/add-employee-new-kra-details", formdata, {
								transformRequest : angular.identity,
								headers : {
									'Content-Type' : undefined
								}
							}).success(
									function(data, status, headers) {
										if (status == 200) {
											swal(data.string);
											$scope.getEmployeeKraDetails();
											document.getElementById("main_body").className = "";
										}
					});
				        }
						if($scope.formData.sectionAList[0].kraType === null)
						{
							formdata.append("kraSections", JSON.stringify($scope.formData));
							formdata.append("kraSections", JSON.stringify($scope.formData));
							formdata.append("type", type);
							formdata.append("list",$scope.kraDetailsId);
							$scope.appraisalYearId = JSON.parse(localStorage.getItem("appraisalYearId"));
							formdata.append("appraisalYearId",$scope.appraisalYearId.id);
							
							for(var i = 0;i<$scope.fileList.length;i++)
							{
								if( $scope.fileList[i].size != undefined)
									{
							formdata.append("fileList", $scope.fileList[i]);
									}
							}
							$http.post("/PMS/add-employee-kra-details", formdata, {
								transformRequest : angular.identity,
								headers : {
									'Content-Type' : undefined
								}
							}).success(
									function(data, status, headers) {
										if (status == 200) {
											swal(data.string);
											$scope.getEmployeeKraDetails();
											document.getElementById("main_body").className = "";
										}
					});
						}
						
						
						
// var input = {
// "kraDetails" : $scope.formData,
// "list" : $scope.kraDetailsId,
// "type" : type,
// };
// var res = $http.post('/PMS/add-employee-kra-details',
// input);
// res
// .success(function(data, status) {
// $scope.result = data;
// if ($scope.result.integer == 1) {
// swal($scope.result.string);
// $scope.getEmployeeKraDetails();
// }
//
// });
					}
					$scope.submit = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													$scope.totalWeightage = 0;
													$scope.sectionDWeightage = 0;
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal !== null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === "") {
																	swal("Please fill all the field in Section A.");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal !== null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
																		|| $scope.sectionAList[i].midYearAchievement === null
																		|| $scope.sectionAList[i].midYearSelfRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
																		|| $scope.sectionAList[i].midYearAchievement === ""
																		|| $scope.sectionAList[i].midYearSelfRating === ""
																		) {
																	swal("Please fill all the field in Section A for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionAList.length; i++) {
															if ($scope.sectionAList[i].smartGoal !== null
																	&& $scope.sectionAList[i].smartGoal != "") {
																if ($scope.sectionAList[i].target === null
																		|| $scope.sectionAList[i].achievementDate === null
																		|| $scope.sectionAList[i].weightage === null
// || $scope.sectionAList[i].midYearAchievement === null
// || $scope.sectionAList[i].midYearSelfRating === null
// || $scope.sectionAList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionAList[i].finalYearAchievement === null
																		|| $scope.sectionAList[i].finalYearSelfRating === null
																		|| $scope.sectionAList[i].target === ""
																		|| $scope.sectionAList[i].achievementDate === ""
																		|| $scope.sectionAList[i].weightage === ""
// || $scope.sectionAList[i].midYearAchievement === ""
// || $scope.sectionAList[i].midYearSelfRating === ""
// || $scope.sectionAList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionAList[i].finalYearAchievement === ""
																		|| $scope.sectionAList[i].finalYearSelfRating === ""
																		) {

																	swal("Please fill all the field in Section A for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal !== null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === "") {
																	swal("Please fill all the field in Section B");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal !== null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
																		|| $scope.sectionBList[i].midYearAchievement === null
																		|| $scope.sectionBList[i].midYearSelfRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
																		|| $scope.sectionBList[i].midYearAchievement === ""
																		|| $scope.sectionBList[i].midYearSelfRating === ""
																		) {
																	swal("Please fill all the field in Section B for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionBList.length; i++) {
															if ($scope.sectionBList[i].smartGoal !== null
																	&& $scope.sectionBList[i].smartGoal != "") {
																if ($scope.sectionBList[i].target === null
																		|| $scope.sectionBList[i].achievementDate === null
																		|| $scope.sectionBList[i].weightage === null
// || $scope.sectionBList[i].midYearAchievement === null
// || $scope.sectionBList[i].midYearSelfRating === null
// || $scope.sectionBList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionBList[i].finalYearAchievement === null
																		|| $scope.sectionBList[i].finalYearSelfRating === null
																		|| $scope.sectionBList[i].target === ""
																		|| $scope.sectionBList[i].achievementDate === ""
																		|| $scope.sectionBList[i].weightage === ""
// || $scope.sectionBList[i].midYearAchievement === ""
// || $scope.sectionBList[i].midYearSelfRating === ""
// || $scope.sectionBList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionBList[i].finalYearAchievement === ""
																		|| $scope.sectionBList[i].finalYearSelfRating === ""
																		) {
																	swal("Please fill all the field in Section B for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal !== null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === "") {
																	swal("Please fill all the field in Section C");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal !== null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
																		|| $scope.sectionCList[i].midYearAchievement === null
																		|| $scope.sectionCList[i].midYearSelfRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
																		|| $scope.sectionCList[i].midYearAchievement === ""
																		|| $scope.sectionCList[i].midYearSelfRating === ""
																	    ) {
																	swal("Please fill all the field in Section C for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionCList.length; i++) {
															if ($scope.sectionCList[i].smartGoal !== null
																	&& $scope.sectionCList[i].smartGoal != "") {
																if ($scope.sectionCList[i].target === null
																		|| $scope.sectionCList[i].achievementDate === null
																		|| $scope.sectionCList[i].weightage === null
// || $scope.sectionCList[i].midYearAchievement === null
// || $scope.sectionCList[i].midYearSelfRating === null
// || $scope.sectionCList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionCList[i].finalYearAchievement === null
																		|| $scope.sectionCList[i].finalYearSelfRating === null
																		|| $scope.sectionCList[i].target === ""
																		|| $scope.sectionCList[i].achievementDate === ""
																		|| $scope.sectionCList[i].weightage === ""
// || $scope.sectionCList[i].midYearAchievement === ""
// || $scope.sectionCList[i].midYearSelfRating === ""
// || $scope.sectionCList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionCList[i].finalYearAchievement === ""
																		|| $scope.sectionCList[i].finalYearSelfRating === "") {
																	swal("Please fill all the field in Section C for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}

														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.initializationYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal !== null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === "") {
																	swal("Please fill all the field in Section D");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal !== null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
																		|| $scope.sectionDList[i].midYearAchievement === null
																		|| $scope.sectionDList[i].midYearSelfRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
																		|| $scope.sectionDList[i].midYearAchievement === ""
																		|| $scope.sectionDList[i].midYearSelfRating === ""
																		) {
																	swal("Please fill all the field in Section D for mid Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.sectionDList.length; i++) {
															if ($scope.sectionDList[i].smartGoal !== null
																	&& $scope.sectionDList[i].smartGoal != "") {
																if ($scope.sectionDList[i].target === null
																		|| $scope.sectionDList[i].achievementDate === null
																		|| $scope.sectionDList[i].weightage === null
// || $scope.sectionDList[i].midYearAchievement === null
// || $scope.sectionDList[i].midYearSelfRating === null
// || $scope.sectionDList[i].midYearAssessmentRemarks === null
																		|| $scope.sectionDList[i].finalYearAchievement === null
																		|| $scope.sectionDList[i].finalYearSelfRating === null
																		|| $scope.sectionDList[i].target === ""
																		|| $scope.sectionDList[i].achievementDate === ""
																		|| $scope.sectionDList[i].weightage === ""
// || $scope.sectionDList[i].midYearAchievement === ""
// || $scope.sectionDList[i].midYearSelfRating === ""
// || $scope.sectionDList[i].midYearAssessmentRemarks === ""
																		|| $scope.sectionDList[i].finalYearAchievement === ""
																		|| $scope.sectionDList[i].finalYearSelfRating === "") {
																	swal("Please fill all the field in Section D for Final Term");
																	$scope.checkValidation = false;
																	break;
																}
															}
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.WeightageCount > 100
															|| $scope.WeightageCount < 100) {
														swal("Required weightage count is 100%");
													}
													if ($scope.WeightageCount == 100) {
														$scope.save(type);
													}
												}
											}
										});
					}

				});

app
		.controller(
				'FinalReviewController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter FinalReviewController');
					logDebug('Browser language >> ' + window.navigator.language);

					$scope.formClear = function() {
						$scope.firstLevelSuperiorComments = null;
						$scope.secondLevelSuperiorComments = null;
						$scope.assesseeComments = null;
					}

					$scope.save = function() {
						var input = {
							"firstLevelSuperiorComments" : $scope.firstLevelSuperiorComments,
							"secondLevelSuperiorComments" : $scope.secondLevelSuperiorComments,
							"assesseeComments" : $scope.assesseeComments
						};
						var res = $http.post('/PMS/add-finalReview-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										$scope.message = $scope.result.string;
										document
												.getElementById("finalReviewAlert").className = "alert-successs";
										$timeout(
												function() {
													document
															.getElementById("finalReviewAlert").className = "";
													$scope.message = "";
												}, 5000);
										$scope.formClear();
									}

								});
					}

				});

app
		.controller(
				'TrainingNeedsController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter TrainingNeedsController');
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					logDebug('Browser language >> ' + window.navigator.language);
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					// var checkColumnAdd = 1;
					$scope.trainingNeedsDetailsOldIdList = [];
					$scope.approveRejectList = [ "Approve", "Reject" ];
					$scope.trainingNeedsDetails = [ {
						'trainingTopic' : null,
						'trainingReasons' : null,
						'manHours' : null,
					// 'child' : $scope.dynamicTrainingNeedsDetails,
					} ];
					$scope.getTrainingNeedsDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "TrainingNeeds"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object;
									$scope.loggedUserAppraisalDetails = $scope.result[0].object;
									if ($scope.result[1].length > 0) {
										$scope.trainingNeedsDetails = $scope.result[1];
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											if ($scope.trainingNeedsDetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
											} else {
												$scope.buttonDisabled = false;
											}
										}
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											$scope.trainingNeedsDetailsOldIdList[i] = $scope.trainingNeedsDetails[i].id;
										}

									}
									document.getElementById("main_body").className = "";
								});
					}
					$scope.editField = function() {
						$scope.buttonDisabled = !$scope.buttonDisabled;
					}
					$scope.addNewChoice = function() {
						// if (checkColumnAdd == 1) {
						$scope.trainingNeedsDetails.push({
							'trainingTopic' : null,
							'trainingReasons' : null,
							'manHours' : null,
						// 'child' : $scope.tishu,
						});

						// $scope.tishu1 = angular.copy($scope.tishu);
						// checkColumnAdd = 0;
						// } else {
						// $scope.trainingNeedsDetails.push({
						// 'topic' : null,
						// 'reasons' : null,
						// 'manHours' : null,
						// // 'child' : $scope.tishu1,
						// });
						// $scope.tishu = angular.copy($scope.tishu1);
						// checkColumnAdd = 1;
						// }
					};

					$scope.removeNewChoice = function(index, data) {
						$scope.trainingNeedsDetails.splice(index, 1);
					};

					$scope.save = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type" : type,
							"list" : $scope.trainingNeedsDetailsOldIdList,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"trainingNeedsDetails" : $scope.trainingNeedsDetails
						};
						var res = $http.post(
								'/PMS/add-employee-trainingNeeds-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.trainingNeedsDetails = $scope.result.object;
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											if ($scope.trainingNeedsDetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
											} else {
												$scope.buttonDisabled = false;
											}
										}
										$scope.trainingNeedsDetailsOldIdList = [];
										for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
											$scope.trainingNeedsDetailsOldIdList[i] = $scope.trainingNeedsDetails[i].id;
										}
										document.getElementById("main_body").className = "";
										// $scope.formClear();
									}

								});
					}

					$scope.submit = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.trainingNeedsDetails.length; i++) {
														if ($scope.trainingNeedsDetails[i].trainingTopic === null
																|| $scope.trainingNeedsDetails[i].trainingReasons === null
																|| $scope.trainingNeedsDetails[i].manHours === null
																|| $scope.trainingNeedsDetails[i].trainingTopic === ""
																|| $scope.trainingNeedsDetails[i].trainingReasons === ""
																|| $scope.trainingNeedsDetails[i].manHours === "") {
															swal("Please fill all the field");
															$scope.checkValidation = false;
															break;
														}
													}
												}
												if ($scope.checkValidation) {
													$scope.save(type);
												}
											}
										});
					}

				});

app
		.controller(
				'ExtraOrdinaryController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter OrganizationalCommitmentController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.loggedUserAppraisalDetails = [];
					$scope.selects = [ 0,1, 2, 3, 4, 5 ];
					$scope.extraOrdinaryOldIdList = [];
					// var checkColumnAdd = 1;
					$scope.WeightageCount = 0;
					$scope.ExtraOrdinaryTotalCalculation = 0;
					$scope.extraOrdinaryDetails = [ {
						'contributions' : null,
						'contributionDetails' : null,
						'weightage' : null,
						'finalYearSelfRating' : null,
						'finalYearAppraisarRating' : null,
						// 'child' : $scope.dynamicExtraOrdinaryDetails,
						'remarks' : null
					} ];

					$scope.countWeightage = function() {
						$scope.WeightageCount = 0;
						for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
							if ($scope.extraOrdinaryDetails[i].weightage != null)
								$scope.WeightageCount = $scope.WeightageCount
										+ parseInt($scope.extraOrdinaryDetails[i].weightage);
						}
					}

					$scope.getExtraOrdinaryDetails = function() {
						$scope.extraOrdinaryOldIdList = [];
						
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"type" : "ExtraOrdinary",
							"appraisalYearId" : $scope.appraisalYearId.id
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									if ($scope.result.object == null) {
										$scope.loggedUserAppraisalDetails
												.push({
													"initializationYear" : null,
													"midYear" : null,
													"finalYear" : null
												});
									} else {
										$scope.loggedUserAppraisalDetails = $scope.result.object;
									}
									// $scope.dynamicExtraOrdinaryDetails =
									// data.object[1];
									// $scope.tishu = angular
									// .copy($scope.dynamicExtraOrdinaryDetails);
									//							

									if (data.object[1].length > 0) {
										// $scope.extraOrdinaryDetails =[];
										// $scope.extraOrdinaryDetails = [ {
										// 'contributions' : null,
										// 'contributionDetails' : null,
										// 'finalYearSelfRating' : null,
										// 'finalYearAppraisarRating' : null,
										// 'finalScore' : null,
										// // 'child' :
										// $scope.dynamicExtraOrdinaryDetails,
										// 'remarks' : null
										// } ];
										$scope.extraOrdinaryDetails = data.object[1];
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											if ($scope.extraOrdinaryDetails[i].isValidatedByEmployee == 1) {
												$scope.buttonDisabled = true;
												break;
											}
										}
										$scope.WeightageCount = 0;
										$scope.ExtraOrdinaryTotalCalculation = 0;
										for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
											$scope.extraOrdinaryOldIdList[i] = $scope.extraOrdinaryDetails[i].id;
											if ($scope.extraOrdinaryDetails[i].weightage != null) {
												$scope.WeightageCount = $scope.WeightageCount
														+ parseInt($scope.extraOrdinaryDetails[i].weightage);
												
											}
											if ($scope.extraOrdinaryDetails[i].weightage != null && $scope.extraOrdinaryDetails[i].finalYearAppraisarRating !=null) {
												
												$scope.ExtraOrdinaryTotalCalculation = $scope.ExtraOrdinaryTotalCalculation +
												(($scope.extraOrdinaryDetails[i].weightage * $scope.extraOrdinaryDetails[i].finalYearAppraisarRating)*10/100);
											}
											
										}
									}
									document.getElementById("main_body").className = "";
								});
					}

					// $scope.formClear = function() {
					// $scope.extraOrdinaryDetails = [];
					// $scope.extraOrdinaryDetails = [ {
					// 'contributions' : null,
					// 'contributionDetails' : null,
					// 'finalYearSelfRating' : null,
					// 'finalYearAppraisarRating' : null,
					// 'finalScore' : null,
					// 'remarks' : null
					// } ];
					// }

					$scope.addNewChoice = function(index) {
						// if (checkColumnAdd == 1) {
						$scope.extraOrdinaryDetails.push({
							'contributions' : null,
							'contributionDetails' : null,
							'weightage' : null,
							'finalYearSelfRating' : null,
							'finalYearAppraisarRating' : null,
							// 'child' : $scope.tishu,
							'remarks' : null
						});
						// $scope.tishu1 = angular.copy($scope.tishu);
						// checkColumnAdd = 0;
						// } else {
						// $scope.extraOrdinaryDetails.push({
						// 'contributions' : null,
						// 'contributionDetails' : null,
						// 'finalYearSelfRating' : null,
						// 'finalYearAppraisarRating' : null,
						// 'finalScore' : null,
						// // 'child' : $scope.tishu1,
						// 'remarks' : null
						// });
						// $scope.tishu = angular.copy($scope.tishu1);
						// checkColumnAdd = 1;
						// }
					};

					$scope.removeNewChoice = function(index, data) {
						$scope.extraOrdinaryDetails.splice(index, 1);
						;
					};

					$scope.save = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"extraOrdinaryDetails" : $scope.extraOrdinaryDetails,
							"type" : type,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"list" : $scope.extraOrdinaryOldIdList
						};
						var res = $http.post(
								'/PMS/add-employee-extraOrdinary-details',
								input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.getExtraOrdinaryDetails();
										document.getElementById("main_body").className = "";
										// $scope.formClear();
									}

								});
					}
					$scope.submit = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													for (var i = 0; i < $scope.extraOrdinaryDetails.length; i++) {
														if ($scope.extraOrdinaryDetails[i].contributions === null
																|| $scope.extraOrdinaryDetails[i].contributionDetails === null
																|| $scope.extraOrdinaryDetails[i].weightage === null
																|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === null
																|| $scope.extraOrdinaryDetails[i].contributions === ""
																|| $scope.extraOrdinaryDetails[i].contributionDetails === ""
																|| $scope.extraOrdinaryDetails[i].weightage === ""
																|| $scope.extraOrdinaryDetails[i].finalYearSelfRating === "") {
															swal("Please fill all the field");
															$scope.checkValidation = false;
															break;
														}
													}
												}
												if ($scope.checkValidation) {
													if ($scope.WeightageCount > 10
															|| $scope.WeightageCount < 10) {
														swal("Required weightage count is 10%");
													}
													if ($scope.WeightageCount == 10) {
														$scope.save(type);
													}
												}

											}
										});
					}
				});
app
		.controller(
				'BehavioralComptenceController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter BehavioralComptenceController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					$scope.loggedUserAppraisalDetails = [];
					$scope.BehavioralComptenceTotalCalculation = 0;
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.getBehavioralComptenceDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "BehavioralComptence"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.formData = data.object[1];
									$scope.formMainData = data.object[2];
									if ($scope.result.object == null) {
										$scope.loggedUserAppraisalDetails
												.push({
													"initializationYear" : null,
													"midYear" : null,
													"finalYear" : null
												});
									} else {
										$scope.loggedUserAppraisalDetails = $scope.result.object;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.buttonDisabled = true;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 1
											&& $scope.loggedUserAppraisalDetails.finalYear == 0) {
										$scope.buttonDisabled = false;
									}
									if ($scope.loggedUserAppraisalDetails.midYear == 0
											&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
										$scope.buttonDisabled = false;
									}
									for (var i = 0; i < $scope.formMainData.length; i++) {
										if ($scope.formMainData[i].isValidatedByEmployee == 1) {
											$scope.buttonDisabled = true;
											break;
										}
									}
//									for (var i = 0; i < $scope.formMainData.length; i++) {// Tishu
									for (var i = 0; i < $scope.formMainData.length; i++) {
										$scope.formData[i].behaviouralCompetenceDetailsId = $scope.formMainData[i].id;
										$scope.formData[i]['comments'] = $scope.formMainData[i].comments;
										$scope.formData[i].isValidatedByEmployee = $scope.formMainData[i].isValidatedByEmployee;
										$scope.formData[i].midYearSelfRating = $scope.formMainData[i].midYearSelfRating;
										$scope.formData[i].midYearAssessorRating = $scope.formMainData[i].midYearAssessorRating;
										$scope.formData[i].finalYearSelfRating = $scope.formMainData[i].finalYearSelfRating;
										$scope.formData[i].finalYearAssessorRating = $scope.formMainData[i].finalYearAssessorRating;
										
										if($scope.formData[i].midYearAssessorRating == null && $scope.formData[i].weightage !=null && $scope.formData[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formData[i].finalYearAssessorRating * $scope.formData[i].weightage)/100)));
										}	
										if($scope.formData[i].midYearAssessorRating !=null && $scope.formData[i].weightage !=null && $scope.formData[i].finalYearAssessorRating !=null)
										{
										$scope.BehavioralComptenceTotalCalculation = $scope.BehavioralComptenceTotalCalculation +
											(((($scope.formData[i].midYearAssessorRating
												* $scope.formData[i].weightage )/100) *30/100) +
												((($scope.formData[i].finalYearAssessorRating * $scope.formData[i].weightage)/100) *
												70/100));
										}	
										
									}
									document.getElementById("main_body").className = "";
									// var res = $http
									// .post('/PMS/get-behavioral-competence-list');
									// res.success(function(data, status) {
									// $scope.formData = data;
									// });
								});
					}
					$scope.selects = [ 1, 2, 3, 4, 5 ];
					$scope.save = function(type) {
						document.getElementById("main_body").className = "loading-pane";
						var input = {
							"type" : type,
							"behaviouralCompetence" : $scope.formData
						};
						var res = $http
								.post(
										'/PMS/add-employee-behavioral-comptence-details',
										input);
						res
								.success(function(data, status) {
									$scope.result = data;
									if ($scope.result.integer == 1) {
										swal($scope.result.string);
										$scope.getBehavioralComptenceDetails();
										document.getElementById("main_body").className = "";
									}

								});
					}
					$scope.submit = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												$scope.checkValidation = true;
												if ($scope.checkValidation) {
													if ($scope.loggedUserAppraisalDetails.midYear > 0) {
														for (var i = 0; i < $scope.formData.length; i++) {
															if ($scope.formData[i].comments === null
																	|| $scope.formData[i].midYearSelfRating === null) {
																swal("Please fill all the field in mid Term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
													if ($scope.loggedUserAppraisalDetails.finalYear > 0) {
														for (var i = 0; i < $scope.formData.length; i++) {
															if ($scope.formData[i].finalYearSelfRating === null
																	|| $scope.formData[i].finalYearSelfRating === "") {
																swal("Please fill all the field in final term");
																$scope.checkValidation = false;
																break;
															}
														}
													}
												}
												if ($scope.checkValidation) {
													$scope.save(type);
												}

											}
										});
					}
				});
app
		.controller(
				'CareerAspirationController',
				function($window, $scope, $http, $timeout, sharedProperties) {
					logDebug('Enter CareerAspirationController');
					logDebug('Browser language >> ' + window.navigator.language);
					document.getElementById("appraisalYearID").style.visibility = "visible";
					document.getElementById("main_body").className = "loading-pane";
					$scope.appraisalYearId = JSON.parse(localStorage
							.getItem("appraisalYearId"));
					$scope.employeeCareerAspirationDetails = [];

					$scope.getCareerAspirationDetails = function() {
						var input = {
							"empCode" : document
									.getElementById("globalUserName").value,
							"appraisalYearId" : $scope.appraisalYearId.id,
							"type" : "CareerAspiration"
						};
						var res = $http.post('/PMS/getDetails', input);
						res
								.success(function(data, status) {
									$scope.result = data.object[0];
									$scope.loggedUserAppraisalDetails = $scope.result.object;
									$scope.employeeCareerAspirationDetails = data.object[1][0];
									if ($scope.employeeCareerAspirationDetails === undefined) {
										$scope.employeeCareerAspirationDetails = [];
									$scope.employeeCareerAspirationDetails.push({
										"initializationComments" :null,
										"midYearComments" :null,
										"yearEndComments" :null,
									})
									$scope.employeeCareerAspirationDetails = $scope.employeeCareerAspirationDetails[0];
									}
									if ($scope.employeeCareerAspirationDetails != undefined && $scope.loggedUserAppraisalDetails != null) {
									if ($scope.employeeCareerAspirationDetails.isValidatedByEmployee == 1
											&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
										$scope.isCareerAspirationsSubmitted = true;
									}
									if ($scope.employeeCareerAspirationDetails.midYearCommentsStatus == 1
											&& $scope.loggedUserAppraisalDetails.midYear == 1) {
										$scope.isCareerAspirationsSubmitted = true;
									}
									if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatus == 1
											&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
										$scope.isCareerAspirationsSubmitted = true;
									}
									}
									document.getElementById("main_body").className = "";
								});
					}

					$scope.save = function(type) {
						if (($scope.employeeCareerAspirationDetails === undefined
								|| $scope.employeeCareerAspirationDetails.initializationComments === null 
								|| $scope.employeeCareerAspirationDetails.initializationComments == "") 
								&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
							swal("Please enter career aspirations");
						} else if (($scope.employeeCareerAspirationDetails.midYearComments === null
								|| $scope.employeeCareerAspirationDetails.midYearComments == "")
								&& $scope.loggedUserAppraisalDetails.midYear == 1) {
							swal("Please enter career aspirations for mid term ");
						} else if (($scope.employeeCareerAspirationDetails.yearEndComments === null
								|| $scope.employeeCareerAspirationDetails.yearEndComments == "")
								&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
							swal("Please enter career aspirations for final term ");
						} else {
							document.getElementById("main_body").className = "loading-pane";
							var input = {
								"empCode" : document
										.getElementById("globalUserName").value,
								"type" : type,
								"initializationComments" : $scope.employeeCareerAspirationDetails.initializationComments,
								"midYearComments" : $scope.employeeCareerAspirationDetails.midYearComments,
								"yearEndComments" : $scope.employeeCareerAspirationDetails.yearEndComments,
								"id" : $scope.employeeCareerAspirationDetails.id,
								"appraisalYearId" : $scope.appraisalYearId.id
							};
							var res = $http
									.post(
											'/PMS/add-employee-career_aspirations-details',
											input);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											swal($scope.result.string)
											$scope.employeeCareerAspirationDetails = $scope.result.object[0];
											if ($scope.employeeCareerAspirationDetails.isValidatedByEmployee == 1
													&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.midYearCommentsStatus == 1
													&& $scope.loggedUserAppraisalDetails.midYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											if ($scope.employeeCareerAspirationDetails.yearEndCommentsStatus == 1
													&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
												$scope.isCareerAspirationsSubmitted = true;
											}
											document.getElementById("main_body").className = "";
										}
									});
						}
					}
					$scope.submit = function(type) {
						swal({
							title : "Are you sure?",
							icon : "warning",
							buttons : [ 'No, cancel it!', 'Yes, I am sure!' ],
							dangerMode : true,
						})
								.then(
										function(isConfirm) {
											if (isConfirm) {
												if (($scope.employeeCareerAspirationDetails === undefined
														|| $scope.employeeCareerAspirationDetails.initializationComments === "")
														&& $scope.loggedUserAppraisalDetails.initializationYear == 1) {
													swal("Please enter career aspirations");
												} else if ($scope.employeeCareerAspirationDetails.midYearComments === ""
														&& $scope.loggedUserAppraisalDetails.midYear == 1) {
													swal("Please enter career aspirations for mid term");
												}else if ($scope.employeeCareerAspirationDetails.yearEndComments === ""
														&& $scope.loggedUserAppraisalDetails.finalYear == 1) {
													swal("Please enter career aspirations for final term");
												}  else {
													$scope.save(type);
												}
											}
										});
					}
				});

app.controller('NavigationController', function($scope, $http) {
	logDebug('Enter NavigationController');
	// Must use a wrapper object, otherwise "activeItem" won't work
	$scope.states = {};

	// Check if user refreshed the browser window, mark selected item in
	// left menu
	$scope.setActiveMenu = function() {
		$scope.states.activeItem = "";
		cpath = window.location.hash.toString();
		clpath = cpath.substring(cpath.lastIndexOf('/') + 1);
		if (clpath.length == 0) {
			$scope.states.activeItem = 'home';
		} else {
			$scope.states.activeItem = clpath;
		}
	}

	$http.get('/PMS/get-menu-items').success(function(data) {
		$scope.items = data;
	});

	$scope.$on('$locationChangeSuccess', function(event) {
		$scope.setActiveMenu();
	});
	$scope.showChilds = function(item) {
		item.active = !item.active;
	};
});

// Controller for Employee Management - Search / Delete
app.controller('MainCtrl', function($scope, $http, sharedProperties, $window) {
	$('#sidebar-wrapper').height($(window).height()-50);
	$scope.clearActiveYear = function() {
		localStorage.clear();
	}
	$scope.getCurrentAppraisalYearDetails = function() {
		var res = $http.post('/PMS/get-all-appraisal-year-details');
		res.success(function(data, status) {
			$scope.appraisalYearList = data.object[0];
			$scope.appraisalYear = data.object[1];
			$scope.previousYear = localStorage.getItem("appraisalYearId");
			if ($scope.previousYear == undefined) {
				localStorage.setItem("appraisalYearId", JSON
						.stringify($scope.appraisalYear));

			} else {
				$scope.appraisalYear = JSON.parse(localStorage
						.getItem("appraisalYearId"));
// console.log($scope.appraisalYear);
			}
			// document.getElementById("main_body").className = "";
		});

	}

	$scope.setAppraisalYearDetails = function() {
		localStorage.setItem("appraisalYearId", JSON
				.stringify($scope.appraisalYear));
		$window.location.reload();

	}

});
