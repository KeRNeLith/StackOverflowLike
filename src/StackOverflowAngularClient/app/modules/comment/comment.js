'use strict';

var commentModule = angular.module('segFault.comment', ['ngRoute']);

// Define available routes
commentModule.config(['$routeProvider', function($routeProvider)
{
  // Redact page
  $routeProvider.when('/comment/redact', {
      templateUrl: 'modules/comment/redact.html',
      controller: 'RedactCommentCtrl',
      loginRequired: true
  });
}]);