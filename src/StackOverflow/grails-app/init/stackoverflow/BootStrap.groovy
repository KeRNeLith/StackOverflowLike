package stackoverflow

import fr.isima.stackoverflow.Role;
import fr.isima.stackoverflow.User;
import fr.isima.stackoverflow.UserRole;

class BootStrap 
{
    def init = 
	{
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()

        def testUser = new User(username: 'kernelith', password: 'azerty', firstName: 'Alexandre', lastName: 'Rabérin', registerDate: new Date()).save()
        def testUser2 = new User(username: 'TJGamerz', password: 'azerty', firstName: 'Jonas', lastName: 'Elysée', registerDate: new Date()).save()

        UserRole.create testUser, adminRole
        UserRole.create testUser2, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 2
        assert Role.count() == 2
        assert UserRole.count() == 2
    }

    def destroy =
    {
    }
}
