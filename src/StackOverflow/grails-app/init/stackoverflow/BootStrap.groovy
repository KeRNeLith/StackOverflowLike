package stackoverflow

import fr.isima.marshallers.JSONMarshallers
import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.Badge
import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.Role
import fr.isima.stackoverflow.Tag
import fr.isima.stackoverflow.TagValue
import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.UserRole
import fr.isima.stackoverflow.Vote
import grails.converters.JSON

class BootStrap
{
    def init = 
	{
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()
        def anonymousRole = new Role(authority: 'ROLE_ANONYMOUS').save()

        def kernelith = new User(username: 'kernelith', password: 'azerty').save()
        def tjgamerz = new User(username: 'TJGamerz', password: 'azerty').save()
        def fooUser = new User(username: 'foo', password: 'azerty').save()

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

        def question1 = new Question(title: 'Question très difficile sur les templates Cpp', message: 'Bonjour, j\'ai une question sur les templates C++. Je ne sais pas quoi choisir entre typename ou class.', user: kernelith, isResolved: true).save()
        def answer1 = new Answer(message: 'Depuis l\'arrivée du typename il est préférable de mettre typename', question: question1, user: tjgamerz)
        def comment1 = new Comment(message: 'J\'approuve !', answer: answer1, user: fooUser)
        answer1 .addToComments(comment1)
                .addToVotes(post: question1, vote: Vote.Value.UP, user: kernelith)
                .addToVotes(post: question1, vote: Vote.Value.UP, user: tjgamerz).save()
        question1   .addToAnswers(answer1)
                    .addToTags(question: question1, tag: tagCpp).save()

        def question2 = new Question(title: 'grails Project', message: 'Bonjour, j\'ai un TP de Grails à faire. Quelqu\'un peut il me le faire ?', user: fooUser).save()
        def answer2 = new Answer(message: 'Débrouille toi !', question: question2, user: tjgamerz)
        answer2.addToVotes(post: answer2, vote: Vote.Value.UP, user: kernelith).save()
        def answer3 = new Answer(message: 'S\'il vous plait', question: question2, user: fooUser)
        answer3.addToVotes(post: answer3, vote: Vote.Value.DOWN, user: tjgamerz).save()
        answer3.addToVotes(post: answer3, vote: Vote.Value.DOWN, user: kernelith).save()
        def answer4 = new Answer(message: 'ça ne t\'aidera pas', question: question2, user: kernelith)
        question2   .addToAnswers(answer2)
                    .addToAnswers(answer3)
                    .addToAnswers(answer4)
                    .addToTags(question: question2, tag: tagHTML)
                    .addToTags(question: question2, tag: tagCSS)
                    .addToTags(question: question2, tag: tagGroovy)
                    .addToVotes(post: question2, vote: Vote.Value.DOWN, user: kernelith)
                    .addToVotes(post: question2, vote: Vote.Value.DOWN, user: tjgamerz).save()

        def question3 = new Question(title: 'Question inutile : Pourquoi J2EE ?', message: 'Bonjour, j\'aurai voulu savoir pour le J2EE existe ?', user: tjgamerz).save()
        def answer5 = new Answer(message: 'C\'est un truc de pros', question: question3, user: kernelith)
        def comment2 = new Comment(message: 'D\'accord merci !', answer: answer5, user: tjgamerz)
        answer5.addToComments(comment2).save()
        question3   .addToAnswers(answer5)
                    .addToTags(question: question3, tag: tagJava)
                    .addToTags(question: question3, tag: tagJEE).save()

        def question4 = new Question(title: 'Question ouverte', message: 'Que signifie une question ouverte ?', user: fooUser).save()
        def answer6 = new Answer(message: 'On fait pas de philo ici !', question: question4, user: kernelith)
        def answer7 = new Answer(message: 'Rien n\'empêche de commencer un jour', question: question4, user: fooUser)
        question4   .addToAnswers(answer6)
                    .addToAnswers(answer7).save()

        def question5 = new Question(title: 'Question sans réponse (Java)', message: 'Bonjour, j\'aurai voulu savoir quand sortira Java 9 ?', user: tjgamerz).save()
        question5.addToTags(question: question3, tag: tagJava).save()

        def badge1 = new Badge(name: 'Ask your First Question', rank: Badge.Rank.SILVER).save()
        def badge2 = new Badge(name: 'First Answer', rank: Badge.Rank.BRONZE).save()
        def badge3 = new Badge(name: 'First Comment', rank: Badge.Rank.BRONZE).save()
        def badge4 = new Badge(name: 'Gain a positive vote', rank: Badge.Rank.BRONZE).save()
        def badge5 = new Badge(name: 'Gain more than 10 positives votes', rank: Badge.Rank.SILVER).save()
        def badge6 = new Badge(name: 'Gain more than 50 positives votes', rank: Badge.Rank.GOLD).save()

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 3
        assert Role.count() == 3
        assert UserRole.count() == 3
        assert Question.count() == 5
        assert Answer.count() == 7
        assert Comment.count() == 2
        assert TagValue.count() == 12

        // Initialize Marshallers
        JSONMarshallers.init()
    }

    def destroy =
    {
    }
}
