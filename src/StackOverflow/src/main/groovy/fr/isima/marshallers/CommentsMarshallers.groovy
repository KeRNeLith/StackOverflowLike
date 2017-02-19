package fr.isima.marshallers

import fr.isima.stackoverflow.Comment

/**
 * Created by kernelith on 19/02/17.
 */
class CommentsMarshallers
{
	static init()
	{

	}

	public static fullCommentMarshaller = { Comment comment ->
		def output = [:]
		output['id'] = comment.id
		output['user'] = comment.user
		output['message'] = comment.message
		output['dateCreated'] = comment.dateCreated
		output['lastUpdated'] = comment.lastUpdated
		return output
	}
}
