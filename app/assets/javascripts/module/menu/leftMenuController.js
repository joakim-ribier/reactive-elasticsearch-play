(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'LeftMenuController', 
            ['$scope', '$log', '$http', '$window', '$location', '$translate', '$filter', '$rootScope', 'leftMenuService',
            function ($scope, $log, $http, $window, $location, $translate, $filter, $rootScope, leftMenuService) {
          
        $scope.uploadForm = {
            file: undefined
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
                        $rootScope.loading = false;
                        $scope.uploadForm.file = undefined;
                        
                        $(":file").filestyle('clear');
                        
                        $scope.infoMessage = $filter('translate')('module.upload.file.success');
                    }, 
                    function (data) {
                        $(":file").filestyle('clear');
                        $rootScope.loading = false;
                        
                        $scope.errorMessage =  $filter('translate')(data.key);
                    }
                );
            } else {
                $(":file").filestyle('clear');
                
                $scope.errorMessage =  $filter('translate')('module.upload.select.error');
            }
        };
        
        $scope.setFile = function (element) {
            $scope.errorMessage = '';
            $scope.uploadForm.file = element.files[0];
            $scope.$apply();
        };
        
    }]);
    
})();