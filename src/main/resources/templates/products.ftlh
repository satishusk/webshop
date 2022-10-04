<!doctype html>
<html>
<head>
  <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<#if authentication.authorise>
  <h3>User: <i>${authentication.username}</i></h3>
  <form action="/users/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Logout"/>
  </form>
    <#if isAdmin>
      <a href="/users/admin">Admin panel</a>
    </#if>
<#else>
  <a href="/users/login">Login</a>
</#if>
<hr>
<h4>List of products</h4>
<#list products as product>
  <div>
    <p><b>${product.title}</b> - ${product.price} rub. | <a href="/products/${product.id}">More</a> </p>
  </div>
<#else>
  <h3>Products not found</h3>
</#list>
<#if authentication.authorise>
  <hr>
  <h3>Create new product</h3>
  <form action="/products/add" method="post" enctype="multipart/form-data">
    Name: <input type="text" name="title"><br><br>
    Description: <input type="text" name="description"><br><br>
    Price: <input type="number" name="price"><br><br>
    City: <input type="text" name="city"><br><br>
    Images: <input type=file name="imageFiles" multiple="multiple"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Add product"/>
  </form>
</#if>
</body>
</html>