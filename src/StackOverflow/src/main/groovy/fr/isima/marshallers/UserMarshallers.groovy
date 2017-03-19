package fr.isima.marshallers

import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.Vote
import fr.isima.stackoverflow.Tag
import grails.converters.JSON

/**
 * Created by kernelith on 18/02/17.
 */
class UserMarshallers
{
	public static String MEDIUM_USER_INFO = 'MEDIUM_USER_INFO_MARSHALLER'
	static init()
	{
		JSON.createNamedConfig(MEDIUM_USER_INFO) { config ->
			config.registerObjectMarshaller(User, mediumUserInfoMarshaller)
			config.registerObjectMarshaller(Question, QuestionMarshallers.userOwnedQuestionMarshaller)
			config.registerObjectMarshaller(Answer, AnswersMarshallers.fullAnswerMarshaller)
			config.registerObjectMarshaller(Tag, TagsMarshallers.lightTagMarshaller)
		}
	}

	private static getLightUserData(User user)
	{
		def output = [:]
		output['id'] = user.id
		output['username'] = user.username
		return output
	}

	public static lightUserMarshaller = { User user ->
		return getLightUserData(user)
	}

	public static mediumUserInfoMarshaller = { User user ->
		def output = getLightUserData(user)
		output['description'] = user.description
		output['questions'] = user.questions
		output['registerDate'] = user.registerDate
		output['reputation'] = user.reputation

		return output
	}
}
