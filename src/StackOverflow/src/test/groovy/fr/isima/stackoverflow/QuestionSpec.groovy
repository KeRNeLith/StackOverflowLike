package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Question)
class QuestionSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Question constraints"() {
      when: 'Question is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)

      then: 'validate answer => return true'
          question.validate()
          !question.hasErrors()
          question.errors.errorCount == 0

      when: 'Question has a title that is too long'
          question = new Question (user: user, message: "simple message", title: 'this title has over 150 caracters aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', nbViews: 0)

      then: 'validate answer => return true'
          !question.validate()
          question.hasErrors()
          question.errors.errorCount == 1

      when: 'Question has negative views'
          question = new Question (user: user, message: "simple message", title: 'Simple Title', nbViews: -1)

      then: 'validate answer => return true'
          !question.validate()
          question.hasErrors()
          question.errors.errorCount == 1
    }
}
