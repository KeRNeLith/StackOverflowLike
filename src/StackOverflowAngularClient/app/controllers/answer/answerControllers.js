/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var answerModule = angular.module('segFault.answer');

// Define controllers
answerModule.controller('RedactAnswerCtrl', function($translate, PageService)
{
    $translate('answer.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});
