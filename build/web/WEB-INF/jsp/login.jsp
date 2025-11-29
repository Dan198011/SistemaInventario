<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Sistema Inventario</title>
    <style>body{font-family:Arial;padding:20px;}label{display:block;margin-top:8px}</style>
</head>
<body>
<h2>Iniciar sesión</h2>
<c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Usuario: <input type="text" name="username" required></label>
    <label>Contraseña: <input type="password" name="password" required></label>
    <button type="submit">Ingresar</button>
</form>
</body>
</html>
