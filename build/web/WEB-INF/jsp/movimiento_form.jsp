<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Registrar Movimiento</title></head>
<body>
<h2>Registrar Movimiento</h2>
<form action="${pageContext.request.contextPath}/movimiento" method="post">
    <label>Material ID: <input type="number" name="materialId" required></label>
    <label>Tipo:
        <select name="tipo">
            <option value="ENTRADA">ENTRADA</option>
            <option value="SALIDA">SALIDA</option>
        </select>
    </label>
    <label>Cantidad: <input type="number" step="0.01" name="cantidad" required></label>
    <label>Proveedor: <input type="text" name="proveedor"></label>
    <label>Lote: <input type="text" name="lote"></label>
    <label>Referencia / Orden ID (si aplica): <input type="text" name="ordenId"></label>
    <label>Comentario / referencia: <input type="text" name="referencia"></label>
    <button type="submit">Registrar</button>
</form>
</body>
</html>
