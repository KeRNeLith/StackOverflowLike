<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
        <title>SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <a href="#create-answer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>

        <div class="col-md-8">
            <h3>
                Write your response
            </h3>
            <g:hasErrors bean="${this.answer}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.answer}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <g:form action="addAnswer">
                <f:field bean="answer" property="message">
                    <g:textArea name="message" rows="6" class="form-control"/>
                </f:field>
                <br>

                <g:submitButton title="Send your response" name="send" value="Send" class="btn btn-success" />
                <g:hiddenField name="question" value="${this.answerTo}" />
            </g:form>
        </div>
    </body>
</html>
