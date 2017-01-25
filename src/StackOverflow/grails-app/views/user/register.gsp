<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="user.register.title"/> - SegFault</title>

        <asset:stylesheet src="user/registration.css"/>
    </head>
    <body>
        <div class="container top-margin-50">
            <div id="registerDiv" class="top-margin-50">
                <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.user}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.user}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
                </g:hasErrors>
                <g:form action="registerSave">
                    <h1><g:message code="user.register.createAccount.title"/></h1>
                    <f:field bean="user" property="username" placeholder="${message(code: 'register.field.username')}" class="input pass"/>
                    <f:field bean="user" property="password" placeholder="${message(code: 'register.field.password')}" class="input pass"/>
                    <g:submitButton name="create" class="inputButton" value="${message(code: 'user.register.button.label')}" />
                </g:form>
            </div>
        </div>
    </body>
</html>
