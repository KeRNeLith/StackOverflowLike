package stackoverflow

class UrlMappings {

    static mappings = {
        "/api/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"question", action:"home")

        "401"(view:'/denied')
        "404"(view:'/notFound')
        "500"(view:'/error')
        "503"(view:'/unavailable')

        /*"400"(controller: "error", action: "invalid")
        "401"(controller: "error", action: "denied")
        "403"(controller: "error", action: "denied")
        "404"(controller: "error", action: "notFound")
        "500"(controller: "error", action: "error")
        "503"(controller: "error", action: "unavailable")*/

		"/login/$action?"(controller: "login")
		"/logout/$action?"(controller: "logout")
    }
}
