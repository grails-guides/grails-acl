<html>
<head>
    <title><g:message code="player.index.title" default="Players"/></title>
    <meta name="layout" content="main" />
</head>
<body>
    <g:form controller="logout" action="index">
        <input type="submit" value="${g.message(code: 'logout', default: 'Logout')}"/>
    </g:form>
    <g:if test="${playerList}">
        <ul>
            <g:each var="player" in="${playerList}">
                <li>${player.name}</li>
            </g:each>
        </ul>
    </g:if>
    <g:form controller="player" action="save">
        <g:textField name="name" />
        <input type="submit" value="${g.message(code: 'player.save', default: 'Save')}"/>
    </g:form>
</body>
</html>