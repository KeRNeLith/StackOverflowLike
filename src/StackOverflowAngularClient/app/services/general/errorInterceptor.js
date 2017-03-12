/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultGeneralModule = angular.module('segFault.general');

segFaultGeneralModule.factory('ErrorInterceptor', function ($q, ErrorService, RedirectionService) {
    return {
        request: function (config)
        {
            // Nothing
            return config;
        },

        requestError: function(config)
        {
            // Nothing
            return config;
        },

        response: function (response)
        {
            // Nothing
            return response;
        },

        responseError: function(response)
        {
            // Unavailable
            if (response.status == 503)
            {
                if (response.data.message)
                {
                    ErrorService.setErrors(JSON.parse(response.data.message));
                }

                RedirectionService.redirectToUnavailable();
            }
            // Method not allowed => should never arrive but...
            else if (response.status == 405)
            {
                ErrorService.setErrors(['error.service.notAllowed.method']);

                RedirectionService.redirectToError();
            }
            else
            {
                ErrorService.setErrors(['error.service.unknown.error']);

                RedirectionService.redirectToError();
            }

            return response;
        }
    }
});

segFaultGeneralModule.config(function($httpProvider)
{
    $httpProvider.interceptors.push('ErrorInterceptor');
});