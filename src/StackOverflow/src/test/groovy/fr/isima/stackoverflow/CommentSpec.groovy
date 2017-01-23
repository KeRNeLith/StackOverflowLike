package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
class CommentSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Comment Constraints"() {
      when: 'Comment is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)
          def answer = new Answer(user: user, question: question, message: "Just a random message")

          def comment = new Comment(message: 'Just a random message', user: user, answer: answer)

      then: 'validate Comment => return true'
          comment.validate()
          !comment.hasErrors()
          comment.errors.errorCount == 0

      when: 'Comment has no message'
          comment = new Comment(user: user, answer: answer)

      then: 'validate Comment => return false'
          !comment.validate()
          comment.hasErrors()
          comment.errors.errorCount == 1
    }
}
