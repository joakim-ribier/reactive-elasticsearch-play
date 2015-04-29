(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'LoginController', 
            ['$scope', '$log', '$http', '$window', '$location', '$translate', '$filter', '$rootScope', 'loginService',
            function ($scope, $log, $http, $window, $location, $translate, $filter, $rootScope, loginService) {
        
        $scope.credentials = {
            username: '',
            password: '',
            password2: '',
            firstConnection:  false
        };
        
        $scope.login = function (credentials) {
            $rootScope.loading = true;
            if (credentials.firstConnection === true) {
                if (credentials.password == credentials.password2) {
                    loginService.firstConnection(credentials).then(
                        function(data){
                            $rootScope.loading = false;
                            
                            credentials.username = '';
                            credentials.password = '';
                            credentials.password2 = '';
                            credentials.firstConnection = false;
                            
                            $scope.errorMessage = '';
                            $scope.infoMessage = $filter('translate')('module.login.first.connection.success');
                        }, 
                        function (data) {
                            $rootScope.loading = false;
                            $scope.errorMessage =  $filter('translate')(data.key);
                        }
                    );
                } else {
                    $rootScope.loading = false;
                    $scope.errorMessage = $filter('translate')('module.login.form.error');
                }
            } else {
                loginService.authentication(credentials).then(
                    function(data){
                        $window.location = "/";
                    }, 
                    function (data) {
                        $rootScope.loading = false;
                        $scope.errorMessage = $filter('translate')('module.login.form.error');
                    }
                );
            }
        };
    }]);
    
})();