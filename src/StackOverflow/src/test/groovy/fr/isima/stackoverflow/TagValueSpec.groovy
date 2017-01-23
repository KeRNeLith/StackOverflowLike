package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(TagValue)
class TagValueSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "TagValue constraints"() {
      when: 'TagValue is valid'
          def tagv = new TagValue(tagName: 'validTag')

      then: 'validate TagValue => return true'
          tagv.validate()
          !tagv.hasErrors()
          tagv.errors.errorCount == 0

      when: 'TagValue has a name thats too long'
          tagv = new TagValue(tagName: 'this tagname has over 50 caracters aaaaaaaaaaaaaaaaaaaaaaaa')

      then: 'validate TagValue => return false'
          !tagv.validate()
          tagv.hasErrors()
          tagv.errors.errorCount == 1

      when: 'TagValue has no name'
          tagv = new TagValue()

      then: 'validate TagValue => return false'
          !tagv.validate()
          tagv.hasErrors()
          tagv.errors.errorCount == 1

          /*
      when: 'TagValue doesnt have a unique name'
          tagv = new TagValue(tagName: "same name")
          def tagvv = new TagValue(tagName: "same name")

      then: 'validate TagValue => return false'
          !tagvv.validate()
          tagvv.hasErrors()
          tagvv.errors.errorCount == 1
          //*/
    }


}
