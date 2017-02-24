/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultGeneralModule = angular.module('segFault.general');

class ErrorService
{
    constructor()
    {
        this._errors = [];
    }

    errors()
    {
        return this._errors;
    }

    setErrors(errors)
    {
        this._errors = [];

        if (!angular.isArray(errors))
        {
            this._errors.push(errors);
        }
        else
        {
            this._errors = errors;
        }
    }
}

segFaultGeneralModule.service('ErrorService', ErrorService);