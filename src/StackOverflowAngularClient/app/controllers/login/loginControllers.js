/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var loginModule = angular.module('segFault.login');

// Define controllers
loginModule.controller('LoginCtrl', function($scope, PageService)
{
    PageService.setTitle('Login - ' + PageService.default());

    // TODO
});

loginModule.controller('RegisterCtrl', function($scope, PageService)
{
    PageService.setTitle('Register - ' + PageService.default());

    // TODO
});

loginModule.controller('AuthCtrl', function (UserService, AuthService) {
    let self = this;

    self.login = function()
    {
        UserService.login(self.username, self.password);
    };

    self.register = function()
    {
        UserService.register(self.username, self.password);
    };

    self.logout = function()
    {
        UserService.logout();
    };

    self.isAuthenticated = function()
    {
        return AuthService.isAuthenticated();
    };

    self.currentUser = function()
    {
        return AuthService.currentUser();
    };
});