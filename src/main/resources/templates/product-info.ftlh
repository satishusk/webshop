<!doctype html>
<html>
<head>
    <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>Product info</h4>
<#list images as img>
    <img src="/images/${img.id}" height="300px" alt="Nothing">
</#list><br>
<b>Name: </b>${product.title}<br>
<b>Description: </b>${product.description}<br>
<b>Price: </b>${product.price}<br>
<b>City: </b>${product.city}<br>
<b>Author: </b>${product.user.username}<br>
<b>Telephone: </b>${product.user.phoneNumber}<br>
<hr>
</body>
</html>