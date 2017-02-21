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

loginModule.controller('AuthCtrl', function ($scope, UserService, AuthService) {
    let self = this;

    self.login = function()
    {
        UserService .login(self.username, self.password)
                    .then(function successCallback(response)
                    {
                        if (response.status >= 400)
                        {
                            self.resetForm($scope.loginForm);
                            
                            // Display error
                            $scope.error = 'error.login.signIn.fails';
                        }
                        else
                        {
                            // Go back to previous URL before needs to authenticate
                            // ... TODO
                        }
                    },
                        function errorCallback(response)
                        {
                            $scope.error = 'error.login.signIn.impossible';
                        });
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

    self.resetForm = function(form)
    {
        if (form)
        {
            // Each control (input, select, textarea, etc) gets added as a property of the form.
            // The form has other built-in properties as well. However it's easy to filter those out,
            // because the Angular team has chosen to prefix each one with a dollar sign.
            // So, we just avoid those properties that begin with a dollar sign.
            let controlNames = Object.keys(form).filter(key => key.indexOf('$') !== 0);

            // Set each control back to undefined. This is the only way to clear validation messages.
            // Calling `form.$setPristine()` won't do it (even though you wish it would).
            for (let name of controlNames)
            {
                let control = form[name];
                control.$setViewValue(undefined);
                control.$render();  // Force render
            }

            // Reset form
            form.$setPristine();
            form.$setUntouched();
        }
    };
});