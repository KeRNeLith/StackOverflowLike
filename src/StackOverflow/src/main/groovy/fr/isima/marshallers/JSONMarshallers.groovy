package fr.isima.marshallers

import fr.isima.marshallers.questions.QuestionMarshallers
import fr.isima.marshallers.tags.TagsMarshallers
import fr.isima.marshallers.users.UserMarshallers

/**
 * Created by kernelith on 18/02/17.
 */
class JSONMarshallers
{
	static init()
	{
		UserMarshallers.init()
		QuestionMarshallers.init()
		TagsMarshallers.init()
	}
}
