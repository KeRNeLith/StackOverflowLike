/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultGeneralModule = angular.module('segFault.general');

// Define controllers
// General controller for pages
segFaultGeneralModule.controller('PageCtrl', function PageCtrl($scope, PageService)
{
    $scope.PageService = PageService;
});

segFaultGeneralModule.controller('UnavailableCtrl', function($scope, $translate, ErrorService, PageService)
{
    $translate('error.service.unavailable.title').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });

    $scope.messages = ErrorService.errors();
});