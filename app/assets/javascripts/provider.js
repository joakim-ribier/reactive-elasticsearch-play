(function () {
    'use strict';
    var app = angular.module('App');
    
    app.config(['$translateProvider', function ($translateProvider) {
        $translateProvider.preferredLanguage('en');
        $translateProvider.useUrlLoader('/i18n/get');
    }]);
    
})();
