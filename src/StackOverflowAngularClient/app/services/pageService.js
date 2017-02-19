/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultApp = angular.module('segFault');

class PageService
{
    constructor()
    {
        this._default = 'Segfault';
        this._title = this._default;
    }

    default()
    {
        return this._default;
    }

    title()
    {
        return this._title;
    }

    setTitle(newTitle)
    {
        this._title = newTitle;
    }
}

segFaultApp.service('PageService', PageService);