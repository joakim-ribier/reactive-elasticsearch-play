(function() {
    
    'use strict';
    
    angular.module('App').controller(
            'RootController',
            ['$scope', '$window', '$rootScope',
            function ($scope, $window, $rootScope) {
        
        $rootScope.hostname = $window.hostName;
        
    }]);
    
})();