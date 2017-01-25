<html>
<head>
	<meta name="layout" content="${gspLayout ?: 'main'}"/>
	<title>Login - SegFault</title>
	<asset:stylesheet src="user/registration.css"/>
</head>

<body>
<div id="login">
	<div class="inner">
		<div class="container top-margin-50">
			<div id="logDiv" class="top-margin-50">
				<form  action="${postUrl ?: '/login/authenticate'}" method="POST" id="loginForm" autocomplete="off">
					<h1><g:message code='login.title.label'/></h1>

					<g:field id="username" autofocus="autofocus" type="text" name="username" placeholder="${message(code: 'login.field.username')}" class="input pass" />

					<g:field id="password" type="password" required="required" name="password" placeholder="${message(code: 'login.field.password')}" class="input pass" />

					<g:submitButton id="submit" name="SignIn" value="${message(code: 'login.button.signin')}" class="inputButton"/>

					<div class="text-center">
						<g:link controller="user" action="register">
							<g:message code='login.link.register'/>
						</g:link>
					</div>
				</form>
				<g:if test='${flash.message}'>
					<div class="alert alert-danger top-margin-50">${flash.message}</div>
				</g:if>
			</div>
		</div>
	</div>
</body>
</html>
