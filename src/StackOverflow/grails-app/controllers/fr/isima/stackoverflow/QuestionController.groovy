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
    def tagService

    // Actions
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Question.list(params), model:[questionCount: Question.count()]
    }

    @Secured('ROLE_ANONYMOUS')
    def home()
    {
        def nbCategories = 5
        def nbRecentQuestions = 20
        def nbQuestionByCat = 10

        def tags = tagService.getMostRecentTags(nbCategories)
        def questionsByCat = [:]
        tags.each {
            questionsByCat.put(it.tagName, questionService.getQuestionsForTag(nbQuestionByCat, it))
        }

        // Get recent and question by category
        respond recents: questionService.getMostRecentQuestions(nbRecentQuestions), questionsByCat: questionsByCat
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
        question.nbViews++;
        question.save()

        def questionVotes = questionService.voteCount(question.votes)
        // Sort answers by votes and posting date
        def sortedAnswers = questionService.sortAnswersByVotes(question)

        def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

        respond question, model: [questionVotes: questionVotes, sortedAnswers: sortedAnswers, currentUser: user]
    }

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
            respond question.errors, view:'redact', model: [tags: TagValue.findAll()]
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

    @Secured('ROLE_USER')
    def redactEdit(Question question)
    {
        respond question, view: 'redactEdit', model: [tags: TagValue.findAll()]
    }

    @Secured('ROLE_USER')
    def resolve(Question question)
    {
        if (question == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        questionService.resolveQuestion(question)

        redirect(action: 'display', id: question.id)
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

    @Secured('ROLE_USER')
    @Transactional
    def updateEdit(Question question)
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
            respond question.errors, view:'redactEdit'
            return
        }

        // Update tags
        if (params.containsKey('selectedTags'))
        {
            def tags = params.list('selectedTags');
            tagService.updateTagsToQuestion(question, tags)
        }

        question.save flush:true

        redirect(action: 'display', id: question.id)
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
