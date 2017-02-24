'use strict';

var answerModule = angular.module('segFault.answer', ['ngRoute']);

// Define available routes
answerModule.config(['$routeProvider', function($routeProvider)
{
  // Register page
  $routeProvider.when('/answer/redact', {
      templateUrl: 'modules/answer/redact.html',
      controller: 'RedactAnswerCtrl',
      loginRequired: true
  });
}]);