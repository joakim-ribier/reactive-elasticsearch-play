(function() {
    
    'use strict';
    
    angular.module('App').controller(
            'IndexController', 
            ['$scope', '$log', '$http', '$window', '$rootScope', 'searchService', '$modal', 
             function ($scope, $log, $http, $window, $rootScope, searchService, $modal) {
        
        $scope.searchResults = [];
        $scope.searchString = '';
        
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
        
        $scope.openAllMark = function () {
            angular.forEach($scope.searchResults, function (result) {
                $scope.openMark(result);
            });
        };
        
        $scope.openMark = function (result) {
            if (result.open === true) {
                result.open = false;
            } else {
                result.open = true;
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
        
        $scope.openSendFileByMailModal = function (result) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'sendmail',
                controller: 'SendController',
                resolve: {
                    data: function () {
                        return result;
                  }
                }
            });
            
            modalInstance.result.then(function (data) {
                if (data === 'failed') {
                    $rootScope.$broadcast('error', 'module.global.error');    
                } else {
                    $rootScope.$broadcast('success', 'module.send.new.message.send.success');
                }
            }, function () {
            });
        };
        
        $rootScope.$broadcast('tags-cloud');
    }]);
    
})();