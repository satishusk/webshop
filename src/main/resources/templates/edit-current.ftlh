<!doctype html>
<html lang="en">
<head>
  <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>Edit</h4>
<form action="/users/edit" method="post" enctype="multipart/form-data">
  Username: <input type="text" name="username" value="${user.username}"><br><br>
  Phone number: <input type="text" name="phoneNumber" value="${user.phoneNumber}"><br><br>
  Avatar: <input type="file" name="avatarFile" value="${user.avatar}"><br><br>
  Email: <input type="email" name="email" value="${user.email}"><br><br>
  Password: <input type="password" name="password"><br><br>
  <input type="hidden" name="_csrf" value="${_csrf.token}">
  <input type="submit" value="Edit">
</form>
</body>
</html>