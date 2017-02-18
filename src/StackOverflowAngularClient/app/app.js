'use strict';

// Declare app level module which depends on views, and components
var segFaultApp = angular.module('segFault', [
    'ngRoute',
    'pascalprecht.translate', // angular-translate
    'ngCookies',
    'segFault.question',
    // TO BE COMPLETED HERE
    'segFault.version'
]);

segFaultApp.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider)
{
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/'});
}]);

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