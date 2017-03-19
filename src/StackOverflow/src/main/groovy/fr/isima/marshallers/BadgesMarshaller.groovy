package fr.isima.marshallers

import fr.isima.stackoverflow.Badge

/**
 * Created by kernelith on 18/02/17.
 */
class BadgesMarshallers
{
	static init()
	{
	}

	public static simpleBadgeMarshaller = { Badge badge ->
		return getBadgeData(badge)
	}

	private static getBadgeData(Badge badge)
	{
		def output = [:]
		output['rank'] = badge.rank.name()
		output['name'] = badge.name

		return output
	}
}
