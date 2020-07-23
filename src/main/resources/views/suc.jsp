<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="./assets/main.css">
</head>
<body>
<h1>success jsp</h1>
<h3>
    id: ${customer.id},info:${info}
    <br>
    session id: ${sessionScope.customer.id}
</h3>
</body>
</html>