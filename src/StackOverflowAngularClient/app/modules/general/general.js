/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultGeneralModule = angular.module('segFault.general', ['ngRoute']);

// Define available routes
segFaultGeneralModule.config(['$routeProvider', function($routeProvider)
{
    // Unavailable Page
    $routeProvider.when('/unavailable', {
        templateUrl: 'modules/general/unavailable.html',
        controller: 'UnavailableCtrl'
    });
}]);