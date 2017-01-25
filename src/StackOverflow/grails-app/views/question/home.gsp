<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>SegFault</title>
    </head>
    <body>
        <div>
            <g:form controller="question" action="redact">
                <g:submitButton class="btn btn-success" name="${message(code: 'question.ask.button')}"/>
            </g:form>
        </div>

        <div>
            <h1><g:message code="question.home.mostRecent.title"/></h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th class="text-center"><g:message code="question.home.question.title"/></th>
                        <th class="text-center"><g:message code="question.home.tags.title"/></th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in = "${this.recents}" var = "question">
                        <tr>
                            <td class="text-center">
                                <h4><g:link action="display" id="${question.id}">${question.title}</g:link></h4>
                            </td>
                            <td class="text-center">
                                <g:each in="${question.tags}" var="tag">
                                    <h4 style="display:inline-block;">
                                        <g:link controller="tag" action="show" id="${tag.tag.id}">
                                            <div class="label label-default right-margin-10">
                                                ${tag.tag.tagName}
                                            </div>
                                        </g:link>
                                    </h4>
                                </g:each>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </div>

        <div>
            <h1><g:message code="question.home.byCat.title"/></h1>
            <g:each in = "${this.questionsByCat}" var = "questionAssoc">
                <g:set var="tagName" value="${questionAssoc.key}"/>
                <g:set var="questions" value="${questionAssoc.value}"/>
                <h2>${tagName}</h2>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th class="text-center"><g:message code="question.home.question.title"/></th>
                        <th class="text-center"><g:message code="question.home.tags.title"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in = "${this.questions}" var = "question">
                        <tr>
                            <td class="text-center">
                                <h4><g:link action="display" id="${question.id}">${question.title}</g:link></h4>
                            </td>
                            <td class="text-center">
                                <g:each in="${question.tags}" var="tag">
                                    <h4 style="display:inline-block;">
                                        <g:link controller="tag" action="show" id="${tag.tag.id}">
                                            <div class="label label-default right-margin-10">
                                                ${tag.tag.tagName}
                                            </div>
                                        </g:link>
                                    </h4>
                                </g:each>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:each>
        </div>
    </body>
</html>