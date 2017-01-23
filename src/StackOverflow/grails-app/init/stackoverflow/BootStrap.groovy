package stackoverflow

import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.Role
import fr.isima.stackoverflow.TagValue
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

        def tagC = new TagValue(tagName: 'C').save()
        def tagCpp = new TagValue(tagName: 'C++').save()
        def tagCsharp = new TagValue(tagName: 'C#').save()
        def tagJava = new TagValue(tagName: 'Java').save()
        def tagPHP = new TagValue(tagName: 'PHP').save()
        def tagJS = new TagValue(tagName: 'Javascript').save()
        def tagHTML = new TagValue(tagName: 'HTML').save()
        def tagCSS = new TagValue(tagName: 'CSS').save()
        def tagJEE = new TagValue(tagName: 'J2EE').save()
        def tagdotNET = new TagValue(tagName: '.NET').save()
        def tagGroovy = new TagValue(tagName: 'Groovy').save()
        def tagOther = new TagValue(tagName: 'Other').save()

        def question1 = new Question(title: 'Titre 1', message: 'Message question 1', user: kernelith).save()
        def answer1 = new Answer(message: 'response 1', question: question1, user: tjgamerz)
        def comment1 = new Comment(message: 'comment 1', answer: answer1, user: kernelith)
        def comment2 = new Comment(message: 'responser au comment 1', answer: answer1, user: tjgamerz)
        answer1.addToComments(comment1).addToComments(comment2).save()

        def answer2 = new Answer(message: 'response 2', question: question1, user: fooUser)
        question1.addToAnswers(answer1).addToAnswers(answer2).addToTags(question: question1, tag: tagGroovy).save()

        def questionx = new Question(title: 'Titre 2', message: 'Message question 1', user: kernelith).save()
        questionx.addToTags(question: questionx, tag: tagGroovy).save()
        def questionxx = new Question(title: 'Titre 3', message: 'Message question 1', user: kernelith).save()
        questionxx.addToTags(question: questionxx, tag: tagCsharp).save()
        def questionxxx = new Question(title: 'Titre 4', message: 'Message question 1', user: kernelith).save()
        questionxxx.addToTags(question: questionxxx, tag: tagCSS).addToTags(question: questionxxx, tag: tagHTML).save()
        def questionxxxx = new Question(title: 'Titre 5', message: 'Message question 1', user: kernelith).save()
        questionxxxx.addToTags(question: questionxxxx, tag: tagHTML).save()
        new Question(title: 'Titre 6', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 7', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 8', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 9', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 10', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 11', message: 'Message question 1', user: kernelith).save()
        new Question(title: 'Titre 12', message: 'Message question 1', user: kernelith).save()

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 3
        assert Role.count() == 3
        assert UserRole.count() == 3
        //assert Question.count() == 1
        assert Answer.count() == 2
        assert Comment.count() == 2
        assert TagValue.count() == 12
    }

    def destroy =
    {
    }
}
