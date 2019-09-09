	var app = angular.module('loginApp', []);
	app
			.controller(
					'loginCtrl',
					function($window, $scope, $timeout, $http) {

						$scope.toggleForgetPassword = function() {
							$scope.forgetPassword = !$scope.forgetPassword;
						}
					
						$scope.submit = function() {
							var res = $http.post('/PMS/forgetPassword',
									$scope.email);
							res
									.success(function(data, status) {
										$scope.result = data;
										if ($scope.result.integer == 1) {
											swal($scope.result.string);
											$scope.email = null;
										}
									});
						}

					});