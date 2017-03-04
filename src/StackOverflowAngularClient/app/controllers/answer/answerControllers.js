/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var answerModule = angular.module('segFault.answer');

// Define controllers
answerModule.controller('RedactAnswerCtrl', function($translate, PageService)
{
    $translate('answer.redact.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});

answerModule.controller('EditAnswerCtrl', function($scope, $http, $routeParams, $translate, API, PageService)
{
    $translate('answer.edit.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });

    $http.get(API + '/api/answer/redactEdit/' + $routeParams.id)
        .then(function(response)
        {
            let data = response.data;

            $scope.message = data.message;
        });
});
