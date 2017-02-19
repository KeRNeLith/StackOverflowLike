package fr.isima.marshallers

import fr.isima.stackoverflow.Answer

/**
 * Created by kernelith on 19/02/17.
 */
class AnswersMarshallers
{
	static init()
	{

	}

	public static fullAnswerMarshaller = { Answer answer ->
		def output = getMainAnswerData(answer)
		output['votes'] = answer.votes
		return output
	}

	public static fullAnswerForDisplayMarshaller = { Answer answer ->
		return getMainAnswerData(answer)
	}

	private static getMainAnswerData(Answer answer)
	{
		def output = [:]
		output['id'] = answer.id
		output['user'] = answer.user
		output['message'] = answer.message
		output['dateCreated'] = answer.dateCreated
		output['lastUpdated'] = answer.lastUpdated
		output['comments'] = answer.comments
		return output
	}
}
