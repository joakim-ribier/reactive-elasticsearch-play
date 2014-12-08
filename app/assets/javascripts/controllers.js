'use strict';

angular.module('realtimeSearch.controllers', [])
    .controller('SearchCtrl', ['$scope', '$log', '$http', '$window', function ($scope, $log, $http, $window) {
        $scope.searchResults = [];
        $scope.searchString = '';
        
        $scope.startSearching = function () {
        	if ($scope.searchString) {
        		$http.get($window.hostName + '/search/by/query/' + $scope.searchString)
        			.success(function(data) {
        				$log.debug(data);
        				$scope.searchResults = data;
        			}
        		);
        	}
        }
    }]);