package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Vote)
class VoteSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Vote constraints"() {
      when: 'Vote is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)
          def vote = new Vote(user: user, post: question, vote: Vote.Value.UP)

      then: 'validate Vote => return true'
          vote.validate()
          !vote.hasErrors()
          vote.errors.errorCount == 0
    }

}
