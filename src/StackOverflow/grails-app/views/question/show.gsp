<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title>SegFault - ${this.question.title}</title>

        <asset:stylesheet src="qa-styles.css"/>
        <asset:stylesheet src="font-awesome.min.css"/>
    </head>
    <body>
        <a href="#show-question" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>

        <div class="qa-main">
            <h1>
                <g:link action="show" id="${this.question.id}">
                    <span class="entry-title">${this.question.title}</span>
                </g:link>
            </h1>

            <div class="qa-part-q-view">
                <div class="qa-q-view  hentry question" id="question_${this.question.id}">
                    <g:form controller="vote" action="performVote">
                        <div class="qa-q-view-stats">
                            <div class="qa-voting qa-voting-net" id="voting_${this.question.id}">
                                <div class="qa-vote-buttons qa-vote-buttons-net">
                                    <g:submitButton title="${message (code: 'question.show.voteUpTooltip')}" name="vote_up_question_${this.question.id}" value="+" class="qa-vote-first-button qa-vote-up-button" />
                                    <g:submitButton title="${message (code: 'question.show.voteDownTooltip')}" name="vote_down_question_${this.question.id}" value="-" class="qa-vote-second-button qa-vote-down-button" />
                                </div>
                                <div class="qa-vote-count qa-vote-count-net">
                                    <span class="qa-netvote-count">
                                        <span class="qa-netvote-count-data">${this.question.votes.size()}</span>
                                        <span class="qa-netvote-count-pad"> <g:if test="${this.question.votes.size() > 1}"><g:message code="question.show.votes" /></g:if><g:else><g:message code="question.show.vote" /></g:else></span>
                                    </span>
                                </div>
                                <div class="qa-vote-clear">
                                </div>
                            </div>
                            <span class="qa-view-count">
                                <span class="qa-view-count-data">${this.question.nbViews}</span>
                                <span class="qa-view-count-pad"> <g:if test="${this.question.nbViews > 1}"><g:message code="question.show.views" /></g:if><g:else><g:message code="question.show.view" /></g:else></span>
                            </span>
                        </div>
                    </g:form>

                    <div class="qa-q-view-main">
                        <g:form controller="answer" action="create">
                            <span class="qa-q-view-avatar-meta">
                                <span class="qa-q-view-avatar">
                                    <g:link controller="user" action="show" id="${this.question.user.id}" class="qa-avatar-link">
                                        <asset:image src="avatar.jpg" width="50" height="50" class="qa-avatar-image" alt=""/>
                                    </g:link>
                                </span>
                                <span class="qa-q-view-meta">
                                    <g:link action="show" id="${this.question.id}" class="qa-q-view-what"><g:message code="question.show.Asked" /></g:link>
                                    <span class="qa-q-view-when">
                                        <span class="qa-q-view-when-data">
                                            <span class="published updated">
                                                <span class="value-title"><g:formatDate date="${this.question.dateCreated}" type="datetime" style="MEDIUM"/></span>
                                            </span>
                                        </span>
                                    </span>
                                    <span class="qa-q-view-who">
                                        <span class="qa-q-view-who-pad"><g:message code="question.show.by" /> </span>
                                        <span class="qa-q-view-who-data">
                                            <span class="vcard author">
                                                <g:link controller="user" action="show" id="${this.question.user.id}" class="qa-user-link url fn entry-title nickname">${this.question.user.username}</g:link>
                                            </span>
                                        </span>
                                    </span>
                                </span>
                            </span>

                            <div class="qa-q-view-content">
                                <div class="entry-content">
                                    ${this.question.message}
                                </div>
                            </div>

                            <div class="qa-q-view-extra">
                            </div>

                            <div class="qa-q-view-tags">
                                <ul class="qa-q-view-tag-list">
                                    <g:each in = "${this.question.tags}" var = "tag">
                                        <li class="qa-q-view-tag-item">
                                            <g:link controller="tagValue" action="show" id="${tag.tag.id}" class="qa-tag-link">${tag.tag.tagName}</g:link>
                                        </li>
                                    </g:each>
                                </ul>
                            </div>

                            <div class="qa-q-view-buttons">
                                <i class="fa fa-comment" aria-hidden="true"></i>
                                <g:submitButton id="doanswer_question" name="doanswer_question_${this.question.id}" title="${message (code: 'question.show.addAnswerTooltip')}" value="${message (code: 'question.show.answer')}" class="qa-form-light-button qa-form-light-button-answer" />
                            </div>
                        </g:form>
                    </div> <!-- End qa-q-view-main -->

                </div>  <!-- End qa-q-view -->
            </div>  <!-- End qa-part-q-view -->

            <div class="qa-part-a-form">
                <!--<div class="qa-a-form" id="anew" style="display:none;">
                                <h2>Please <a href="">log in</a> or <a href="">register</a> to answer this question.</h2>
                            </div>--> <!-- END qa-a-form -->
            </div> <!-- End qa-part-a-form -->

            <div class="qa-part-a-list">
            <h2 id="a_list_title">${this.question.answers.size()} <g:if test="${this.question.answers.size() > 1}"><g:message code="question.show.Answers" /></g:if><g:else><g:message code="question.show.Answer" /></g:else></h2>
            <div class="qa-a-list" id="a_list">
                <g:each in = "${this.question.answers}" var = "answer">
                    <div class="qa-a-list-item  hentry answer" id="answer_${answer.id}">

                        <g:form controller="vote" action="performVote">
                            <div class="qa-voting qa-voting-net" id="voting_${answer.id}">
                                <div class="qa-vote-buttons qa-vote-buttons-net">
                                    <g:submitButton title="${message (code: 'question.show.voteUpTooltip')}" name="vote_up_question_${this.question.id}_answer_${answer.id}" value="+" class="qa-vote-first-button qa-vote-up-button" />
                                    <g:submitButton title="${message (code: 'question.show.voteDownTooltip')}" name="vote_down_question_${this.question.id}_answer_${answer.id}" value="-" class="qa-vote-second-button qa-vote-down-button" />
                                </div>
                                <div class="qa-vote-count qa-vote-count-net">
                                    <span class="qa-netvote-count">
                                        <span class="qa-netvote-count-data">0<span class="votes-up">
                                            <span class="value-title"></span>
                                        </span>
                                            <span class="votes-down"><span class="value-title"></span>
                                            </span>
                                        </span>
                                        <span class="qa-netvote-count-pad"> <g:if test="${answer.votes.size() > 1}"><g:message code="question.show.votes" /></g:if><g:else><g:message code="question.show.vote" /></g:else></span>
                                    </span>
                                </div>
                                <div class="qa-vote-clear">
                                </div>
                            </div>
                        </g:form>

                        <div class="qa-a-item-main">
                            <span class="qa-a-item-avatar-meta">
                                <span class="qa-a-item-avatar">
                                    <g:link controller="user" action="show" id="${answer.user.id}" class="qa-avatar-link">
                                        <asset:image src="avatar.jpg" width="40" height="40" class="qa-avatar-image" alt=""/>
                                    </g:link>
                                </span>
                                <span class="qa-a-item-meta">
                                    <g:link controller="answer" action="show" id="${answer.id}" class="qa-a-item-what">
                                        <g:message code="question.show.Answered" />
                                    </g:link>
                                    <span class="qa-a-item-when">
                                        <span class="qa-a-item-when-data">
                                            <span class="published updated">
                                                <span class="value-title"><g:formatDate date="${answer.dateCreated}" type="datetime" style="MEDIUM"/></span>
                                            </span>
                                        </span>
                                    </span>
                                    <span class="qa-a-item-who">
                                        <span class="qa-a-item-who-pad"><g:message code="question.show.by" /> </span>
                                        <span class="qa-a-item-who-data">
                                            <span class="vcard author">
                                                <g:link controller="user" action="show" id="${answer.user.id}" class="qa-user-link url fn entry-title nickname">${answer.user.username}</g:link>
                                            </span>
                                        </span>
                                    </span>
                                </span>
                            </span>

                            <g:form controller="comment" action="create">
                                <div class="qa-a-selection">
                                </div>
                                <div class="qa-a-item-content">
                                    <div class="entry-content">
                                        ${answer.message}
                                    </div>
                                </div>
                                <div class="qa-a-item-buttons">
                                    <i class="fa fa-comment-o" aria-hidden="true"></i>
                                    <g:submitButton name="answer_${answer.id}_docomment" title="${message (code: 'question.show.addCommentTooltip')}" value="${message (code: 'question.show.comment')}" class="qa-form-light-button qa-form-light-button-comment" />
                                </div>

                                <div class="qa-a-item-c-list" id="comment_${answer.id}_list">
                                    <g:each in = "${answer.comments}" var = "comment">
                                        <div class="qa-c-list-item  hentry comment" id="comment_${comment.id}">
                                            <span class="qa-c-item-avatar-meta">
                                                <span class="qa-c-item-avatar">
                                                    <g:link controller="user" action="show" id="${comment.user.id}" class="qa-avatar-link">
                                                        <asset:image src="avatar.jpg" width="20" height="20" class="qa-avatar-image" alt=""/>
                                                    </g:link>
                                                </span>
                                                <span class="qa-c-item-meta">
                                                    <g:link controller="comment" action="show" id="${comment.id}" class="qa-c-item-what">
                                                        <g:message code="question.show.Commented" />
                                                    </g:link>
                                                    <span class="qa-c-item-when">
                                                        <span class="qa-c-item-when-data">
                                                            <span class="published updated">
                                                                <span class="value-title"><g:formatDate date="${comment.dateCreated}" type="datetime" style="MEDIUM"/></span>
                                                            </span>
                                                        </span>
                                                    </span>
                                                    <span class="qa-c-item-who">
                                                        <span class="qa-c-item-who-pad"><g:message code="question.show.by" /> </span>
                                                        <span class="qa-c-item-who-data">
                                                            <span class="vcard author">
                                                                <g:link controller="user" action="show" id="${comment.user.id}" class="qa-user-link url fn entry-title nickname">${comment.user.username}</g:link>
                                                            </span>
                                                        </span>
                                                    </span>
                                                </span>
                                            </span>
                                            <div class="qa-c-item-content">
                                                <div class="entry-content">${comment.message}</div>
                                            </div>
                                            <div class="qa-c-item-clear">
                                            </div>
                                        </div> <!-- END qa-c-item -->
                                    </g:each>
                                </div> <!-- END qa-c-list -->
                            </g:form>

                        </div> <!-- END qa-a-item-main -->
                        <div class="qa-a-item-clear">
                        </div>
                    </div> <!-- END qa-a-list-item -->
                </g:each>
            </div>
        </div> <!-- End qa-part-a-list -->
        </div> <!-- End qa-main -->
    </body>
</html>
