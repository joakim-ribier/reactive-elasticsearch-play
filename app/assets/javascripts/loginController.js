'use strict';

var myApp = angular.module('App.controllers',[]);
myApp.controller('LoginController', ['$scope', '$log', '$http', '$window', '$location', function ($scope, $log, $http, $window, $location) {
    $scope.loading = false;
    $scope.errorMessage = '';
    $scope.credentials = {
        username: '',
        password: ''
    };
    
    $scope.login = function (credentials) {
        $scope.loading = true;
        
        $http.post($window.hostName + '/page/login/authentication', {credentials: credentials})
        .success(function(data, status, headers, config) {
            $window.location = "/";
        })
        .error(function(data, status, headers, config) {
            $log.error(data);
            $scope.loading = false;
            $scope.errorMessage = 'L\'identifiant et le mot de passe que vous avez saisi sont incorrects.';
        });
    };
}]);