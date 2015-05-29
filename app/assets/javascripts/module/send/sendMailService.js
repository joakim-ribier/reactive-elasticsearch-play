(function() {
    
    'use strict';
    
    angular.module('sendmail.service').factory(
            'sendMailService',
            ['$http', '$q', '$rootScope', '$log', 
            function($http, $q, $rootScope, $log) {
        
        function SendMailService() {
            var self = this;
        }
        
        SendMailService.prototype.send = function(mail) {
            $log.debug('SendService.prototype.send');
            $log.debug(mail);
            var defer = $q.defer();
            $http.post($rootScope.hostname + '/module/send/mail', {mail : mail})
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                });
            return defer.promise;
        };
        
        SendMailService.prototype.get = function() {
            $log.debug('SendService.prototype.get');
            var defer = $q.defer();
            $http.get($rootScope.hostname + '/module/send/mail/get')
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                });
            return defer.promise;
        };
        
        return new SendMailService();
        
    }]);
    
})();