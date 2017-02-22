/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var loginModule = angular.module('segFault.login');

// Define controllers
loginModule.controller('LoginCtrl', function($scope, $translate, PageService)
{
    $translate('login.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});

loginModule.controller('RegisterCtrl', function($scope, $translate, PageService)
{
    $translate('register.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});

loginModule.controller('AuthCtrl', function ($scope, $timeout, UserService, AuthService, RedirectionService) {
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
                            $scope.errorMessage = 'error.login.signIn.fails';
                        }
                        else
                        {
                            // Go back to previous URL before needs to authenticate
                            RedirectionService.redirectToAttemptedURL();
                        }
                    },
                        function errorCallback(response)
                        {
                            $scope.errorMessage = 'error.login.signIn.impossible';
                        });
    };

    self.register = function()
    {
        UserService .register(self.username, self.password)
                    .then(function successCallback(response)
                    {
                        if (response.status >= 400)
                        {
                            self.resetForm($scope.registerForm);

                            // Display errors
                            if (response.data.message)
                            {
                                let errors = JSON.parse(response.data.message);

                                let usernameError = null;
                                let passwordError = null;
                                let otherErrors = [];

                                // Check error's type
                                angular.forEach(errors, function (errorCode) {
                                    if (errorCode.search('username') != -1)
                                    {
                                        usernameError = errorCode;
                                    }
                                    else if (errorCode.search('password') != -1)
                                    {
                                        passwordError = errorCode;
                                    }
                                    else
                                    {
                                        otherErrors.push(errorCode);
                                    }
                                });

                                $scope.usernameError = usernameError;
                                $scope.passwordError = passwordError;
                                $scope.otherErrors = otherErrors;
                            }
                        }
                        else
                        {
                            $scope.successMessage = 'success.register.user';

                            // Timeout before redirecting
                            // (require bind to set method context execution to the one of RedirectionService)
                            $timeout(RedirectionService.redirectToHome.bind(RedirectionService), 3500);
                        }
                    },
                        function errorCallback(response)
                        {
                            $scope.errorMessage = 'error.register.signUp.impossible';
                        });
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
