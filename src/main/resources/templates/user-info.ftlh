<html>
<head>
    <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>User info</h4>
<img src="/images/${user.avatar.id}" height="200px" alt="Nothing"><br>
<b>Name: </b>${user.username}<br>
<b>Email: </b>${user.email}<br>
<b>Roles: </b> <#list user.roles as role>${role.name} <#if role_has_next>, </#if></#list><br>
<b>Telephone: </b>${user.phoneNumber}<br>
<b>Products: </b> <#list user.products as product>
    <div>
        ${product_index + 1}) ${product.title} - ${product.price} rub. | <a href="/products/${product.id}">More</a><br>
    </div>
</#list><br>
</body>
</html>