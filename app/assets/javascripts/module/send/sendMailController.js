(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'SendController', 
            ['$scope', '$modalInstance', 'data', '$log', 'sendMailService', 
            function ($scope, $modalInstance, data, $log, sendMailService) {
         
        $scope.data = {
            result: data,
            mail : {
                id: data.id,
                to: '',
                subject: '',
                body: ''
            },
            error: ''
        };
        
        $scope.send = function (mail) {
            $scope.form.$submitted = true;
            if ($scope.form.$valid) {
                sendMailService.send(mail).then(
                    function(data){
                        $modalInstance.close('success');
                    }, 
                    function (data) {
                        $modalInstance.close('failed');
                    }
                );
            } else {
                $scope.data.error = 'module.send.new.message.error';
            }
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
        
    }]);
    
})();