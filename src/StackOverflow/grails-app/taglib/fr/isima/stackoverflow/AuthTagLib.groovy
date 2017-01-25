package fr.isima.stackoverflow

class AuthTagLib
{
    def springSecurityService

    def isOwner = { attrs, body ->
        def loggedInUser = springSecurityService.currentUser
        def owner = attrs?.owner

        if(loggedInUser?.id == owner?.id)
        {
            out << body()
        }
    }
}
