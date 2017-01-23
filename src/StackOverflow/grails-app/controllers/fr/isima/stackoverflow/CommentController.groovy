package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class CommentController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def answerService

    // Actions
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentCount: Comment.count()]
    }

    def show(Comment comment)
    {
        respond comment
    }

    def create()
    {
        respond new Comment(params)
    }

    @Secured('ROLE_USER')
    def redact()
    {
        if (params.containsKey('answer'))
        {
            def answerId = params.long('answer')
            if (Answer.exists(answerId))
            {
                respond new Comment(params), view: 'redact', model: [commentTo: answerId]
            }
            else
            {
                notFound()
            }
        }
        else
        {
            notFound()
        }
    }

    @Secured('ROLE_USER')
    def redactEdit(Comment comment)
    {
        respond comment, view: 'redactEdit'
    }

    @Transactional
    def save(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'create'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*' { respond comment, [status: CREATED] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def addComment(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        // Validate message constraint
        if (!comment.validate(['message']))
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'redact'
            return
        }

        // Add comment
        if (params.containsKey('answer'))
        {
            def answerId = params.long('answer')
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            if (answerService.addCommentToAnswer(comment.message, user, answerId))
            {
                Answer a = Answer.read(answerId)

                redirect(controller: 'question', action: 'display', id: a.question.id)
            }
            else
            {
                notFound()
            }
        }
        else
        {
            notFound()
        }
    }

    def edit(Comment comment)
    {
        respond comment
    }

    @Transactional
    def update(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'edit'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*'{ respond comment, [status: OK] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateEdit(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'redactEdit'
            return
        }

        comment.save flush:true

        redirect(controller: 'question', action: 'display', id: comment.answer.question.id)
    }

    @Transactional
    def delete(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
