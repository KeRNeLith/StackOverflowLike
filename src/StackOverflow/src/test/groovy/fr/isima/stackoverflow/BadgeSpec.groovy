package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Badge)
class BadgeSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Badge constraints"() {
      when: 'Badge is valid'
          def badge = new Badge(name: 'Test', rank: Badge.Rank.GOLD)

      then: 'validate badge => return true'
          badge.validate()
          !badge.hasErrors()
          badge.errors.errorCount == 0

      when: 'Badge has invalid rank'
               badge = new Badge(name: 'Test')

      then: 'validate badge => return false'
              !badge.validate()
              badge.hasErrors()
              badge.errors.errorCount == 1

      when: 'Badge has no name'
               badge = new Badge(rank: Badge.Rank.GOLD)

      then: 'validate badge => return false'
              !badge.validate()
              badge.hasErrors()
              badge.errors.errorCount == 1

      when: 'Badge has a name too long'
               badge = new Badge(name: 'this has more than 50 Caracters aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',rank: Badge.Rank.GOLD)

      then: 'validate badge => return false'
              !badge.validate()
              badge.hasErrors()
              badge.errors.errorCount == 1
              badge.errors['name'].code == 'maxSize.exceeded'

      when: 'Badge has bad name and bad rank'
               badge = new Badge()

      then: 'validate badge => return false'
              !badge.validate()
              badge.hasErrors()
              badge.errors.errorCount == 2
    }
}
