package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(UserBadges)
class UserBadgesSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "UserBadges constraints"() {
      when: 'UserBadge is valid'
          def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')
          def badge = new Badge(name: 'Validity', rank: Badge.Rank.GOLD)
          def nowDate = new Date()

          def usrBad = new UserBadges(user: user, badge: badge, dateEarned: nowDate)

      then: 'validate UserBadges => return true'
          usrBad.validate()
          !usrBad.hasErrors()
          usrBad.errors.errorCount == 0
    }
}
