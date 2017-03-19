/**
 * Created by kernelith on 21/02/17.
 */

'use strict';

var loginModule = angular.module('segFault.login');

// Directives
loginModule.directive('sfUsername', function ($q, $http, API) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl)
        {
            ctrl.$asyncValidators.username = function(modelValue, viewValue) {

                if (ctrl.$isEmpty(modelValue))
                {
                    // consider empty model valid
                    return $q.resolve();
                }

                let def = $q.defer();

                $http.get(API + '/api/user/available?username=' + modelValue)
                    .then(function successCallback(response)
                        {
                            // Username available
                            if (response.status < 400)
                            {
                                def.resolve();
                            }
                            // Username not available
                            else
                            {
                                def.reject();
                            }
                        },
                        function errorCallback(response)
                        {
                            // Error => default is to say not available
                            def.reject();
                        });

                return def.promise;
            };
        }
    };
});
