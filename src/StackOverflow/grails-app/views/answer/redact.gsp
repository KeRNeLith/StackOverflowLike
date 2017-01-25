<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
        <title><g:message code="answer.redact.title"/> - SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <div class="col-md-8">
            <h3>
                <g:message code="answer.redact.write.title"/>
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

                <g:submitButton title="${message(code: 'answer.send.button.tooltip')}" name="send" value="${message(code: 'answer.send.button.label')}" class="btn btn-success" />
                <g:hiddenField name="question" value="${this.answerTo}" />
            </g:form>
        </div>
    </body>
</html>
