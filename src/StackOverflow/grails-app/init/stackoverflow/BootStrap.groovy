package stackoverflow

import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.Role
import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.UserRole

class BootStrap
{
    def init = 
	{
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()
        def anonymousRole = new Role(authority: 'ROLE_ANONYMOUS').save()

        def kernelith = new User(username: 'kernelith', password: 'azerty', firstName: 'Alexandre', lastName: 'Rabérin').save()
        def tjgamerz = new User(username: 'TJGamerz', password: 'azerty', firstName: 'Jonas', lastName: 'Elysée').save()
        def fooUser = new User(username: 'foo', password: 'azerty', firstName: 'Jean', lastName: 'Dupont').save()

        UserRole.create kernelith, adminRole
        UserRole.create tjgamerz, adminRole
        UserRole.create fooUser, userRole

        def question1 = new Question(title: 'Titre 1', message: 'Message question 1', user: kernelith).save()
        def answer1 = new Answer(message: 'response 1', question: question1, user: tjgamerz)
        def comment1 = new Comment(message: 'comment 1', answer: answer1, user: kernelith)
        def comment2 = new Comment(message: 'responser au comment 1', answer: answer1, user: tjgamerz)
        answer1.addToComments(comment1).addToComments(comment2).save()

        def answer2 = new Answer(message: 'response 2', question: question1, user: fooUser)
        question1.addToAnswers(answer1).addToAnswers(answer2).save()

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 3
        assert Role.count() == 3
        assert UserRole.count() == 3
        assert Question.count() == 1
        assert Answer.count() == 2
        assert Comment.count() == 2
    }

    def destroy =
    {
    }
}
