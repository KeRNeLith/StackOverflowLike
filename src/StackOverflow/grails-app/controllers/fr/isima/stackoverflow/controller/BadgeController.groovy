package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Badge
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class BadgeController
{
    def scaffold = Badge

    //def index() { }
}
