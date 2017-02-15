'use strict';

// Declare app level module which depends on views, and components
angular.module('segFault', [
  'ngRoute',
  'segFault.question',
  // TO BE COMPLETED HERE
  'segFault.version'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider)
{
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/'});
}]);
