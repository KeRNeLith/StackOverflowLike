<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
        <title>SegFault</title>

        <asset:stylesheet src="answer/redact/bootstrap.min.css"/>
    </head>
    <body>
        <a href="#create-comment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>

        <div class="col-md-8">
            <h3>
                Write your comment
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

                <g:submitButton title="Send your comment" name="send" value="Send" class="btn btn-success" />
                <g:hiddenField name="answer" value="${this.commentTo}" />
            </g:form>
        </div>
    </body>
</html>
