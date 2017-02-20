'use strict';

var loginModule = angular.module('segFault.login', ['ngRoute']);

// Define available routes
loginModule.config(['$routeProvider', function($routeProvider)
{
  // Login Page
  $routeProvider.when('/login', {
    templateUrl: 'modules/login/login.html',
    controller: 'LoginCtrl'
  });

  // Register page
  $routeProvider.when('/register', {
      templateUrl: 'modules/login/register.html',
      controller: 'RegisterCtrl'
  });
}]);