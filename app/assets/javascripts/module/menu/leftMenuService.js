(function() {
    
    'use strict';
    
    angular.module('menu.service').factory(
            'leftMenuService',
            ['$http', '$q', '$rootScope',
            function($http, $q, $rootScope) {
        
        function LeftMenuService() {
            var self = this;
        }
        
        LeftMenuService.prototype.upload = function(file) {
            var defer = $q.defer();
            var formData = new FormData();
            formData.append('file', file);
            formData.append('name', file.name);
            $http.post($rootScope.hostname + '/module/upload/file', formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(data) {
                defer.resolve(data);
            })
            .error(function(data) {
                defer.reject(data);
            });
            return defer.promise;
        };
        
        return new LeftMenuService();
        
    }]);
    
})();