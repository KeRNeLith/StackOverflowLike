<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="question.redactEdit.title"/> - SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <div id="edit-question" class="content scaffold-edit" role="main">
            <h1><g:message code="question.redactEdit.edit.title"/></h1>
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
            <g:form action="updateEdit" resource="${this.question}" method="PUT">
                <g:hiddenField name="version" value="${this.question?.version}" />

                <f:field bean="question" property="title">
                    <g:textField name="title" value="${this.question.title}"/>
                </f:field>
                <f:field bean="question" property="message">
                    <g:textArea name="message" rows="10" value="${this.question.message}" class="form-control"/>
                </f:field>

                <h4><g:message code="question.redact.tags"/></h4>
                <g:select name="selectedTags" from="${this.tags}" value="${this.question.tags.tag}" optionKey="id" optionValue="tagName" multiple="true"/>

                <g:submitButton title="${message(code: 'question.redactEdit.button.tooltip')}" name="edit" value="${message(code: 'question.redactEdit.button.label')}" class="btn btn-success" />
            </g:form>
        </div>
    </body>
</html>
