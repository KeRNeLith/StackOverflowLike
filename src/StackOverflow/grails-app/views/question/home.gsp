<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>SegFault</title>
    </head>
    <body>
        <div>
            <h1>Most recent questions</h1>
            <ul>
                <g:each in = "${this.recents}" var = "question">
                    <li>
                        <g:link controller="question" action="display" id="${question.id}">${question.title}</g:link>
                    </li>
                </g:each>
            </ul>
        </div>

        <div>
            <h1>Questions by Tag</h1>
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