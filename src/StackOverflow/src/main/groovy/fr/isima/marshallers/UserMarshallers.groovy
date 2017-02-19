package fr.isima.marshallers

import fr.isima.stackoverflow.User

/**
 * Created by kernelith on 18/02/17.
 */
class UserMarshallers
{
	static init()
	{

	}

	public static lightUserMarshaller = { User user ->
		def output = [:]
		output['id'] = user.id
		output['username'] = user.username
		return output
	}
}
