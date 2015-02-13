<%@ page import="ru.javawebinar.webapp.web.HtmlUtil" %>
<%@ page import="ru.javawebinar.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <STYLE type="text/css">
        th, td {
            padding: 4px 10px 4px 0;
            vertical-align: top;
        }

        tr {
            border-bottom: 1px solid #DDDDDD;
        }

        table {
            margin-bottom: 1.4em;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
        }
    </STYLE>
    <jsp:useBean id="resume" type="ru.javawebinar.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}</h2>
    <c:if test="${not empty  resume.homePage}">
    Домашняя старница: ${resume.homePage}<br>
    </c:if>
    <c:if test="${not empty  resume.location}">
    Проживание: ${resume.location}
    </c:if>
    <p>
        <c:forEach var="type" items="${resume.contacts.keySet()}">
            <jsp:useBean id="type" type="ru.javawebinar.webapp.model.ContactType"/>
                <%=HtmlUtil.getContact(resume, type)%><br>
        </c:forEach>

    <p>
    <table cellpadding="8">
        <c:forEach var="entry" items="${resume.sections.entrySet()}">
            <jsp:useBean id="entry" type="java.util.Map.Entry"/>
                <%
                SectionType sectionType = ((SectionType)entry.getKey());
                Section section =  ((Section)entry.getValue());
            %>

        <tr>
            <td><h3><a name="<%=sectionType.name()%>"><%=sectionType.getTitle()%></a></h3></td>
            <%
                switch (sectionType) {
                    case OBJECTIVE:
            %>
            <td><h3><%=((TextSection) entry.getValue()).getValue()%></h3></td>
        </tr>
                <%
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
        %>
        <td>
            <ul>
                <c:forEach var="item" items="<%=((MultiTextSection) entry.getValue()).getValues()%>">
                    <li>${item}</li>
                </c:forEach>
            </ul>
        </td>
        </tr>
                <% break;
            case EXPERIENCE:
            case EDUCATION:
        %>

            <%
                }
            %>
        </c:forEach>
</table>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
