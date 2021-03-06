package fr.isima.marshallers

import fr.isima.stackoverflow.*
import grails.converters.JSON

/**
 * Created by kernelith on 18/02/17.
 */
class QuestionMarshallers
{
	public static String LIGHT_QUESTION = 'LIGHT_QUESTION_MARSHALLER'
	public static String LIGHT_FOR_EDIT_QUESTION = 'LIGHT_FOR_EDIT_QUESTION_MARSHALLER'
	public static String FULL_QUESTION = 'FULL_QUESTION_MARSHALLER'
	public static String FULL_QUESTION_FOR_DISPLAY = 'FULL_QUESTION_FOR_DISPLAY_MARSHALLER'

	static init()
	{
		JSON.createNamedConfig(LIGHT_QUESTION) { config ->
			config.registerObjectMarshaller(Question, lightQuestionMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
			config.registerObjectMarshaller(Tag, TagsMarshallers.lightTagMarshaller)
		}

		JSON.createNamedConfig(LIGHT_FOR_EDIT_QUESTION) { config ->
			config.registerObjectMarshaller(Question, lightForEditQuestionMarshaller)
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

	public static lightForEditQuestionMarshaller = { Question question ->
		def output = [:]
		output['id'] = question.id
		output['title'] = question.title
		output['message'] = question.message
		output['user'] = question.user
		output['tags'] = question.tags

		return output
	}

	public static fullQuestionMarshaller = { Question question ->
		def output = getMediumQuestionData(question)
		output['answers'] = question.answers
		output['votes'] = question.votes
		return output
	}

	public static fullQuestionForDisplayMarshaller = { Question question ->
		return getMediumQuestionData(question)
	}

	public static userOwnedQuestionMarshaller = { Question question ->
		def output = [:]
		output['id'] = question.id
		output['title'] = question.title
		//output['views'] = question.nbViews
		//output['resolved'] = question.isResolved
		output['tags'] = question.tags

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

	private static getMediumQuestionData(Question question)
	{
		def output = getLightQuestionData(question)
		output['dateCreated'] = question.dateCreated
		output['lastUpdated'] = question.lastUpdated
		output['message'] = question.message
		return output
	}
}
