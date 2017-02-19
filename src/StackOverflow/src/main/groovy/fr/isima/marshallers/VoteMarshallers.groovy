package fr.isima.marshallers

import fr.isima.stackoverflow.Vote

/**
 * Created by kernelith on 19/02/17.
 */
class VoteMarshallers
{
	static init()
	{

	}

	public static lightVoteMarshaller = { Vote vote ->
		def output = [:]
		output['vote'] = vote.vote
		return output
	}
}
