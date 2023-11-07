<!DOCTYPE html>
<html>
<head>
    <title>QUICKbuy</title>
</head>
<body>
<h1>QUICKbuy</h1><hr>
<#if message??>
    <h2 style = "color: red">${message}</h2>
</#if>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>