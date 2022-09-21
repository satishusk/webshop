<!doctype html>
<html>
<head>
    <title>WebShop</title>
</head>
<body>
<h1>WebShop</h1><hr>
<h4>Admin panel</h4>

<table>
    <tr>
        <th>Email</th>
        <th>Phone number</th>
        <th>Active</th>
        <th>Roles</th>
        <th>Ban</th>
        <th>Edit</th>
        <th>More</th>
    </tr>
    <#list users as user>
        <tr>
            <th>${user.email}</th>
            <th>${user.phoneNumber}</th>
            <th>${user.active?string}</th>
            <th><#list user.roles as role>${role.name}<#sep>, </#list></th>
            <th>
                <#if user.active>
                    <form action="/users/admin/deactivate/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Ban">
                    </form>
                <#else>
                    <form action="/users/admin/activate/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Unban">
                    </form>
                </#if>
            </th>
            <th><a href="/users/admin/edit/${user.id}">Edit</a></th>
            <th><a href="/users/admin/${user.id}">More</a></th>
        </tr>
    <#else>
        <h3>Users not found</h3>
    </#list>
</table>

</body>
</html>