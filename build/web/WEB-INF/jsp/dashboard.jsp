<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Dashboard</title></head>
<body>
<h2>Dashboard</h2>
<p>
    <a href="${pageContext.request.contextPath}/materiales">Materiales</a> |
    <a href="${pageContext.request.contextPath}/movimiento">Registrar movimiento</a> |
    <a href="${pageContext.request.contextPath}/ordenes">Órdenes</a> |
    <a href="${pageContext.request.contextPath}/reporte/inventario.xlsx">Exportar inventario</a>
</p>

<h3>Alertas - Bajo mínimo</h3>
<table border="1">
    <tr><th>ID</th><th>Tipo</th><th>Existencia</th><th>Mínimo</th></tr>
    <c:forEach var="m" items="${bajoMinimo}">
        <tr>
            <td>${m.id}</td>
            <td>${m.tipo}</td>
            <td>${m.existencia} ${m.unidad}</td>
            <td>${m.minimo}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
