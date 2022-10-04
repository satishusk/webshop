<!doctype html>
<html>
<head>
    <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>Authorize</h4>
<form action="/users/login" method="post">
    Username: <input type="text" name="username"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Login">
</form>
<br>
<#if errorMessage??><div style="color: red">${errorMessage}</div><br></#if>
<a href="/users/registration">Registration</a>
</body>
</html>