(function() {
    
    'use strict';
    
    angular.module('App').controller(
            'RootController',
            ['$scope', '$window', '$rootScope', '$log', '$filter', '$translate', 
            function ($scope, $window, $rootScope, $log, $filter, $translate) {
        
        $rootScope.hostname = $window.hostName;
        
        $scope.notification = {
            'message': '',
            'type': 'info'
        };
        
        $scope.$on('info', function (event, key) {
            $scope.notification = {
                'message': $filter('translate')(key),
                'type': 'info'
            }
        });
        
        $scope.$on('error', function (event, key) {
            $scope.notification = {
                'message': $filter('translate')(key),
                'type': 'danger'
            }
        });
        
        $scope.$on('warning', function (event, key) {
            $scope.notification = {
                'message': $filter('translate')(key),
                'type': 'warning'
            }
        });
        
        $scope.$on('success', function (event, key) {
            $scope.notification = {
                'message': $filter('translate')(key),
                'type': 'success'
            }
        });
        
        $scope.close = function () {
            $scope.notification = {
                'message': ''
            };
        };
        
    }]);
    
})();