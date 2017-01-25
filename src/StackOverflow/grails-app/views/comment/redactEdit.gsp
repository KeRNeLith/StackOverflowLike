<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
        <title><g:message code="comment.redactEdit.title"/> - SegFault</title>
    </head>
    <body>
        <div id="edit-comment" class="content scaffold-edit" role="main">
            <h1><g:message code="comment.redactEdit.edit.title"/></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.comment}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.comment}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="updateEdit" resource="${this.comment}" method="PUT">
                <g:hiddenField name="version" value="${this.comment?.version}" />
                <f:field bean="comment" property="message">
                    <g:textArea name="message" rows="4" value="${this.comment.message}" class="form-control"/>
                </f:field>

                <g:submitButton title="${message(code: 'comment.sendEdit.button.tooltip')}" name="edit" value="${message(code: 'comment.sendEdit.button.label')}" class="btn btn-success" />
            </g:form>
        </div>
    </body>
</html>
