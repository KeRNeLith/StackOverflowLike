package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Answer)
class AnswerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Answer constraints"() {
      when: 'Answer is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)
          def answer = new Answer(user: user, question: question, message: "Just a random message")

      then: 'validate answer => return true'
          answer.validate()
          !answer.hasErrors()
          answer.errors.errorCount == 0

      when: 'Answer has no message'
          answer = new Answer(question: question, user: user)

      then: 'validate answer => return false'
          !answer.validate()
          answer.hasErrors()
          answer.errors.errorCount == 1


    }
}
