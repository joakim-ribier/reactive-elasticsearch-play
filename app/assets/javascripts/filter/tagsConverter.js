(function() {
    
    'use strict';
    
    angular.module('global.filter').filter(
            'tagsConverter',
            [ function () {
                
        return function (value) {
            if (value) {
                return value.join(', ');
            }
            return "";
        };
    }]);
})();