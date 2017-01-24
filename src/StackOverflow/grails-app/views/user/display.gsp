<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <p>
            Reputation: ${user.reputation}
        </p>
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
