package stackoverflow

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker

@EnableCircuitBreaker
class Application extends GrailsAutoConfiguration
{
    static void main(String[] args)
    {
        GrailsApp.run(Application, args)
    }
}