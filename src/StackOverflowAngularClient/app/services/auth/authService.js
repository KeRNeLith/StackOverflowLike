/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth', []);

class AuthService
{
    constructor($window)
    {
    }

    // JWT methods
    //...
}

segFaultAuthModule.service('AuthService', AuthService);