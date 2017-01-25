<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="user.profile.display.title" args="[user.username]" /> - SegFault</title>
    </head>
    <body>
        <h1><g:message code="user.profile.display.general" args="[user.username, user.reputation]" /></h1>
        <sec:ifLoggedIn>
            <g:form action="updateProfile" resource="${this.user}" method="PUT">
                <g:hiddenField name="id" value="${user.id}"/>
                <g:hiddenField name="version" value="${user.version}"/>
                <div class="panel panel-primary top-margin-50">
                    <div class="panel-heading">
                        <h3><g:message code="user.profile.updateInfo.title" /></h3>
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
                                <label><g:message code="user.profile.update.username" /></label> <br/>
                                <label><g:message code="user.profile.update.password" /></label> <br/>
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
                                    <g:message code="user.profile.update.button" />
                                </strong>
                            </button>
                        </div>
                    </div>
                </div>
            </g:form>
        </sec:ifLoggedIn>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3><g:message code="user.profile.answers.title"/></h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="text-center"><g:message code="user.profile.questions.votes.title"/></th>
                        <th class="text-center"><g:message code="user.profile.questions.question.title"/></th>
                        <th class="text-center"><g:message code="user.profile.questions.tags.title"/></th>
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
                <h3><g:message code="user.profile.answers.title"/></h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th class="text-center"><g:message code="user.profile.answers.votes.title"/></th>
                            <th class="text-center"><g:message code="user.profile.answers.question.title"/></th>
                            <th class="text-center"><g:message code="user.profile.answers.tags.title"/></th>
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
                <h3><g:message code="user.profile.badges.title"/></h3>
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
