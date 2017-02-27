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

        this._lastURL = '/';
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

    saveLastURL()
    {
        this._lastURL = this.$location.path();
    }

    redirectToHome()
    {
        this.$location.path('/');
    }

    redirectToLogin()
    {
        this.$location.path('/login');
    }

    redirectToAttemptedURL()
    {
        this.$location.path(this.redirectURLAfterLogin.url);
    }

    redirectToLastURL()
    {
        this.$location.path(this._lastURL);
    }

    redirectTo(path)
    {
        this.$location.path(path);
    }

    redirectToUnavailable()
    {
        this.$location.path('/unavailable');
    }
}

segFaultAuthModule.service('RedirectionService', RedirectionService);