(function() {
    
    'use strict';
    
    var app = angular.module('App', []);
    
    angular.module('login.service', []);
    angular.module('menu.service', []);
    angular.module('indexation.service', []);
    
    angular.module('App',
            ['pascalprecht.translate',
             'login.service', 'menu.service', 'indexation.service']);
    
})();