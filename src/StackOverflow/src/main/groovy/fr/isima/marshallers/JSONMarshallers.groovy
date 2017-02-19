package fr.isima.marshallers
/**
 * Created by kernelith on 18/02/17.
 */
class JSONMarshallers
{
	static init()
	{
		UserMarshallers.init()
		CommentsMarshallers.init()
		AnswersMarshallers.init()
		QuestionMarshallers.init()
		VoteMarshallers.init()
		TagsMarshallers.init()
	}
}
