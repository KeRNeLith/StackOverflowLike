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
            <ul>
                <g:each in = "${this.recents}" var = "question">
                    <li>
                        <g:link controller="question" action="display" id="${question.id}">${question.title}</g:link>
                    </li>
                </g:each>
            </ul>
        </div>

        <div>
            <h1><g:message code="question.home.byCat.title"/></h1>
            <g:each in = "${this.questionsByCat}" var = "questionAssoc">
                <g:set var="tagName" value="${questionAssoc.key}"/>
                <g:set var="questions" value="${questionAssoc.value}"/>
                <h2>${tagName}</h2>
                <ul>
                    <g:each in = "${this.questions}" var = "question">
                        <li>
                            <g:link controller="question" action="display" id="${question.id}">${question.title}</g:link>
                        </li>
                    </g:each>
                </ul>
            </g:each>
        </div>
    </body>
</html>