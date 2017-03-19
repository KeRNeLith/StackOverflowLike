package fr.isima.marshallers

import fr.isima.stackoverflow.Badge
import grails.converters.JSON

/**
 * Created by kernelith on 18/02/17.
 */
class BadgesMarshallers
{
	static init()
	{
	}

	private static getBadgeData(Badge badge)
	{
		def output = [:]
		output['Rank'] = badge.rank
		output['name'] = badge.name
		return output
	}

	public static simpleBadgeMarshaller = { Badge badge ->
		return getBadgeData(badge)
	}

}
