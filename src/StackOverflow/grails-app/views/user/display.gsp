<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <h1>Profil of: ${user.username} (Reputation: ${user.reputation})</h1>
        <sec:ifLoggedIn>
            <g:form action="updateProfile" resource="${this.user}" method="PUT">
                <g:hiddenField name="id" value="${user.id}"/>
                <g:hiddenField name="version" value="${user.version}"/>
                <div class="panel panel-primary top-margin-50">
                    <div class="panel-heading">
                        <h3>Update user profile information</h3>
                    </div>
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
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-4" style="display: inline-block">
                                <label>Enter a new username:</label> <br/>
                                <label>Enter a new password:</label> <br/>
                            </div>
                            <div class="col-md-6" style="display: inline-block">
                                <g:field type="text" name="username" placeholder="${user.username}"/>
                                <br />
                                <g:field type="password" name="password"/>
                                <br />
                            </div>
                        </div>
                        <hr/>
                        <div class="row text-center">

                            <button type="submit" class="btn btn-warning text-center">
                                <strong>
                                    ${message(code: 'user.update.button', default: 'Update the user')}
                                </strong>
                            </button>
                        </div>
                    </div>
                </div>
            </g:form>
        </sec:ifLoggedIn>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3>Questions asked</h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="text-center">Votes</th>
                        <th class="text-center">Questions</th>
                        <th class="text-center">Tags</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${user.questions}" var="question">
                        <tr class="text-center">
                            <td>
                                ${question.votes.size()}
                            </td>
                            <td>
                                <g:link controller="question" action="display" id="${question.id}">
                                    ${question.title}
                                </g:link>
                            </td>
                            <td>
                                <g:each in="${question.tags}" var="tag">
                                    <g:link controller="tag" action="show" id="${tag.id}">
                                        <div class="label label-default right-margin-10">
                                            ${tag.tag.tagName}
                                        </div>
                                    </g:link>
                                </g:each>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">
                <h3>Answers</h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="text-center">Votes answers</th>
                        <th class="text-center">Question</th>
                        <th class="text-center">Tags</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${user.answers}" var="answer">
                        <tr class="text-center">
                            <td>${answer.votes.size()}</td>
                            <td>
                                <g:link controller="question" action="display" id="${answer.question.id}">
                                    ${answer.question.title}
                                </g:link>
                            </td>
                            <td>
                                <g:each in="${answer.question.tags}" var="tag">
                                    <g:link controller="tag" action="show" id="${tag.id}">
                                        <div class="label label-default right-margin-10">
                                            ${tag.tag.tagName}
                                        </div>
                                    </g:link>
                                </g:each>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">
                <h3>Badges</h3>
            </div>
            <div class="panel-body">
                <g:each in="${badges}" var="badgeAssoc">
                    <g:set var="badge" value="${badgeAssoc.key}"/>
                    <g:set var="count" value="${badgeAssoc.value}"/>
                    <span class="label label-success right-margin-10">
                        ${badge.name}
                    </span>
                     x${count}
                </g:each>
            </div>
        </div>
    </body>
</html>
