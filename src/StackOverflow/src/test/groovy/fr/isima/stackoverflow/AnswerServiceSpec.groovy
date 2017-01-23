package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AnswerService)
class AnswerServiceSpec extends Specification {

    def setup() {

    }

    def cleanup() {
    }

    void "Adding Comment to Answer"() {
        when: 'Comment is successfully added'
            def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
            def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)
            def answer = new Answer(user: user, question: question, message: "Just a random message")
            // def id = answer.getId;
            def id = 0
            //def answerService = new AnswerService()
            def state = AnswerService.addCommentToAnswer(message: "Comment number one", writer: user, answerId: id)

        then: 'validate Comment => return true'
            answer.validate()
            !answer.hasErrors()
            answer.errors.errorCount == 0
            answer.comments.validate()
            !answer.comments.hasErrors()
            answer.comments.errors.errorCount == 0
            answer.comments.count() == 1;
    }
}
