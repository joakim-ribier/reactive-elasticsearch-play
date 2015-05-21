(function() {
    
    'use strict';
    
    angular.module('App').controller(
            'IndexController', 
            ['$scope', '$log', '$http', '$window', '$rootScope', 'searchService',
             function ($scope, $log, $http, $window, $rootScope, searchService) {
        
        $scope.searchResults = [];
        $scope.searchString = '';
        
        $scope.words = [];
        
        $scope.getTags = function () {
            searchService.getTags().then(
                function(data) {
                    $scope.words = data;
                }, 
                function (data) {
                    $rootScope.$broadcast('error', data.key);
                }
            );
        };
        
        $scope.startSearching = function () {
            if ($scope.searchString) {
                $rootScope.loading = true;
                searchService.search($scope.searchString).then(
                    function(data) {
                        $scope.searchResults = data;
                        $rootScope.loading = false;
                        
                        if (data.length < 1) {
                            $rootScope.$broadcast('info', 'module.index.search.nothing');
                        }
                    }, 
                    function (data) {
                        $rootScope.loading = false;
                        $rootScope.$broadcast('error', data.key);
                    }
                );
            }
        };
        
        $scope.selectOrUnSelectAll = function () {
            angular.forEach($scope.searchResults, function (item) {
                item.selected = $scope.selectedAll;
            });
        };
        
        $scope.downloadFiles = function () {
            if ($scope.searchResults && $scope.searchResults.length > 0) {
                var documentIds = '';
                angular.forEach($scope.searchResults, function (document) {
                    if (document.selected) {
                        documentIds = documentIds + document.id + ';'
                    }
                });
                
                if (documentIds && documentIds != '') {
                    $rootScope.loading = true;
                    
                    $http.get($window.hostName + '/file/download/by/ids/' + documentIds)
                    .success(function(data) {
                        $rootScope.loading = false;
                        $window.location = $window.hostName + '/file/download/zip';
                    })
                    .error(function(data) {
                        $rootScope.loading = false;
                        $rootScope.$broadcast('error', data.key);
                    }); 
                } else {
                    $rootScope.$broadcast('warning', 'module.index.dowload.zip.select.error');
                }
            }
        };
        
        $scope.getTags();
    }]);
    
})();