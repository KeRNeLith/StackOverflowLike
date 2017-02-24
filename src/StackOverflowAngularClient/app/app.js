'use strict';

// Declare app level module which depends on views, and components
var segFaultApp = angular.module('segFault', [
    // Utils dependencies
    'ngRoute',
    'pascalprecht.translate',   // Angular-translate
    'ngCookies',
    'segFault.version',
    // Modules
    'segFault.auth',
    'segFault.post',
    'segFault.login',
    'segFault.question',
    'segFault.answer',
    'segFault.user'
    // TO BE COMPLETED HERE
]);

// General controller for pages
segFaultApp.controller('PageCtrl', function PageCtrl($scope, PageService)
{
    $scope.PageService = PageService;
});

// Application routes configuration
segFaultApp.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider)
{
    $locationProvider.hashPrefix('!');

    $routeProvider.otherwise({redirectTo: '/'});
}]);

// Setup Up target API (Grails API)
segFaultApp.constant('API', 'http://localhost:8080');

// Setup redirect URL after login
segFaultApp.value('redirectURLAfterLogin', { url: '/'});

// Check route changes
segFaultApp.run(function ($location, $rootScope, AuthService, RedirectionService)
{
    $rootScope.$on('$routeChangeStart', function (event, nextRoute, previousRoute)
    {
        // If login required and user is not logged, capture the current path
        if (nextRoute.loginRequired && !AuthService.isAuthenticated())
        {
            RedirectionService.saveAttemptURL();
            RedirectionService.redirectToLogin();
        }
    });
});

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
