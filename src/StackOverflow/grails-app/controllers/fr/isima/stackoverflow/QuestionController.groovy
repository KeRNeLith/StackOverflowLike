package fr.isima.stackoverflow

import fr.isima.marshallers.QuestionMarshallers
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class QuestionController
{
    static responseFormats = ['json']
    static allowedMethods = [saveQuestion: "POST", updateQuestion: "PUT", resolve: "PUT",
                             // Default grails methods
                             save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def questionService
    def tagService
    def numbersService
    def featuresFlippingService

    // Actions
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

        JSON.use(QuestionMarshallers.LIGHT_QUESTION) {
            // Get recent and question by category
            respond recents: questionService.getMostRecentQuestions(nbRecentQuestions), questionsByCat: questionsByCat, randomSentence: numbersService.getRandomNumberSentence()
        }
    }

    @Secured('ROLE_ANONYMOUS')
    def display(Question question)
    {
        if (question == null)
        {
            notFound()
            return
        }

        question.nbViews++
        question.save()

        def questionVotes = questionService.voteCount(question.votes)
        // Sort answers by votes and posting date
        def sortedAnswers = questionService.sortAnswersByVotes(question)
        //def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

	    JSON.use(QuestionMarshallers.FULL_QUESTION_FOR_DISPLAY) {
		    respond question: question, questionVotes: questionVotes, sortedAnswers: sortedAnswers
	    }
    }

    @Secured('ROLE_USER')
    def resolve()
    {
        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.question.resolve.wrong.parameters"'

        // Resolve question
        if (inputRequest.question != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            Long questionId = -1
            if (inputRequest.question instanceof String)
            {
                questionId = Long.parseLong(inputRequest.question)
            }
            else
            {
                questionId = inputRequest.question
            }

            retCode = questionService.resolveQuestion(questionId, user)

            if (retCode == '"success.question.mark.resolved.added"')
                status = OK
            else if (retCode == '"success.question.mark.resolved.notChanged"')
                status = NOT_MODIFIED
        }

        render status: status, message: retCode
    }

    @Secured('ROLE_USER')
    @Transactional
    def saveQuestion()
    {
        if (!featuresFlippingService.isQuestionPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.question"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        def retCode = '"error.create.question.wrong.parameters"'
        // Add question
        if (inputRequest.title != null && inputRequest.message != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            retCode = questionService.createQuestion(inputRequest.title, inputRequest.message, user, inputRequest.tags)

            // Creation succeed
            if ((retCode instanceof Long || retCode instanceof Integer) && retCode >= 0)
            {
                status = CREATED
            }
        }

        render status: status, message: retCode
    }

    @Secured('ROLE_USER')
    def redactEdit(Question question)
    {
        JSON.use(QuestionMarshallers.LIGHT_FOR_EDIT_QUESTION) {
            respond question
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateQuestion()
    {
        if (!featuresFlippingService.isQuestionPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.question"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.question.edit.wrong.parameters"'

        // Edit question
        if (inputRequest.message != null && inputRequest.question != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            Long questionId = -1
            if (inputRequest.question instanceof String)
            {
                questionId = Long.parseLong(inputRequest.question)
            }
            else
            {
                questionId = inputRequest.question
            }

            retCode = questionService.editQuestion( questionId,
                                                    inputRequest.title,
                                                    inputRequest.message,
                                                    inputRequest.tags,
                                                    user)
            if (retCode == '"success.question.edit.question"')
            {
                status = OK
            }
        }

        render status: status, message: retCode
    }

    // Grails default routes
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Question.list(params), model:[questionCount: Question.count()]
    }

    def show(Question question)
    {
        respond question
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
