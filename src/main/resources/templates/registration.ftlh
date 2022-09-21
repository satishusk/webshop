<!doctype html>
<html lang="en">
<head>
    <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>Registration</h4>
<form action="/users/registration" method="post" enctype="multipart/form-data">
    Username: <input type="text" name="username"><br><br>
    Phone number: <input type="text" name="phoneNumber"><br><br>
    Avatar: <input type="file" name="avatarFile"><br><br>
    Email: <input type="email" name="email"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Create">
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>