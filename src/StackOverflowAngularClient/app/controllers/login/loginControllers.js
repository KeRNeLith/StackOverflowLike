/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var loginModule = angular.module('segFault.login');

// Define controllers
class AuthCtrl
{
    constructor(UserService, AuthService)
    {
        this.UserService = UserService;
        this.AuthService = AuthService;
    }

    login()
    {
        this.UserService.login(this.username, this.password);
    }

    register()
    {
        this.UserService.register(this.username, this.password);
    }

    logout()
    {
        this.UserService.logout();
    }

    isAuthenticated()
    {
        return this.AuthService.isAuthenticated();
    }

    currentUser()
    {
        return this.AuthService.currentUser();
    }
}
loginModule.controller('AuthCtrl', AuthCtrl);

// Other controllers
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