package fr.isima.healthcheckers

import fr.isima.stackoverflow.Question
import fr.isima.stackoverflow.tools.LinkService
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

/**
 * Created by kernelith on 08/02/17.
 */
class APIRoutesHealthIndicator implements HealthIndicator
{
	LinkService linkService

	private final int timeout = 2000

	public APIRoutesHealthIndicator(LinkService service)
	{
		linkService = service
	}

	@Override
	public Health health()
	{
		Health.Builder builder = new Health.Builder()

		// Check some API's anonymous routes
		if (linkService != null)
		{
			boolean error = false

			String apiBaseURL = linkService.serverUrl()

			Question lastQuestion = null
			Question.withTransaction{status->
				lastQuestion = Question.find("FROM Question ORDER BY dateCreated DESC")
			}

			if (lastQuestion == null)
			{
				error = true
			}

			// List of URLs to check
			def urlsToCheck = [ '/', '/api/question/home', '/api/question/display/' + lastQuestion.id ]

			// Check URLs
			int i = 0
			while (!error && i < urlsToCheck.size())
			{
				String urlToCheck = apiBaseURL + urlsToCheck.get(i)

				final HttpURLConnection urlConnection = (HttpURLConnection) urlToCheck.toURL().openConnection()
				final int responseCode =
						urlConnection.with {
							requestMethod = 'GET'
							readTimeout = timeout
							connectTimeout = timeout
							connect()
							responseCode
						}

				// If code is not in 200 to 399 range => KO
				if (responseCode >= 400)
				{
					builder.down(new Exception("Getting error code '${responseCode}' when checking '${urlToCheck}'."))
					error = true
				}

				++i
			}

			// No error => health check OK
			if (!error)
			{
				builder.up()
			}
		}
		// Service unavailable
		else
		{
			builder.down(new Exception("Link service is unavailable."))
		}

		return builder.build()
	}
}