'use strict';

var answerModule = angular.module('segFault.answer', ['ngRoute']);

// Define available routes
answerModule.config(['$routeProvider', function($routeProvider)
{
  // Redact page
  $routeProvider.when('/answer/redact', {
      templateUrl: 'modules/answer/redact.html',
      controller: 'RedactAnswerCtrl',
      loginRequired: true
  });

  // Edit page
  $routeProvider.when('/answer/edit/:id', {
      templateUrl: 'modules/answer/edit.html',
      controller: 'EditAnswerCtrl',
      loginRequired: true
  });
}]);