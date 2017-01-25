<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
        <title><g:message code="comment.redact.title"/> - SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <div class="col-md-8">
            <h3>
                <g:message code="comment.redact.write.title"/>
            </h3>
            <g:hasErrors bean="${this.comment}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.comment}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <g:form action="addComment">
                <f:field bean="comment" property="message">
                    <g:textArea name="message" rows="4" class="form-control"/>
                </f:field>
                <br>

                <g:submitButton title="${message(code: 'comment.send.button.tooltip')}" name="send" value="${message(code: 'comment.send.button.label')}" class="btn btn-success" />
                <g:hiddenField name="answer" value="${this.commentTo}" />
            </g:form>
        </div>
    </body>
</html>
