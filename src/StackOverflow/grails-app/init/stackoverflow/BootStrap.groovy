package stackoverflow

import fr.isima.stackoverflow.Role
import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.UserRole

class BootStrap
{
    def init = 
	{
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()

        def testUser = new User(username: 'kernelith', password: 'azerty', firstName: 'Alexandre', lastName: 'Rabérin').save()
        def testUser2 = new User(username: 'TJGamerz', password: 'azerty', firstName: 'Jonas', lastName: 'Elysée').save()
        def testUser3 = new User(username: 'foo', password: 'azerty', firstName: 'Jean', lastName: 'Dupont').save()

        UserRole.create testUser, adminRole
        UserRole.create testUser2, adminRole
        UserRole.create testUser3, userRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 3
        assert Role.count() == 2
        assert UserRole.count() == 3
    }

    def destroy =
    {
    }
}
