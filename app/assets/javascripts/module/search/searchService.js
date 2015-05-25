(function() {
    
    'use strict';
    
    angular.module('search.service').factory(
            'searchService',
            ['$http', '$q', '$rootScope', '$log',
             function($http, $q, $rootScope, $log) {
        
        function SearchService() {
            var self = this;
        }
        
        SearchService.prototype.getTags = function() {
            $log.debug('SearchService.prototype.getTags');
            var defer = $q.defer();
            $http.get($rootScope.hostname + '/search/tags/1000')
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                }
            );
            return defer.promise;
        };
        
        SearchService.prototype.search = function(value) {
            var defer = $q.defer();
            $http.get($rootScope.hostname + '/search/by/query/' + value)
                .success(function(data) {
                    defer.resolve(data);
                })
                .error(function(data) {
                    defer.reject(data);
                }
            );
            return defer.promise;
        };
        
        return new SearchService();
        
    }]);
    
})();