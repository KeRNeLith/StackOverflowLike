package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.UserBadges
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class UserBadgesController
{
    def scaffold = UserBadges

    //def index() { }
}
