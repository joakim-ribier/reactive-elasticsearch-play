(function() {
    
    'use strict';
    
    angular.module('login.service').factory(
            'loginService',
            ['$http', '$q', '$rootScope',
            function($http, $q, $rootScope) {
        
        function LoginService() {
            var self = this;
        }
        
        LoginService.prototype.authentication = function(credentials) {
            var defer = $q.defer();
            $http.post($rootScope.hostname + '/page/login/authentication', {credentials : credentials})
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                });
            return defer.promise;
        };
        
        LoginService.prototype.firstConnection = function(credentials) {
            var defer = $q.defer();
            $http.post($rootScope.hostname + '/page/login/firstConnection', {credentials : credentials})
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                });
            return defer.promise;
        };
        
        return new LoginService();
        
    }]);
    
})();