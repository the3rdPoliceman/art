<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <style>
        .preview {
            display: inline-block;
            padding: 50px;
            float: left;
        }

    </style>
</head>
<body>
<h1>Gallery</h1>

<c:forEach items="${fileMap}" var="entry">
<div class="preview">
    <a href="image.html?${entry.key}" title="${entry.value.description}">
    <c:out value="${entry.value.image}" escapeXml="false"/>
    </a>
    </div>
</c:forEach>
</body>
</html>
