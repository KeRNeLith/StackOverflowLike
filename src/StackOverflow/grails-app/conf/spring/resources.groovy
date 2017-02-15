import fr.isima.healthcheckers.APIRoutesHealthIndicator

// Place your Spring DSL code here
beans = {
	// Health indicator
	apiRoutesHealthIndicator(APIRoutesHealthIndicator, linkService)
	// Not working
	/*ex(APIRoutesHealthIndicator) {
		bean -> linkService = ref('linkService')
	}*/
}
