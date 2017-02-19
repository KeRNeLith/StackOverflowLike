/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth');

segFaultAuthModule.factory('AuthInterceptor', function (API, AuthService) {
    return {
        // Automatically attach Authorization header
        request: function (config)
        {
            return config;
        },

        // If a token was sent back => save it
        reponse: function (res)
        {
            return res;
        }
    }
});

segFaultAuthModule.config(function($httpProvider)
{
    $httpProvider.interceptors.push('AuthInterceptor');
});