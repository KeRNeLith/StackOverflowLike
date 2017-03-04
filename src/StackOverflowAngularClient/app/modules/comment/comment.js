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

  // Edit page
  $routeProvider.when('/comment/edit/:id', {
      templateUrl: 'modules/comment/edit.html',
      controller: 'EditCommentCtrl',
      loginRequired: true
  });
}]);