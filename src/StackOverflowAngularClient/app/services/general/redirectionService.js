/**
 * Created by kernelith on 21/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.general');

class RedirectionService
{
    constructor($location, redirectURLAfterLogin)
    {
        this.$location = $location;
        this.redirectURLAfterLogin = redirectURLAfterLogin;
    }

    saveAttemptURL()
    {
        if (this.$location.path().toLowerCase() != '/login')
        {
            this.redirectURLAfterLogin.url = this.$location.path();
        }
        else
        {
            this.redirectURLAfterLogin.url = '/';
        }
    }

    redirectToHome()
    {
        this.$location.path('/');
    }

    redirectToUnavailable()
    {
        this.$location.path('/unavailable');
    }

    redirectToLogin()
    {
        this.$location.path('/login');
    }

    redirectToAttemptedURL()
    {
        this.$location.path(this.redirectURLAfterLogin.url);
    }
}

segFaultAuthModule.service('RedirectionService', RedirectionService);