package fr.isima.marshallers

import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.Tag
import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.Vote
import grails.converters.JSON

/**
 * Created by kernelith on 18/02/17.
 */
class QuestionMarshallers
{
	public static String LIGHT_QUESTION = 'LIGHT_QUESTION_MARSHALLER'
	public static String FULL_QUESTION = 'FULL_QUESTION_MARSHALLER'
	public static String FULL_QUESTION_FOR_DISPLAY = 'FULL_QUESTION_FOR_DISPLAY_MARSHALLER'

	static init()
	{
		JSON.createNamedConfig(LIGHT_QUESTION) { config ->
			config.registerObjectMarshaller(Question, lightQuestionMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
			config.registerObjectMarshaller(Tag, TagsMarshallers.lightTagMarshaller)
		}

		JSON.createNamedConfig(FULL_QUESTION) { config ->
			config.registerObjectMarshaller(Question, fullQuestionMarshaller)
			config.registerObjectMarshaller(Answer, AnswersMarshallers.fullAnswerMarshaller)
			config.registerObjectMarshaller(Comment, CommentsMarshallers.fullCommentMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
			config.registerObjectMarshaller(Vote, VoteMarshallers.lightVoteMarshaller)
			config.registerObjectMarshaller(Tag, TagsMarshallers.lightTagMarshaller)
		}

		JSON.createNamedConfig(FULL_QUESTION_FOR_DISPLAY) { config ->
			config.registerObjectMarshaller(Question, fullQuestionForDisplayMarshaller)
			config.registerObjectMarshaller(Answer, AnswersMarshallers.fullAnswerForDisplayMarshaller)
			config.registerObjectMarshaller(Comment, CommentsMarshallers.fullCommentMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
			config.registerObjectMarshaller(Tag, TagsMarshallers.lightTagMarshaller)
		}
	}

	public static lightQuestionMarshaller = { Question question ->
		getLightQuestionData(question)
	}

	public static fullQuestionMarshaller = { Question question ->
		def output = getLightQuestionData(question)
		output['answers'] = question.answers
		output['dateCreated'] = question.dateCreated
		output['lastUpdated'] = question.lastUpdated
		output['votes'] = question.votes
		return output
	}

	public static fullQuestionForDisplayMarshaller = { Question question ->
		def output = getLightQuestionData(question)
		output['dateCreated'] = question.dateCreated
		output['lastUpdated'] = question.lastUpdated
		return output
	}

	private static getLightQuestionData(Question question)
	{
		def output = [:]
		output['id'] = question.id
		output['title'] = question.title
		output['views'] = question.nbViews
		output['resolved'] = question.isResolved
		output['title'] = question.title
		output['tags'] = question.tags
		output['user'] = question.user
		return output
	}
}
