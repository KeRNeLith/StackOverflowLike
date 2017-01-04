package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Vote
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class VoteController
{
    def scaffold = Vote

    //def index() { }
}
