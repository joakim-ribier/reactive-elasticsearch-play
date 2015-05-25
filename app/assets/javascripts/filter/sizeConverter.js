(function() {
    
    'use strict';
    
    angular.module('global.filter').filter(
            'sizeConverter',
            ['$translate', '$filter', function ($translate, $filter) {
                
        return function (value) {
            var sizes = ['bytes', 'kb', 'mb', 'gb', 'tb'];
            var index = 0;
            
            if (value == 0 || value == null){
                return "";
            }
            
            if (value < 1024) {
                return Number(value) + " " + $filter('translate')(sizes[index]);
            }
            
            while(value >= 1024) {
                index++;
                value = value / 1024;
            }
            
            var power = Math.pow(10, 2);
            var poweredVal = Math.ceil(value * power);
            
            value = poweredVal / power;
            
            return value + " " + $filter('translate')(sizes[index]);
        };
    }]);
})();