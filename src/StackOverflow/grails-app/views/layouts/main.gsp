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
                    <g:link class="navbar-brand" url="/">${message(code: 'menu.home.label', default: 'Home')}</g:link>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav"></ul>
                    <!-- Login part -->
                    <div class="nav navbar-nav navbar-right">
                        <sec:ifNotLoggedIn>
                            <g:link class="navbar-brand" url="/user/create">${message(code: 'menu.user.signup', default: 'Sign up')}</g:link>
                            <g:link controller="login" action="auth" class="navbar-brand">${message(code: 'menu.user.signin', default: 'Sign in')}</g:link>
                        </sec:ifNotLoggedIn>
                        <sec:ifLoggedIn>
                            <g:link controller="user" action="show" id="${sec.loggedInUserInfo(field: 'id')}" class="navbar-brand">
                                <sec:username/>
                            </g:link>
                            <g:link controller="logout" class="navbar-brand">Logout</g:link>
                        </sec:ifLoggedIn>
                    </div>
                </div>
            </div>
        </div>

        <div class="container body-content">
            <g:form controller="question" action="redact">
                <g:submitButton name="Ask a question"/>
            </g:form>

            <g:layoutBody/>
        </div>

        <div class="footer" role="contentinfo"></div>

        <div id="spinner" class="spinner" style="display:none;">
            <g:message code="spinner.alt" default="Loading&hellip;"/>
        </div>

        <asset:javascript src="application.js"/>
    </body>
</html>
