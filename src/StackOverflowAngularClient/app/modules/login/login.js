'use strict';

var loginModule = angular.module('segFault.login', ['ngRoute', 'routeStyles'/* Custom CSS per route */ ]);

// Define available routes
loginModule.config(['$routeProvider', function($routeProvider)
{
  // Login Page
  $routeProvider.when('/login', {
    templateUrl: 'modules/login/login.html',
    controller: 'LoginCtrl',
      css: 'assets/stylesheets/user/registration.css'
  });

  // Register page
  $routeProvider.when('/register', {
      templateUrl: 'modules/login/register.html',
      controller: 'RegisterCtrl',
      css: 'assets/stylesheets/user/registration.css'
  });
}]);