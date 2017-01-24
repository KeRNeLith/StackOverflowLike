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
            def user = new User(username: 'Test', password: 'azerty')

        then: 'validate user => return true'
            user.validate()
            !user.hasErrors()
            user.errors.errorCount == 0

        when: 'User is invalid (too small)'
            user.username = 'Te'
            user.password = 'az'

        then: 'validate user => return false'
            !user.validate()
            user.hasErrors()
            user.errors.errorCount == 2
            user.errors['username'].code == 'minSize.notmet'
            user.errors['password'].code == 'minSize.notmet'

        when: 'User is invalid (too long)'
            user.username = 'Lorem ipsum dolor sit amet, consectetur massa nunc.'
            user.password = 'azerty'    // Valid password

        then: 'validate user => return false'
            !user.validate()
            user.hasErrors()
            user.errors.errorCount == 1
            user.errors['username'].code == 'maxSize.exceeded'

        when: 'User is valid'
            user.username = 'Test'

        then: 'validate user => return true'
            user.validate()
            !user.hasErrors()
            user.errors.errorCount == 0
    }
}
