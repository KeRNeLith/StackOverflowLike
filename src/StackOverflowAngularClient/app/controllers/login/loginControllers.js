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
                            resetForm($scope.registerForm);

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
});
