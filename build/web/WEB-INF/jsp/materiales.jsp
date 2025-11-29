<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Materiales</title></head>
<body>
<h2>Materiales</h2>
<p><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></p>
<table border="1">
    <tr><th>ID</th><th>Tipo</th><th>Descripcion</th><th>Existencia</th><th>Minimo</th><th>Unidad</th><th>Lote</th></tr>
    <c:forEach var="m" items="${materiales}">
        <tr>
            <td>${m.id}</td>
            <td>${m.tipo}</td>
            <td>${m.descripcion}</td>
            <td>${m.existencia}</td>
            <td>${m.minimo}</td>
            <td>${m.unidad}</td>
            <td>${m.lote}</td>
        </tr>
    </c:forEach>
</table>

<h3>Agregar material</h3>
<form action="${pageContext.request.contextPath}/materiales" method="post">
    <label>Tipo: <input type="text" name="tipo" required></label>
    <label>Descripción: <input type="text" name="descripcion"></label>
    <label>Existencia: <input type="number" step="0.01" name="existencia" value="0"></label>
    <label>Mínimo: <input type="number" step="0.01" name="minimo" value="1"></label>
    <label>Unidad: <input type="text" name="unidad" value="kg"></label>
    <label>Lote: <input type="text" name="lote"></label>
    <button type="submit">Agregar</button>
</form>
</body>
</html>
