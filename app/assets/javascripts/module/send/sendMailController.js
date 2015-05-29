(function(){
    
    'use strict';
    
    angular.module('App').controller(
            'SendController', 
            ['$scope', '$modalInstance', 'data', '$log', 'sendMailService', '$filter', '$translate',
            function ($scope, $modalInstance, data, $log, sendMailService, $filter, $translate) {
         
        $scope.data = {
            result: data,
            mail : {
                id: data.id,
                to: '',
                bcc: '',
                bccEnable: false,
                subject: '',
                body: ''
            },
            error: ''
        };
        
        $scope.send = function (mail) {
            $scope.form.$submitted = true;
            if ($scope.form.$valid) {
                $scope.loading = true;
                sendMailService.send(mail).then(
                    function(data) {
                        $scope.loading = false;
                        $modalInstance.close('success');
                    }, 
                    function (data) {
                        $scope.loading = false;
                        $scope.data.error = 'module.global.error';
                    }
                );
            } else {
                $scope.data.error = 'module.send.new.message.error';
            }
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
        
        function get() {
            sendMailService.get().then(
                function(data) {
                    var attachment = $filter('translate')('module.send.new.message.footer.attachment.text');
                    attachment += ' ' + $scope.data.result.fileName; 
                    $scope.data.mail.bcc = data.bcc;
                    $scope.data.mail.to = data.to;
                    $scope.data.mail.bccEnable = data.bccEnable;
                    $scope.data.mail.body = '\n--\n' + attachment + '\n\n'+ $filter('translate')('module.send.new.message.footer.text');
                }, 
                function (data) {
                    $log.error(data);
                }
            );
        };
        
        get();
        
    }]);
    
})();