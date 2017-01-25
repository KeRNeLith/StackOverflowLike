<!doctype html>
<html lang="en" class="utf-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="SegFault"/>
    </title>

    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="application.css"/>
    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>

    <g:layoutHead/>
</head>
    <body>
        <div class="navbar navbar-inverse" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    </button>
                    <g:link class="navbar-brand" url="/">${message(code: 'home.label')}</g:link>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav"></ul>
                    <!-- Login part -->
                    <div class="nav navbar-nav navbar-right">
                        <sec:ifNotLoggedIn>
                            <g:link controller="user" action="register" class="navbar-brand"><g:message code='signup.button'/></g:link>
                            <g:link controller="login" action="auth" class="navbar-brand"><g:message code='signin.button'/></g:link>
                        </sec:ifNotLoggedIn>
                        <sec:ifLoggedIn>
                            <g:link controller="user" action="display" id="${sec.loggedInUserInfo(field: 'id')}" class="navbar-brand">
                                <sec:username/>
                            </g:link>
                            <g:link controller="logout" class="navbar-brand"><g:message code='logout.button'/></g:link>
                        </sec:ifLoggedIn>
                    </div>
                </div>
            </div>
        </div>

        <div class="container body-content">
            <g:layoutBody/>
        </div>

        <div id="spinner" class="spinner" style="display:none;">
            <g:message code="spinner.alt" default="Loading&hellip;"/>
        </div>

        <asset:javascript src="application.js"/>
    </body>
</html>
