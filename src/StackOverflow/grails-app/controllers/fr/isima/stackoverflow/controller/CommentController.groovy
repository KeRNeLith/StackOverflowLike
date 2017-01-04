package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Comment
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class CommentController
{
    def scaffold = Comment

    //def index() { }
}
