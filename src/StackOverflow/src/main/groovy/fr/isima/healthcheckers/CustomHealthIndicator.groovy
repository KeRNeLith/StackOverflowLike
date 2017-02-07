package fr.isima.healthcheckers

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

/**
 * Created by kernelith on 08/02/17.
 */
class CustomHealthIndicator implements HealthIndicator
{
	@Override
	public Health health()
	{
		int errorCode = 0 // perform some specific health check => Here does nothing
		if (errorCode != 0)
		{
			return Health.down().withDetail("Error Code", errorCode).build()
		}

		return Health.up().build()
	}
}