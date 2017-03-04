package fr.isima.marshallers

import fr.isima.stackoverflow.Comment
import fr.isima.stackoverflow.User
import grails.converters.JSON

/**
 * Created by kernelith on 19/02/17.
 */
class CommentsMarshallers
{
	public static String LIGHT_COMMENT = 'LIGHT_COMMENT_MARSHALLER'

	static init()
	{
		JSON.createNamedConfig(LIGHT_COMMENT) { config ->
			config.registerObjectMarshaller(Comment, lightCommentMarshaller)
			config.registerObjectMarshaller(User, UserMarshallers.lightUserMarshaller)
		}
	}

	public static lightCommentMarshaller = { Comment comment ->
		return getLightCommentData(comment)
	}

	public static fullCommentMarshaller = { Comment comment ->
		def output = getLightCommentData(comment)
		output['dateCreated'] = comment.dateCreated
		output['lastUpdated'] = comment.lastUpdated
		return output
	}

	private static getLightCommentData(Comment comment)
	{
		def output = [:]
		output['id'] = comment.id
		output['user'] = comment.user
		output['message'] = comment.message

		return output
	}
}
