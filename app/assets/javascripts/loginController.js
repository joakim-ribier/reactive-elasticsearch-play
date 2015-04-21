(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'LoginController', 
            ['$scope', '$log', '$http', '$window', '$location', '$translate', '$filter',
            function ($scope, $log, $http, $window, $location, $translate, $filter) {
        
        $scope.loading = false;
        $scope.errorMessage = '';
        $scope.credentials = {
            username: '',
            password: '',
            password2: '',
            firstConnection:  false
        };
        
        $scope.login = function (credentials) {
            $scope.loading = true;
            if (credentials.firstConnection === true) {
                if (credentials.password == credentials.password2) {
                    $http.post($window.hostName + '/page/login/firstConnection', {credentials: credentials})
                    .success(function(data, status, headers, config) {
                        credentials.username = '';
                        credentials.password = '';
                        credentials.password2 = '';
                        credentials.firstConnection = false;
                        
                        $scope.errorMessage = '';
                        $scope.infoMessage = $filter('translate')('module.login.first.connection.success');
                    })
                    .error(function(data, status, headers, config) {
                        $log.error(data);
                        $scope.loading = false;
                        $scope.errorMessage =  $filter('translate')(data.key);
                    });
                } else {
                    $scope.errorMessage = $filter('translate')('module.login.form.error');
                }
            } else {
                $http.post($window.hostName + '/page/login/authentication', {credentials: credentials})
                .success(function(data, status, headers, config) {
                    $window.location = "/";
                })
                .error(function(data, status, headers, config) {
                    $log.error(data);
                    $scope.loading = false;
                    $scope.errorMessage = $filter('translate')('module.login.form.error');
                });
            }
            
        };
    }]);
    
})();