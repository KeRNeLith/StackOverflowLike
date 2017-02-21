'use strict';

var questionModule = angular.module('segFault.question', ['ngRoute', 'routeStyles'/* Custom CSS per route */ ]);

// Define available routes
questionModule.config(['$routeProvider', function($routeProvider)
{
  // Home page
  $routeProvider.when('/', {
      templateUrl: 'modules/question/home.html',
      controller: 'HomeCtrl'
  });
  $routeProvider.when('/question/home', {
      templateUrl: 'modules/question/home.html',
      controller: 'HomeCtrl'
  });

  // Display question
  $routeProvider.when('/question/display/:id', {
      templateUrl: 'modules/question/display.html',
      controller: 'QuestionDisplayCtrl',
      css: 'assets/stylesheets/question/display.css'
  });
}]);