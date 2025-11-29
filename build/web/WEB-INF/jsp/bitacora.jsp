<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Bitacora</title></head>
<body>
<h2>Bitácora</h2>
<table border="1">
    <tr><th>ID</th><th>Usuario</th><th>Acción</th><th>Fecha</th></tr>
    <!-- Debes implementar un servlet/DAO que ponga la lista en request attribute "bitacora" -->
    <c:forEach var="b" items="${bitacora}">
        <tr>
            <td>${b.id}</td>
            <td>${b.usuarioId}</td>
            <td>${b.accion}</td>
            <td>${b.fecha}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
