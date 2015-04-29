(function() {
    
    'use strict';
    
    var app = angular.module('App', []);
    
    angular.module('login.service', []);
    
    angular.module('App', ['pascalprecht.translate', 'login.service']);
    
})();