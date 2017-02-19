package fr.isima.marshallers.tags

import fr.isima.stackoverflow.Tag


/**
 * Created by kernelith on 18/02/17.
 */
class TagsMarshallers
{
	static init()
	{

	}

	public static lightTagMarshaller = { Tag tag ->
		def output = [:]
		output['id'] = tag.tag.id
		output['tag'] = tag.tag.tagName
		return output
	}
}
