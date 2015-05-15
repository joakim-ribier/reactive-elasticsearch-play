(function() {
    
    'use strict';
    
    var app = angular.module('App', []);
    
    angular.module('global.service', []);
    angular.module('login.service', []);
    angular.module('menu.service', []);
    angular.module('indexation.service', []);
    
    angular.module('App',
            ['pascalprecht.translate',
             'global.service', 'login.service', 'menu.service', 'indexation.service']);
    
})();