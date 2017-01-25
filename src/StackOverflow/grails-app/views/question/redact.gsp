<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="question.redact.title"/> - SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <div id="create-question" class="content scaffold-create" role="main">
            <h1><g:message code="question.redact.write.title"/></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.question}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.question}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="addQuestion">
                <f:field bean="question" property="title">
                    <g:textField name="title" />
                </f:field>
                <f:field bean="question" property="message">
                    <g:textArea name="message" rows="10" class="form-control"/>
                </f:field>

                <h4><g:message code="question.redact.tags"/></h4>
                <g:select name="tags" from="${this.tags}" optionKey="id" optionValue="tagName" multiple="true"/>

                <g:submitButton title="${message(code: 'question.redact.button.tooltip')}" name="send" value="${message(code: 'question.redact.button.label')}" class="btn btn-success" />
            </g:form>
        </div>
    </body>
</html>
