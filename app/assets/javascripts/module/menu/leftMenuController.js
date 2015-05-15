(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'LeftMenuController', 
            ['$scope', '$log', '$http', '$window', '$location', '$translate', '$filter',
             '$rootScope', 'leftMenuService', 'indexationService', 
            function ($scope, $log, $http, $window, $location, $translate, $filter,
                      $rootScope, leftMenuService, indexationService) {
          
        $scope.data = {
            number: 0,
            uploadForm: {
                file: undefined
            }
        };
        
        $scope.upload = function (uploadForm) {
            $scope.errorMessage = '';
            $scope.infoMessage = '';
            
            $scope.form.$submitted = true;
            if ($scope.form.$valid) {
                $rootScope.loading = true;
                var file = uploadForm.file;
                leftMenuService.upload(file).then(
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
            } else {
                clearUploadForm();
                $rootScope.$broadcast('success', 'module.upload.select.error');
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
            $scope.data.uploadForm.file = undefined;
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