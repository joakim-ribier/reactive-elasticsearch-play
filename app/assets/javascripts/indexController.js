'use strict';

var myApp = angular.module('reactiveElasticsearchPlay.controllers',[]);
myApp.controller('IndexController', ['$scope', '$log', '$http', '$window', function ($scope, $log, $http, $window) {
	$scope.loading = false;
	$scope.validationMessage = '';
	$scope.searchResults = [];
    $scope.searchString = '';
        
    $scope.startSearching = function () {
    	if ($scope.searchString) {
    		
    		$scope.loading = true;
    		$scope.validationMessage = '';
    		
    		$http.get($window.hostName + '/search/by/query/' + $scope.searchString)
    			.success(function(data) {
    				$log.debug(data);
    				$scope.searchResults = data;
    				$scope.loading = false;
    				
    				if (data.length < 1) {
    					$scope.validationMessage = 'Il n\'y a pas de résultat.'
    				}
    			})
    			.error(function(data, status, headers, config) {
    				$log.error(data);
    				$scope.loading = false;
    			});
    	}
    }
}]);