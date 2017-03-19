package fr.isima.marshallers

import fr.isima.stackoverflow.Answer
import fr.isima.stackoverflow.User
import grails.converters.JSON

/**
 * Created by kernelith on 19/02/17.
 */
class AnswersMarshallers
{
	public static String LIGHT_ANSWER = 'LIGHT_ANSWER_MARSHALLER'

	static init()
	{
		JSON.createNamedConfig(LIGHT_ANSWER) { config ->
			config.registerObjectMarshaller(Answer, lightAnswerMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
		}
	}

	public static lightAnswerMarshaller = { Answer answer ->
		return getLightAnswerData(answer)
	}

	public static fullAnswerMarshaller = { Answer answer ->
		def output = getMainAnswerData(answer)
		output['votes'] = answer.votes
		return output
	}

	public static fullAnswerForDisplayMarshaller = { Answer answer ->
		return getMainAnswerData(answer)
	}

	public static associatedQuestionMarshaller = {Answer answer ->
		return getAssociatedQuestion(answer)
	}

	private static getLightAnswerData(Answer answer)
	{
		def output = [:]
		output['id'] = answer.id
		output['user'] = answer.user
		output['message'] = answer.message

		return output
	}

	private static getMainAnswerData(Answer answer)
	{
		def output = getLightAnswerData(answer)
		output['dateCreated'] = answer.dateCreated
		output['lastUpdated'] = answer.lastUpdated
		output['comments'] = answer.comments

		return output
	}

	private static getAssociatedQuestion(Answer answer)
	{
		def output = [:]

		output["id"] = answer.id
		output["question"] = answer.question
		//output['question'] = answer.question
		return output
	}

}
