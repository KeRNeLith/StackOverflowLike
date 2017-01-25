<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
        <title><g:message code="answer.redactEdit.title"/> - SegFault</title>
    </head>
    <body>
        <div id="edit-answer" class="content scaffold-edit" role="main">
            <h1><g:message code="answer.redactEdit.edit.title"/></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.answer}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.answer}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="updateEdit" resource="${this.answer}" method="PUT">
                <g:hiddenField name="version" value="${this.answer?.version}" />

                <f:field bean="answer" property="message">
                    <g:textArea name="message" rows="6" value="${this.answer.message}" class="form-control"/>
                </f:field>

                <g:submitButton title="${message(code: 'answer.sendEdit.button.tooltip')}" name="edit" value="${message(code: 'answer.sendEdit.button.label')}" class="btn btn-success" />
            </g:form>
        </div>
    </body>
</html>
