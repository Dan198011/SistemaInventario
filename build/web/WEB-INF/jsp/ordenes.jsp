<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Órdenes</title></head>
<body>
<h2>Órdenes de Producción</h2>
<p><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></p>
<table border="1">
    <tr><th>ID</th><th>Código</th><th>Descripción</th><th>Fecha Inicio</th><th>Fecha Entrega</th><th>Estado</th></tr>
    <c:forEach var="o" items="${ordenes}">
        <tr>
            <td>${o.id}</td>
            <td>${o.codigo}</td>
            <td>${o.descripcion}</td>
            <td>${o.fechaInicio}</td>
            <td>${o.fechaEntrega}</td>
            <td>${o.estado}</td>
        </tr>
    </c:forEach>
</table>

<h3>Crear Orden</h3>
<form action="${pageContext.request.contextPath}/ordenes" method="post">
    <label>Código: <input type="text" name="codigo" required></label>
    <label>Descripción: <input type="text" name="descripcion"></label>
    <label>Fecha Inicio: <input type="date" name="fechaInicio"></label>
    <label>Fecha Entrega: <input type="date" name="fechaEntrega"></label>
    <button type="submit">Crear</button>
</form>
</body>
</html>
