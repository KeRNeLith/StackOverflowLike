package fr.isima.stackoverflow.tools

import grails.transaction.Transactional
import grails.web.mapping.LinkGenerator

@Transactional
class LinkService
{
    /**
     * Grails link generator.
     */
    LinkGenerator grailsLinkGenerator

    /**
     * Get the application context path.
     * @return Application context path.
     */
    String contextPath()
    {
        grailsLinkGenerator.contextPath
    }

    /**
     * Get the application server URL.
     * @return Application server URL.
     */
    String serverUrl()
    {
        grailsLinkGenerator.serverBaseURL
    }
}
