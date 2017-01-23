package fr.isima.stackoverflow

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification
{
    def setup()
	{
    }

    def cleanup()
	{
    }

    void "User constraints"()
    {
        when: 'User is valid'
            def user = new User(username: 'Test', password: 'azerty', firstName: 'Jean', lastName: 'Dupont')

        then: 'validate user => return true'
            user.validate()
            !user.hasErrors()
            user.errors.errorCount == 0

        when: 'User is invalid (too small)'
            user.username = 'Te'
            user.password = 'az'
            user.firstName = 'Je'
            user.lastName = 'Du'

        then: 'validate user => return false'
            !user.validate()
            user.hasErrors()
            user.errors.errorCount == 4
            user.errors['username'].code == 'minSize.notmet'
            user.errors['password'].code == 'minSize.notmet'
            user.errors['firstName'].code == 'minSize.notmet'
            user.errors['lastName'].code == 'minSize.notmet'

        when: 'User is invalid (too long)'
            user.username = 'Lorem ipsum dolor sit amet, consectetur massa nunc.'
            user.password = 'azerty'    // Valid password
            user.firstName = 'Lorem ipsum dolor sit amet, consectetur massa nunc.'
            user.lastName = 'Lorem ipsum dolor sit amet, consectetur massa nunc.'

        then: 'validate user => return false'
            !user.validate()
            user.hasErrors()
            user.errors.errorCount == 3
            user.errors['username'].code == 'maxSize.exceeded'
            user.errors['firstName'].code == 'maxSize.exceeded'
            user.errors['lastName'].code == 'maxSize.exceeded'

        when: 'User is valid'
            user.username = 'Test'
            user.firstName = 'Jean'
            user.lastName = 'Dupont'

        then: 'validate user => return true'
            user.validate()
            !user.hasErrors()
            user.errors.errorCount == 0
    }
}
