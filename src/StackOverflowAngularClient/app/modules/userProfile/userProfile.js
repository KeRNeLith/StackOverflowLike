'use strict';

var userModule = angular.module('segFault.userProfile', ['ngRoute', 'routeStyles'/* Custom CSS per route */ ]);

// Define available routes
userModule.config(['$routeProvider', function($routeProvider)
{
  // userProfile page
  $routeProvider.when('/profile', {
      templateUrl: 'modules/userProfile/profile.html',
      controller: 'ProfileCtrl',
      css: ['assets/stylesheets/profile/font-awesome.css','assets/stylesheets/profile/custom.css']
  });
  $routeProvider.when('/profile/dashboard', {
      templateUrl: 'modules/userProfile/profile.html',
      controller: 'ProfileCtrl',
      css: ['assets/stylesheets/profile/font-awesome.css','assets/stylesheets/profile/custom.css']
  });
}]);
