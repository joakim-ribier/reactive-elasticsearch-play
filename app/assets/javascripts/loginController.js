'use strict';

var myApp = angular.module('reactiveElasticsearchPlay.controllers',[]);
myApp.controller('LoginController', ['$scope', '$log', '$http', '$window', '$location', function ($scope, $log, $http, $window, $location) {
	$scope.loading = false;
	$scope.validationMessage = '';
	$scope.credentials = {
		username: '',
		password: ''
	};
	
	$scope.login = function (credentials) {
		$scope.loading = true;
		$http.post($window.hostName + '/page/login/authentication', {credentials: credentials}).
			success(function(data, status, headers, config) {
				$window.location = "/";
			}).
			error(function(data, status, headers, config) {
				$log.error(data);
				$scope.loading = false;
				$scope.validationMessage = 'L\'identifiant ou le mot de passe que vous avez entré est incorrect.';
			});
	};
}]);