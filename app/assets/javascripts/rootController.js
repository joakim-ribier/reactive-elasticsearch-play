(function() {
    
    'use strict';
    
    angular.module('App').controller(
            'RootController',
            ['$scope', '$window', '$rootScope', '$log', '$filter', '$translate', 'searchService',
            function ($scope, $window, $rootScope, $log, $filter, $translate, searchService) {
        
        $rootScope.hostname = $window.hostName;
        
        $rootScope.data = {
            tagscloud : [],
            tags : [],
        };
        
        $scope.notification = {
            'message': '',
            'type': 'info'
        };
        
        $scope.$on('tags-cloud', function (event) {
            $log.debug('tags-cloud');
            $rootScope.data = {
                tagscloud : [],
                tags : [],
            };
            searchService.getTags().then(
                function(data) {
                    $rootScope.data.tagscloud = data.slice(0, 25);
                    angular.forEach(data, function(value, key) {
                        this.push(value.text);
                    }, $rootScope.data.tags);
                }, 
                function (data) {
                    $rootScope.$broadcast('error', data.key);
                }
            );
        });
        
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