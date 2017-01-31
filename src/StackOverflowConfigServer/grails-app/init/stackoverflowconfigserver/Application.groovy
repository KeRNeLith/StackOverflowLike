package stackoverflowconfigserver

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
class Application extends GrailsAutoConfiguration
{
    static void main(String[] args)
    {
        GrailsApp.run(Application, args)
    }
}