package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class VoteController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def voteService;
    def springSecurityService

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

    protected boolean hasParams(Map params, String... paramsName)
    {
        boolean ret = true;

        for (param in paramsName)
        {
            if (!params.containsKey(param))
            {
                ret = false;
                break;
            }
        }

        return ret;
    }

    @Secured('ROLE_USER')
    def performVote()
    {
        if (hasParams(params, 'post', 'vote'))
        {
            def postId = params.long('post')
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null
            def voteState = Vote.Value.valueOf((params.vote as String).toUpperCase())

            Post post = Post.get(postId)
            if (post != null)
            {
                voteService.updateVotes(post, user, voteState)

                def questionId = postId
                if (post instanceof Answer)
                {
                    Answer a = post as Answer
                    questionId = a.question.id
                }

                redirect(controller: 'question', action: 'show', id: questionId)
            }
            else
            {
                notFound()
            }
        }
        else
        {
            redirect(controller: 'question', action: 'list')
        }
    }
}
