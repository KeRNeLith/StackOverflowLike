package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Tag
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class TagController
{
    def scaffold = Tag

    //def index() { }
}
