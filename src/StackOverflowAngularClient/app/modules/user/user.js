'use strict';

var userModule = angular.module('segFault.user', ['ngRoute', 'routeStyles'/* Custom CSS per route */ ]);

// Define available routes
userModule.config(['$routeProvider', function($routeProvider)
{
    // User Profile page
    $routeProvider.when('/user/profile/:username', {
        templateUrl: 'modules/user/profile.html',
        controller: 'ProfileCtrl',
        css: [ 'assets/stylesheets/profile/font-awesome.css', 'assets/stylesheets/profile/custom.css', 'assets/stylesheets/profile/badges.css' ]
    });

    // User profile Edit page
    $routeProvider.when('/user/profile/:username/edit', {
        templateUrl: 'modules/user/editProfile.html',
        controller: 'EditProfileCtrl',
        loginRequired: true
    });
}]);
