package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Post
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class PostController
{
    def scaffold = Post

    //def index() { }
}
