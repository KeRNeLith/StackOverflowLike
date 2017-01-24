<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>

        <asset:stylesheet src="registration.css"/>
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
                    <h1>Create a new account</h1>
                    <f:field bean="user" property="username" class="input pass"/>
                    <f:field bean="user" property="password" class="input pass"/>
                    <g:submitButton name="create" class="inputButton" value="Create an account" />
                </g:form>
            </div>
        </div>
    </body>
</html>
