(function() {
    
    'use strict';
    
    angular.module('indexation.service').factory(
            'indexationService',
            ['$http', '$q', '$rootScope', function($http, $q, $rootScope) {
        
        function IndexationService() {
            var self = this;
        }
        
        IndexationService.prototype.getNumber = function() {
            var defer = $q.defer();
            $http.get($rootScope.hostname + '/index/number-files-waiting-to-be-indexed')
            .success(function(data) {
                defer.resolve(data.number);
            })
            .error(function(data) {
                defer.reject(data);
            });
            return defer.promise;
        };
        
        return new IndexationService();
        
    }]);
    
})();