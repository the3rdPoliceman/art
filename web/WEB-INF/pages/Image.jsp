<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link href="./style/layout.css" type="text/css">
</head>
<body>
<h1>Image ${imagePath}</h1>
<c:out value="${imageSVG}" escapeXml="false"/>
</body>
</html>
