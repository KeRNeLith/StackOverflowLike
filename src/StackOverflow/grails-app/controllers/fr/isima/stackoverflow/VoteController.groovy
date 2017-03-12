package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class VoteController
{
    static responseFormats = ['json']
    static allowedMethods = [performVote: "PUT",
                             // Default grails methods
                             save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def voteService
    def springSecurityService
    def featuresFlippingService

    // Actions
    protected boolean hasParams(Map params, String... paramsName)
    {
        boolean ret = true

        for (param in paramsName)
        {
            if (!params.containsKey(param))
            {
                ret = false
                break
            }
        }

        return ret
    }

    @Secured('ROLE_USER')
    def performVote()
    {
        if (!featuresFlippingService.isVotesEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.vote"'
            return
        }

        def status = BAD_REQUEST
        String retCode = '"error.vote.perform.vote.wrong.parameters"'

        def inputRequest = request.JSON
        if (hasParams(inputRequest, 'post', 'vote'))
        {
            def postId = -1
            if (inputRequest.post instanceof String)
            {
                postId = Long.parseLong(inputRequest.post)
            }
            else
            {
                postId = inputRequest.post
            }

            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null
            def voteState = inputRequest.vote.equalsIgnoreCase('UP') ? Vote.Value.UP : Vote.Value.DOWN

            Post post = Post.get(postId)
            if (post != null)
            {
                int ret = voteService.updateVotes(post, user, voteState)

                if (Math.abs(ret) == 1)
                    status = CREATED
                else if (ret == 2)
                    status = OK
                else
                    status = NOT_MODIFIED

                retCode = '"success.vote.perform.vote"'
            }
            else
            {
                status = NOT_FOUND
                retCode = '"error.vote.perform.vote.post.notFound"'
            }
        }

        render status: status, message: retCode
    }

    // Default Grails routes
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Vote.list(params), model:[voteCount: Vote.count()]
    }

    def show(Vote vote)
    {
        respond vote
    }

    def create()
    {
        respond new Vote(params)
    }

    @Transactional
    def save(Vote vote)
    {
        if (vote == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vote.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond vote.errors, view:'create'
            return
        }

        vote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vote.label', default: 'Vote'), vote.id])
                redirect vote
            }
            '*' { respond vote, [status: CREATED] }
        }
    }

    def edit(Vote vote)
    {
        respond vote
    }

    @Transactional
    def update(Vote vote)
    {
        if (vote == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vote.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond vote.errors, view:'edit'
            return
        }

        vote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vote.label', default: 'Vote'), vote.id])
                redirect vote
            }
            '*'{ respond vote, [status: OK] }
        }
    }

    @Transactional
    def delete(Vote vote)
    {

        if (vote == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vote.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vote.label', default: 'Vote'), vote.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vote.label', default: 'Vote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
