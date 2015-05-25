(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'LeftMenuController', 
            ['$scope', '$log', '$http', '$window', '$location', 
             '$rootScope', 'leftMenuService', 'indexationService', 
            function ($scope, $log, $http, $window, $location,
                      $rootScope, leftMenuService, indexationService) {
          
        $scope.data = {
            number: 0,
            uploadForm: {
                file: undefined,
                tags: ''
            }
        };
        
        $scope.upload = function (uploadForm) {
            $scope.form.$submitted = true;
            if ($scope.form.$valid) {
                $rootScope.loading = true;
                
                if (uploadForm.tags) {
                    leftMenuService.uploadAndIndexing(uploadForm).then(
                        function(data){
                            clearUploadForm();
                            $rootScope.loading = false;
                            
                            $rootScope.$broadcast('success', 'module.uploadAndIndexing.file.success');
                            $rootScope.$broadcast('tags-cloud');
                            displayNumberOfFilesWaitingToBeIndexed();
                        }, 
                        function (data) {
                            clearUploadForm();
                            $rootScope.loading = false;
                            $rootScope.$broadcast('error', data.key);
                        }
                    );
                } else {
                    leftMenuService.upload(uploadForm).then(
                        function(data){
                            clearUploadForm();
                            $rootScope.loading = false;
                            
                            $rootScope.$broadcast('success', 'module.upload.file.success');
                            displayNumberOfFilesWaitingToBeIndexed();
                        }, 
                        function (data) {
                            clearUploadForm();
                            $rootScope.loading = false;
                            $rootScope.$broadcast('error', data.key);
                        }
                    );
                }
            } else {
                clearUploadForm();
                $rootScope.$broadcast('warning', 'module.upload.select.error');
            }
        };
        
        $scope.indexing = function () {
            if ($('#left-menu-indexing-button.disabled')[0]){
                return;
            } else {
                $('#left-menu-indexing-button').addClass("disabled");
                $rootScope.loading = true;
                indexationService.indexing().then(
                    function(data) {
                        $rootScope.loading = false;
                        
                        $('#left-menu-indexing-button').removeClass("disabled");
                        displayNumberOfFilesWaitingToBeIndexed();
                        
                        $rootScope.$broadcast('success', 'module.left.menu.edm.start.success');
                    }, 
                    function (data) {
                        $log.error(data);
                        $rootScope.loading = false;
                    }
                );
            }
        };
        
        $scope.setFile = function (element) {
            $scope.errorMessage = '';
            $scope.data.uploadForm.file = element.files[0];
            $scope.$apply();
        };
        
        function clearUploadForm() {
            $(":file").filestyle('clear');
            $scope.data.uploadForm = {
                file: undefined,
                tags: ''
            };
        };
        
        function displayNumberOfFilesWaitingToBeIndexed() {
            indexationService.getNumber().then(
                function(data) {
                    $scope.data.number = data;
                }, 
                function (data) {
                    $scope.data.number = -1;
                }
            );
        };
        
        displayNumberOfFilesWaitingToBeIndexed();
    }]);
    
})();