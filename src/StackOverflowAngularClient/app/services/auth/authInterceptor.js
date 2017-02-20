/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth');

segFaultAuthModule.factory('AuthInterceptor', function ($window, API, AuthService) {
    return {
        // Automatically attach Authorization header
        request: function (config)
        {
            let token = AuthService.getToken();
            if(config.url.indexOf(API) === 0 && token)
            {
                config.headers.Authorization = 'Bearer ' + token;
            }

            return config;
        },

        requestError: function(config)
        {
            // Nothing
            return config;
        },

        // If a token was sent back => save it
        response: function (response)
        {
            if (response.config.url.indexOf(API) === 0)
            {
                AuthService.handleRequest(response);
            }

            return response;
        },

        // Automatically ask to login when getting unauthorized
        responseError: function(response)
        {
            // Unauthorized => Redirect to login
            if (response.status == 401)
            {
                $window.location.href = '/login'
            }

            return response;
        }
    }
});

segFaultAuthModule.config(function($httpProvider)
{
    $httpProvider.interceptors.push('AuthInterceptor');
});