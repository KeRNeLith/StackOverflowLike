'use strict';

var userModule = angular.module('segFault.user', ['ngRoute', 'routeStyles'/* Custom CSS per route */ ]);

// Define available routes
userModule.config(['$routeProvider', function($routeProvider)
{
  // User Profile page
  $routeProvider.when('/profile', {
      templateUrl: 'modules/user/profile.html',
      controller: 'ProfileCtrl',
      css: [ 'assets/stylesheets/profile/font-awesome.css', 'assets/stylesheets/profile/custom.css' ]
  });

  $routeProvider.when('/profile/dashboard', {
      templateUrl: 'modules/user/profile.html',
      controller: 'ProfileCtrl',
      css: [ 'assets/stylesheets/profile/font-awesome.css', 'assets/stylesheets/profile/custom.css' ]
  });
}]);
