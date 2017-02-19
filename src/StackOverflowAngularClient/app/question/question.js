'use strict';

var questionModule = angular.module('segFault.question', ['ngRoute']);

// Define available routes
questionModule.config(['$routeProvider', function($routeProvider)
{
  // Home page
  $routeProvider.when('/', {
    templateUrl: 'question/home.html',
    controller: 'HomeCtrl'
  });
  $routeProvider.when('/question/home', {
      templateUrl: 'question/home.html',
      controller: 'HomeCtrl'
  });

  // Display question
  $routeProvider.when('/question/display/:id', {
      templateUrl: 'question/display.html',
      controller: 'QuestionDisplayCtrl',
      css: 'assets/stylesheets/question/display.css'
  });
}]);