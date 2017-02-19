'use strict';

// Declare app level module which depends on views, and components
var segFaultApp = angular.module('segFault', [
    // Utils dependencies
    'ngRoute',
    'routeStyles',              // Custom CSS per route
    'pascalprecht.translate',   // Angular-translate
    'ngCookies',
    'segFault.version',
    // Modules
    'segFault.auth',
    'segFault.question'
    // TO BE COMPLETED HERE
]);

// General controller
segFaultApp.controller('PageCtrl', function PageCtrl($scope, PageService)
{
    $scope.PageService = PageService;
});

// Application configuration
segFaultApp.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider)
{
    $locationProvider.hashPrefix('!');

    $routeProvider.otherwise({redirectTo: '/'});
}]);

// Setup Up target API (Grails API)
segFaultApp.constant('API', 'http://localhost:8080');

// Translation
segFaultApp.config(function($translateProvider)
{
    $translateProvider.useStaticFilesLoader({
        prefix: 'i18n/messages_',
        suffix: '.json'
    });

    $translateProvider.registerAvailableLanguageKeys(['en', 'fr'], {
        'en*': 'en',
        'fr*': 'fr',
        '*': 'en'
    });
    $translateProvider.fallbackLanguage('en');

    $translateProvider.determinePreferredLanguage();

    $translateProvider.useSanitizeValueStrategy('escape');
    $translateProvider.useLocalStorage(); // Saves selected language to localStorage

    $translateProvider.useMissingTranslationHandlerLog();
});