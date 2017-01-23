package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class QuestionController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def questionService

    // Actions
    @Secured('ROLE_USER')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Question.list(params), model:[questionCount: Question.count()]
    }

    def show(Question question)
    {
        respond question
    }

    @Secured('ROLE_USER')
    def redact()
    {
        respond new Question(params), view: 'redact', model: [tags: TagValue.findAll()]
    }

    @Secured('ROLE_ANONYMOUS')
    def display(Question question)
    {
        def questionVotes = questionService.voteCount(question.votes)
        // Sort answers by votes and posting date
        def sortedAnswers = questionService.sortAnswersByVotes(question)

        def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

        respond question, model: [questionVotes: questionVotes, sortedAnswers: sortedAnswers, currentUser: user]
    }

    @Secured('ROLE_USER')
    def create()
    {
        respond new Question(params)
    }

    @Transactional
    def save(Question question)
    {
        if (question == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (question.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'create'
            return
        }

        question.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*' { respond question, [status: CREATED] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def addQuestion(Question question)
    {
        println params
        if (question == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        // Validate message constraint
        if (!question.validate(['title', 'message']))
        {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'redact'
            return
        }

        // Add optional tags
        def tags = null
        if (params.containsKey('tags'))
        {
            tags = params.list('tags');
        }

        def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

        int questionId = questionService.createQuestion(question.title, question.message, user, tags)
        if (questionId > 0)
        {
            redirect(action: 'display', id: questionId)
        }
        else
        {
            notFound()
        }
    }

    def edit(Question question)
    {
        respond question
    }

    @Transactional
    def update(Question question)
    {
        if (question == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (question.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'edit'
            return
        }

        question.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*'{ respond question, [status: OK] }
        }
    }

    @Transactional
    def delete(Question question)
    {

        if (question == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        question.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
