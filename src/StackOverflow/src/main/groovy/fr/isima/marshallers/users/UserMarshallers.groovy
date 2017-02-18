package fr.isima.marshallers.users

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
		output['username'] = user.username
		return output
	}
}
