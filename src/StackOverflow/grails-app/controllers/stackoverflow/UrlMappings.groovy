package stackoverflow

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index")
        "/"(controller:"question", action:"home")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "503"(view:'/unavailable')
		"/login/$action?"(controller: "login")
		"/logout/$action?"(controller: "logout")
    }
}
