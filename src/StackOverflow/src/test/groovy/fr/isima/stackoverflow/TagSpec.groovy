package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Tag)
class TagSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Tag constraints"() {
      when: 'Tag is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def question = new Question (user: user, message: "simple message", title: 'some title', nbViews: 0)
          def tagv = new TagValue(tagName: 'validTag')
          def tag = new Tag(tag: tagv, question: question)

      then: 'validate Tag => return true'
          tag.validate()
          !tag.hasErrors()
          tag.errors.errorCount == 0
    }
}
